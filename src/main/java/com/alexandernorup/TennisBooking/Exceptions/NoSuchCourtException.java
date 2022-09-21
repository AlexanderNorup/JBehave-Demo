package com.alexandernorup.TennisBooking.Exceptions;

public class NoSuchCourtException extends BookingException {
    private final String court;

    public NoSuchCourtException(String court) {
        super("The requested court did not exist");
        this.court = court;
    }

    public String getCourt() {
        return court;
    }
}
