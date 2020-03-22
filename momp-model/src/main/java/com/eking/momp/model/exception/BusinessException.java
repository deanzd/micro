package com.eking.momp.model.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String message;

}

