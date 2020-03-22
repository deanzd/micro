package com.eking.momp.model.controller;

import com.eking.momp.model.dto.ModelRelationDetailDTO;
import com.eking.momp.model.dto.ModelRelationDTO;
import com.eking.momp.model.param.ModelRelationParam;
import com.eking.momp.model.service.ModelRelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/model-relations")
@Api(tags = "模型关系")
@RestController
public class ModelRelationController {

	@Autowired
	private ModelRelationService modelRelationService;

	@GetMapping("")
	@ApiOperation(value = "获取模型关系列表", notes = "模型ID和目标模型ID只能传一个")
	public List<ModelRelationDetailDTO> listByModelId(
			@ApiParam("模型ID") @RequestParam(required = false) Integer modelId,
			@ApiParam("目标模型ID") @RequestParam(required = false) Integer targetModelId) {
		if (modelId != null) {
			return modelRelationService.listDetailByModelId(modelId);
		} else if (targetModelId != null) {
			return modelRelationService.listDetailByTargetModelId(targetModelId);
		} else {
			return modelRelationService.listDetail();
		}
	}

	@GetMapping("/{id}")
	@ApiOperation("根据ID获取模型关系")
	public ModelRelationDTO getById(@ApiParam("模型关系ID") @PathVariable Integer id) {
		return modelRelationService.getById(id);
	}

	@PostMapping("")
	@ApiOperation("添加模型关系")
//	@PreAuthorize("hasAnyRole('sysadmin', 'admin')")
	public ModelRelationDTO save(@ApiParam("模型关系参数") @RequestBody ModelRelationParam param) {
		return modelRelationService.save(param);
	}

	@PutMapping("/{id}")
	@ApiOperation("更新模型关系")
//	@PreAuthorize("hasAnyRole('sysadmin', 'admin')")
	public boolean update(
			@ApiParam("模型关系ID") @PathVariable Integer id, 
			@ApiParam("模型关系参数") @RequestBody ModelRelationParam param) {
		return modelRelationService.update(id, param);
	}

	@DeleteMapping("/{id}")
	@ApiOperation("删除模型关系")
//	@PreAuthorize("hasAnyRole('sysadmin', 'admin')")
	public boolean delete(@ApiParam("模型关系ID") @PathVariable Integer id) {
		return modelRelationService.delete(id);
	}

}
