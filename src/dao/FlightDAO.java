package dao;

import database.DBConnection;
import model.Flight;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlightDAO {

    public void addFlight(Flight flight) {
        String sql = "INSERT INTO flights (airline, origin, destination, departure_time, arrival_time, total_seats, available_seats, price) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, flight.getAirline());
            pstmt.setString(2, flight.getOrigin());
            pstmt.setString(3, flight.getDestination());
            pstmt.setString(4, flight.getDepartureTime());
            pstmt.setString(5, flight.getArrivalTime());
            pstmt.setInt(6, flight.getTotalSeats());
            pstmt.setInt(7, flight.getAvailableSeats());
            pstmt.setDouble(8, flight.getPrice());

            pstmt.executeUpdate();
            System.out.println("Flight added successfully!");

        } catch (SQLException e) {
            System.out.println("Error adding flight: " + e.getMessage());
        }
    }

    public Flight getFlightById(int id) {
        String sql = "SELECT * FROM flights WHERE flight_id = ?";
        Flight flight = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                flight = mapRowToFlight(rs);
            }

        } catch (SQLException e) {
            System.out.println("Error getting flight: " + e.getMessage());
        }
        return flight;
    }

    public List<Flight> getAllFlights() {
        List<Flight> flights = new ArrayList<>();
        String sql = "SELECT * FROM flights";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                flights.add(mapRowToFlight(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error listing flights: " + e.getMessage());
        }
        return flights;
    }

    public List<Flight> searchFlights(String origin, String destination) {
        List<Flight> flights = new ArrayList<>();
        String sql = "SELECT * FROM flights WHERE origin = ? AND destination = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, origin);
            pstmt.setString(2, destination);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                flights.add(mapRowToFlight(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error searching flights: " + e.getMessage());
        }
        return flights;
    }

    public void updateSeats(int flightId, int newAvailableSeats) {
        String sql = "UPDATE flights SET available_seats = ? WHERE flight_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, newAvailableSeats);
            pstmt.setInt(2, flightId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error updating seats: " + e.getMessage());
        }
    }

    private Flight mapRowToFlight(ResultSet rs) throws SQLException {
        return new Flight(
                rs.getInt("flight_id"), // Matches SQL column name
                rs.getString("airline"),
                rs.getString("origin"),
                rs.getString("destination"),
                rs.getString("departure_time"), // Assuming DB stores as String or you convert logic
                rs.getString("arrival_time"),
                rs.getInt("total_seats"),
                rs.getInt("available_seats"),
                rs.getDouble("price")
        );
    }
}