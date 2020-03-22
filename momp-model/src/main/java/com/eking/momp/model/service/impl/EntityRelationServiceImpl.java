package com.eking.momp.model.service.impl;

import com.eking.momp.common.param.PageFilterParam;
import com.eking.momp.common.util.Page;
import com.eking.momp.common.util.Prop;
import com.eking.momp.model.dto.*;
import com.eking.momp.model.exception.BusinessException;
import com.eking.momp.model.exception.ResourceNotFoundException;
import com.eking.momp.model.param.EntityRelationParam;
import com.eking.momp.model.param.EntityRelationQueryParam;
import com.eking.momp.model.param.EntityRelationUpdateParam;
import com.eking.momp.model.po.EntityRelationPO;
import com.eking.momp.model.po.ModelRelationPO;
import com.eking.momp.model.service.EntityRelationService;
import com.eking.momp.model.service.EntityService;
import com.eking.momp.model.service.ModelFieldService;
import com.eking.momp.model.service.ModelRelationService;
import com.eking.momp.model.util.ResourceType;
import com.eking.momp.web.LocaleMessageHolder;
import com.momp.mongo.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EntityRelationServiceImpl extends AbstractService<EntityRelationPO>
        implements EntityRelationService {

    @Autowired
    private ModelRelationService modelRelationService;
    @Autowired
    private EntityService entityService;
    @Autowired
    private ModelFieldService modelFieldService;

    @Override
    public List<EntityRelationDTO> listByEntityIdAndModelRelationIds(String entityId,
                                                                    List<Integer> allowModelRelationIds) {
        return super.listObjs(Query.query(Criteria
                .where("entity_id").is(entityId)
                .and("model_relation_id").in(allowModelRelationIds))).stream()
                .map(EntityRelationDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<EntityRelationDTO> listByTargetEntityIdAndModelRelationIds(String targetEntityId,
                                                                          List<Integer> allowModelRelationIds) {
        return super.listObjs(Query.query(Criteria
                .where("target_entity_id").is(targetEntityId)
                .and("model_relation_id").in(allowModelRelationIds))).stream()
                .map(EntityRelationDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public EntityRelationDTO save(EntityRelationParam param) {
        this.checkMapping(null, param.getModelRelationId(), param.getEntityId(), param.getTargetEntityId());

        EntityRelationPO relation = new EntityRelationPO();
        relation.setModelRelationId(param.getModelRelationId());
        relation.setEntityModel(param.getEntityModel());
        relation.setEntityId(param.getEntityId());
        relation.setTargetEntityModel(param.getTargetEntityModel());
        relation.setTargetEntityId(param.getTargetEntityId());
        EntityRelationPO entityRelation = super.saveObj(relation);
        return new EntityRelationDTO(entityRelation);
    }

    @Override
    public EntityRelationDTO update(String id, EntityRelationUpdateParam param) {
        EntityRelationPO relation = super.getObjById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceType.EntityRelation, id));
        this.checkMapping(id, relation.getModelRelationId(), param.getEntityId(), param.getTargetEntityId());

        if (param.getEntityId() != null) {
            relation.setEntityId(param.getEntityId());
        }
        if (param.getTargetEntityId() != null) {
            relation.setTargetEntityId(param.getTargetEntityId());
        }
        EntityRelationPO entityRelation = super.updateObj(relation);
        return new EntityRelationDTO(entityRelation);
    }

    @Override
    public void delete(String... ids) {
        for (String id : ids) {
            super.deleteObj(id);
        }
    }

    @Override
    public Page<EntityRelationDetailDTO> page(PageFilterParam<EntityRelationQueryParam> param) {
        int pageIndex = param.getPageIndex();
        int pageSize = param.getPageSize();
        String sortBy = param.getSortBy();
        Boolean asc = param.getAsc();
        EntityRelationQueryParam filter = param.getFilter();

        Criteria criteria = new Criteria();
        if (filter.getEntityId() != null) {
            criteria.and("entity_id").is(filter.getEntityId());
        }
        if (filter.getTargetEntityId() != null) {
            criteria.and("target_entity_id").is(filter.getTargetEntityId());
        }
        if (filter.getModelRelationId() != null) {
            criteria.and("model_relation_id").is(filter.getModelRelationId());
        }
        Page<EntityRelationPO> page = super.pageObjs(pageIndex, pageSize, sortBy, asc, criteria);
        List<EntityRelationDetailDTO> rows = page.getRows().stream()
                .map(relation -> {
                    ModelRelationDetailDTO modelRelation =
                            modelRelationService.getDetailById(relation.getModelRelationId());
                    Map<String, Object> entity = entityService.getById(relation.getEntityModel(),
                            relation.getEntityId());
                    Map<String, Object> targetEntity = entityService.getById(relation.getTargetEntityModel(),
                            relation.getTargetEntityId());

                    List<ModelFiledValueDTO> showInListFields = new ArrayList<>();
                    if (filter.getEntityId() != null) {
                        showInListFields = buildShowInListFields(modelRelation.getTargetModel().getId(),
                                modelRelation.getTargetModel().getCode(), relation.getTargetEntityId());
                    } else if (filter.getTargetEntityId() != null) {
                        showInListFields = buildShowInListFields(modelRelation.getModel().getId(),
                                modelRelation.getModel().getCode(), relation.getEntityId());
                    }
                    return new EntityRelationDetailDTO(relation.getId(), modelRelation, entity, targetEntity,
                            showInListFields);
                })
                .collect(Collectors.toList());
        return Page.of(rows, page.getTotal(), page.getPages());
    }

    @Override
    public List<EntityRelationDTO> listByEntityId(String entityId) {
        List<EntityRelationPO> relations = super.listObjs(Prop.of("entity_id", entityId));
        return relations.stream()
                .map(EntityRelationDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<EntityRelationDTO> listByTargetEntityId(String targetEntityId) {
        List<EntityRelationPO> relations = super.listObjs(Prop.of("target_entity_id", targetEntityId));
        return relations.stream()
                .map(EntityRelationDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<EntityRelationDTO> listByModelRelationId(Integer modelRelationId) {
        List<EntityRelationPO> relations = super.listObjs(Prop.of("model_relation_id", modelRelationId));
        return relations.stream()
                .map(EntityRelationDTO::new)
                .collect(Collectors.toList());
    }

    private List<ModelFiledValueDTO> buildShowInListFields(Integer modelId, String modelCode, String entityId) {
        List<ModelFieldDTO> fields = modelFieldService.listByModelId(modelId);
        Map<String, Object> entity = entityService.getById(modelCode, entityId);
        return fields.stream()
                .filter(field -> Boolean.TRUE.equals(field.getShowInList()))
                .map(field -> {
                    ModelFiledValueDTO fieldValue = new ModelFiledValueDTO();
                    fieldValue.setCode(field.getCode());
                    fieldValue.setName(field.getName());
                    fieldValue.setDataType(field.getDataType());
                    fieldValue.setValue(entity.get(field.getCode()));
                    return fieldValue;
                })
                .collect(Collectors.toList());
    }

    private void checkMapping(String thisId, Integer modelRelationId, String entityId, String targetEntityId) {
        ModelRelationDTO modelRelation = modelRelationService.getById(modelRelationId);
        ModelRelationPO.Mapping mapping = modelRelation.getMapping();
        if (mapping == null) {
            return;
        }
        switch (mapping) {
            case OneToOne:
                checkMappingInternal(thisId,
                        LocaleMessageHolder.getMessage("exception.modelRelationMappingConflict", mapping.getText()),
                        Prop.of("model_relation_id", modelRelationId),
                        Prop.of("target_entity_id", targetEntityId));
                checkMappingInternal(thisId,
                        LocaleMessageHolder.getMessage("exception.modelRelationMappingConflict", mapping.getText()),
                        Prop.of("model_relation_id", modelRelationId),
                        Prop.of("entity_id", entityId));
                break;
            case OneToMany:
                checkMappingInternal(thisId,
                        LocaleMessageHolder.getMessage("exception.modelRelationMappingConflict", mapping.getText()),
                        Prop.of("model_relation_id", modelRelationId),
                        Prop.of("target_entity_id", targetEntityId));
                break;
            case ManyToOne:
                checkMappingInternal(thisId,
                        LocaleMessageHolder.getMessage("exception.modelRelationMappingConflict", mapping.getText()),
                        Prop.of("model_relation_id", modelRelationId),
                        Prop.of("entity_id", entityId));
                break;
            case ManyToMany:
                checkMappingInternal(thisId,
                        LocaleMessageHolder.getMessage("exception.alreadyExists", "关联标识"),
                        Prop.of("model_relation_id", modelRelationId),
                        Prop.of("entity_id", entityId),
                        Prop.of("target_entity_id", targetEntityId));
                break;
        }
    }

    private void checkMappingInternal(String thisId, String message, Prop... props) {
        super.listObjs(props).stream()
                .filter(entityRelation -> thisId == null || !thisId.equals(entityRelation.getId()))
                .findAny()
                .ifPresent(er -> {
                    throw new BusinessException(message);
                });
    }

}
