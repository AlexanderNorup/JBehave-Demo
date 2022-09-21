package Steps;

import com.alexandernorup.TennisBooking.*;
import com.alexandernorup.TennisBooking.Exceptions.BookingException;
import com.alexandernorup.TennisBooking.Exceptions.NoSuchCourtException;
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

    @Given("there is $num courts in the booking system")
    public void createCourts(int num){
        String[] courts = new String[num];
        for (int i = 0; i < num; i++){
            courts[i] = "court " + (i+1);
        }
        bookingSystem = new BookingSystem(courts);
    }

    @When("all courts are fully booked")
    public void fillAllCourts(){
        Arrays.stream(bookingSystem.getCourts())
                .forEach(x -> {
                    try {
                        bookingSystem.addBooking(new TennisBooking(getStartOfToday(), Duration.ofHours(24), "test", x));
                    } catch (BookingException e) {
                        runtimeExceptions.add(e);
                    }
                });
    }

    @When("I add a $duration booking starting at $time in court $courtNum")
    public void addBooking(String duration, int time, int courtNum){
        var timestamp = getStartOfToday().plus(Duration.ofHours(time));
        var parsedDuration = Duration.parse(duration);
        var court = "court " + courtNum;

        try {
            bookingSystem.addBooking(new TennisBooking(timestamp, parsedDuration, "test", court));
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

    @Then("there is a no such court exception")
    public void assertNoSuchCourtException() {
        assertThat(this.runtimeExceptions.stream().anyMatch(x-> x instanceof NoSuchCourtException), Matchers.is(true));
    }

    @Then("there should be $num bookings in total")
    public void assertNumBookingInCourt(int num){
        assertThat(this.bookingSystem.getAllBookings().size(), Matchers.is(num));
    }

    @Then("there should be $num bookings in court $courtNum")
    public void assertNumBookingInCourt(int num, int courtNum) throws BookingException {
        assertThat(this.bookingSystem.getBookingsForCourt("court " + courtNum).size(), Matchers.equalTo(num));
    }

    private Instant getStartOfToday(){
        return LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC);
    }
}
