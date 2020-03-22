package com.eking.momp.auth.to;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionTO {

    private String code;

    private String path;

    private String method;

}
