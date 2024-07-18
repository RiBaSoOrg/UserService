package com.ribaso.userservice.port.user.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ribaso.userservice.core.domain.service.exceptions.UnknownUserException;

@ControllerAdvice
public class UnknownUserAdvice {

    @ResponseBody
    @ExceptionHandler(value = UnknownUserException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String unknownUserHandler(UnknownUserException ex) {
        return ex.getMessage();
    }
}
