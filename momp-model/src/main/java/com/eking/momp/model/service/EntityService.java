package com.eking.momp.model.service;

import java.util.List;
import java.util.Map;

import com.eking.momp.common.param.PageKeywordParam;
import com.eking.momp.model.dto.*;
import org.springframework.data.mongodb.core.query.Query;

import com.eking.momp.common.util.Page;

import javax.servlet.http.HttpServletResponse;

public interface EntityService {

	ModelEntitiesDTO getModelEntities(Integer dimensionId, String keyword);

	TopoDto<List<ModelEntitiesDTO>, List<EntityRelationDTO>> getTopo(String model, String entityId,
                                                                     Integer dimensionId);

	TopoDto<List<ModelEntitiesDTO>, List<EntityRelationTopoDTO>> getTopoLevel2(String model, String entityId,
                                                                               List<Integer> modelRelationIds, List<Integer> modelRelationIdsR);

	void export(String modelCode, String keyword, HttpServletResponse response);

	Page<EntityDTO> search(String keyword, String lastId);


	Map<String, Object> save(String model, Map<String, Object> params);

	boolean update(String model, String id, Map<String, Object> params);

	List<Map<String, Object>> list(String model, Query query);

	Page<Map<String, Object>> page(String model, PageKeywordParam pageParam);

	List<Map<String, Object>> listAll(String model);

	boolean delete(String model, String... id);

    Map<String, Object> getById(String model, String id);
}
