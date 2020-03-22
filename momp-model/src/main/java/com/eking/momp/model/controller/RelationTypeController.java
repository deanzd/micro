package com.eking.momp.model.controller;

import com.eking.momp.model.dto.RelationTypeDTO;
import com.eking.momp.model.param.RelationTypeParam;
import com.eking.momp.model.service.RelationTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/relation-types")
@Api(tags = "关联关系类型")
public class RelationTypeController {

	@Autowired
	private RelationTypeService relationTypeService;
	
	@GetMapping("")
	@ApiOperation("获取关联关系类型列表")
	public List<RelationTypeDTO> list() {
		return relationTypeService.list();
	}

	@GetMapping("/{id}")
	@ApiOperation("根据ID获取关联关系类型")
	public RelationTypeDTO getById(@ApiParam("关联关系类型ID") @PathVariable Integer id) {
		return relationTypeService.getById(id);
	}

	@PostMapping("")
	@ApiOperation("添加关联关系类型")
//	@PreAuthorize("hasAnyRole('sysadmin', 'admin')")
	public RelationTypeDTO save(@RequestBody RelationTypeParam param) {
		return relationTypeService.save(param);
	}

	@PutMapping("/{id}")
	@ApiOperation("更新关联关系类型")
//	@PreAuthorize("hasAnyRole('sysadmin', 'admin')")
	public boolean update(
			@ApiParam("关联关系类型ID") @PathVariable Integer id, 
			@ApiParam("关联关系属性") @RequestBody RelationTypeParam param) {
		return relationTypeService.update(id, param);
	}

	@DeleteMapping("/{id}")
	@ApiOperation("删除关联关系类型")
//	@PreAuthorize("hasAnyRole('sysadmin', 'admin')")
	public boolean delete(@ApiParam("关联关系类型ID") @PathVariable Integer id) {
		return relationTypeService.delete(id);
	}

}
