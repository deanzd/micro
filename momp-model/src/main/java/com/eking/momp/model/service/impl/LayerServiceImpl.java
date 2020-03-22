package com.eking.momp.model.service.impl;

import com.eking.momp.model.dao.LayerDao;
import com.eking.momp.model.dto.LayerDTO;
import com.eking.momp.model.dto.ModelDTO;
import com.eking.momp.model.exception.ResourceNotFoundException;
import com.eking.momp.model.param.LayerParam;
import com.eking.momp.model.po.LayerPO;
import com.eking.momp.model.service.AbstractModelService;
import com.eking.momp.model.service.LayerService;
import com.eking.momp.model.service.ModelService;
import com.eking.momp.model.mq.EntitySearchSender;
import com.eking.momp.model.util.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LayerServiceImpl extends AbstractModelService<LayerDao, LayerPO> implements LayerService {
    @Autowired
    private ModelService modelService;
    @Autowired
    private EntitySearchSender searchSender;

    @Override
    public List<LayerDTO> listWithModels(String keyword) {
        List<ModelDTO> models = modelService.list();
        if (keyword == null) {
            return this.list().stream()
                    .peek(layer -> {
                        List<ModelDTO> fitModels = models.stream()
                                .filter(model -> layer.getId().equals(model.getLayerId()))
                                .collect(Collectors.toList());
                        layer.setModels(fitModels);
                    })
                    .collect(Collectors.toList());
        } else {
            return this.list().stream()
                    .peek(layer -> {
                        List<ModelDTO> fitModels = models.stream()
                                .filter(model -> layer.getId().equals(model.getLayerId()))
                                .filter(model -> model.getName().contains(keyword))
                                .collect(Collectors.toList());
                        layer.setModels(fitModels);
                    })
                    .filter(layerDto -> layerDto.getModels().size() > 0)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<LayerDTO> list() {
        return super.lambdaQuery()
                .orderByAsc(LayerPO::getShowOrder).list().stream()
                .map(LayerDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public LayerDTO getById(Integer id) {
        return super.getObjById(id).map(LayerDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceType.Layer, id));
    }

    @Override
    public LayerDTO save(LayerParam param) {
        super.checkResourceExists("label.code", LayerPO::getCode, param.getCode());
        super.checkResourceExists("label.name", LayerPO::getName, param.getName());
        if (Boolean.TRUE.equals(param.getSysInit())) {
            super.checkRole("sysadmin");
        }

        LayerPO layer = new LayerPO();
        layer.setCode(param.getCode());
        layer.setName(param.getName());
        layer.setDescription(param.getDescription());
        layer.setIconImage(param.getIconImage());
        layer.setShowOrder(param.getShowOrder());
        if (param.getSysInit() != null) {
            layer.setSysInit(param.getSysInit());
        }
        LayerPO retLayer = super.saveObj(layer);
        return new LayerDTO(retLayer);
    }

    @Override
    public boolean update(Integer layerId, LayerParam param) {
        super.checkResourceExists(layerId, "label.code", LayerPO::getCode, param.getCode());
        super.checkResourceExists(layerId, "label.name", LayerPO::getName, param.getName());

        LayerPO layer = super.getObjById(layerId)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceType.Layer, layerId));

        if (layer.isSysInit() || Boolean.TRUE.equals(param.getSysInit())) {
            super.checkRole("sysadmin");
        }

        if (param.getCode() != null) {
            layer.setCode(param.getCode());
        }
        boolean updateName = false;
        if (param.getName() != null && !layer.getName().equals(param.getName())) {
            layer.setName(param.getName());
            updateName = true;
        }
        if (param.getSysInit() != null) {
            layer.setSysInit(param.getSysInit());
        }
        layer.setDescription(param.getDescription());
        layer.setIconImage(param.getIconImage());
        layer.setShowOrder(param.getShowOrder());
        boolean ret = super.updateObjById(layer);

        if (updateName) {
            modelService.listByLayerId(layerId).stream()
                    .map(ModelDTO::getCode)
                    .forEach(searchSender::updateModel);
        }

        return ret;
    }

    @Override
    public boolean delete(Integer id) {
        LayerDTO layer = this.getById(id);
        if (Boolean.TRUE.equals(layer.isSysInit())) {
            super.checkRole("sysadmin");
        }
        modelService.clearLayerId(id);
        return super.deleteObj(id);
    }

}
