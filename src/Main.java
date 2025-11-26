import service.BookingService;
import service.FlightService;
import service.PassengerService;
import model.Flight;
import model.Passenger;
import database.DatabaseInitializer;

import java.util.Scanner;
import java.util.List;

public class Main {

    private static final BookingService bookingService = new BookingService();
    private static final FlightService flightService = new FlightService();
    private static final PassengerService passengerService = new PassengerService();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("*".repeat(20));
        System.out.println("Welcome to the Flight Booking System!");
        System.out.println("*".repeat(20));

        while (running) {
            displayMenu();
            System.out.print("Please select an option: ");

            // Basic validation to prevent crash on non-integer input
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // consume bad input
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("\n--- All Flights ---");
                    List<Flight> flights = flightService.getAllFlights();
                    if(flights.isEmpty()) System.out.println("No flights available.");
                    for(Flight f : flights) System.out.println(f);
                    break;

                case 2:
                    System.out.println("\n--- Search Flights ---");
                    System.out.print("Enter Origin: ");
                    String origin = scanner.nextLine();
                    System.out.print("Enter Destination: ");
                    String dest = scanner.nextLine();
                    List<Flight> results = flightService.searchFlights(origin, dest);
                    if(results.isEmpty()) System.out.println("No flights found.");
                    for(Flight f : results) System.out.println(f);
                    break;

                case 3:
                    System.out.println("\n--- Book Ticket ---");
                    System.out.print("Enter Flight ID to book: ");
                    int fId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter Passenger Name: ");
                    String pName = scanner.nextLine();
                    System.out.print("Enter Email: ");
                    String pEmail = scanner.nextLine();
                    System.out.print("Enter Phone: ");
                    String pPhone = scanner.nextLine();

                    Passenger newPassenger = new Passenger(0, pName, pEmail, pPhone);
                    bookingService.bookTicket(fId, newPassenger);
                    break;

                case 4:
                    System.out.println("\n--- Cancel Ticket ---");
                    System.out.print("Enter Ticket ID to cancel: ");
                    int tId = scanner.nextInt();
                    bookingService.cancelTicket(tId);
                    break;

                case 5:
                    System.out.println("\n--- Add Flight ---");
                    System.out.print("Airline: ");
                    String airline = scanner.nextLine();
                    System.out.print("Origin: ");
                    String org = scanner.nextLine();
                    System.out.print("Destination: ");
                    String dst = scanner.nextLine();
                    System.out.print("Departure Time (YYYY-MM-DD HH:MM): ");
                    String dep = scanner.nextLine();
                    System.out.print("Arrival Time (YYYY-MM-DD HH:MM): ");
                    String arr = scanner.nextLine();
                    System.out.print("Total Seats: ");
                    int seats = scanner.nextInt();
                    System.out.print("Price: ");
                    double price = scanner.nextDouble();

                    Flight newFlight = new Flight(0, airline, org, dst, dep, arr, seats, seats, price);
                    flightService.addFlight(newFlight);
                    break;

                case 6:
                    System.out.println("\n--- Add Passenger ---");
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Phone: ");
                    String phone = scanner.nextLine();
                    passengerService.addPassenger(new Passenger(0, name, email, phone));
                    System.out.println("Passenger added.");
                    break;

                case 9:
                    System.out.println("WARNING: This will delete all existing data.");
                    System.out.print("Are you sure? (yes/no): ");
                    String confirm = scanner.nextLine();
                    if (confirm.equalsIgnoreCase("yes")) {
                        DatabaseInitializer.initialize();
                    }
                    break;

                case 0:
                    System.out.println("Exiting system. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
            System.out.println();
        }
    }

    public static void displayMenu() {
        System.out.println("--- MENU ---");
        System.out.println("1. View All Flights");
        System.out.println("2. Search Flights");
        System.out.println("3. Book Ticket");
        System.out.println("4. Cancel Ticket");
        System.out.println("5. Add Flight");
        System.out.println("6. Add Passenger");
        System.out.println("9. [ADMIN] Reset Database & Seed Data"); // Hidden-ish option
        System.out.println("0. Exit");
    }
}