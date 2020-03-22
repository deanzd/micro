package com.eking.momp.org.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.eking.momp.org.dto.UserDTO;
import com.eking.momp.org.param.UserParam;
import com.eking.momp.org.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Api(tags = "用户")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    @ApiOperation("查询用户")
    public UserDTO findById(@ApiParam("用户ID") @PathVariable Integer id) {
        return userService.getById(id);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除用户")
    public boolean delete(@ApiParam("用户ID") @PathVariable Integer id) {
        return userService.delete(id);
    }

    @PostMapping("")
    @ApiOperation("添加用户")
    public UserDTO save(@ApiParam("用户属性") @RequestBody UserParam userParam) {
        return userService.save(userParam);
    }

    @GetMapping("")
    @ApiOperation("获取用户列表")
    @SentinelResource(value = "org-users-list")
    public List<UserDTO> list(@ApiParam("用户名") @RequestParam(required = false) String username) {
        return userService.list(username);
    }

    @PutMapping("/{id}")
    @ApiOperation("更新用户")
    public boolean updateUser(@ApiParam("用户ID") @PathVariable Integer id,
                              @ApiParam("用户属性") @RequestBody UserParam userParam) {
        return userService.update(id, userParam);
    }

}
