package com.eking.momp.auth.client;

import com.eking.momp.auth.to.PermissionTO;
import com.eking.momp.auth.to.UserTO;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@FeignClient(name = "momp-org", fallback = OrgFeignClientFallback.class)
public interface OrgFeignClient {
    @GetMapping("/users")
    List<UserTO> listUsers(@ApiParam("用户名") @RequestParam(required = false) String username);

    @GetMapping("/permissions")
    List<PermissionTO> listPermissions(@ApiParam("角色ID") @RequestParam(required = false) Integer roleId);
}

@Slf4j
@Component
class OrgFeignClientFallback implements OrgFeignClient {

    @Override
    public List<UserTO> listUsers(String username) {
        log.error("OrgFeignClient.listUsers failed." + username);
        return Collections.emptyList();
    }

    @Override
    public List<PermissionTO> listPermissions(Integer roleId) {
        log.error("OrgFeignClient.listPermissions failed." + roleId);
        return Collections.emptyList();
    }
}

