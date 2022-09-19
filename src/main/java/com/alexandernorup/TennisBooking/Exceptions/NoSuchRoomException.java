package com.alexandernorup.TennisBooking.Exceptions;

public class NoSuchRoomException extends BookingException {
    private final String room;

    public NoSuchRoomException(String room) {
        super("The requested room did not exist");
        this.room = room;
    }

    public String getRoom() {
        return room;
    }
}
