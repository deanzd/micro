package com.eking.momp.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MompGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(MompGatewayApplication.class);
    }
}
