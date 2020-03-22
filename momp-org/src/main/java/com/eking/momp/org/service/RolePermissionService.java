package com.eking.momp.org.service;

import com.eking.momp.org.dto.RolePermissionDTO;

import java.util.List;

public interface RolePermissionService {
    List<RolePermissionDTO> listByRoleId(Integer roleId);
}
