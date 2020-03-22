package com.eking.momp.model.dto;


import com.eking.momp.model.po.EntityRelationPO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class EntityRelationDTO implements Serializable {

    private static final long serialVersionUID = 3559172021028294517L;
    private String id;

	private Integer modelRelationId;
	
	private String entityModel;

	private String entityId;

	private String targetEntityModel;

	private String targetEntityId;
	
	private RelationTypeDTO relationType;
	
	public EntityRelationDTO(EntityRelationPO relation) {
		this.id = relation.getId();
		this.modelRelationId = relation.getModelRelationId();
		this.entityModel = relation.getEntityModel();
		this.entityId = relation.getEntityId();
		this.targetEntityModel = relation.getTargetEntityModel();
		this.targetEntityId = relation.getTargetEntityId();
	}
}
