package com.eking.momp.menu.dto;

import com.eking.momp.menu.po.MenuPO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuDTO {
	@JsonIgnore
	private Integer id;

	private String name;

	private String path;

	private String icon;

	private List<MenuDTO> children;

	public MenuDTO(MenuPO menu) {
		this.id = menu.getId();
		this.name = menu.getName();
		this.path = menu.getPath();
		this.icon = menu.getIcon();
	}

	public MenuDTO(String name, String path, String icon) {
		super();
		this.name = name;
		this.path = path;
		this.icon = icon;
	}
}
