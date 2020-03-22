package com.eking.momp.model.service;

import com.eking.momp.model.dto.DimensionModelDTO;
import com.eking.momp.model.po.DimensionModelPO;

import java.util.List;

public interface DimensionModelService {

	List<DimensionModelDTO> listByDimensionId(Integer dimensionId);

}
