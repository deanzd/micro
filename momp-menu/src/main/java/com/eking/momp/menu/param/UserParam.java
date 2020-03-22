package com.eking.momp.menu.param;

import lombok.Data;

@Data
public class UserParam {
    private String username;
    private String password;
    private Integer roleId;
}
