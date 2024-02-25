package system;

import airports.*;
import flights.*;
import actors.*;
import misc.*;

public class Console {
    private ArrayList<Airport> airports;
    private ArrayList<Flight> flights;

    public Console() {
        this.airports = new ArrayList<>();
        this.flights = new ArrayList<>();
    }

    public void addAirport(Airport airport) {
        this.airports.add(airport);
    }

    public void addFlight(Flight flight) {
        this.flights.add(flight);
    }

    public void viewFlights(Airport source, Airport destination, Actor actor) {

        for (Flight flight : this.flights) {
            if (flight.getSource().equals(source) && flight.getDestination().equals(destination)) {
                if (flight instanceof PrivateFlight) {
                    if (actor instanceof AirportAdmin) {
                        if (source.equals(((AirportAdmin) actor).getAirport()) || destination.equals(((AirportAdmin) actor).getAirport())) {
                            printFlightDetails(flight, Auth.AIRPORT_ADMIN);
                        }
                    }
                } else {
                    if (actor.getAuth() == Auth.UNREGISTERED) {
                        printFlightDetails(flight, Auth.UNREGISTERED);
                    } else {
                        printFlightDetails(flight, Auth.REGISTERED);
                    }
                }
            }
        }
    }

    public void printFlightDetails(Flight flight, Auth auth) {
        if (auth == Auth.UNREGISTERED) {
            System.out.println("Flight " + flight.getFlightNumber() + ": \n\t" + flight.getSource().getAirportName()
                    + " to " + flight.getDestination().getAirportName());
        } else if (auth == Auth.REGISTERED || auth == Auth.AIRPORT_ADMIN || auth == Auth.AIRLINE_ADMIN || auth == Auth.ADMIN) {
            System.out.println("Flight " + flight.getFlightNumber() + ": \n\t" + flight.getSource().getAirportName()
                    + " to " + flight.getDestination().getAirportName()) + "\n Airline: " + flight.getAirline().getAirlineName() 
                    + "\n Aircraft: " + flight.getAircraft().getAircraftName();
        }
    }
}