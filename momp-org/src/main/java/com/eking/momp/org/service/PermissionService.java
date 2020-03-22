package com.eking.momp.org.service;

import com.eking.momp.org.dto.PermissionDTO;

import java.util.List;

public interface PermissionService {
    List<PermissionDTO> list(Integer roleId);
}
