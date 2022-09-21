package com.alexandernorup.TennisBooking;

import java.time.Duration;
import java.time.Instant;

public class TennisBooking implements Comparable<TennisBooking>{
    private Instant timestamp;
    private Duration duration;
    private String bookee;
    private String courtId;

    public TennisBooking(Instant timestamp, Duration duration, String bookee, String courtId) {
        this.timestamp = timestamp;
        this.duration = duration;
        this.bookee = bookee;
        this.courtId = courtId;
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

    public String getCourtId() {
        return courtId;
    }

    public void setCourtId(String courtId) {
        this.courtId = courtId;
    }

    @Override
    public int compareTo(TennisBooking o) {
        return o.getTimestamp().compareTo(o.getTimestamp());
    }
}
