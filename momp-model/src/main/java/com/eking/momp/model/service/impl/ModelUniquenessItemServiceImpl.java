package com.eking.momp.model.service.impl;

import com.eking.momp.model.dao.ModelUniquenessItemDao;
import com.eking.momp.model.dto.ModelFieldDTO;
import com.eking.momp.model.dto.ModelUniquenessItemDTO;
import com.eking.momp.model.param.ModelUniquenessItemParam;
import com.eking.momp.model.po.ModelUniquenessItemPO;
import com.eking.momp.model.service.ModelFieldService;
import com.eking.momp.model.service.ModelUniquenessItemService;
import com.eking.momp.mybatis.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class ModelUniquenessItemServiceImpl extends AbstractService<ModelUniquenessItemDao, ModelUniquenessItemPO>
        implements ModelUniquenessItemService {

    @Autowired
    private ModelFieldService modelFieldService;

    @Override
    public List<ModelUniquenessItemDTO> listByModelUniquenessId(Integer modelUniquenessId) {
        return super.listObjs(ModelUniquenessItemPO::getModelUniquenessId, modelUniquenessId).stream()
                .map(ModelUniquenessItemDTO::new)
                .peek(dto -> {
                    ModelFieldDTO field = modelFieldService.getById(dto.getModelFieldId());
                    dto.setModelFieldName(field.getName());
                    dto.setModelFieldCode(field.getCode());
                })
                .collect(Collectors.toList());
    }

    @Override
    public ModelUniquenessItemDTO save(ModelUniquenessItemParam param) {
        ModelUniquenessItemPO item = new ModelUniquenessItemPO();
        item.setModelUniquenessId(param.getModelUniquenessId());
        item.setModelFieldId(param.getModelFieldId());
        ModelUniquenessItemPO returnItem = super.saveObj(item);
        return new ModelUniquenessItemDTO(returnItem);
    }

    @Override
    public boolean delete(Integer id) {
        return super.deleteObj(id);
    }

    @Override
    public List<ModelUniquenessItemDTO> listByModelFieldId(Integer modelFieldId) {
        return super.listObjs(ModelUniquenessItemPO::getModelFieldId, modelFieldId).stream()
                .map(ModelUniquenessItemDTO::new)
                .collect(Collectors.toList());
    }

}
