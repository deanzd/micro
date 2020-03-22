package com.eking.momp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

public class UserContext {

    @Autowired
    private HttpServletRequest request;

    private static HttpServletRequest s_request;

    @PostConstruct
    public void init() {
        s_request = request;
    }

    public static String getUsername() {
        return s_request.getHeader("username");
    }

    public static String getRole() {
        return s_request.getHeader("role");
    }

}
