package flights;

import airports.*;
import misc.*;

import java.time.LocalDateTime;

public class Flight {

    private LocalDateTime scheduledDepart;
    private LocalDateTime scheduledArrival;
    private LocalDateTime actualDepart;
    private LocalDateTime actualArrival;

    private String flightNumber;
    private Airport source;
    private Airport destination;
    private Aircraft flightAircraft;
    
    public Flight(
        LocalDateTime scheduledDepart,
        LocalDateTime scheduledArrival,
        LocalDateTime actualDepart,
        LocalDateTime actualArrival,
        String flightNumber,
        Airport source,
        Airport destination) 
        {
        this.scheduledDepart = scheduledDepart;
        this.scheduledArrival = scheduledArrival;
        this.actualDepart = actualDepart;
        this.actualArrival = actualArrival;
        this.flightNumber = flightNumber;
        this.source = source;
        this.destination = destination;
    }
    
}
