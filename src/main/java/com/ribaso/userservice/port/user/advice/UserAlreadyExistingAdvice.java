package com.ribaso.userservice.port.user.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ribaso.userservice.core.domain.service.exceptions.UserAlreadyExistingException;

@ControllerAdvice
public class UserAlreadyExistingAdvice {
    
    @ResponseBody
    @ExceptionHandler(value = UserAlreadyExistingException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String unknownUserHandler(UserAlreadyExistingException ex) {
        return ex.getMessage();
    }
}
