package com.eking.momp.org.service.impl;

import com.eking.momp.mybatis.AbstractService;
import com.eking.momp.org.dao.RoleDao;
import com.eking.momp.org.dto.RoleDTO;
import com.eking.momp.org.po.RolePO;
import com.eking.momp.org.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends AbstractService<RoleDao, RolePO> implements RoleService {

    @Override
    public RoleDTO getById(Integer id) {
        return super.getObjById(id).map(RoleDTO::new).orElse(null);
    }

    @Override
    public List<RoleDTO> list() {
        return super.listObjs().stream()
                .map(RoleDTO::new)
                .collect(Collectors.toList());
    }
}
