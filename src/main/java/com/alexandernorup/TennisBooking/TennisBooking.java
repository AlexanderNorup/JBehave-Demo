package com.alexandernorup.TennisBooking;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class TennisBooking implements Comparable<TennisBooking>{
    private Instant timestamp;
    private Duration duration;
    private String bookee;
    private String roomId;

    public TennisBooking(Instant timestamp, Duration duration, String bookee, String roomId) {
        this.timestamp = timestamp;
        this.duration = duration;
        this.bookee = bookee;
        this.roomId = roomId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getBookee() {
        return bookee;
    }

    public void setBookee(String bookee) {
        this.bookee = bookee;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    @Override
    public int compareTo(TennisBooking o) {
        return o.getTimestamp().compareTo(o.getTimestamp());
    }
}
