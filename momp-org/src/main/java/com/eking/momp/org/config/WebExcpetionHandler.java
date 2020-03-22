package com.eking.momp.org.config;

import com.eking.momp.common.util.Error;
import com.eking.momp.org.exception.UserAlreadyExistsException;
import com.eking.momp.web.LocaleMessageHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class WebExcpetionHandler {

    public static final String CODE_INTERNAL_SERVER_ERROR ="001001";
    public static final String CODE_USER_ALREADY_EXISTS="001002";

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error common(Exception ex) {
        log.error(ex.getMessage(), ex);
        String message = LocaleMessageHolder.getMessage("exception.internalServerError");
        return Error.of(CODE_INTERNAL_SERVER_ERROR, message);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public Error userAlreadyExists(UserAlreadyExistsException ex) {
        log.error(ex.getMessage(), ex);
        String message = LocaleMessageHolder.getMessage("excpetion.userAlreadyExists", ex.getUsername());
        return Error.of(CODE_USER_ALREADY_EXISTS, message);
    }

}
