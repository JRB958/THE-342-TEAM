package flights;

import airports.*;
import misc.Aircraft;

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
            this.handlerAirport = source; 
        }


}
