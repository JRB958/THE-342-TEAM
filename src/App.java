// Main entry point of the application
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import javax.xml.transform.Source;

import actors.*;
import airports.*;
import flights.PublicFlight;
import system.*;

public class App {
    public static void main(String[] args) {

        // Initilizing console for testing purposes
        Console console = new Console();
        console.init();

        Scanner scanner = new Scanner(System.in);

        System.out.println("====================================");
        System.out.println("\tAirline Simulation");
        System.out.println("====================================\n");
        

        while (true) {

            System.out.println("\n============  Main Menu  ============");
            System.out.println("Logged in as: " + console.getCurrentActor().getAuth() + "\n");
            System.out.println("What would you like to do?");
            System.out.println("1. Register as a client");
            System.out.println("2. Login as a client");
            System.out.println("3. View flights");
            System.out.println("4. Register a flight");
            System.out.println("5. Exit");
            System.out.print("\nEnter choice: ");

            int choice = scanner.nextInt();

            // Validate choice
            while (choice < 1 || choice > 5) {
                System.out.println("Invalid choice");
                System.out.print("\nEnter choice: ");
                choice = scanner.nextInt();
            }
            
            // Loop until user exits
            System.out.println();

            switch (choice) {
                // Register as a client
                case 1:
                    System.out.print("Enter your email: ");
                    String email = scanner.next();
                    System.out.print("Enter your password: ");
                    String password = scanner.next();

                    // Check if email already exists
                    if (console.clientExists(email)) {
                        System.out.println("\nEmail already exists");
                        break;
                    }

                    // Register client
                    console.addRegisteredClient(
                            new RegisteredClient(Auth.REGISTERED, true, true, false, email, password));
                    System.out.println("Client registered successfully");

                    // Login client
                    console.login(email, password);
                    System.out.println("Client logged in successful");
                    System.out.println("");
                    break;

                // Login as a registered client
                case 2:
                    System.out.println("Enter your email: ");
                    String loginEmail = scanner.next();
                    System.out.println("Enter your password: ");
                    String loginPassword = scanner.next();
                    // Login client
                    console.login(loginEmail, loginPassword);
                    break;
                
                // View flights
                case 3:
                    // Display all airports
                    System.out.println("Available Airports: ");
                    for (Airport airport : console.getAirports()) {
                        System.out.println("\t" + airport.getAirportName() + " - " + airport.getAirportCode());
                    }
                    // Ask user to input source airport code
                    System.out.print("\nEnter source airport code (Eg. LOS): ");
                    String source = scanner.next();

                    // Validate source airport
                    while (!console.airportExists(source)) {
                        System.out.println("Invalid source airport");
                        System.out.println("Enter source airport code (Eg. LOS): ");
                        source = scanner.next();
                    }

                    // Ask user to input destination airport code
                    System.out.print("Enter destination airport (Eg. JFK): ");
                    String destination = scanner.next();

                    // Validate destination airport
                    while (!console.airportExists(destination) || destination.equals(source)) {
                        // Check if destination is the same as source
                        if (destination.equals(source)) {
                            System.out.println("Source and destination airports cannot be the same");
                        } else {
                            System.out.println("Invalid destination airport");
                        }
                        System.out.println("Enter destination airport (Eg. LOS): ");
                        destination = scanner.next();
                    }

                    System.out.println("");
                    // Display flights
                    console.viewFlights(console.getAirport(source), console.getAirport(destination),
                            console.getCurrentActor());
                    break;

                // Register a flight
                case 4:
                    String burner;
                    // check for authorization
                    if (console.getCurrentActor().getAuth() != Auth.AIRLINE_ADMIN && console.getCurrentActor().getAuth() != Auth.AIRPORT_ADMIN){
                        System.out.println("You do not have permission to register a flight");
                        break;
                    }

                    // ask and check for flight number
                    System.out.println("Registering for a flight...");
                    System.out.print("Enter flight number (Eg. AF123): ");
                    String flightNumber = scanner.next();
                    burner = scanner.nextLine();
                    
                    while (console.flightExists(flightNumber)) {
                        System.out.print("\nFlight number already exists");
                        System.out.print("\nEnter flight number (Eg. AF123): ");
                        flightNumber = scanner.next();
                        burner = scanner.nextLine();

                    }

                    // ask and check for source airport 
                    System.out.print("\nEnter source airport code (Eg. LOS): ");
                    String sourceAirport = scanner.next();
                    burner = scanner.nextLine();
                    
                    while (!console.airportExists(sourceAirport)) {
                        System.out.print("\nInvalid source airport");
                        System.out.print("\nEnter source airport code (Eg. LOS): ");
                        sourceAirport = scanner.next();
                        burner = scanner.nextLine();
                    }
                    Airport sourceAirportObject = (console.getAirport(sourceAirport));

                    // ask and check for destination airport
                    System.out.print("\nEnter destination airport code (Eg. JFK): ");
                    String destinationAirport = scanner.next();
                    burner = scanner.nextLine();

                    while (!console.airportExists(destinationAirport) || destinationAirport.equals(sourceAirport)) {
                        if (destinationAirport.equals(sourceAirport)) {
                            System.out.print("\nSource and destination airports cannot be the same");
                        } else {
                            System.out.print("\nInvalid destination airport");
                        }
                        System.out.print("Enter destination airport code (Eg. JFK): ");
                        destinationAirport = scanner.next();
                        burner = scanner.nextLine();
                    }
                    Airport destinationAirportObject = (console.getAirport(destinationAirport));

                    // ***********
                    //TODO: uncomment
                    // commented here and hardcoded for how annoying it is to keep typing dates 
                    // ***********

                            // // ask and check for the date and flight of the flight
                            // System.out.print("\nEnter departure date (Eg. 2021-12-31): ");
                            // String departureDate = scanner.next();

                            // System.out.print("\nEnter departure time (Eg. 15:00): ");
                            // String departureTime = scanner.next();

                            // String departureDateTime = departureDate + " " + departureTime;

                            // // Check if source airport has flights at departure date
                            // while (console.hasFlightsSource(departureDateTime, console.getAirport(sourceAirport))) {
                            //     System.out.println("Flight time busy at source at: " + departureDate);
                            //     System.out.print("\nEnter departure date (Eg. 2021-12-31): ");
                            //     departureDate = scanner.next();
                            //     System.out.print("\nEnter departure time (Eg. 15:00): ");
                            //     departureTime = scanner.next();
                            //     departureDateTime = departureDate + " " + departureTime;
                            // }
                            
                            // // once check is done, create a DateTime object to pass to flight
                            // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                            // LocalDateTime departureDateTimeObject = LocalDateTime.parse(departureDateTime, formatter);

                            // System.out.println("Enter arrival date (Eg. 2021-12-31): ");
                            // String arrivalDate = scanner.next();

                            // System.out.println("Enter arrival time (Eg. 15:00): ");
                            // String arrivalTime = scanner.next();

                            // String arrivalDateTime = arrivalDate + " " + arrivalTime;

                            // while (console.hasFlightsDestination(arrivalDateTime, console.getAirport(sourceAirport))) {
                            //     System.out.println("Flight time busy at destination at: " + departureDate);
                            //     System.out.println("Enter arrival date (Eg. 2021-12-31): ");
                            //     arrivalDate = scanner.next();
                            //     System.out.println("Enter arrival time (Eg. 15:00): ");
                            //     arrivalTime = scanner.next();
                            //     arrivalDateTime = departureDate + " " + departureTime;
                            // }
                            // // once check is done, create a DateTime object to pass to flight
                            // LocalDateTime arrivalDateTimeObject = LocalDateTime.parse(arrivalDateTime, formatter);

                    // ***********
                    //TODO: uncomment
                    // ***********

                    System.out.print("Enter aircraft name: ");
                    String aircraftName = scanner.nextLine();

                    //check if the aircraft exists in the source airport
                    while (!(sourceAirportObject).aircraftExist(aircraftName)) {
                        System.out.print("\nAircraft is not available in " + sourceAirportObject.getAirportName() + "\'s fleet");
                        System.out.print("\nEnter aircraft name: ");
                        aircraftName = scanner.nextLine();
                    }

                    
                    // get airline name
                    System.out.print("Enter airline name: ");
                    String airlineName = scanner.nextLine();

                    // check if the airline is a registered airline
                    while (!console.airlineExist(airlineName)) {
                        System.out.print("\nAirline doesn't exist");
                        System.out.print("\nEnter airline name: ");
                        airlineName = scanner.nextLine();
                    }

                    // check if the airline has an available aircraft
                    if (!sourceAirportObject.aircraftExist(aircraftName) || !console.getAirline(airlineName).ownsAircraft(aircraftName)) {
                        System.out.println("Aircraft not available for airline");
                        break;
                    }
                    
                    // ***********
                    //TODO: remove
                    // ***********
                                LocalDateTime departureDateTimeObject = LocalDateTime.now();
                                LocalDateTime arrivalDateTimeObject = LocalDateTime.now().plusHours(1) ;
                    // ***********
                    //TODO: remove
                    // ***********

                    console.addFlight(new PublicFlight(departureDateTimeObject, 
                                                       arrivalDateTimeObject, 
                                                       departureDateTimeObject, 
                                                       arrivalDateTimeObject, 
                                                       flightNumber, 
                                                       sourceAirportObject, 
                                                       destinationAirportObject, 
                                                       console.getAircraft(aircraftName),
                                                       console.getAirline(airlineName)                          
                                                        ));

                    System.out.println("Flight registered successfully");
                    //return the last flight added
                    System.out.println(console.getFlights().get(console.getFlights().size()-1));

                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice");
                    break;
            }

        }

    }
}
