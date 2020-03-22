package com.eking.momp.org.dto;

import com.eking.momp.org.po.PermissionPO;
import lombok.Data;

@Data
public class PermissionDTO {

    private String code;

    private String name;

    private String description;

    private String path;

    private String method;

    public PermissionDTO(PermissionPO permission) {
        this.code = permission.getCode();
        this.name = permission.getName();
        this.description = permission.getDescription();
        this.path = permission.getPath();
        this.method = permission.getMethod();
    }
}
