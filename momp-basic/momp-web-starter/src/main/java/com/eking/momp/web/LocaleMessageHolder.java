package com.eking.momp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Locale;

public class LocaleMessageHolder {

    @Autowired
    private MessageSource messageSource;

    private static MessageSource s_messageSource;

    @PostConstruct
    public void init() {
        s_messageSource = messageSource;
    }

    public static String getMessage(String code, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return s_messageSource.getMessage(code, args, code, locale);
    }
}
