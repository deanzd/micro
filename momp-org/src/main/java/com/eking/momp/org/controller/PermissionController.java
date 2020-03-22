package com.eking.momp.org.controller;

import com.eking.momp.org.dto.PermissionDTO;
import com.eking.momp.org.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@Api(tags = "权限")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("")
    public List<PermissionDTO> list(@ApiParam("角色ID") @RequestParam(required = false) Integer roleId) {
        return permissionService.list(roleId);
    }
}
