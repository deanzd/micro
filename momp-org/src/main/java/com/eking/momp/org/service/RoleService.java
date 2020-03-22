package com.eking.momp.org.service;

import com.eking.momp.org.dto.RoleDTO;

import java.util.List;

public interface RoleService {
    RoleDTO getById(Integer id);
    List<RoleDTO> list();
}
