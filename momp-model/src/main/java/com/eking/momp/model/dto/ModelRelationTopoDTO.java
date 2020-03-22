package com.eking.momp.model.dto;

import com.eking.momp.model.po.ModelRelationPO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ModelRelationTopoDTO implements Serializable {

    private static final long serialVersionUID = 4550816447224464815L;
    private Integer id;
	
	private String code;
	
	private String name;

	private Integer relationTypeId;

	private ModelRelationPO.Mapping mapping;

	private String modelId;//处理后的ID

	private String targetModelId;//处理后的ID
	
	private Integer modelFieldId;
	
	private String description; 

	private RelationTypeDTO relationType;
	
	private ModelTopoDTO model;

	private ModelTopoDTO targetModel;

	private ModelFieldDTO modelField;

}
