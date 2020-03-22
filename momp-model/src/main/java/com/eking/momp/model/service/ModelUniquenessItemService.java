package com.eking.momp.model.service;

import java.util.List;

import com.eking.momp.model.dto.ModelUniquenessItemDTO;
import com.eking.momp.model.param.ModelUniquenessItemParam;

public interface ModelUniquenessItemService {
	List<ModelUniquenessItemDTO> listByModelUniquenessId(Integer modelUniquenessId);

	ModelUniquenessItemDTO save(ModelUniquenessItemParam param);
	
	boolean delete(Integer id);

    List<ModelUniquenessItemDTO> listByModelFieldId(Integer modelFieldId);
}
