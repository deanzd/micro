package com.eking.momp.model.service;

import com.eking.momp.model.dto.ModelDTO;
import com.eking.momp.model.dto.ModelRelationTopoDTO;
import com.eking.momp.model.dto.ModelTopoDTO;
import com.eking.momp.model.dto.TopoDto;
import com.eking.momp.model.param.ModelParam;
import com.eking.momp.model.po.ModelPO;

import java.util.List;

public interface ModelService {

	TopoDto<List<ModelTopoDTO>, List<ModelRelationTopoDTO>> getModelTopo(Integer modelId);

	List<ModelDTO> list(String keyword);

	void clearLayerId(Integer layerId);

	List<ModelDTO> list();

	ModelDTO save(ModelParam param);

	boolean update(Integer id, ModelParam param);

	boolean updateUpdateInfo(Integer id);

	boolean delete(Integer id);

	ModelDTO getById(Integer id);

	ModelDTO getByCode(String modelCode);

    List<ModelDTO> listByLayerId(Integer layerId);

}
