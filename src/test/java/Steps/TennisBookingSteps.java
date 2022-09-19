package Steps;

import com.alexandernorup.TennisBooking.*;
import com.alexandernorup.TennisBooking.Exceptions.BookingException;
import com.alexandernorup.TennisBooking.Exceptions.NoSuchRoomException;
import com.alexandernorup.TennisBooking.Exceptions.OverlappingBookingException;
import org.hamcrest.Matchers;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
public class TennisBookingSteps {
    private BookingSystem bookingSystem;
    private List<Throwable> runtimeExceptions = new ArrayList<>();

    @BeforeScenario
    public void resetExceptions(){
        runtimeExceptions.clear();
    }

    @Given("there is $num rooms in the booking system")
    public void createRooms(int num){
        String[] rooms = new String[num];
        for (int i = 0; i < num; i++){
            rooms[i] = "room " + (i+1);
        }
        bookingSystem = new BookingSystem(rooms);
    }

    @When("all rooms are fully booked")
    public void fillAllRooms(){
        Arrays.stream(bookingSystem.getRooms())
                .forEach(x -> {
                    try {
                        bookingSystem.addBooking(new TennisBooking(getStartOfToday(), Duration.ofHours(24), "test", x));
                    } catch (BookingException e) {
                        runtimeExceptions.add(e);
                    }
                });
    }

    @When("I add a $duration booking starting at $time in room $roomNum")
    public void addBooking(String duration, int time, int roomNum){
        var timestamp = getStartOfToday().plus(Duration.ofHours(time));
        var parsedDuration = Duration.parse(duration);
        var room = "room " + roomNum;

        try {
            bookingSystem.addBooking(new TennisBooking(timestamp, parsedDuration, "test", room));
        } catch (BookingException e) {
            runtimeExceptions.add(e);
        }
    }

    @Then("there should be no exceptions")
    public void assertNoExceptions() {
        assertThat(this.runtimeExceptions.isEmpty(), Matchers.is(true));
    }

    @Then("there is a booking exception")
    public void assertBookingException () {
        assertThat(this.runtimeExceptions.stream().anyMatch(x-> x instanceof BookingException), Matchers.is(false));
    }

    @Then("there is a overlapping booking exception")
    public void assertOverlappingBookingException () {
        assertThat(this.runtimeExceptions.stream().anyMatch(x-> x instanceof OverlappingBookingException), Matchers.is(true));
    }

    @Then("there is a no such room exception")
    public void assertNoSuchRoomException () {
        assertThat(this.runtimeExceptions.stream().anyMatch(x-> x instanceof NoSuchRoomException), Matchers.is(true));
    }

    @Then("there should be $num bookings in total")
    public void assertNumBookingInRoom(int num){
        assertThat(this.bookingSystem.getAllBookings().size(), Matchers.is(num));
    }

    @Then("there should be $num bookings in room $roomNum")
    public void assertNumBookingInRoom(int num, int roomNum) throws BookingException {
        assertThat(this.bookingSystem.getBookingsForRoom("room " + roomNum).size(), Matchers.equalTo(num));
    }

    private Instant getStartOfToday(){
        return LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC);
    }
}
