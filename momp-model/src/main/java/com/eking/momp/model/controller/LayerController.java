package com.eking.momp.model.controller;

import com.eking.momp.model.dto.LayerDTO;
import com.eking.momp.model.param.LayerParam;
import com.eking.momp.model.service.LayerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/layers")
@Api(tags = "资源层")
public class LayerController {
    @Autowired
    private LayerService layerService;

    @GetMapping("/models")
    @ApiOperation("获取资源层下模型信息")
    public List<LayerDTO> listLayersModels(@ApiParam("搜索关键字") @RequestParam(required = false) String keyword) {
        return layerService.listWithModels(keyword);
    }

    @GetMapping("")
    @ApiOperation("获取资源层列表")
    public List<LayerDTO> listLayers() {
        return layerService.list();
    }

    @GetMapping("/{layerId}")
    @ApiOperation("获取资源层")
    public LayerDTO getLayerById(@PathVariable Integer layerId) {
        return layerService.getById(layerId);
    }

    @PostMapping("")
    @ApiOperation("添加资源层")
//    @PreAuthorize("hasAnyRole('sysadmin', 'admin')")
    public LayerDTO saveLayer(@RequestBody LayerParam param) {
        return layerService.save(param);
    }

    @PutMapping("/{layerId}")
    @ApiOperation("修改资源层")
//    @PreAuthorize("hasAnyRole('sysadmin', 'admin')")
    public boolean updateLayer(@PathVariable Integer layerId, @RequestBody LayerParam param) {
        return layerService.update(layerId, param);
    }

    @DeleteMapping("/{layerId}")
    @ApiOperation("删除资源层")
//    @PreAuthorize("hasAnyRole('sysadmin', 'admin')")
    public boolean deleteLayer(@PathVariable Integer layerId) {
        return layerService.delete(layerId);
    }
}
