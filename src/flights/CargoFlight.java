package flights;

import java.time.LocalDateTime;
import airports.Airport;
import misc.Aircraft;
import misc.Airline;

public class CargoFlight extends PublicFlight{
    
    public CargoFlight(
        LocalDateTime scheduledDepart,
        LocalDateTime scheduledArrival,
        LocalDateTime actualDepart,
        LocalDateTime actualArrival,
        String flightNumber,
        Airport source,
        Airport destination,
        Airline handlerAirline,
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
                handlerAirline, 
                aircraft
            );
        }
}
