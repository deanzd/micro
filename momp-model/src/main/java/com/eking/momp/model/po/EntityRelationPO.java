package com.eking.momp.model.po;

import com.momp.mongo.BasePO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Document("entity_relation")
public class EntityRelationPO extends BasePO {
	
	@Field("model_relation_id")
	private Integer modelRelationId;

	@Field("entity_model")
	private String entityModel;

	@Field("entity_id")
	private String entityId;

	@Field("target_entity_model")
	private String targetEntityModel;

	@Field("target_entity_id")
	private String targetEntityId;
}
