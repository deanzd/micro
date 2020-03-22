package com.eking.momp.org.service.impl;

import com.eking.momp.mybatis.AbstractService;
import com.eking.momp.org.dao.RolePermissionDao;
import com.eking.momp.org.dto.RolePermissionDTO;
import com.eking.momp.org.po.RolePermissionPO;
import com.eking.momp.org.service.RolePermissionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RolePermissionServiceImpl extends AbstractService<RolePermissionDao, RolePermissionPO>
        implements RolePermissionService {
    @Override
    public List<RolePermissionDTO> listByRoleId(Integer roleId) {
        return super.listObjs(RolePermissionPO::getRoleId, roleId).stream()
                .map(RolePermissionDTO::new)
                .collect(Collectors.toList());
    }
}
