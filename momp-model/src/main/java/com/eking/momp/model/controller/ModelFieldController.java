package com.eking.momp.model.controller;

import com.eking.momp.model.dto.ModelFieldDTO;
import com.eking.momp.model.param.ModelFieldParam;
import com.eking.momp.model.service.ModelFieldService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/model-fields")
@Api(tags = "模型字段")
@RestController
public class ModelFieldController {
    @Autowired
    private ModelFieldService modelFieldService;

    @GetMapping("")
    @ApiOperation("获取模型字段列表")
    public List<ModelFieldDTO> listModelFields(
            @ApiParam("模型ID") @RequestParam Integer modelId) {
        return modelFieldService.listByModelId(modelId);
    }

    @GetMapping("/{modelFieldId}")
    @ApiOperation("获取模型字段")
    public ModelFieldDTO getModelFieldById(@ApiParam("模型字段ID") @PathVariable Integer modelFieldId) {
        return modelFieldService.getById(modelFieldId);
    }

    @PostMapping("")
    @ApiOperation("添加模型字段")
//    @PreAuthorize("hasAnyRole('sysadmin', 'admin')")
    public ModelFieldDTO saveModelField(@ApiParam("模型字段属性") @RequestBody ModelFieldParam param) {
        return modelFieldService.save(param);
    }

    @PutMapping("/{modelFieldId}")
    @ApiOperation("修改模型字段")
//	@PreAuthorize("hasAnyRole('sysadmin', 'admin')")
    public boolean updateModelField(
            @ApiParam("模型字段ID") @PathVariable Integer modelFieldId,
            @ApiParam("模型字段属性") @RequestBody ModelFieldParam param) {
        return modelFieldService.update(modelFieldId, param);
    }

    @DeleteMapping("/{modelFieldId}")
    @ApiOperation("删除模型字段")
//	@PreAuthorize("hasAnyRole('sysadmin', 'admin')")
    public boolean deleteModelField(@ApiParam("模型字段ID") @PathVariable Integer modelFieldId) {
        return modelFieldService.delete(modelFieldId);
    }
}
