package service;

import dao.FlightDAO;
import dao.PassengerDAO;
import dao.TicketDAO;
import model.Flight;
import model.Passenger;
import model.Ticket;
import model.TicketStatus;

import java.time.LocalDateTime;

public class BookingService {

    private final TicketDAO ticketDAO;
    private final FlightDAO flightDAO;
    private final PassengerDAO passengerDAO;

    public BookingService() {
        this.ticketDAO = new TicketDAO();
        this.flightDAO = new FlightDAO();
        this.passengerDAO = new PassengerDAO();
    }

    // Book a ticket (Create passenger -> Check Seats -> Create Ticket -> Reduce Seats)
    public boolean bookTicket(int flightId, Passenger passenger) {
        Flight flight = flightDAO.getFlightById(flightId);

        if (flight == null) {
            System.out.println("Flight not found!");
            return false;
        }

        if (flight.getAvailableSeats() <= 0) {
            System.out.println("No seats available for this flight.");
            return false;
        }

        // 1. Add Passenger to DB (or get ID if exists logic could be added here)
        int passengerId = passengerDAO.addPassenger(passenger);
        if (passengerId == -1) {
            System.out.println("Failed to register passenger.");
            return false;
        }

        // 2. Create Ticket Object
        Ticket ticket = new Ticket(0, flightId, passengerId, LocalDateTime.now(), TicketStatus.BOOKED);

        // 3. Save Ticket to DB
        ticketDAO.bookTicket(ticket);

        // 4. Update Flight Seat Count (-1)
        flightDAO.updateSeats(flightId, flight.getAvailableSeats() - 1);

        System.out.println("Booking finalized for Passenger ID: " + passengerId);
        return true;
    }

    public boolean cancelTicket(int ticketId) {
        Ticket ticket = ticketDAO.getTicketById(ticketId);

        if (ticket != null && ticket.getStatus() == TicketStatus.BOOKED) {
            ticketDAO.cancelTicket(ticket.getId());

            Flight flight = flightDAO.getFlightById(ticket.getFlightId());

            if (flight != null) {
                flightDAO.updateSeats(flight.getId(), flight.getAvailableSeats() + 1);
                System.out.println("Seat count restored for Flight ID: " + flight.getId());
            }

            System.out.println("Ticket cancelled successfully!");
            return true;
        } else {
            System.out.println("Error: Ticket not found or already cancelled.");
            return false;
        }
    }
}