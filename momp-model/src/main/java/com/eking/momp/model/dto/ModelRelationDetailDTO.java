package com.eking.momp.model.dto;

import com.eking.momp.model.po.ModelRelationPO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ModelRelationDetailDTO extends ModelRelationDTO {

    private static final long serialVersionUID = 1138713190432303870L;

    private RelationTypeDTO relationType;

	private ModelDTO model;

	private ModelDTO targetModel;

	private ModelFieldDTO modelField;

	public ModelRelationDetailDTO(ModelRelationPO relation) {
		super(relation);
	}
}
