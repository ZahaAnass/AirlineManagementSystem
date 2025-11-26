package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            System.out.println("--- Initializing Database ---");

            // 1. DROP TABLES (Order matters due to Foreign Keys)
            // We drop Tickets first because it refers to Flights and Passengers
            stmt.execute("DROP TABLE IF EXISTS tickets");
            stmt.execute("DROP TABLE IF EXISTS passengers");
            stmt.execute("DROP TABLE IF EXISTS flights");
            System.out.println("Existing tables dropped.");

            // 2. CREATE TABLES

            // Flights Table
            String createFlights = "CREATE TABLE flights (" +
                    "flight_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "airline VARCHAR(100), " +
                    "origin VARCHAR(100), " +
                    "destination VARCHAR(100), " +
                    "departure_time DATETIME, " +
                    "arrival_time DATETIME, " +
                    "total_seats INT, " +
                    "available_seats INT, " +
                    "price DOUBLE)";
            stmt.execute(createFlights);

            // Passengers Table
            String createPassengers = "CREATE TABLE passengers (" +
                    "passenger_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(100), " +
                    "email VARCHAR(100) UNIQUE, " +
                    "phone VARCHAR(20))";
            stmt.execute(createPassengers);

            // Tickets Table
            String createTickets = "CREATE TABLE tickets (" +
                    "ticket_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "flight_id INT, " +
                    "passenger_id INT, " +
                    "booking_time DATETIME, " +
                    "status VARCHAR(20), " +
                    "FOREIGN KEY (flight_id) REFERENCES flights(flight_id), " +
                    "FOREIGN KEY (passenger_id) REFERENCES passengers(passenger_id))";
            stmt.execute(createTickets);

            System.out.println("Tables created successfully.");

            // 3. INSERT FAKE DATA

            // Insert Flights
            String insertFlights = "INSERT INTO flights (airline, origin, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES " +
                    "('Delta Airlines', 'New York', 'London', '2024-12-01 10:00:00', '2024-12-01 22:00:00', 150, 148, 550.00), " +
                    "('Emirates', 'Dubai', 'New York', '2024-12-05 08:00:00', '2024-12-05 20:00:00', 300, 300, 1200.50), " +
                    "('British Airways', 'London', 'Paris', '2024-11-20 09:00:00', '2024-11-20 10:30:00', 100, 95, 120.00), " +
                    "('Qatar Airways', 'Doha', 'Tokyo', '2025-01-10 02:00:00', '2025-01-10 18:00:00', 250, 250, 900.00), " +
                    "('Air France', 'Paris', 'New York', '2024-12-15 14:00:00', '2024-12-15 18:00:00', 200, 199, 600.00)";
            stmt.execute(insertFlights);

            // Insert Passengers
            String insertPassengers = "INSERT INTO passengers (name, email, phone) VALUES " +
                    "('John Doe', 'john@example.com', '1234567890'), " +
                    "('Alice Smith', 'alice@example.com', '0987654321'), " +
                    "('Bob Brown', 'bob@example.com', '1122334455')";
            stmt.execute(insertPassengers);

            // Insert Tickets (Simulate that John and Alice have already booked)
            // Note: We manually set available_seats in the flights insert to reflect these bookings
            String insertTickets = "INSERT INTO tickets (flight_id, passenger_id, booking_time, status) VALUES " +
                    "(1, 1, '2024-10-01 12:00:00', 'BOOKED'), " + // John on Delta
                    "(1, 2, '2024-10-02 14:30:00', 'BOOKED'), " + // Alice on Delta
                    "(3, 3, '2024-09-15 09:00:00', 'BOOKED'), " + // Bob on British Airways
                    "(3, 1, '2024-09-20 10:00:00', 'CANCELLED')"; // John cancelled British Airways
            stmt.execute(insertTickets);

            System.out.println("Fake data inserted successfully.");
            System.out.println("---------------------------------");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());;
        }
    }
}