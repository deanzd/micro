package com.eking.momp.model.service.impl;

import com.eking.momp.model.dao.DimensionDao;
import com.eking.momp.model.dto.DimensionDTO;
import com.eking.momp.model.exception.ResourceNotFoundException;
import com.eking.momp.model.po.DimensionPO;
import com.eking.momp.model.service.DimensionService;
import com.eking.momp.model.util.ResourceType;
import com.eking.momp.mybatis.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DimensionServiceImpl extends AbstractService<DimensionDao, DimensionPO> implements DimensionService {

    @Override
    public DimensionDTO getById(Integer dimensionId) {
        return super.getObjById(dimensionId)
                .map(DimensionDTO::new)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceType.Dimension, dimensionId));
    }

    @Override
    public List<DimensionDTO> listSysInit() {
        return super.lambdaQuery()
                .eq(DimensionPO::isSysInit, true)
                .orderByAsc(DimensionPO::getShowOrder)
                .list().stream()
                .map(DimensionDTO::new)
                .collect(Collectors.toList());
    }

}
