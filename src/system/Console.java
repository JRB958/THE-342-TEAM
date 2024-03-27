package system;
import airports.*;
import flights.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import actors.*;
import misc.*;
import sqlite.SQLiteConnection;

public class Console {
    private ArrayList<Airport> airports;
    private ArrayList<Flight> flights;
    private ArrayList<RegisteredClient> registeredClients;
    private ArrayList<Airline> airlines;
    private Actor currentActor;

    public Console() {
        this.airports = new ArrayList<Airport>();
        this.flights = new ArrayList<Flight>();
        this.registeredClients = new ArrayList<RegisteredClient>();
        this.currentActor = new PublicClient();
        this.airlines = new ArrayList<Airline>();
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

    public ArrayList<Airport> getAirports() {
        return this.airports;
    }

    public boolean airportExists(String airportCode) {
        for (Airport airport : this.airports) {
            if (airport.getAirportCode().equals(airportCode)) {
                return true;
            }
        }
        return false;
    }

    public boolean flightExists(String flightNumber) {
        for (Flight flight : this.flights) {
            if (flight.getFlightNumber().equals(flightNumber)) {
                return true;
            }
        }
        return false;
    }

    public Airport getAirport(String airportCode) {
        for (Airport airport : this.airports) {
            if (airport.getAirportCode().equals(airportCode)) {
                return airport;
            }
        }
        return null;
    }

    public Actor getCurrentActor() {
        return this.currentActor;
    }

    public Aircraft getAircraft(String aircraftName) {
        for (Flight flight : this.flights) {
            if (flight.getAircraft().getAircraftName().equals(aircraftName)) {
                return flight.getAircraft();
            }
        }
        return null;
    }

    public Boolean airlineExists(String airlineName) {
        for (Airline airline : this.airlines) {
            if (airline.getAirlineName().equals(airlineName)) {
                return true;
            }
        }
        return false;
    }

    public Airline getAirline(String airlineName) {
        for (Airline airline : this.airlines) {
            if (airline.getAirlineName().equals(airlineName)) {
                return airline;
            }
        }
        return null;
    }

    public ArrayList<Flight> getFlights(){
        return this.flights;
    }

    public void login(String email, String password) {
        for (RegisteredClient client : this.registeredClients) {
            if (client.getEmail().equals(email) && client.getPassword().equals(password)) {
                System.out.println("Login successful");
                this.currentActor = client;
            }
        }
        System.out.println("Login failed");
    }

    public void viewFlights(Airport source, Airport destination, Actor actor) {

        boolean flightExists = false;
        System.out.println("Viewing flights from " + source.getAirportName() + " to " + destination.getAirportName()
                + ". Request by " + actor.getAuth());
        for (Flight flight : this.flights) {

            if (flight.getSource().equals(source) && flight.getDestination().equals(destination)) {
                flightExists = true;
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

        if (!flightExists) {
            System.out.println("No flights available from " + source.getAirportName() + " to " + destination.getAirportName());
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

    public boolean hasFlightsSource(String date, Airport source) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (Flight flight : this.flights) {
            String formattedDate = flight.getScheduledDepart().format(formatter);
            if (flight.getSource().equals(source) && formattedDate.equals(date)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasFlightsDestination(String date, Airport destination) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (Flight flight : this.flights) {
            String formattedDate = flight.getScheduledArrival().format(formatter);
            if (flight.getDestination().equals(destination) && formattedDate.equals(date)) {
                return true;
            }
        }
        return false;
    }

    public Airport airportExist(String code){
        for (Airport airport: airports){
            if (airport.getAirportCode().equals(code));
            return airport;
        }
        return null;
    }

    public Boolean aircraftExist(String aircraft, Airport airport){
        return true;
        
    }

    public Boolean airlineExist(String airline){
        return true;
    }

    public void init() {
        System.out.println("Initializing console...");

        System.out.println("Creating airports, airlines, aircrafts and flights from db...");
        
        // Create airports
        HashMap<Integer, City> cities= SQLiteConnection.getCities();
        HashMap<Integer, Airport> airports = SQLiteConnection.getAirports();
        
        for (Airport airport : airports.values()){
            City city = cities.get(airport.getCityID());
            airport.setAirportCity(city);
            this.addAirport(airport);
        }

        // Create airlines
        HashMap<Integer, Airline> airlines = SQLiteConnection.getAirlines();
        for (Airline airline : airlines.values()){
            this.airlines.add(airline);
        }

        // Create aircrafts
        HashMap<Integer, Aircraft> aircrafts = SQLiteConnection.getAircrafts();
        for (Aircraft aircraft : aircrafts.values()){
            aircraft.setHostingAirport(airports.get(aircraft.getHostingAirportID()));
            airlines.get(aircraft.getAirlineID()).addAircraft(aircraft);
        }

        // Create flights
        HashMap<Integer, Flight> flights = SQLiteConnection.getFlights();
        for (Flight flight : flights.values()){
            flight.setSource(airports.get(flight.getSourceID()));
            flight.setDestination(airports.get(flight.getDestinationID()));
            flight.setHandlerAirline(airlines.get(flight.getAirlineID()));
            flight.setFlightAircraft(aircrafts.get(flight.getAircraftID()));
            this.addFlight(flight);
        }

        // Create registered clients
        HashMap<Integer, RegisteredClient> clients = SQLiteConnection.getRegisteredClients();
        for (RegisteredClient client : clients.values()){
            this.addRegisteredClient(client);
        }

        for (Flight flight : this.flights){
            System.out.println(flight.toString());
        }

        System.out.println("Console initialized");
    }

}