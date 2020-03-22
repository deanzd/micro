package com.eking.momp.org.dto;

import com.eking.momp.org.po.RolePermissionPO;
import lombok.Data;

@Data
public class RolePermissionDTO {

    private Integer roleId;

    private Integer permissionId;

    public RolePermissionDTO(RolePermissionPO rolePermission) {
        this.roleId = rolePermission.getRoleId();
        this.permissionId = rolePermission.getPermissionId();
    }
}
