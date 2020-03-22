package com.eking.momp.org.dto;

import com.eking.momp.org.po.RolePO;
import lombok.Data;

@Data
public class RoleDTO {

    private Integer id;

    private String name;

    private String code;

    private String description;

    public RoleDTO(RolePO role) {
        this.id = role.getId();
        this.name = role.getName();
        this.code = role.getCode();
        this.description = role.getDescription();
    }
}
