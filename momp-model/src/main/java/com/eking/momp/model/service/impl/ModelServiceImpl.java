package com.eking.momp.model.service.impl;

import com.eking.momp.model.dao.ModelDao;
import com.eking.momp.model.dto.*;
import com.eking.momp.model.exception.ResourceInUsedException;
import com.eking.momp.model.exception.ResourceNotFoundException;
import com.eking.momp.model.param.ModelParam;
import com.eking.momp.model.po.ModelPO;
import com.eking.momp.model.service.*;
import com.eking.momp.model.mq.EntitySearchSender;
import com.eking.momp.model.util.ResourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@CacheConfig(cacheNames = "model")
@Service
@Transactional
public class ModelServiceImpl extends AbstractModelService<ModelDao, ModelPO> implements ModelService {
    @Autowired
    private LayerService layerService;
    @Autowired
    private ModelFieldService modelFieldService;
    @Autowired
    private EntityService entityService;
    @Autowired
    private ModelUniquenessService modelUniquenessService;
    @Autowired
    private RelationTypeService relationTypeService;
    @Autowired
    private EntitySearchSender entitySearchSender;
    @Autowired
    private ModelRelationService modelRelationService;

    //    @Cacheable(key = "'all'")
    @Override
    public List<ModelDTO> list() {
        return super.listObjs().stream()
                .map(ModelDTO::new)
                .peek(modelDto -> {
                    if (modelDto.getLayerId() != null) {
                        LayerDTO layerDto = layerService.getById(modelDto.getLayerId());
                        modelDto.setLayerDto(layerDto);
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public ModelDTO save(ModelParam param) {
        super.checkResourceExists("label.code", ModelPO::getCode, param.getCode());
        super.checkResourceExists("label.name", ModelPO::getName, param.getName());
        if (Boolean.TRUE.equals(param.getSysInit())) {
            super.checkRole("sysadmin");
        }

        ModelPO model = new ModelPO();
        model.setCode(param.getCode());
        model.setName(param.getName());
        model.setDescription(param.getDescription());
        model.setIconImage(param.getIconImage());
        model.setLayerId(param.getLayerId());
        model.setShowOrder(param.getShowOrder());
        model.setSysInit(param.getSysInit());
        ModelPO returnModel = super.saveObj(model);
        return new ModelDTO(returnModel);
    }

    //    @Caching(evict = {
//            @CacheEvict(key = "#id"),
//            @CacheEvict(key = "'all'"),
//            @CacheEvict(key = "#result.code")
//    })
    @Override
    public boolean update(Integer id, ModelParam param) {
        super.checkResourceExists(id, "label.code", ModelPO::getCode, param.getCode());
        super.checkResourceExists(id, "label.name", ModelPO::getName, param.getName());

        ModelPO model = super.getObjById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceType.Model, id));

        if (Boolean.TRUE.equals(param.getSysInit()) || Boolean.TRUE.equals(model.isSysInit())) {
            super.checkRole("sysadmin");
        }

        if (param.getCode() != null) {
            model.setCode(param.getCode());
        }
        boolean updateSearch = false;
        if (param.getName() != null && model.getName().equals(param.getName())) {
            model.setName(param.getName());
            updateSearch = true;
        }
        if (param.getIconImage() != null) {
            model.setIconImage(param.getIconImage());
        }
        if (param.getLayerId() != null && model.getLayerId() != param.getLayerId()) {
            model.setLayerId(param.getLayerId());
            updateSearch = true;
        }
        if (param.getSysInit() != null) {
            model.setSysInit(param.getSysInit());
        }
        if (param.getShowOrder() != null) {
            model.setShowOrder(param.getShowOrder());
        }
        model.setDescription(param.getDescription());
        boolean ret = super.updateObjById(model);

        if (updateSearch) {
            entitySearchSender.updateModel(model.getCode());
        }

        return ret;
    }

    //    @Caching(evict = {
//            @CacheEvict(key = "#model.id"),
//            @CacheEvict(key = "#result.code"),
//            @CacheEvict(key = "'all'")
//    })
    @Override
    public boolean updateUpdateInfo(Integer id) {
        ModelPO modelPO = super.getObjById(id).orElseThrow(() -> new ResourceNotFoundException(ResourceType.Model, id));
        return super.updateObjById(modelPO);
    }

    //    @Caching(evict = {
//            @CacheEvict(key = "#result.id"),
//            @CacheEvict(key = "#result.code"),
//            @CacheEvict(key = "'all'")
//    })
    @Override
    public boolean delete(Integer id) {
        ModelPO model = super.getObjById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceType.Model, id));

        if (Boolean.TRUE.equals(model.isSysInit())) {
            super.checkRole("sysadmin");
        }

        // 正在被实例使用
        entityService.listAll(model.getCode())
                .stream()
                .findFirst()
                .ifPresent(entity -> {
                    throw new ResourceInUsedException(ResourceType.Model, model.getName(),
                            ResourceType.Entity, entity.get("id").toString());
                });
        // 正在被唯一性约束使用
        modelUniquenessService.listByModelId(id)
                .stream()
                .findAny()
                .ifPresent(uniq -> {
                    throw new ResourceInUsedException(ResourceType.Model, model.getName(), ResourceType.Uniqueness,
                            uniq.getId());
                });
        // 删除
        modelFieldService.listByModelId(id)
                .forEach(field -> modelFieldService.delete(field.getId()));
        return super.deleteObj(id);
    }

    //    @Cacheable(key = "#id")
    @Override
    public ModelDTO getById(Integer id) {
        return super.getObjById(id)
                .map(ModelDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceType.Model, id));
    }

    //    @Cacheable(key = "#code")
    @Override
    public ModelDTO getByCode(String code) {
        return super.getOneObj(ModelPO::getCode, code)
                .map(ModelDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceType.Model, code));
    }

    @Override
    public List<ModelDTO> listByLayerId(Integer layerId) {
        return super.lambdaQuery()
                .eq(ModelPO::getLayerId, layerId)
                .orderByAsc(ModelPO::getShowOrder).list().stream()
                .map(ModelDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ModelDTO> list(String keyword) {
        if (keyword == null) {
            return this.list();
        } else {
            return this.list().stream()
                    .filter(modelDto -> modelDto.getName().contains(keyword))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public void clearLayerId(Integer layerId) {
        super.listObjs(ModelPO::getLayerId, layerId).forEach(modelPO -> {
            modelPO.setLayerId(null);
            super.updateObjById(modelPO);
        });
    }

    @Override
    public TopoDto<List<ModelTopoDTO>, List<ModelRelationTopoDTO>> getModelTopo(Integer rootModelId) {
        List<ModelTopoDTO> models = new ArrayList<>();
        List<ModelRelationTopoDTO> relations = new ArrayList<>();

        ModelTopoDTO rootModel = genTopoModelDto(rootModelId.toString(), rootModelId, true, false, 0);
        models.add(rootModel);

        List<String> addedIds = new ArrayList<>();

        Pair<List<ModelTopoDTO>, List<ModelRelationTopoDTO>> pair1 = buildTopo(rootModel, 1, addedIds);
        transPairToResult(pair1, models, relations);

        pair1.getFirst().stream()
                .filter(m -> !m.getId().contains("_c"))
                .map(m -> buildTopo(m, 2, addedIds))
                .forEach(pair2 -> transPairToResult(pair2, models, relations));

        Pair<List<ModelTopoDTO>, List<ModelRelationTopoDTO>> pair1r = buildReverseTopo(rootModel, -1, addedIds);
        transPairToResult(pair1r, models, relations);

        pair1r.getFirst().stream()
                .filter(m -> !m.getId().contains("_c"))
                .map(m -> buildReverseTopo(m, -2, addedIds))
                .forEach(pair2r -> transPairToResult(pair2r, models, relations));

        return TopoDto.of(models, relations);
    }

    /**
     * id_level_r_c
     * <p>
     * root ID 不变 正向level1，ID_1 正向level2，ID_2 反向向level1，ID_1_r 反向向level2，ID_2_r
     * 副本末尾加_c
     */
    private Pair<List<ModelTopoDTO>, List<ModelRelationTopoDTO>> buildTopo(ModelTopoDTO parentModel,
                                                                           Integer level, List<String> addedIds) {

        List<ModelTopoDTO> models = new ArrayList<>();
        List<ModelRelationTopoDTO> relations = new ArrayList<>();
        List<ModelRelationDTO> mrs = modelRelationService.listByModelId(parentModel.getRealId());
        for (ModelRelationDTO mr : mrs) {
            Integer realId = mr.getTargetModelId();

            String id = realId + "_" + level;
            int realLevel = level;
            if (parentModel.getRealId().equals(realId)) {
                id += "_c";
                realLevel -= 1;
            }
            ModelTopoDTO model = genTopoModelDto(id, realId, false, realLevel == 0 || realLevel == 1, realLevel);//
            // 0是root副本
            ModelRelationTopoDTO relation = genTopoRelationDto(mr, parentModel, model);

            if (!addedIds.contains(model.getId())) {
                models.add(model);
                addedIds.add(model.getId());
            }
            relations.add(relation);
        }
        return Pair.of(models, relations);
    }

    private Pair<List<ModelTopoDTO>, List<ModelRelationTopoDTO>> buildReverseTopo(ModelTopoDTO parentModel,
                                                                                  Integer level,
                                                                                  List<String> addedIds) {

        List<ModelTopoDTO> models = new ArrayList<>();
        List<ModelRelationTopoDTO> relations = new ArrayList<>();
        List<ModelRelationDTO> mrs = modelRelationService.listByTargetModelId(parentModel.getRealId());
        for (ModelRelationDTO mr : mrs) {
            Integer realId = mr.getModelId();

            String id = realId + "_" + level + "_r";
            int realLevel = level;
            if (parentModel.getRealId().equals(realId)) {
                realLevel += 1;
                if (realLevel == 0) {// root的副本在正向已经统计了，这里不统计，其他层的副本还要，正反像都展示副本
                    continue;
                }
                id += "_c";
            }
            ModelTopoDTO model = genTopoModelDto(id, realId, false, realLevel == -1, realLevel);
            ModelRelationTopoDTO relation = genTopoRelationDto(mr, model, parentModel);

            if (!addedIds.contains(model.getId())) {
                models.add(model);
                addedIds.add(model.getId());
            }
            relations.add(relation);
        }
        return Pair.of(models, relations);
    }

    private ModelTopoDTO genTopoModelDto(String id, Integer realId, boolean root, boolean editable, int order) {
        ModelPO modelPO = super.getObjById(realId)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceType.Model, id));
        ModelTopoDTO topoModelDto = new ModelTopoDTO(modelPO);
        topoModelDto.setId(id);
        topoModelDto.setRealId(realId);
        topoModelDto.setRoot(root);
        topoModelDto.setEditable(editable);
        topoModelDto.setShowOrder(order);
        return topoModelDto;
    }

    private ModelRelationTopoDTO genTopoRelationDto(ModelRelationDTO relation, ModelTopoDTO model,
                                                    ModelTopoDTO targetModel) {
        ModelRelationTopoDTO topo = new ModelRelationTopoDTO();
        topo.setId(relation.getId());
        topo.setCode(relation.getCode());
        topo.setName(relation.getName());
        topo.setRelationTypeId(relation.getRelationTypeId());
        topo.setMapping(relation.getMapping());
        topo.setModelId(model.getId());
        topo.setTargetModelId(targetModel.getId());
        topo.setModelFieldId(relation.getModelFieldId());
        topo.setDescription(relation.getDescription());
        RelationTypeDTO relationType = relationTypeService.getById(relation.getRelationTypeId());
        topo.setRelationType(relationType);
        topo.setModel(model);
        topo.setTargetModel(targetModel);
        if (relation.getModelFieldId() != null) {
            ModelFieldDTO modelField = modelFieldService.getById(relation.getModelFieldId());
            topo.setModelField(modelField);
        }
        return topo;
    }

    private void transPairToResult(Pair<List<ModelTopoDTO>, List<ModelRelationTopoDTO>> pair,
                                   List<ModelTopoDTO> models, List<ModelRelationTopoDTO> relations) {

        pair.getFirst().sort(Comparator.comparingInt(o -> Math.abs(o.getShowOrder())));
        models.addAll(pair.getFirst());
        relations.addAll(pair.getSecond());
    }

}
