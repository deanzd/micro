package com.eking.momp.model.service;

import com.eking.momp.common.util.Page;
import com.eking.momp.common.param.PageFilterParam;
import com.eking.momp.model.dto.EntityRelationDTO;
import com.eking.momp.model.dto.EntityRelationDetailDTO;
import com.eking.momp.model.param.EntityRelationParam;
import com.eking.momp.model.param.EntityRelationQueryParam;
import com.eking.momp.model.param.EntityRelationUpdateParam;

import java.util.List;

public interface EntityRelationService {

    List<EntityRelationDTO> listByEntityIdAndModelRelationIds(String entityId, List<Integer> allowModelRelationIds);

    List<EntityRelationDTO> listByTargetEntityIdAndModelRelationIds(String targetEntityId,
                                                                 List<Integer> allowModelRelationIds);

    EntityRelationDTO save(EntityRelationParam param);

    EntityRelationDTO update(String id, EntityRelationUpdateParam param);

    void delete(String... ids);

    Page<EntityRelationDetailDTO> page(PageFilterParam<EntityRelationQueryParam> param);

    List<EntityRelationDTO> listByEntityId(String entityId);

    List<EntityRelationDTO> listByTargetEntityId(String targetEntityId);

    List<EntityRelationDTO> listByModelRelationId(Integer modelRelationId);
}
