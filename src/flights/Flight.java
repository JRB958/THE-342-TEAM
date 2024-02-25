package flights;
import java.util.ArrayList;
import java.util.List;
import airports.*;
import misc.*;

import java.time.LocalDateTime;

public class Flight {
    private List<Flight> listOfFlights = new ArrayList<>();

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
        Aircraft flightAircraft) 
        {
        this.scheduledDepart = scheduledDepart;
        this.scheduledArrival = scheduledArrival;
        this.actualDepart = actualDepart;
        this.actualArrival = actualArrival;
        this.flightNumber = flightNumber;
        //ensures unique destination and source. ensure the implementation of equals in 
        if(aSource != aDestination){
            this.source = aSource;
            this.destination = aDestination;   
        }
        this.flightAircraft = flightAircraft;
    }

    public void addFlight(Flight flight) {
        listOfFlights.add(flight);
    }

    public List<Flight> getListOfFlights() {
        return listOfFlights;
    }

    public List<Flight> viewFlight(Airport flightSource, Airport flightDestination) {
        List<Flight> matchingFlights = new ArrayList<>();
    
        for (Flight flight : listOfFlights) {
            if (flight.source.equals(flightSource) && flight.destination.equals(flightDestination)) {
                matchingFlights.add(flight);
            }
        }
    
        return matchingFlights;
    }
    
}
