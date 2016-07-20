package org.innopolis.mammba.poker.engine.errors;

public class InvalidStateError extends RuntimeException {
    public InvalidStateError(String msg) {
        super("Invalid state: " + msg);
    }
}
