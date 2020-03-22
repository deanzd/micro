package com.eking.momp.model.service.impl;

import com.eking.momp.model.dao.DimensionModelDao;
import com.eking.momp.model.dto.DimensionModelDTO;
import com.eking.momp.model.po.DimensionModelPO;
import com.eking.momp.model.service.DimensionModelService;
import com.eking.momp.mybatis.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
@Transactional
public class DimensionModelServiceImpl extends AbstractService<DimensionModelDao, DimensionModelPO>
		implements DimensionModelService {

	@Autowired
	private ThreadPoolTaskExecutor executor;

	@Override
	public List<DimensionModelDTO> listByDimensionId(Integer dimensionId) {
		return super.listObjs(DimensionModelPO::getDimensionId, dimensionId).stream()
				.map(DimensionModelDTO::new)
				.collect(Collectors.toList());
	}
}
