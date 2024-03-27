package flights;

import airports.*;
import misc.*;
import java.time.LocalDateTime;

public class Flight {
    private int flightID;
    private LocalDateTime scheduledDepart;
    private LocalDateTime scheduledArrival;
    private LocalDateTime actualDepart;
    private LocalDateTime actualArrival;
    private String flightNumber;
    private int sourceID;
    private Airport source;
    private int destinationID;
    private Airport destination;
    private int airlineID;
    private Airline handlerAirline;
    private int aircraftID;
    private Aircraft flightAircraft;
    private String type;

    public Flight() {}

    public Flight(LocalDateTime scheduledDepart, LocalDateTime scheduledArrival, LocalDateTime actualDepart,
            LocalDateTime actualArrival, String flightNumber, Airport aSource, Airport aDestination,
            Aircraft flightAircraft, Airline handlerAirline) {
        this.scheduledDepart = scheduledDepart;
        this.scheduledArrival = scheduledArrival;
        this.actualDepart = actualDepart;
        this.actualArrival = actualArrival;
        this.flightNumber = flightNumber;
        if (aSource.getAirportCode().equals(aDestination.getAirportCode())) {
            throw new IllegalArgumentException("Source and destination cannot be the same");
        }
        this.source = aSource;
        this.destination = aDestination;
        this.flightAircraft = flightAircraft;
        this.handlerAirline = handlerAirline;
    }

    // Getters and setters
    public int getFlightID() {
        return flightID;
    }

    public void setFlightID(int flightID) {
        this.flightID = flightID;
    }

    public LocalDateTime getScheduledDepart() {
        return scheduledDepart;
    }

    public void setScheduledDepart(LocalDateTime scheduledDepart) {
        this.scheduledDepart = scheduledDepart;
    }

    public LocalDateTime getScheduledArrival() {
        return scheduledArrival;
    }

    public void setScheduledArrival(LocalDateTime scheduledArrival) {
        this.scheduledArrival = scheduledArrival;
    }

    public LocalDateTime getActualDepart() {
        return actualDepart;
    }

    public void setActualDepart(LocalDateTime actualDepart) {
        this.actualDepart = actualDepart;
    }

    public LocalDateTime getActualArrival() {
        return actualArrival;
    }

    public void setActualArrival(LocalDateTime actualArrival) {
        this.actualArrival = actualArrival;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public int getSourceID() {
        return sourceID;
    }

    public void setSourceID(int sourceID) {
        this.sourceID = sourceID;
    }

    public Airport getSource() {
        return source;
    }

    public void setSource(Airport source) {
        this.source = source;
    }

    public int getDestinationID() {
        return destinationID;
    }

    public void setDestinationID(int destinationID) {
        this.destinationID = destinationID;
    }

    public Airport getDestination() {
        return destination;
    }

    public void setDestination(Airport destination) {
        this.destination = destination;
    }

    public int getAirlineID() {
        return airlineID;
    }

    public void setAirlineID(int airlineID) {
        this.airlineID = airlineID;
    }

    public Airline getHandlerAirline() {
        return handlerAirline;
    }

    public void setHandlerAirline(Airline handlerAirline) {
        this.handlerAirline = handlerAirline;
    }

    public int getAircraftID() {
        return aircraftID;
    }

    public void setAircraftID(int aircraftID) {
        this.aircraftID = aircraftID;
    }

    public Aircraft getFlightAircraft() {
        return flightAircraft;
    }

    public void setFlightAircraft(Aircraft flightAircraft) {
        this.flightAircraft = flightAircraft;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // public Aircraft getAircraft() {
    //     return flightAircraft;
    // }

    // public Airline getAirline() {
    //     return handlerAirline;
    // }

    @Override
    public String toString() {
        return "Flight [scheduledDepart=" + scheduledDepart + ", scheduledArrival=" + scheduledArrival
                + ", actualDepart=" + actualDepart + ", actualArrival=" + actualArrival + ", flightNumber="
                + flightNumber + ", source=" + source + ", destination=" + destination + ", handlerAirline="
                + handlerAirline + ", flightAircraft=" + flightAircraft + "]";
    }
}
