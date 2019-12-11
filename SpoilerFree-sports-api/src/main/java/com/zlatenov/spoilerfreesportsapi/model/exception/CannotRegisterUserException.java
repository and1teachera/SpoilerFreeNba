package com.zlatenov.spoilerfreesportsapi.model.exception;

import lombok.NoArgsConstructor;

/**
 * @author Angel Zlatenov
 */
@NoArgsConstructor
public class CannotRegisterUserException extends Exception {

    public CannotRegisterUserException(final String message) {
        super(message);
    }
}
