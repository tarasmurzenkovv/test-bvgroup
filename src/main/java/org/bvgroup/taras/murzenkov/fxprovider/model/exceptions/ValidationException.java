package org.bvgroup.taras.murzenkov.fxprovider.model.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ValidationException extends RuntimeException {
    private final String message;
}
