package com.eking.momp.auth.config;

import com.eking.momp.auth.exception.UsernameOrPasswordWrongException;
import com.eking.momp.common.util.Error;
import com.eking.momp.web.LocaleMessageHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class WebExcpetionHandler {

    public static final String CODE_INTERNAL_SERVER_ERROR ="002001";
    public static final String CODE_USERNAME_OR_PASSWORD_WRONG="002002";

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error common(Exception ex) {
        log.error(ex.getMessage(), ex);
        String message = LocaleMessageHolder.getMessage("exception.internalServerError");
        return Error.of(CODE_INTERNAL_SERVER_ERROR, message);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Error userAlreadyExists(UsernameOrPasswordWrongException ex) {
        log.error(ex.getMessage(), ex);
        String message = LocaleMessageHolder.getMessage("exception.usernameOrPasswordWrong");
        return Error.of(CODE_USERNAME_OR_PASSWORD_WRONG, message);
    }

}
