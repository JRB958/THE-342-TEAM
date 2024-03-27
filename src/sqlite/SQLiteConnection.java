package sqlite;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        if (connect() == null) {
            connection = connect();
        }
        return connect();
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

    // public static void main(String[] args) {
    //     try (Connection connection = connect()) {
    //         if (connection != null) {
    //             System.out.println("Connected to SQLite database.");
    //         }
    //     } catch (SQLException e) {
    //         System.err.println("Error closing SQLite database: " + e.getMessage());
    //     }

    //     // Example usage
    //     viewFlights("LOS", "JFK", "REGISTERED");
    //     viewFlights("LHR", "CDG", "AIRPORT_ADMIN");
    // }
}
