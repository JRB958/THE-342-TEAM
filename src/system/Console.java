package system;
import airports.*;
import flights.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import actors.*;
import misc.*;

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

        System.out.println("Creating default airports, airlines, aircrafts and flights...");
        Airport airport1 = new Airport();
        airport1.setAirportName("Murtala Muhammed International Airport");
        airport1.setAirportCode("LOS");
        City city1 = new City();
        city1.setName("Lagos");
        city1.setCountry("Nigeria");
        airport1.setAirportCity(city1);
        this.addAirport(airport1);
        

        Airport airport2 = new Airport();
        airport2.setAirportName("Heathrow Airport");
        airport2.setAirportCode("LHR");
        City city2 = new City();
        city2.setName("London");
        city2.setCountry("United Kingdom");
        airport2.setAirportCity(city2);
        this.addAirport(airport2);

        Airport airport3 = new Airport();
        airport3.setAirportName("JFK International Airport");
        airport3.setAirportCode("JFK");
        City city3 = new City();
        city3.setName("New York");
        city3.setCountry("United States");
        airport3.setAirportCity(city3);
        this.addAirport(airport3);

        Airport airport4 = new Airport();
        airport4.setAirportName("Charles de Gaulle Airport");
        airport4.setAirportCode("CDG");
        City city4 = new City();
        city4.setName("Paris");
        city4.setCountry("France");
        airport4.setAirportCity(city4);
        this.addAirport(airport4);

        // Create aircrafts and link some to an airport
        Aircraft aircraft1 = new Aircraft(false, "Boeing 747");
        aircraft1.setHostingAirport(airport1);
        airport1.addAircraft(aircraft1);

        Aircraft aircraft2 = new Aircraft(true, "Boeing 737");
        aircraft2.setHostingAirport(airport2);
        airport2.addAircraft(aircraft2);

        Aircraft aircraft3 = new Aircraft(false, "Airbus A380");
        aircraft3.setHostingAirport(airport3);
        airport3.addAircraft(aircraft3);

        Aircraft aircraft4 = new Aircraft(true, "Airbus A320");
        aircraft4.setHostingAirport(airport4);
        airport4.addAircraft(aircraft4);

        Aircraft aircraft5 = new Aircraft(false, "Boeing 777");
        aircraft5.setHostingAirport(airport1);
        airport1.addAircraft(aircraft5);

        // Create airlines and register them in the console and buy an aircraft
        Airline airline1 = new Airline(aircraft1, "British Airways");
        this.airlines.add(airline1);
        airline1.buyAnAircraft(aircraft1);

        Airline airline2 = new Airline(aircraft2, "Virgin Atlantic");
        this.airlines.add(airline2);
        airline1.buyAnAircraft(aircraft2);

        Airline airline3 = new Airline(aircraft3, "Delta Airlines");
        this.airlines.add(airline3);

        Airline airline4 = new Airline(aircraft4, "Air France");
        this.airlines.add(airline4);

        Airline airline5 = new Airline(aircraft5, "Lufthansa");
        this.airlines.add(airline5);

        // Create flights
        Flight flight1 = new Flight(
            LocalDateTime.parse("2021-12-12T12:00:00"),
            LocalDateTime.parse("2021-12-12T18:00:00"),
            LocalDateTime.parse("2021-12-12T12:00:00"),
            LocalDateTime.parse("2021-12-12T18:00:00"),
            "BA123",
            airport1,
            airport3,
            aircraft1,
            airline1
            );
        this.addFlight(flight1);

        Flight flight2 = new Flight(
            LocalDateTime.parse("2021-12-12T12:00:00"),
            LocalDateTime.parse("2021-12-12T18:00:00"),
            LocalDateTime.parse("2021-12-12T12:00:00"),
            LocalDateTime.parse("2021-12-12T18:00:00"),
            "VS123",
            airport2,
            airport1,
            aircraft2,
            airline2
        );
        this.addFlight(flight2);

        Flight flight3 = new Flight(
            LocalDateTime.parse("2021-12-12T12:00:00"),
            LocalDateTime.parse("2021-12-12T18:00:00"),
            LocalDateTime.parse("2021-12-12T12:00:00"),
            LocalDateTime.parse("2021-12-12T18:00:00"),
            "DL123",
            airport3,
            airport4,
            aircraft3,
            airline3
        );
        this.addFlight(flight3);

        Flight flight4 = new Flight(
            LocalDateTime.parse("2021-12-12T12:00:00"),
            LocalDateTime.parse("2021-12-12T18:00:00"),
            LocalDateTime.parse("2021-12-12T12:00:00"),
            LocalDateTime.parse("2021-12-12T18:00:00"),
            "AF123",
            airport4,
            airport3,
            aircraft4,
            airline4
        );
        this.addFlight(flight4);

        Flight flight5 = new PrivateFlight(
            LocalDateTime.parse("2021-12-12T12:00:00"),
            LocalDateTime.parse("2021-12-12T18:00:00"),
            LocalDateTime.parse("2021-12-12T12:00:00"),
            LocalDateTime.parse("2021-12-12T18:00:00"),
            "Private",
            airport1,
            airport3,
            aircraft5,
            airline5
        );
        this.addFlight(flight5);

        AirlineAdmin airlineAdmin = new AirlineAdmin(airline1, "airline", "airline");
        this.addRegisteredClient(airlineAdmin);

        System.out.println("Console initialized");
    }

}