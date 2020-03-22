package com.eking.momp.model.dao;

import com.eking.momp.model.po.EntityRelationPO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntityRelationDao extends MongoRepository<EntityRelationPO, String> {

	List<EntityRelationPO> findByEntityIdAndModelRelationIdIn(String entityId, List<Integer> modelRelationIds);

	List<EntityRelationPO> findByTargetEntityIdAndModelRelationIdInAndDeletedIsNull(String targetEntityId,
                                                                                  List<Integer> modelRelationIds);
}
