package com.eking.momp.model.dto;

import com.eking.momp.model.po.ModelUniquenessItemPO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ModelUniquenessItemDTO {

    private static final long serialVersionUID = 6103146618118373422L;

    private Integer id;

    private Integer modelUniquenessId;

	private Integer modelFieldId;

	private String modelFieldName;
	
	private String modelFieldCode;

	public ModelUniquenessItemDTO(ModelUniquenessItemPO modelUniquenessItemPO) {
		this.id = modelUniquenessItemPO.getId();
		this.modelUniquenessId = modelUniquenessItemPO.getModelUniquenessId();
		this.modelFieldId = modelUniquenessItemPO.getModelFieldId();
	}

}
