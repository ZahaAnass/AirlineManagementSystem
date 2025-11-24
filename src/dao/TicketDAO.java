package dao;

import database.DBConnection;
import model.Ticket;
import model.TicketStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {

    public void bookTicket(Ticket ticket) {
        String sql = "INSERT INTO tickets (flight_id, passenger_id, booking_time, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, ticket.getFlightId());
            pstmt.setInt(2, ticket.getPassengerId());
            // Convert LocalDateTime to SQL Timestamp
            pstmt.setTimestamp(3, Timestamp.valueOf(ticket.getBookingTime()));
            // Store Enum as String
            pstmt.setString(4, ticket.getStatus().name());

            pstmt.executeUpdate();
            System.out.println("Ticket booked successfully!");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public List<Ticket> getTicketsByPassenger(int passengerId) {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM tickets WHERE passenger_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, passengerId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Ticket ticket = new Ticket(
                        rs.getInt("id"),
                        rs.getInt("flight_id"),
                        rs.getInt("passenger_id"),
                        rs.getTimestamp("booking_time").toLocalDateTime(), // Convert back to LocalDateTime
                        TicketStatus.valueOf(rs.getString("status"))       // Convert String back to Enum
                );
                tickets.add(ticket);
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return tickets;
    }

    public void cancelTicket(int ticketId) {
        String sql = "UPDATE tickets SET status = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // We assume your TicketStatus enum has a value "CANCELLED"
            pstmt.setString(1, TicketStatus.CANCELLED.name());
            pstmt.setInt(2, ticketId);

            int rows = pstmt.executeUpdate();
            if(rows > 0) {
                System.out.println("Ticket ID " + ticketId + " has been cancelled.");
            } else {
                System.out.println("Ticket ID " + ticketId + " not found.");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}