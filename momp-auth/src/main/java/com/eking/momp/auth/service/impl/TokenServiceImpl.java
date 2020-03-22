package com.eking.momp.auth.service.impl;

import com.eking.momp.auth.client.OrgFeignClient;
import com.eking.momp.auth.exception.UsernameOrPasswordWrongException;
import com.eking.momp.auth.param.TokenParam;
import com.eking.momp.auth.service.TokenService;
import com.eking.momp.auth.to.PermissionTO;
import com.eking.momp.auth.to.RoleTO;
import com.eking.momp.auth.to.UserTO;
import com.eking.momp.common.util.JwtUtils;
import com.eking.momp.common.util.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class TokenServiceImpl implements TokenService {

    public static final String JWT_SUBJECT = "authorization_token";

    @Autowired
    private OrgFeignClient orgFeignClient;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String create(TokenParam param) {
        String username = param.getUsername();
        String password = param.getPassword();

        List<UserTO> users = orgFeignClient.listUsers(username);
        if (CollectionUtils.isEmpty(users)) {
            throw new UsernameOrPasswordWrongException();
        }
        UserTO user = users.get(0);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UsernameOrPasswordWrongException();
        }
        RoleTO role = user.getRole();
        List<URL> authUrls = null;
        if (role != null) {
            List<PermissionTO> permissions = orgFeignClient.listPermissions(role.getId());
            authUrls = permissions.stream()
                    .map(permission -> new URL(permission.getPath(), permission.getMethod()))
                    .collect(Collectors.toList());
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("role", role.getCode());
        claims.put("authUrls", authUrls);
        String jwt = JwtUtils.createJwt(username, JWT_SUBJECT, claims, Duration.ofHours(1));
        return jwt;
    }
}
