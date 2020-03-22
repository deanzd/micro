package com.eking.momp.menu.client;

import com.eking.momp.menu.param.UserParam;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "momp-org", fallback = OrgFeignClientFallback.class)
public interface OrgFeignClient {
    @PostMapping("users")
    void saveUser(@ApiParam("用户属性") @RequestBody UserParam userParam);
}

@Slf4j
@Component
class OrgFeignClientFallback implements OrgFeignClient {
    @Override
    public void saveUser(UserParam userParam) {
        System.out.println(111111111);
    }
}

