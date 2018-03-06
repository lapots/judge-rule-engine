package com.lapots.breed.judge.rest.exception;

/**
 * Exception for incorrect request data.
 */
public class IncorrectRequestContentException extends RuntimeException {

    /**
     * Constructor.
     * @param message error message
     */
    public IncorrectRequestContentException(String message) {
        super(message);
    }
}
