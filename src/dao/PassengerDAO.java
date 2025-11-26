package dao;

import database.DBConnection;
import model.Passenger;

import java.sql.*;

public class PassengerDAO {

    public int addPassenger(Passenger passenger) {
        String sql = "INSERT INTO passengers (name, email, phone) VALUES (?, ?, ?)";
        int generatedId = -1;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, passenger.getName());
            pstmt.setString(2, passenger.getEmail());
            pstmt.setString(3, passenger.getPhone());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedId = generatedKeys.getInt(1);
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error adding passenger: " + e.getMessage());
        }

        return generatedId;
    }

    public Passenger getPassengerByEmail(String email) {
        String sql = "SELECT * FROM passengers WHERE email = ?";
        Passenger passenger = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                passenger = new Passenger(
                        rs.getInt("passenger_id"), // Matches SQL column name
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error getting passenger: " + e.getMessage());
        }
        return passenger;
    }
}