package com.eking.momp.model.service;

import java.util.List;

import com.eking.momp.model.dto.ModelUniquenessDTO;
import com.eking.momp.model.param.ModelUniquenessParam;

public interface ModelUniquenessService {

	List<ModelUniquenessDTO> listByModelId(Integer modelId);

	ModelUniquenessDTO getById(Integer id);

	ModelUniquenessDTO save(ModelUniquenessParam param);
	
	ModelUniquenessDTO update(Integer id, ModelUniquenessParam param);
	
	boolean delete(Integer id);
	
}
