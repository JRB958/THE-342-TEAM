package flights;

import java.time.LocalDateTime;
import airports.Airport;
import misc.Aircraft;
import misc.Airline;

public class PublicFlight extends Flight{
    
    private Airline handlerAirline;

    public PublicFlight(
        LocalDateTime scheduledDepart,
        LocalDateTime scheduledArrival,
        LocalDateTime actualDepart,
        LocalDateTime actualArrival,
        String flightNumber,
        Airport source,
        Airport destination,
        Airline airline,
        Aircraft aircraft) 
        {
            super(
            scheduledDepart,
            scheduledArrival,
            actualDepart,
            actualArrival,
            flightNumber,
            source,
            destination,
            aircraft);
            this.handlerAirline = airline;
        }
}
