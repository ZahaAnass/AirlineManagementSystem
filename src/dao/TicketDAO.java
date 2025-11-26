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
            pstmt.setTimestamp(3, Timestamp.valueOf(ticket.getBookingTime()));
            pstmt.setString(4, ticket.getStatus().name());

            pstmt.executeUpdate();
            System.out.println("Ticket booked successfully in DB!");

        } catch (SQLException e) {
            System.out.println("Error booking ticket: " + e.getMessage());
        }
    }

    public Ticket getTicketById(int ticketId) {
        String sql = "SELECT * FROM tickets WHERE ticket_id = ?";
        Ticket ticket = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, ticketId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                ticket = new Ticket(
                        rs.getInt("ticket_id"), // Matches SQL column name
                        rs.getInt("flight_id"),
                        rs.getInt("passenger_id"),
                        rs.getTimestamp("booking_time").toLocalDateTime(),
                        TicketStatus.valueOf(rs.getString("status"))
                );
            }

        } catch (SQLException e) {
            System.out.println("Error getting ticket: " + e.getMessage());
        }
        return ticket;
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
                        rs.getInt("ticket_id"), // Matches SQL column name
                        rs.getInt("flight_id"),
                        rs.getInt("passenger_id"),
                        rs.getTimestamp("booking_time").toLocalDateTime(),
                        TicketStatus.valueOf(rs.getString("status"))
                );
                tickets.add(ticket);
            }

        } catch (SQLException e) {
            System.out.println("Error getting tickets: " + e.getMessage());
        }
        return tickets;
    }

    public void cancelTicket(int ticketId) {
        String sql = "UPDATE tickets SET status = ? WHERE ticket_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, TicketStatus.CANCELLED.name());
            pstmt.setInt(2, ticketId);

            int rows = pstmt.executeUpdate();
            if(rows > 0) {
                System.out.println("Ticket status updated to CANCELLED.");
            } else {
                System.out.println("Ticket ID " + ticketId + " not found.");
            }

        } catch (SQLException e) {
            System.out.println("Error cancelling ticket: " + e.getMessage());
        }
    }
}