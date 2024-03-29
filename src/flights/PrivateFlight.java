package flights;

import airports.*;
import misc.Aircraft;
import misc.Airline;

import java.time.LocalDateTime;

public class PrivateFlight extends Flight{
    
    private Airport handlerAirport;

    public PrivateFlight(
        LocalDateTime scheduledDepart,
        LocalDateTime scheduledArrival,
        LocalDateTime actualDepart,
        LocalDateTime actualArrival,
        String flightNumber,
        Airport source,
        Airport destination,
        Airport handlerAirport,
        Aircraft aircraft,
        Airline airline,
        String type) 
        {
            super(
            scheduledDepart,
            scheduledArrival,
            actualDepart,
            actualArrival,
            flightNumber,
            source,
            destination,
            aircraft,
            airline);
            super.setType(type);
            this.handlerAirport = handlerAirport;
        }


}
