package com.goeuro.challenge.exceptions;

import org.apache.log4j.Logger;

public class InvalidInputException extends IllegalArgumentException {

    private static final Logger logger = Logger.getLogger(InvalidInputException.class);

    InputErrorType error;

    public InvalidInputException(final InputErrorType errorType) {
        super(errorType.getMessage());
        logger.debug(errorType.getMessage());
        this.error = errorType;
    }

    public InputErrorType getErrorType() {
        return error;
    }
}
