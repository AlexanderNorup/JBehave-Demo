Meta:

Narrative:

As a tennis player
I want to book tennis courts in the facility
So that I have a place to play tennis.

Scenario: Add a single booking

Given there is 3 courts in the booking system
When I add a PT5H booking starting at 11 in court 2
Then there should be no exceptions
And there should be 1 bookings in court 2

Scenario: All bookings are filled out and another booking is added

Given there is 3 courts in the booking system
When all courts are fully booked
And I add a PT1H booking starting at 15 in court 1
Then there is a overlapping booking exception

Scenario: Adding two non-overlapping bookings

Given there is 1 courts in the booking system
When I add a PT1H booking starting at 12 in court 1
And I add a PT1H booking starting at 14 in court 1
Then there should be no exceptions
And there should be 2 bookings in court 1


Scenario: Adding two non-overlapping bookings in different courts

Given there is 2 courts in the booking system
When I add a PT1H booking starting at 12 in court 1
And I add a PT1H booking starting at 12 in court 2
Then there should be no exceptions
And there should be 2 bookings in total

Scenario: You book in a court that does not exist

Given there is 2 courts in the booking system
When I add a PT1H booking starting at 12 in court 3
Then there is a no such court exception
And there should be 0 bookings in total

