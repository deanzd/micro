package com.eking.momp.model.service;

import java.util.List;

import com.eking.momp.model.dto.RelationTypeDTO;
import com.eking.momp.model.param.RelationTypeParam;

public interface RelationTypeService {
	
	List<RelationTypeDTO> list();

	RelationTypeDTO getById(Integer id);

	RelationTypeDTO save(RelationTypeParam param);
	
	boolean update(Integer id, RelationTypeParam param);
	
	boolean delete(Integer id);
}
