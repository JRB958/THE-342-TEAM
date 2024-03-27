package sqlite;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import actors.Actor;
import actors.Auth;
import actors.RegisteredClient;
import airports.Airport;
import flights.Flight;
import misc.Aircraft;
import misc.Airline;
import misc.City;

import java.nio.file.Path;

public class SQLiteConnection {
    private static Connection connection;

    private static Connection connect() {
        Path currentDir = Paths.get(System.getProperty("user.dir"));
        String databasePath = currentDir.resolve("temp.db").toString();
        String url = "jdbc:sqlite:" + databasePath;
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error connecting to SQLite database: " + e.getMessage());
        }
        return connection;
    }

    public static Connection getConnection() {
        if (connection == null) {
            connection = connect();
        }
        return connect();
    }

    public static HashMap<Integer, Flight> getFlights() {
        String query = "SELECT * FROM Flight";
        HashMap<Integer, Flight> flights = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try (Connection connection = getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {

                Flight flight = new Flight();
                flight.setFlightID(resultSet.getInt("FlightID"));
                flight.setScheduledDepart(LocalDateTime.parse(resultSet.getString("ScheduledDepartureTime"), formatter));
                flight.setScheduledArrival(LocalDateTime.parse(resultSet.getString("ScheduledArrivalTime"), formatter));
                flight.setActualDepart(LocalDateTime.parse(resultSet.getString("ActualDepartureTime"),  formatter));
                flight.setActualArrival(LocalDateTime.parse(resultSet.getString("ActualArrivalTime"), formatter));
                flight.setFlightNumber(resultSet.getString("FlightCode"));
                flight.setSourceID(resultSet.getInt("SourceAirportID"));
                flight.setDestinationID(resultSet.getInt("DestinationAirportID"));
                flight.setAirlineID(resultSet.getInt("AirlineID"));
                flight.setAircraftID(resultSet.getInt("AircraftID"));
                flight.setType(resultSet.getString("Type"));
                flights.put(flight.getFlightID(), flight);
            }

        } catch (SQLException e) {
            System.err.println("Error querying SQLite database: " + e.getMessage());
        }
        return flights;
    }

    public static HashMap<Integer, City> getCities() {
        String query = "SELECT * FROM City";
        HashMap<Integer, City> cities = new HashMap<>();

        try (Connection connection = getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                City city = new City(
                        resultSet.getInt("CityID"),
                        resultSet.getString("Name"),
                        resultSet.getString("Country"),
                        Double.parseDouble(resultSet.getString("Temp")));
                cities.put(city.getCityID(), city);
            }
        } catch (SQLException e) {
            System.err.println("Error querying SQLite database: " + e.getMessage());
        }
        return cities;
    }

    public static HashMap<Integer, Airport> getAirports() {
        String airportQuery = "SELECT * FROM Airport";
        HashMap<Integer, Airport> airports = new HashMap<>();

        // Retrieve airports and set their corresponding city
        try (PreparedStatement airportPstmt = connection.prepareStatement(airportQuery);
                ResultSet resultSet = airportPstmt.executeQuery()) {

            while (resultSet.next()) {
                Airport airport = new Airport();

                airport.setAirportID(resultSet.getInt("AirportID"));
                airport.setAirportName(resultSet.getString("AirportName"));
                airport.setAirportCode(resultSet.getString("AirportCode"));
                airport.setCityID(resultSet.getInt("CityID"));

                airports.put(airport.getAirportID(), airport);
            }
        } catch (SQLException e) {
            System.err.println("Error querying SQLite database: " + e.getMessage());
        }

        return airports;
    }

    public static HashMap<Integer, Airline> getAirlines() {
        String query = "SELECT * FROM Airline";
        HashMap<Integer, Airline> airlines = new HashMap<>();

        try (Connection connection = getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Airline airline = new Airline();

                airline.setAirlineID(resultSet.getInt("AirlineID"));
                airline.setAirlineName(resultSet.getString("AirlineName"));

                airlines.put(airline.getAirlineID(), airline);
            }
        } catch (SQLException e) {
            System.err.println("Error querying SQLite database: " + e.getMessage());
        }
        return airlines;
    }

    public static HashMap<Integer, Aircraft> getAircrafts() {
        String query = "SELECT * FROM Aircraft";
        HashMap<Integer, Aircraft> aircrafts = new HashMap<>();

        try (Connection connection = getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Aircraft aircraft = new Aircraft();

                aircraft.setAircraftID(resultSet.getInt("AircraftID"));
                aircraft.setInTransit(resultSet.getBoolean("IsActive"));
                aircraft.setAircraftName(resultSet.getString("AircraftName"));
                aircraft.setHostingAirportID(resultSet.getInt("CurrentAirportID"));

                int fleetID = resultSet.getInt("FleetID");

                PreparedStatement airlinePstmt = connection
                        .prepareStatement("SELECT AirlineID FROM Fleet WHERE FleetID = ?");
                airlinePstmt.setInt(1, fleetID);
                ResultSet airlineResultSet = airlinePstmt.executeQuery();
                aircraft.setAirlineID(airlineResultSet.getInt("AirlineID"));

                aircrafts.put(aircraft.getAircraftID(), aircraft);
            }
        } catch (SQLException e) {
            System.err.println("Error querying SQLite database: " + e.getMessage());
        }
        return aircrafts;
    }

    public static HashMap<Integer, RegisteredClient> getRegisteredClients() {
        String query = "SELECT * FROM Actor";
        HashMap<Integer, RegisteredClient> registeredClients = new HashMap<>();

        try (Connection connection = getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                RegisteredClient registeredClient = new RegisteredClient();

                registeredClient.setClientID(resultSet.getInt("ActorID"));
                registeredClient.setEmail(resultSet.getString("Email"));
                registeredClient.setPassword(resultSet.getString("Password"));
                registeredClient.setAuth(Auth.valueOf(resultSet.getString("Type")));

                registeredClients.put(registeredClient.getClientID(), registeredClient);
            }
        } catch (SQLException e) {
            System.err.println("Error querying SQLite database: " + e.getMessage());
        }
        return registeredClients;
    }

    public static void viewFlights(String sourceAirportCode, String destinationAirportCode, String userType) {
        String query = "SELECT Flight.FlightNumber, SourceAirport.AirportName AS SourceAirport, " +
                "DestinationAirport.AirportName AS DestinationAirport " +
                "FROM Flight " +
                "JOIN Airport AS SourceAirport ON Flight.SourceAirportID = SourceAirport.AirportID " +
                "JOIN Airport AS DestinationAirport ON Flight.DestinationAirportID = DestinationAirport.AirportID " +
                "WHERE SourceAirport.AirportCode = ? AND DestinationAirport.AirportCode = ?";

        if (!userType.equalsIgnoreCase("REGISTERED")) {
            query += " AND Flight.Type = 'Private'";
        }

        try (Connection connection = getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, sourceAirportCode);
            pstmt.setString(2, destinationAirportCode);

            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                System.out.println("Flight Number: " + resultSet.getString("FlightNumber") +
                        ", Source Airport: " + resultSet.getString("SourceAirport") +
                        ", Destination Airport: " + resultSet.getString("DestinationAirport"));
            }
        } catch (SQLException e) {
            System.err.println("Error querying SQLite database: " + e.getMessage());
        }
    }

    public static void main(String[] args) {

    }

}
