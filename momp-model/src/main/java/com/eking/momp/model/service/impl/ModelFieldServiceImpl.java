package com.eking.momp.model.service.impl;

import com.eking.momp.model.dao.ModelFieldDao;
import com.eking.momp.model.dto.ModelDTO;
import com.eking.momp.model.dto.ModelFieldDTO;
import com.eking.momp.model.exception.BusinessException;
import com.eking.momp.model.exception.ResourceInUsedException;
import com.eking.momp.model.exception.ResourceNotFoundException;
import com.eking.momp.model.param.ModelFieldParam;
import com.eking.momp.model.po.ModelFieldPO;
import com.eking.momp.model.service.*;
import com.eking.momp.model.mq.EntitySearchSender;
import com.eking.momp.model.util.ResourceType;
import com.eking.momp.web.LocaleMessageHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ModelFieldServiceImpl extends AbstractModelService<ModelFieldDao, ModelFieldPO> implements ModelFieldService {

    @Autowired
    private EntityService entityService;
    @Autowired
    private ModelService modelService;
    @Autowired
    private ModelUniquenessItemService modelUniquenessItemService;
    @Autowired
    private EntitySearchSender entitySearchSender;

    @Override
    public List<ModelFieldDTO> listByModelId(Integer modelId) {
        return super.lambdaQuery()
                .eq(ModelFieldPO::getModelId, modelId)
                .orderByAsc(ModelFieldPO::getShowOrder).list().stream()
                .map(ModelFieldDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public ModelFieldDTO getById(Integer id) {
        return super.getObjById(id)
                .map(ModelFieldDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceType.ModelField, id));
    }

    @Override
    public ModelFieldDTO save(ModelFieldParam param) {
        ModelDTO modelDto = modelService.getById(param.getModelId());
        if (Boolean.TRUE.equals(modelDto.isSysInit())) {
            super.checkRole("sysadmin");
        }
        super.checkResourceExists("label.fieldCode", ModelFieldPO::getCode, param.getCode());
        super.checkResourceExists("label.fieldName", ModelFieldPO::getName, param.getName());

        ModelFieldPO field = new ModelFieldPO();
        field.setModelId(param.getModelId());
        field.setCode(param.getCode());
        field.setName(param.getName());
        field.setDescription(param.getDescription());
        field.setRequired(param.getRequired());
        field.setDataType(param.getDataType());
        field.setVerifyRegex(param.getVerifyRegex());
        field.setShowInTable(param.getShowInTable());
        field.setShowInList(param.getShowInList());
        field.setSearchKey(param.getSearchKey());
        field.setShowOrder(param.getShowOrder());
        ModelFieldPO returnField = super.saveObj(field);
        this.checkShowInListCount(returnField.getModelId());
        modelService.updateUpdateInfo(field.getModelId());
        return new ModelFieldDTO(returnField);
    }

    @Override
    public boolean update(Integer fieldId, ModelFieldParam param) {
        ModelDTO model = modelService.getById(param.getModelId());
        if (Boolean.TRUE.equals(model.isSysInit())) {
            super.checkRole("sysadmin");
        }

        super.checkResourceExists(fieldId, "label.fieldCode", ModelFieldPO::getCode, param.getCode());
        super.checkResourceExists(fieldId, "label.fieldName", ModelFieldPO::getName, param.getName());

        ModelFieldPO field = super.getObjById(fieldId)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceType.ModelField, fieldId));
        if (param.getCode() != null) {
            field.setCode(param.getCode());
        }
        if (param.getName() != null) {
            field.setName(param.getName());
        }
        field.setDescription(param.getDescription());
        if (param.getRequired() != null) {
            field.setRequired(param.getRequired());
        }
        if (param.getDataType() != null) {
            field.setDataType(param.getDataType());
        }
        if (param.getShowInTable() != null) {
            field.setShowInTable(param.getShowInTable());
        }
        if (param.getShowInList() != null) {
            field.setShowInList(param.getShowInList());
        }
        boolean updateSearchKey = false;
        if (param.getSearchKey() != null) {
            boolean b1 = field.getSearchKey() != null ? field.getSearchKey() : false;
            boolean b2 = param.getSearchKey() != null ? param.getSearchKey() : false;
            if (b1 != b2) {
                field.setSearchKey(param.getSearchKey());
                updateSearchKey = true;
            }
        }
        field.setShowOrder(param.getShowOrder());
        field.setVerifyRegex(param.getVerifyRegex());
        boolean ret = super.updateObjById(field);

        this.checkShowInListCount(model.getId());

        modelService.updateUpdateInfo(model.getId());//需要更新model的更新时间

        if (updateSearchKey) {
            entitySearchSender.updateModel(model.getCode());
        }

        return ret;
    }

    public boolean delete(Integer id) {
        ModelFieldPO field = super.getObjById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceType.ModelField, id));
        ModelDTO model = modelService.getById(field.getModelId());

        if (Boolean.TRUE.equals(model.isSysInit())) {
            super.checkRole("sysadmin");
        }

        // 检查字段在实例中是否被使用
        Criteria criteria = Criteria.where(field.getCode()).ne(null);
        Query query = Query.query(criteria);
        entityService.list(model.getCode(), query).stream()
                .findFirst()
                .ifPresent(entity -> {
                    throw new ResourceInUsedException(ResourceType.ModelField, field.getName(),
                            ResourceType.Entity, entity.get("id").toString());
                });
        // 检查是否在唯一性约束item中使用
        modelUniquenessItemService.listByModelFieldId(id)
                .stream()
                .findAny()
                .ifPresent(uniqItem -> {
                    throw new ResourceInUsedException(ResourceType.ModelField, field.getName(),
                            ResourceType.Uniqueness, uniqItem.getModelUniquenessId());
                });
        return super.deleteObj(id);
    }

    @Override
    public ModelFieldDTO getByCode(Integer modelId, String code) {
        return super.getOneObj(ModelFieldPO::getModelId, modelId, ModelFieldPO::getCode, code)
                .map(ModelFieldDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceType.ModelField, code));
    }

    private void checkShowInListCount(Integer modelId) {
        List<ModelFieldDTO> fields = this.listByModelId(modelId);
        long count = fields.stream()
                .filter(ModelFieldDTO::getShowInList)
                .count();
        if (count > 5) {
            throw new BusinessException(LocaleMessageHolder.getMessage("exception.showInListModelFiledCountLimit", 5));
        }
    }
}
