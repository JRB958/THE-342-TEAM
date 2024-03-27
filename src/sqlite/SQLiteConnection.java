package sqlite;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import airports.Airport;
import flights.Flight;
import misc.City;

import java.lang.reflect.Array;
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

    public static ArrayList<Flight> getFlights() {
        String query = "SELECT * FROM Flight";
        ArrayList<Flight> flights = new ArrayList<>();
        
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet resultSet = pstmt.executeQuery();
            // while (resultSet.next()) {
            //     Flight flight = new Flight(
            //         New LocalDateTime(resultSet.getString("ScheduledDepartureTime")),
            //         New LocalDateTime(resultSet.getString("ScheduledArrivalTime")),
            //         New LocalDateTime(resultSet.getString("ActualDepartureTime")),
            //         New LocalDateTime(resultSet.getString("ActualArrivalTime")),
            //         resultSet.getString("FlightNumber"),
            //         resultSet.getString("SourceAirportID"),
            //         resultSet.getString("DestinationAirportID"),
            //         resultSet.getString("Type"),
            //         resultSet.getString("Status"),
            //         resultSet.getString("AircraftID"),
            //         resultSet.getString("AirlineID")
            //     );
                
            // }
        } catch (SQLException e) {
            System.err.println("Error querying SQLite database: " + e.getMessage());
        }
        return flights;
    }

    public static ArrayList<City> getCities() {
        String query = "SELECT * FROM City";
        ArrayList<City> cities = new ArrayList<>();
        
        try (Connection connection = getConnection();
            PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                City city = new City(
                    resultSet.getInt("CityID"),
                    resultSet.getString("Name"),
                    resultSet.getString("Country"),
                    Double.parseDouble(resultSet.getString("Temp"))
                );
                cities.add(city);
            }
        } catch (SQLException e) {
            System.err.println("Error querying SQLite database: " + e.getMessage());
        }
        return cities;
    }

    public static ArrayList<Airport> getAirports() {
        String aiportQuery = "SELECT * FROM Airport";
        String cityQuery = "SELECT * FROM City WHERE CityID = ?";
        ArrayList<Airport> airports = new ArrayList<>();
        
        try (Connection connection = getConnection();
             PreparedStatement pstmt = connection.prepareStatement(aiportQuery)) {
                ResultSet resultSet = pstmt.executeQuery();


                while (resultSet.next()) {
                    Airport airport = new Airport();
                    airport.setAirportID(resultSet.getInt("AirportID"));
                    airport.setAirportName(resultSet.getString("AirportName"));
                    airport.setAirportCode(resultSet.getString("AirportCode"));
                    
                    PreparedStatement cityPstmt = connection.prepareStatement(cityQuery);
                    cityPstmt.setInt(1, resultSet.getInt("CityID"));
                    ResultSet cityResultSet = cityPstmt.executeQuery();

                    City city = new City(
                        cityResultSet.getInt("CityID"),
                        cityResultSet.getString("Name"),
                        cityResultSet.getString("Country"),
                        Double.parseDouble(cityResultSet.getString("Temp"))
                    );

                    airport.setAirportCity(city);
                    airports.add(airport);
                }
        } catch (SQLException e) {
            System.err.println("Error querying SQLite database: " + e.getMessage());
        }
        return airports;
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
        ArrayList<Airport> airports = getAirports();
        for (Airport airport : airports) {
            System.out.println(airport.getAirportName());
            System.out.println(airport.getAirportCity().getName());
        }
    }

}
