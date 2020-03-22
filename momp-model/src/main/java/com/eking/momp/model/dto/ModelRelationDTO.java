package com.eking.momp.model.dto;

import com.eking.momp.model.po.ModelRelationPO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ModelRelationDTO {

    private static final long serialVersionUID = -4079723782138659830L;

	private Integer id;

    private String code;
	
	private String name;

	private Integer relationTypeId;

	private ModelRelationPO.Mapping mapping;

	private Integer modelId;

	private Integer targetModelId;
	
	private Integer modelFieldId;
	
	private String description;

	private boolean sysInit;

	public ModelRelationDTO(ModelRelationPO relation) {
		this.id = relation.getId();
		this.code = relation.getCode();
		this.name = relation.getName();
		this.relationTypeId = relation.getRelationTypeId();
		this.mapping = relation.getMapping();
		this.modelId = relation.getModelId();
		this.targetModelId = relation.getTargetModelId();
		this.modelFieldId = relation.getModelFieldId();
		this.description = relation.getDescription();
		this.sysInit = relation.isSysInit();
	}

}
