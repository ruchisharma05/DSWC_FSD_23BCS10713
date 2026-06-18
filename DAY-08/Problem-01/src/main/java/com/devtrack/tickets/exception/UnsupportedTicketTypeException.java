package com.devtrack.tickets.exception;

public class UnsupportedTicketTypeException extends RuntimeException {

    public UnsupportedTicketTypeException(String type) {
        super(buildMessage(type));
    }

    private static String buildMessage(String type) {
        return "Ticket type '" + type + "' is not supported. Supported types are BUG and FEATURE.";
    }
}
