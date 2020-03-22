package com.eking.momp.org.dto;

import com.eking.momp.org.po.UserPO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class UserDTO {

    private Integer id;
    private String username;
    private String password;
    private Integer roleId;
    private RoleDTO role;

    public UserDTO(UserPO user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.roleId = user.getRoleId();
    }
}
