import java.time.LocalDateTime;

import actors.*;
import airports.*;
import flights.*;
import misc.*;
import system.*;



public class App {
    public static void main(String[] args){

        // Initilizing console for testing purposes
        Console console = new Console();
        Airport airport1 = new Airport();
        airport1.setAirportName("Murtala Muhammed International Airport");
        airport1.setAirportCode("LOS");
        City city1 = new City();
        city1.setName("Lagos");
        city1.setCountry("Nigeria");
        airport1.setAirportCity(city1);
        console.addAirport(airport1);

        Airport airport2 = new Airport();
        airport2.setAirportName("Heathrow Airport");
        airport2.setAirportCode("LHR");
        City city2 = new City();
        city2.setName("London");
        city2.setCountry("United Kingdom");
        airport2.setAirportCity(city2);
        console.addAirport(airport2);

        Airport airport3 = new Airport();
        airport3.setAirportName("JFK International Airport");
        airport3.setAirportCode("JFK");
        City city3 = new City();
        city3.setName("New York");
        city3.setCountry("United States");
        airport3.setAirportCity(city3);
        console.addAirport(airport3);

        Airport airport4 = new Airport();
        airport4.setAirportName("Charles de Gaulle Airport");
        airport4.setAirportCode("CDG");
        City city4 = new City();
        city4.setName("Paris");
        city4.setCountry("France");
        airport4.setAirportCity(city4);
        console.addAirport(airport4);

        // Create aircrafts
        Aircraft aircraft1 = new Aircraft(false, "Boeing 747");
        Aircraft aircraft2 = new Aircraft(true, "Boeing 737");
        Aircraft aircraft3 = new Aircraft(false, "Airbus A380");
        Aircraft aircraft4 = new Aircraft(true, "Airbus A320");
        Aircraft aircraft5 = new Aircraft(false, "Boeing 777");

        // Create airlines
        Airline airline1 = new Airline(
            aircraft1, "British Airways"
        );

        Airline airline2 = new Airline(
            aircraft2, "Virgin Atlantic"
        );

        Airline airline3 = new Airline(
            aircraft3, "Delta Airlines"
        );

        Airline airline4 = new Airline(
            aircraft4, "Air France"
        );

        Airline airline5 = new Airline(
            aircraft5, "Lufthansa"
        );

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

        // Add flights to console
        console.addFlight(flight1);
        console.addFlight(flight2);
        console.addFlight(flight3);
        console.addFlight(flight3);
        console.addFlight(flight4);
        console.addFlight(flight5);

        // Add airports to console
        console.addAirport(airport1);
        console.addAirport(airport2);
        console.addAirport(airport3);
        console.addAirport(airport4);

        // create clients
        Actor client = new PublicClient();

        Actor admin1 = new AirportAdmin(airport1, "airport@test.com", "airport123");

        // add clients to console
        System.out.println("\n====================================");
        console.viewFlights(airport1, airport3, admin1);
        System.out.println("\n====================================");
        console.viewFlights(airport1, airport3, client);
        System.out.println("\n====================================");

        
    }
}
