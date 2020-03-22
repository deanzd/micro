package com.eking.momp.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class GlobalSwaggerResourcesProvider implements SwaggerResourcesProvider {

    private static final String SWAGGER2_URL = "/v2/api-docs";
    public static final String PATH = "Path";


    private final GatewayProperties gatewayProperties;

    public GlobalSwaggerResourcesProvider(GatewayProperties gatewayProperties) {
        this.gatewayProperties = gatewayProperties;
    }

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        gatewayProperties.getRoutes().forEach(routeDefinition -> {
            routeDefinition.getPredicates().stream()
                    .filter(predicateDefinition -> PATH.equalsIgnoreCase(predicateDefinition.getName()))
                    .forEach(predicateDefinition -> {
                        SwaggerResource resource = this.createSwaggerResource(routeDefinition.getId(),
                                predicateDefinition.getArgs().get(NameUtils.generateName(0)).replace("/**", SWAGGER2_URL));
                        resources.add(resource);
                    });
        });
        return resources;
    }

    private SwaggerResource createSwaggerResource(String name, String url) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setUrl(url);
        swaggerResource.setName(name);
        return swaggerResource;
    }
}
