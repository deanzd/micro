package com.eking.momp.gateway.filter;

import com.eking.momp.common.util.CommonUtils;
import com.eking.momp.common.util.Error;
import com.eking.momp.common.util.JwtUtils;
import com.eking.momp.common.util.URL;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtTokenFilter implements GlobalFilter, Ordered {

    private final static List<String> SKIP_AUTH_URLS = Arrays.asList("/api/auth/tokens", "/api/*/v2/api-docs");
    private final static String REQUEST_HEADER_TOKEN = "Authorization";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        //跳过不需要验证的url
        if (SKIP_AUTH_URLS.stream().anyMatch(skipUrl -> new AntPathMatcher().match(skipUrl, path))) {
            return chain.filter(exchange);
        }

        String token = exchange.getRequest().getHeaders().getFirst(REQUEST_HEADER_TOKEN);
        if (StringUtils.isEmpty(token)) {
            return this.authFaild(exchange, "请登录", HttpStatus.UNAUTHORIZED);
        }

        String username;
        String role;
        List<Map<String, String>> authUrls;
        try {
            Claims claims = JwtUtils.parseJwt(token);
            username = claims.get("username", String.class);
            role = claims.get("role", String.class);
            authUrls = claims.get("authUrls", List.class);
        } catch (ExpiredJwtException e) {
            return this.authFaild(exchange, "登录已过期，请重新登录", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return this.authFaild(exchange, "请登录", HttpStatus.UNAUTHORIZED);
        }

        String method = exchange.getRequest().getMethodValue();
        boolean hasPermission = authUrls.stream()
                .map(map -> new URL(map.get("path"), map.get("method")))
                .filter(url -> url.getMethod() == null || url.getMethod().equalsIgnoreCase(method))
                .anyMatch(url -> new AntPathMatcher().match(url.getPath(), path));
        if (hasPermission) {
            ServerHttpRequest request = exchange.getRequest().mutate()
                    .header("username", username)
                    .header("role", role)
                    .build();
            ServerWebExchange retExchange = exchange.mutate().request(request).build();
//            exchange.getRequest().getHeaders().remove(REQUEST_HEADER_TOKEN);
            return chain.filter(retExchange);
        } else {
            return this.authFaild(exchange, "您没有访问该资源的权限", HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private Mono<Void> authFaild(ServerWebExchange exchange, String msg, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes;
        try {
            bytes = objectMapper.writeValueAsBytes(Error.of("000001", msg));
        } catch (JsonProcessingException e) {
            bytes = new byte[0];
        }
        DataBuffer dataBuffer = response.bufferFactory().wrap(bytes);
        return response.writeWith(Flux.just(dataBuffer));
    }
}
