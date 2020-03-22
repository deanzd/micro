package com.eking.momp.model.dto;

import com.eking.momp.model.po.RelationTypePO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RelationTypeDTO {

    private static final long serialVersionUID = -3281865357176937837L;

    private Integer id;

    private String code;

	private String name;
	
	private String text;

	private String reverseText;

	private boolean sysInit;

	public RelationTypeDTO(RelationTypePO type) {
		this.code = type.getCode();
		this.name = type.getName();
		this.text = type.getText();
		this.reverseText = type.getReverseText();
		this.sysInit = type.isSysInit();
	}
}
