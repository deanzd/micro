package com.eking.momp.auth.controller;

import com.eking.momp.auth.param.TokenParam;
import com.eking.momp.auth.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "token")
@RestController
@RequestMapping("/tokens")
public class TokenController {
    @Autowired
    private TokenService tokenService;

    @ApiOperation("获取Token")
    @PostMapping("")
    public String create(@ApiParam("token参数") @Valid @RequestBody TokenParam tokenParam) {
        return tokenService.create(tokenParam);
    }

}
