package com.eking.momp.model.dto;

import com.eking.momp.model.po.LayerPO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LayerDTO {

    private static final long serialVersionUID = 765200259072401812L;

	private Integer id;

    private String code;

	private String name;

	private String description;

	private Integer showOrder;

	private String iconImage;

	private boolean sysInit;

	private List<ModelDTO> models;

	public LayerDTO(LayerPO layer) {
		this.id = layer.getId();
		this.code = layer.getCode();
		this.name = layer.getName();
		this.description = layer.getDescription();
		this.showOrder = layer.getShowOrder();
		this.iconImage = layer.getIconImage();
		this.sysInit = layer.isSysInit();
	}
}
