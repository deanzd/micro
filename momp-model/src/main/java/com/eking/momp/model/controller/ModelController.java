package com.eking.momp.model.controller;

import com.eking.momp.model.dto.ModelDTO;
import com.eking.momp.model.dto.ModelRelationTopoDTO;
import com.eking.momp.model.dto.ModelTopoDTO;
import com.eking.momp.model.dto.TopoDto;
import com.eking.momp.model.param.ModelParam;
import com.eking.momp.model.service.ModelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/models")
@Api(tags = "模型")
public class ModelController {
    @Autowired
    private ModelService modelService;

    @GetMapping("")
    @ApiOperation("获取模型信息列表")
    public List<ModelDTO> list(@ApiParam("搜索关键字") @RequestParam(required = false) String keyword) {
        return modelService.list(keyword);
    }

    @GetMapping("/{id}")
    @ApiOperation("获取模型信息")
    public ModelDTO getById(@ApiParam("模型ID") @PathVariable Integer id) {
        return modelService.getById(id);
    }

    @PostMapping("")
    @ApiOperation("添加模型")
//    @PreAuthorize("hasAnyRole('sysadmin', 'admin')")
    public ModelDTO save(@ApiParam("模型属性") @Validated() @RequestBody ModelParam param) {
        return modelService.save(param);
    }

    @PutMapping("/{id}")
    @ApiOperation("修改模型")
//    @PreAuthorize("hasAnyRole('sysadmin', 'admin')")
    public boolean update(
            @ApiParam("模型ID") @PathVariable Integer id,
            @ApiParam("模型属性") @RequestBody ModelParam param) {
        return modelService.update(id, param);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除模型")
//    @PreAuthorize("hasAnyRole('sysadmin', 'admin')")
    public boolean delete(@ApiParam("模型ID") @PathVariable Integer id) {
        return modelService.delete(id);
    }

    @GetMapping("/{id}/topo")
    @ApiOperation("模型拓扑")
    public TopoDto<List<ModelTopoDTO>, List<ModelRelationTopoDTO>> getModelTopo(
            @ApiParam("模型ID") @PathVariable Integer id) {
        return modelService.getModelTopo(id);
    }
}
