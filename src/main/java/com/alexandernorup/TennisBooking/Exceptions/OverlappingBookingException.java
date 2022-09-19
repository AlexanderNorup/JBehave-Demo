package com.alexandernorup.TennisBooking.Exceptions;

import com.alexandernorup.TennisBooking.TennisBooking;

public class OverlappingBookingException extends BookingException {
    private final TennisBooking oldBooking;
    private final TennisBooking newBooking;

    public OverlappingBookingException(TennisBooking oldBooking, TennisBooking newBooking) {
        super("You cannot double-book a timeslot");
        this.oldBooking = oldBooking;
        this.newBooking = newBooking;
    }

    public TennisBooking getOldBooking() {
        return oldBooking;
    }

    public TennisBooking getNewBooking() {
        return newBooking;
    }
}
