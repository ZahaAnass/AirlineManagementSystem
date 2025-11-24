package model;

import java.time.LocalDateTime;

public class Ticket {
    private int id;
    private int flightId;
    private int passengerId;
    private LocalDateTime bookingTime;
    private TicketStatus status;

    // Constructor
    public Ticket(int id, int flightId, int passengerId, LocalDateTime bookingTime, TicketStatus status) {
        this.id = id;
        this.flightId = flightId;
        this.passengerId = passengerId;
        this.bookingTime = bookingTime;
        this.status = status;
    }

    // --- Getters ---

    public int getId() {
        return id;
    }

    public int getFlightId() {
        return flightId;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public TicketStatus getStatus() {
        return status;
    }

    // --- Setters ---

    public void setId(int id) {
        this.id = id;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    // --- toString Method ---

    @Override
    public String toString() {
        return "Ticket ID: " + id +
                " | Flight ID: " + flightId +
                " | Passenger ID: " + passengerId +
                " | Date: " + bookingTime +
                " | Status: " + status;
    }
}