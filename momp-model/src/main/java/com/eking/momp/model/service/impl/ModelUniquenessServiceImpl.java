package com.eking.momp.model.service.impl;

import com.eking.momp.model.dao.ModelUniquenessDao;
import com.eking.momp.model.dto.ModelUniquenessDTO;
import com.eking.momp.model.dto.ModelUniquenessItemDTO;
import com.eking.momp.model.exception.ResourceAlreadyExistsException;
import com.eking.momp.model.exception.ResourceNotFoundException;
import com.eking.momp.model.param.ModelUniquenessItemParam;
import com.eking.momp.model.param.ModelUniquenessParam;
import com.eking.momp.model.po.ModelUniquenessPO;
import com.eking.momp.model.service.ModelUniquenessItemService;
import com.eking.momp.model.service.ModelUniquenessService;
import com.eking.momp.model.util.ResourceType;
import com.eking.momp.mybatis.AbstractService;
import com.eking.momp.web.LocaleMessageHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
public class ModelUniquenessServiceImpl extends AbstractService<ModelUniquenessDao, ModelUniquenessPO>
        implements ModelUniquenessService {

    @Autowired
    private ModelUniquenessItemService itemService;

    @Override
    public List<ModelUniquenessDTO> listByModelId(Integer modelId) {
        return super.listObjs(ModelUniquenessPO::getModelId, modelId).stream()
                .map(ModelUniquenessDTO::new)
                .peek(dto -> {
                    List<ModelUniquenessItemDTO> items = itemService.listByModelUniquenessId(dto.getId());
                    dto.setItems(items);
                })
                .collect(Collectors.toList());
    }

    @Override
    public ModelUniquenessDTO getById(Integer id) {
        return super.getObjById(id)
                .map(ModelUniquenessDTO::new)
                .map(uniq -> {
                    List<ModelUniquenessItemDTO> items = itemService.listByModelUniquenessId(uniq.getId());
                    uniq.setItems(items);
                    return uniq;
                })
                .orElseThrow(() -> new ResourceNotFoundException(ResourceType.Uniqueness, id));
    }

    @Override
    public ModelUniquenessDTO save(ModelUniquenessParam param) {
        checkExist(null, param.getModelId(), param.getItems());

        ModelUniquenessPO obj = new ModelUniquenessPO();
        obj.setModelId(param.getModelId());
        obj.setRequired(param.getRequired());
        ModelUniquenessPO uniq = super.saveObj(obj);

        if (param.getItems() != null) {
            param.getItems().stream()
                    .peek(itemParam -> itemParam.setModelUniquenessId(uniq.getId()))
                    .forEach(itemService::save);
        }
        return new ModelUniquenessDTO(uniq);
    }

    @Override
    public ModelUniquenessDTO update(Integer id, ModelUniquenessParam param) {
        checkExist(id, param.getModelId(), param.getItems());

        ModelUniquenessPO uniq = super.getObjById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceType.Uniqueness, id));
        if (param.getRequired() != null) {
            uniq.setRequired(param.getRequired());
            super.updateObjById(uniq);
        }

        List<ModelUniquenessItemDTO> preItems = itemService.listByModelUniquenessId(id);
        preItems.forEach(item -> itemService.delete(item.getId()));

        if (param.getItems() != null) {
            param.getItems().forEach(itemParam -> {
                itemParam.setModelUniquenessId(id);
                itemService.save(itemParam);
            });
        }
        return new ModelUniquenessDTO(uniq);
    }

    @Override
    public boolean delete(Integer id) {
        List<ModelUniquenessItemDTO> items = itemService.listByModelUniquenessId(id);
        items.forEach(item -> itemService.delete(item.getId()));
        return super.deleteObj(id);
    }

    private void checkExist(Integer uniqId, Integer modelId, List<ModelUniquenessItemParam> thisItems) {
        Set<Integer> thisIds = thisItems.stream()
                .map(ModelUniquenessItemParam::getModelFieldId)
                .collect(Collectors.toSet());
        super.listObjs(ModelUniquenessPO::getModelId, modelId).stream()
                .filter(uniq -> uniqId == null || !uniqId.equals(uniq.getId()))
                .map(uniq -> itemService.listByModelUniquenessId(uniq.getId()))
                .map(items -> items.stream()
                        .map(ModelUniquenessItemDTO::getModelFieldId)
                        .collect(Collectors.toSet()))
                .filter(ids -> compareSet(ids, thisIds))
                .findAny()
                .ifPresent(ids -> {
                    throw new ResourceAlreadyExistsException(LocaleMessageHolder.getMessage("label.uniqueness"));
                });
    }

    private <T> boolean compareSet(Set<T> aList, Set<T> bList) {
        if (aList.size() != bList.size()) {
            return false;
        }
        boolean notSame = aList.stream()
                .anyMatch(a -> !bList.contains(a));
        return !notSame;
    }
}
