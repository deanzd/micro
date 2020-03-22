package com.eking.momp.search.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/models")
public class ModelController {

    @PutMapping("/{code}")
    @ApiOperation("重建es索引")
    public void rebuildIndex(@ApiParam("模型code") @PathVariable(required = false) String code) {
    }

}
