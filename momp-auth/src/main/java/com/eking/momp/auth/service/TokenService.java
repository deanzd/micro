package com.eking.momp.auth.service;

import com.eking.momp.auth.param.TokenParam;

public interface TokenService {
    String create(TokenParam param);
}
