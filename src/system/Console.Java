package system;
import airports.*;
import flights.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import actors.*;
import misc.*;

public class Console {
    private ArrayList<Airport> airports;
    private ArrayList<Flight> flights;
    private ArrayList<RegisteredClient> registeredClients;

    public Console() {
        this.airports = new ArrayList<Airport>();
        this.flights = new ArrayList<Flight>();
        this.registeredClients = new ArrayList<RegisteredClient>();
    }

    public void addAirport(Airport airport) {
        this.airports.add(airport);
    }

    public void addFlight(Flight flight) {
        this.flights.add(flight);
    }

    public void addRegisteredClient(RegisteredClient client) {
        if (clientExists(client.getEmail())) {
            throw new IllegalArgumentException("Client already exists");
        }
        this.registeredClients.add(client);
    }

    public Boolean clientExists(String email) {
        for (RegisteredClient client : this.registeredClients) {
            if (client.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public void login(String email, String password) {
        for (RegisteredClient client : this.registeredClients) {
            if (client.getEmail().equals(email) && client.getPassword().equals(password)) {
                System.out.println("Login successful");
            }
        }
        System.out.println("Login failed");
    }

    public void viewFlights(Airport source, Airport destination, Actor actor) {

        System.out.println("Viewing flights from " + source.getAirportName() + " to " + destination.getAirportName()
                + ". Request by " + actor.getAuth());
        for (Flight flight : this.flights) {

            if (flight.getSource().equals(source) && flight.getDestination().equals(destination)) {

                if (flight instanceof PrivateFlight) {

                    if (actor instanceof AirportAdmin) {
                        
                        if (source.equals(((AirportAdmin) actor).getAirport())
                                || destination.equals(((AirportAdmin) actor).getAirport())) {
                            printFlightDetails(flight, Auth.AIRPORT_ADMIN);
                        }
                   
                    } else {
                        System.out.println("You are not autherized to view this private flight.");
                    }
                
                } else if (!(flight instanceof PrivateFlight)) {
                    System.out.println("Flight type: " + flight.getClass().getName());
                    printFlightDetails(flight, actor.getAuth());
                }
            }
        }
    }

    public void printFlightDetails(Flight flight, Auth auth) {
        if (auth == Auth.UNREGISTERED) {
            System.out.println("Flight " + flight.getFlightNumber() + ": \n\t" + flight.getSource().getAirportName()
                    + " to " + flight.getDestination().getAirportName() + "\n");
        } else if (auth == Auth.REGISTERED || auth == Auth.AIRPORT_ADMIN || auth == Auth.AIRLINE_ADMIN
                || auth == Auth.ADMIN) {
            System.out.println("Flight " + flight.getFlightNumber() + ": \n\t" + flight.getSource().getAirportName()
                    + " to " + flight.getDestination().getAirportName() + "\n\tAirline: "
                    + flight.getAirline().getAirlineName()
                    + "\n\tAircraft: " + flight.getAircraft().getAircraftName() + "\n");
        }
    }

}