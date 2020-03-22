package com.eking.momp.org.service.impl;

import com.eking.momp.mybatis.AbstractService;
import com.eking.momp.org.dao.PermissionDao;
import com.eking.momp.org.dto.PermissionDTO;
import com.eking.momp.org.po.PermissionPO;
import com.eking.momp.org.service.PermissionService;
import com.eking.momp.org.service.RolePermissionService;
import com.eking.momp.org.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl extends AbstractService<PermissionDao, PermissionPO> implements PermissionService {

    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private UserService userService;

    @Override
    public List<PermissionDTO> list(Integer roleId) {
        if (roleId == null) {
            return super.listObjs().stream()
                    .map(PermissionDTO::new)
                    .collect(Collectors.toList());
        } else {
            return rolePermissionService.listByRoleId(roleId).stream()
                    .map(rolePermissionDTO -> super.getObjById(rolePermissionDTO.getPermissionId()).orElse(null))
                    .map(PermissionDTO::new)
                    .collect(Collectors.toList());
        }
    }
}
