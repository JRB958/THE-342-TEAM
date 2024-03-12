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
    private Airline handlerAirline;

    private Aircraft flightAircraft;
    
    public Flight(
        LocalDateTime scheduledDepart,
        LocalDateTime scheduledArrival,
        LocalDateTime actualDepart,
        LocalDateTime actualArrival,
        String flightNumber,
        Airport aSource,
        Airport aDestination,
        Aircraft flightAircraft,
        Airline handlerAirline) 
        {
        this.scheduledDepart = scheduledDepart;
        this.scheduledArrival = scheduledArrival;
        this.actualDepart = actualDepart;
        this.actualArrival = actualArrival;
        this.flightNumber = flightNumber;
        //ensures unique destination and source. ensure the implementation of equals in 
        if (aSource.getAirportCode().equals(aDestination.getAirportCode())) {
            throw new IllegalArgumentException("Source and destination cannot be the same");
        }
        this.source = aSource;
        this.destination = aDestination;
        this.flightAircraft = flightAircraft;
        this.handlerAirline = handlerAirline;
    }

    public Airport getSource() {
        return source;
    }

    public LocalDateTime getScheduledDepart() {
        return scheduledDepart;
    }

    public LocalDateTime getScheduledArrival() {
        return scheduledArrival;
    }

    public LocalDateTime getActualDepart() {
        return actualDepart;
    }

    public LocalDateTime getActualArrival() {
        return actualArrival;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public void setSource(Airport source) {
        this.source = source;
    }

    public Airport getDestination() {
        return destination;
    }

    public void setDestination(Airport destination) {
        this.destination = destination;
    }

    public Airline getAirline() {
        return handlerAirline;
    }
    
    public Aircraft getAircraft() {
        return flightAircraft;
    }
}