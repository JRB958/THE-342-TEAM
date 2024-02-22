package flights;

import java.time.LocalDateTime;
import airports.Airport;
import misc.Airline;

public class PublicFlight extends Flight{
    
    private Airline flightAirline;

    public PublicFlight(
        LocalDateTime scheduledDepart,
        LocalDateTime scheduledArrival,
        LocalDateTime actualDepart,
        LocalDateTime actualArrival,
        String flightNumber,
        Airport source,
        Airport destination) 
        {
            super(
            scheduledDepart,
            scheduledArrival,
            actualDepart,
            actualArrival,
            flightNumber,
            source,
            destination);
        }
}
