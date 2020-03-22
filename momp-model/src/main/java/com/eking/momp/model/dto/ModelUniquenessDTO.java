package com.eking.momp.model.dto;

import com.eking.momp.model.po.ModelUniquenessPO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ModelUniquenessDTO {

    private static final long serialVersionUID = -2652468524176793444L;

    private Integer id;

    private Integer modelId;
	
	private Boolean required;

	private List<ModelUniquenessItemDTO> items;

	public ModelUniquenessDTO(ModelUniquenessPO model) {
		this.id = model.getId();
		this.modelId = model.getModelId();
		this.required = model.getRequired();
	}

}
