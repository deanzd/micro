package com.eking.momp.model.service;

import java.util.List;

import com.eking.momp.model.dto.DimensionDTO;

public interface DimensionService {

	DimensionDTO getById(Integer dimensionId);

	List<DimensionDTO> listSysInit();
	
}
