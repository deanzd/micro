package com.eking.momp.org.service.impl;

import com.eking.momp.mybatis.AbstractService;
import com.eking.momp.org.dao.UserDao;
import com.eking.momp.org.dto.RoleDTO;
import com.eking.momp.org.dto.UserDTO;
import com.eking.momp.org.exception.UserAlreadyExistsException;
import com.eking.momp.org.param.UserParam;
import com.eking.momp.org.po.UserPO;
import com.eking.momp.org.service.RoleService;
import com.eking.momp.org.service.UserService;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class UserServiceImpl extends AbstractService<UserDao, UserPO> implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;

    @Override
    public UserDTO getById(Integer id) {
        return super.getObjById(id)
                .map(UserDTO::new)
                .map(UserDTO -> {
                    RoleDTO roleDTO = roleService.getById(UserDTO.getRoleId());
                    if (roleDTO != null) {
                        UserDTO.setRole(roleDTO);
                    }
                    return UserDTO;
                })
                .orElse(null);
    }

    @Override
    @Transactional
    public UserDTO save(UserParam userParam) {
        String username = userParam.getUsername();
        String password = userParam.getPassword();
        Integer roleId = userParam.getRoleId();

        super.getOneObj(UserPO::getUsername, username).ifPresent(userPO -> {
            throw new UserAlreadyExistsException(username);
        });

        UserPO user = new UserPO();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoleId(roleId);
        UserPO returnUser = super.saveObj(user);
        return new UserDTO(returnUser);
    }

    @Override
    public boolean delete(Integer id) {
        super.getObjById(id)
                .filter(this::notSuperadmin)
                .ifPresent(userPO -> super.deleteObj(userPO.getId()));
        return true;
    }

    @Override
    public boolean update(Integer id, UserParam userParam) {
        String password = userParam.getPassword();
        Integer roleId = userParam.getRoleId();

        UserPO userPO = super.getObjById(id).orElse(null);
        if (userPO == null) {
            return false;
        }
        return super.lambdaUpdate()
                .set(!StringUtils.isEmpty(password), UserPO::getPassword, password)
                .set(userParam.getRoleId() != null, UserPO::getRoleId, roleId)
                .update(userPO);
    }

    @Override
    public List<UserDTO> list(String username) {
        return super.lambdaQuery()
                .eq(!StringUtils.isEmpty(username), UserPO::getUsername, username)
                .list().stream()
                .map(UserDTO::new)
                .peek(userDTO -> {
                    RoleDTO roleDTO = roleService.getById(userDTO.getRoleId());
                    if (roleDTO != null) {
                        userDTO.setRole(roleDTO);
                    }
                })
                .collect(Collectors.toList());
    }

    private boolean isSuperadmin(UserPO userPO) {
        return "superadmin".equals(userPO.getUsername());
    }

    private boolean notSuperadmin(UserPO userPO) {
        return !isSuperadmin(userPO);
    }
}
