package com.eking.momp.model.service.impl;

import com.eking.momp.model.dao.DimensionRelationDao;
import com.eking.momp.model.po.DimensionRelationPO;
import com.eking.momp.model.service.DimensionRelationService;
import com.eking.momp.mybatis.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DimensionRelationServiceImpl extends AbstractService<DimensionRelationDao, DimensionRelationPO>
		implements DimensionRelationService {

	@Override
	public List<Integer> listModelRelationIdsByDimensionId(Integer dimensionId) {
		return super.listObjs(DimensionRelationPO::getDimensionId, dimensionId).stream()
				.map(DimensionRelationPO::getModelRelationId)
				.collect(Collectors.toList());
	}
}
