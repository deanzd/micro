package com.eking.momp.model.service;

import java.util.List;

import com.eking.momp.model.dto.ModelRelationDetailDTO;
import com.eking.momp.model.dto.ModelRelationDTO;
import com.eking.momp.model.param.ModelRelationParam;

public interface ModelRelationService {

	List<ModelRelationDetailDTO> listDetailByModelId(Integer modelId);

	List<ModelRelationDetailDTO> listDetailByTargetModelId(Integer targetModelId);
	
	List<ModelRelationDetailDTO> listDetail();

	List<ModelRelationDTO> listByModelId(Integer modelId);

	List<ModelRelationDTO> listByTargetModelId(Integer targetModelId);
	
	List<ModelRelationDTO> list();

	ModelRelationDTO getById(Integer id);

	ModelRelationDetailDTO getDetailById(Integer id);

	ModelRelationDTO save(ModelRelationParam param);
	
	boolean update(Integer id, ModelRelationParam param);
	
	boolean delete(Integer id);

	List<ModelRelationDTO> listByRelationTypeId(Integer relationTypeId);
}
