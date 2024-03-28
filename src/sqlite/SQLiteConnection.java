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
import actors.AirlineAdmin;
import actors.AirportAdmin;
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

                if (resultSet.getString("Type").equalsIgnoreCase("PRIVATE")) {
                    // PrivateFlight flight = new PrivateFlight();
                    // flight.setHandlerAirport(resultSet.getInt("HandlerAirportID"));
                }
                Flight flight = new Flight();
                flight.setFlightID(resultSet.getInt("FlightID"));
                flight.setScheduledDepart(
                        LocalDateTime.parse(resultSet.getString("ScheduledDepartureTime"), formatter));
                flight.setScheduledArrival(LocalDateTime.parse(resultSet.getString("ScheduledArrivalTime"), formatter));
                flight.setActualDepart(LocalDateTime.parse(resultSet.getString("ActualDepartureTime"), formatter));
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

                // Check if the client is an airline admin
                if (resultSet.getString("Type").equalsIgnoreCase("AIRLINE_ADMIN")) {
                    AirlineAdmin registeredClient = new AirlineAdmin();

                    registeredClient.setClientID(resultSet.getInt("ActorID"));
                    registeredClient.setEmail(resultSet.getString("Email"));
                    registeredClient.setPassword(resultSet.getString("Password"));
                    registeredClient.setAuth(Auth.valueOf(resultSet.getString("Type")));

                    PreparedStatement clientPstmt = connection
                            .prepareStatement("SELECT AirlineID FROM AirlineAdmin WHERE ActorID = ?");
                    clientPstmt.setInt(1, registeredClient.getClientID());
                    ResultSet clientResultSet = clientPstmt.executeQuery();

                    registeredClient.setAirlineID(clientResultSet.getInt("AirlineID"));
                    registeredClients.put(registeredClient.getClientID(), registeredClient);

                } else if (resultSet.getString("Type").equalsIgnoreCase("AIRPORT_ADMIN")) {
                    // Check if the client is an airport admin
                    AirportAdmin registeredClient = new AirportAdmin();

                    registeredClient.setClientID(resultSet.getInt("ActorID"));
                    registeredClient.setEmail(resultSet.getString("Email"));
                    registeredClient.setPassword(resultSet.getString("Password"));
                    registeredClient.setAuth(Auth.valueOf(resultSet.getString("Type")));

                    PreparedStatement clientPstmt = connection
                            .prepareStatement("SELECT AirportID FROM AirportAdmin WHERE ActorID = ?");
                    clientPstmt.setInt(1, registeredClient.getClientID());
                    ResultSet clientResultSet = clientPstmt.executeQuery();

                    registeredClient.setAirportID(clientResultSet.getInt("AirportID"));
                    registeredClients.put(registeredClient.getClientID(), registeredClient);
                } else {
                    // Otherwise, the client is a registered client
                    RegisteredClient registeredClient = new RegisteredClient();
                    registeredClient.setClientID(resultSet.getInt("ActorID"));
                    registeredClient.setEmail(resultSet.getString("Email"));
                    registeredClient.setPassword(resultSet.getString("Password"));
                    registeredClient.setAuth(Auth.valueOf(resultSet.getString("Type")));
                    registeredClients.put(registeredClient.getClientID(), registeredClient);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error querying SQLite database: " + e.getMessage());
        }
        return registeredClients;
    }

    static public void insertFlight(Flight flight) {
        String query = "INSERT INTO Flight (ScheduledDepartureTime, ScheduledArrivalTime, ActualDepartureTime, ActualArrivalTime, FlightCode, SourceAirportID, DestinationAirportID, AirlineID, AircraftID, Type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, flight.getScheduledDepart().toString());
            pstmt.setString(2, flight.getScheduledArrival().toString());
            pstmt.setString(3, flight.getActualDepart().toString());
            pstmt.setString(4, flight.getActualArrival().toString());
            pstmt.setString(5, flight.getFlightNumber());
            pstmt.setInt(6, flight.getSourceID());
            pstmt.setInt(7, flight.getDestinationID());
            pstmt.setInt(8, flight.getAirlineID());
            pstmt.setInt(9, flight.getAircraftID());
            pstmt.setString(10, flight.getType());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error inserting into SQLite database: " + e.getMessage());
        }
    }

    static public void insertRegisteredClient(RegisteredClient client) {
        String query = "INSERT INTO Actor (Email, Password, Type) VALUES (?, ?, ?)";

        try (Connection connection = getConnection();
                PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, client.getEmail());
            pstmt.setString(2, client.getPassword());
            pstmt.setString(3, client.getAuth().toString());
            pstmt.executeUpdate();

            PreparedStatement returnPstmt = connection.prepareStatement("SELECT last_insert_rowid()");
            ResultSet resultSet = returnPstmt.executeQuery();
            if (resultSet.next()) {
                client.setClientID(resultSet.getInt(1));
            }

            if (client.getAuth() == Auth.AIRLINE_ADMIN) {
                PreparedStatement airlinePstmt = connection.prepareStatement("INSERT INTO AirlineAdmin (ActorID, AirlineID) VALUES (?, ?)");
                airlinePstmt.setInt(1, client.getClientID());
                airlinePstmt.setInt(2, ((AirlineAdmin) client).getAirlineID());
                airlinePstmt.executeUpdate();

                PreparedStatement returnPstmt2 = connection.prepareStatement("SELECT last_insert_rowid()");
                ResultSet resultSet2 = returnPstmt2.executeQuery();
                if (resultSet2.next()) {
                    ((AirlineAdmin) client).setAirlineAdminID(resultSet2.getInt(1));
                }
            } else if (client.getAuth() == Auth.AIRPORT_ADMIN) {
                PreparedStatement airportPstmt = connection.prepareStatement("INSERT INTO AirportAdmin (ActorID, AirportID) VALUES (?, ?)");
                airportPstmt.setInt(1, client.getClientID());
                airportPstmt.setInt(2, ((AirportAdmin) client).getAirportID());
                airportPstmt.executeUpdate();

                PreparedStatement returnPstmt2 = connection.prepareStatement("SELECT last_insert_rowid()");
                ResultSet resultSet2 = returnPstmt2.executeQuery();
                if (resultSet2.next()) {
                    ((AirportAdmin) client).setAirportAdminID(resultSet2.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error inserting into SQLite database: " + e.getMessage());
        }
    }

    public static void insertAirport(Airport airport) {
        String query = "INSERT INTO Airport (AirportName, AirportCode, CityID) VALUES (?, ?, ?)";
    
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query);
             PreparedStatement returnPstmt = connection.prepareStatement("SELECT last_insert_rowid()")) {
    
            pstmt.setString(1, airport.getAirportName());
            pstmt.setString(2, airport.getAirportCode());
            pstmt.setInt(3, airport.getCityID());
            pstmt.executeUpdate();
    
            try (ResultSet resultSet = returnPstmt.executeQuery()) {
                if (resultSet.next()) {
                    airport.setAirportID(resultSet.getInt(1));
                }
            }
    
        } catch (SQLException e) {
            System.err.println("Error inserting into SQLite database: " + e.getMessage());
            // Consider additional error handling here
        }
    }
    
    public static void insertCity(City cityObj) {
        String query = "INSERT INTO City (Name, Country, Temp) VALUES (?, ?, ?)";
    
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query);
             PreparedStatement returnPstmt = connection.prepareStatement("SELECT last_insert_rowid()")) {
    
            pstmt.setString(1, cityObj.getName());
            pstmt.setString(2, cityObj.getCountry());
            pstmt.setDouble(3, cityObj.getTemp());
            pstmt.executeUpdate();
    
            try (ResultSet resultSet = returnPstmt.executeQuery()) {
                if (resultSet.next()) {
                    cityObj.setCityID(resultSet.getInt(1));
                }
            }
    
        } catch (SQLException e) {
            System.err.println("Error inserting into SQLite database: " + e.getMessage());
            // Consider additional error handling here
        }

        System.out.println("successfully inserted city with ID: " + cityObj.getCityID());
    }
    
}