package com.eking.momp.model.service.impl;

import com.eking.momp.model.dao.RelationTypeDao;
import com.eking.momp.model.dto.RelationTypeDTO;
import com.eking.momp.model.exception.ResourceInUsedException;
import com.eking.momp.model.exception.ResourceNotFoundException;
import com.eking.momp.model.param.RelationTypeParam;
import com.eking.momp.model.po.RelationTypePO;
import com.eking.momp.model.service.AbstractModelService;
import com.eking.momp.model.service.ModelRelationService;
import com.eking.momp.model.service.RelationTypeService;
import com.eking.momp.model.util.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RelationTypeServiceImpl extends AbstractModelService<RelationTypeDao, RelationTypePO>
        implements RelationTypeService {

    @Autowired
    private ModelRelationService modelRelationService;

    @Override
    public List<RelationTypeDTO> list() {
        return super.listObjs().stream()
                .map(RelationTypeDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public RelationTypeDTO getById(Integer id) {
        return super.getObjById(id)
                .map(RelationTypeDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceType.RelationType, id));
    }

    @Override
    public RelationTypeDTO save(RelationTypeParam param) {
        super.checkResourceExists("label.code", RelationTypePO::getCode, param.getCode());
        super.checkResourceExists("label.name", RelationTypePO::getName, param.getName());

        RelationTypePO type = new RelationTypePO();
        type.setCode(param.getCode());
        type.setName(param.getName());
        type.setText(param.getText());
        type.setReverseText(param.getReverseText());
        RelationTypePO ret = super.saveObj(type);
        return new RelationTypeDTO(ret);
    }

    @Override
    public boolean update(Integer id, RelationTypeParam param) {
        super.checkResourceExists(id, "label.code", RelationTypePO::getCode, param.getCode());
        super.checkResourceExists(id, "label.name", RelationTypePO::getName, param.getName());
        super.checkResourceExists(id, "label.relationType", RelationTypePO::getText, param.getText());
        super.checkResourceExists(id, "label.relationType", RelationTypePO::getReverseText, param.getReverseText());

        RelationTypePO type = super.getObjById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceType.RelationType, id));

        if (Boolean.TRUE.equals(type.isSysInit())) {
            super.checkRole("sysadmin");
        }

        if (param.getCode() != null) {
            type.setCode(param.getCode());
        }
        if (param.getName() != null) {
            type.setName(param.getName());
        }
        type.setText(param.getText());
        type.setReverseText(param.getReverseText());
        return super.updateObjById(type);
    }

    @Override
    public boolean delete(Integer id) {
        RelationTypeDTO relationType = this.getById(id);

        if (Boolean.TRUE.equals(relationType.isSysInit())) {
            super.checkRole("sysadmin");
        }

        modelRelationService.listByRelationTypeId(id).stream()
                .findAny()
                .ifPresent(modelRelation -> {
                    throw new ResourceInUsedException(ResourceType.RelationType, relationType.getName(),
                            ResourceType.ModelRelation, modelRelation.getName());
                });
        return super.deleteObj(id);
    }
}
