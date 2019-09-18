package com.accenture.library.exceptions;

public class LibraryException extends RuntimeException {

    public LibraryException(final String message) {
        super(message);
    }

    public LibraryException(final String message, final Exception exception ){
        super(message, exception);
    }

}
