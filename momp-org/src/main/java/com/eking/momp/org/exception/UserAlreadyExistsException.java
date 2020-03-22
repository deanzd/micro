package com.eking.momp.org.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserAlreadyExistsException extends RuntimeException{

    private String username;

}
