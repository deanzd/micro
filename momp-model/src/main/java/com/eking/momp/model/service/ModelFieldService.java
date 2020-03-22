package com.eking.momp.model.service;

import com.eking.momp.model.dto.ModelFieldDTO;
import com.eking.momp.model.param.ModelFieldParam;

import java.util.List;

public interface ModelFieldService {
	
	ModelFieldDTO getById(Integer id);

	ModelFieldDTO save(ModelFieldParam param);

	boolean update(Integer modelFieldId, ModelFieldParam param);

	boolean delete(Integer id);

	ModelFieldDTO getByCode(Integer modelId, String code);

	List<ModelFieldDTO> listByModelId(Integer modelId);
}
