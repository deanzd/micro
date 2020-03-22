package com.eking.momp.auth.to;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTO {

    private String username;

    private String password;

    private RoleTO role;

}
