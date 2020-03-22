package com.eking.momp.model.config;

import com.eking.momp.common.util.Error;
import com.eking.momp.model.exception.*;
import com.eking.momp.model.util.ResourceType;
import com.eking.momp.web.LocaleMessageHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.Serializable;

@Slf4j
@RestControllerAdvice
public class WebExcpetionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error common(Exception ex) {
        log.error(ex.getMessage(), ex);
        String message = LocaleMessageHolder.getMessage("exception.internalServerError");
        return Error.of("004001", message);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error userAlreadyExists(ResourceNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        ResourceType resourceType = ex.getResourceType();
        Serializable resouceId = ex.getResouceId();
        String resourceLabel = LocaleMessageHolder.getMessage(resourceType.getLabel());
        String message = LocaleMessageHolder.getMessage("exception.dataNotFound", resourceLabel + "(" + resouceId +
                ")");
        return Error.of("004002", message);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public Error alreadyExists(ResourceAlreadyExistsException ex) {
        log.error(ex.getMessage(), ex);
        String message = LocaleMessageHolder.getMessage("exception.alreadyExists", ex.getShowText());
        return Error.of("004003", message);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error inUsed(ResourceInUsedException ex) {
        log.error(ex.getMessage(), ex);
        ResourceType resourceType = ex.getResourceType();
        String resourceLabel = LocaleMessageHolder.getMessage(resourceType.getLabel());
        Serializable resourceName = ex.getResourceName();

        ResourceType refResourceType = ex.getRefResourceType();
        String reResourceLabel = LocaleMessageHolder.getMessage(refResourceType.getLabel());
        Serializable reResourceName = ex.getRefResourceName();

        String message = LocaleMessageHolder.getMessage("exception.inUsed", resourceLabel + "(" + resourceName + ")",
                reResourceLabel + "(" + reResourceName + ")");
        return Error.of("004004", message);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error business(BusinessException ex) {
        log.error(ex.getMessage(), ex);
        return Error.of("004005", ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Error forbidden(ForbiddenException ex) {
        log.error(ex.getMessage(), ex);
        String message = LocaleMessageHolder.getMessage("exception.noPermission");
        return Error.of("004006", message);
    }

}
