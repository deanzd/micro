package com.eking.momp.model.service.impl;

import com.eking.momp.model.dao.ModelRelationDao;
import com.eking.momp.model.dto.*;
import com.eking.momp.model.exception.ResourceInUsedException;
import com.eking.momp.model.exception.ResourceNotFoundException;
import com.eking.momp.model.param.ModelRelationParam;
import com.eking.momp.model.po.ModelRelationPO;
import com.eking.momp.model.service.*;
import com.eking.momp.model.util.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ModelRelationServiceImpl extends AbstractModelService<ModelRelationDao, ModelRelationPO>
        implements ModelRelationService {
    @Autowired
    private ModelService modelService;
    @Autowired
    private RelationTypeService relationTypeService;
    @Autowired
    private ModelFieldService modelFieldService;
    @Autowired
    private EntityRelationService entityRelationService;

    @Override
    public List<ModelRelationDetailDTO> listDetailByModelId(Integer modelId) {
        return super.listObjs(ModelRelationPO::getModelId, modelId).stream()
                .map(this::transToDetailDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ModelRelationDetailDTO> listDetailByTargetModelId(Integer modelId) {
        return super.listObjs(ModelRelationPO::getModelId, modelId).stream()
                .map(this::transToDetailDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ModelRelationDetailDTO> listDetail() {
        return super.listObjs().stream()
                .map(this::transToDetailDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ModelRelationDTO> list() {
        return super.listObjs().stream()
                .map(ModelRelationDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ModelRelationDTO> listByModelId(Integer modelId) {
        return super.listObjs(ModelRelationPO::getModelId, modelId).stream()
                .map(ModelRelationDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ModelRelationDTO> listByTargetModelId(Integer modelId) {
        return super.listObjs(ModelRelationPO::getTargetModelId, modelId).stream()
                .map(ModelRelationDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public ModelRelationDTO getById(Integer id) {
        return super.getObjById(id)
                .map(ModelRelationDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceType.ModelRelation, id));
    }

    @Override
    public ModelRelationDetailDTO getDetailById(Integer id) {
        return super.getObjById(id)
                .map(this::transToDetailDto)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceType.ModelRelation, id));
    }

    @Override
    public ModelRelationDTO save(ModelRelationParam param) {
        super.checkResourceExists("label.code", ModelRelationPO::getCode, param.getCode());
        super.checkResourceExists("label.name", ModelRelationPO::getModelId, param.getModelId(),
                ModelRelationPO::getTargetModelId, param.getTargetModelId(), ModelRelationPO::getName, param.getName());

        ModelRelationPO relation = new ModelRelationPO();
        relation.setCode(param.getCode());
        relation.setName(param.getName());
        relation.setRelationTypeId(param.getRelationTypeId());
        relation.setMapping(param.getMapping());
        relation.setModelId(param.getModelId());
        relation.setTargetModelId(param.getTargetModelId());
        relation.setModelFieldId(param.getModelFieldId());
        relation.setDescription(param.getDescription());
        modelService.updateUpdateInfo(param.getModelId());
        ModelRelationPO ret = super.saveObj(relation);
        return new ModelRelationDTO(ret);
    }

    @Override
    public boolean update(Integer id, ModelRelationParam param) {
        super.checkResourceExists(id, "label.code", ModelRelationPO::getCode, param.getCode());
        super.checkResourceExists(id, "laebl.name", ModelRelationPO::getModelId, param.getModelId(),
                ModelRelationPO::getTargetModelId, param.getTargetModelId(),
                ModelRelationPO::getName, param.getName());

        ModelRelationPO relation = super.getObjById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceType.ModelRelation, id));

        if (Boolean.TRUE.equals(relation.isSysInit())) {
            super.checkRole("sysadmin");
        }

        if (param.getCode() != null) {
            relation.setCode(param.getCode());
        }
        if (param.getName() != null) {
            relation.setName(param.getName());
        }
        if (param.getRelationTypeId() != null) {
            relation.setRelationTypeId(param.getRelationTypeId());
        }
        relation.setMapping(param.getMapping());
        if (param.getModelId() != null) {
            relation.setModelId(param.getModelId());
        }
        if (param.getTargetModelId() != null) {
            relation.setTargetModelId(param.getTargetModelId());
        }
        relation.setModelFieldId(param.getModelFieldId());
        relation.setDescription(param.getDescription());
        modelService.updateUpdateInfo(relation.getModelId());
        return super.updateObjById(relation);
    }

    @Override
    public boolean delete(Integer id) {
        ModelRelationDTO relation = this.getById(id);
        if (Boolean.TRUE.equals(relation.isSysInit())) {
            super.checkRole("sysadmin");
        }

        entityRelationService.listByModelRelationId(id)
                .stream()
                .findAny()
                .ifPresent(relationDto -> {
                    ModelRelationDTO modelRelation = this.getById(id);
                    throw new ResourceInUsedException(ResourceType.ModelRelation, modelRelation.getName(),
                            ResourceType.EntityRelation, relationDto.getId());
                });
        return super.deleteObj(id);
    }

    private ModelRelationDetailDTO transToDetailDto(ModelRelationPO relation) {
        ModelRelationDetailDTO dto = new ModelRelationDetailDTO(relation);
        RelationTypeDTO relationType = relationTypeService.getById(dto.getRelationTypeId());
        dto.setRelationType(relationType);
        ModelDTO model = modelService.getById(dto.getModelId());
        dto.setModel(model);
        ModelDTO targetModel = modelService.getById(dto.getTargetModelId());
        dto.setTargetModel(targetModel);
        if (dto.getModelFieldId() != null) {
            ModelFieldDTO modelField = modelFieldService.getById(dto.getModelFieldId());
            dto.setModelField(modelField);
        }
        return dto;
    }

    @Override
    public List<ModelRelationDTO> listByRelationTypeId(Integer relationTypeId) {
        return super.listObjs(ModelRelationPO::getRelationTypeId, relationTypeId).stream()
                .map(ModelRelationDTO::new)
                .collect(Collectors.toList());
    }
}
