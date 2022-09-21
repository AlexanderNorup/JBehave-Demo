package com.alexandernorup.TennisBooking;

import com.alexandernorup.TennisBooking.Exceptions.BookingException;
import com.alexandernorup.TennisBooking.Exceptions.NoSuchCourtException;
import com.alexandernorup.TennisBooking.Exceptions.OverlappingBookingException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookingSystem {
    private List<TennisBooking> bookings;
    private final String[] courts;

    public BookingSystem(String... courts) {
        bookings = new ArrayList<>();
        this.courts = courts;
    }

    public String[] getCourts() {
        return courts;
    }

    public void addBooking(TennisBooking newBooking) throws BookingException {
        var bookingsInCourt = getBookingsForCourt(newBooking.getCourtId());

        if (bookingsInCourt.isEmpty()) {
            bookings.add(newBooking);
            return;
        }

        var newBookingStartTime = newBooking.getTimestamp();
        var newBookingEndTime = newBooking.getTimestamp().plus(newBooking.getDuration());

        //Check if overlapping
        for (var booking : bookingsInCourt) {
            var bookingStartTime = booking.getTimestamp();
            var bookingEndTime = booking.getTimestamp().plus(booking.getDuration());

            boolean timeIsOverlapping = timeIsOverlapping(newBookingStartTime, newBookingEndTime, bookingStartTime, bookingEndTime);

            if (timeIsOverlapping) {
                throw new OverlappingBookingException(booking, newBooking);
            }
        }

        bookings.add(newBooking);
    }

    public List<TennisBooking> getBookingsForCourt(String courtId) throws BookingException {
        if (Arrays.stream(courts).noneMatch(x -> x.equalsIgnoreCase(courtId))) {
            throw new NoSuchCourtException(courtId);
        }

        return bookings.stream().filter(x -> x.getCourtId().equalsIgnoreCase(courtId)).sorted().toList();
    }

    public List<TennisBooking> getAllBookings(){
        return bookings;
    }

    private boolean timeIsOverlapping(Instant time1Start, Instant time1End, Instant time2Start, Instant time2End) {
        return (time1Start.isAfter(time2Start) && time1Start.isBefore(time2End)) || (time1End.isAfter(time2Start) && time1End.isBefore(time2End));
    }
}
