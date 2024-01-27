package org.bvgroup.taras.murzenkov.fxprovider.advice;

import org.bvgroup.taras.murzenkov.fxprovider.model.response.ErrorResponse;
import org.bvgroup.taras.murzenkov.fxprovider.model.exceptions.ValidationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handle(ValidationException exception) {
        return new ErrorResponse(exception.getMessage());
    }
}
