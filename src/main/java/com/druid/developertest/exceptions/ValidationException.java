package com.druid.developertest.exceptions;

public class ValidationException extends RuntimeException {
	private static final String DESCRIPTION = "Input Parameters Failed";

    public ValidationException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }
}
