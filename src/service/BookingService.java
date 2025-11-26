package service;

import dao.TicketDAO;

import model.Ticket;
import model.TicketStatus;

public class BookingService {

    public final TicketDAO ticketDAO;

    public BookingService() {
        this.ticketDAO = new TicketDAO();
    }

    public void bookTicket(Ticket ticket) {
        if (ticket != null) {
            ticketDAO.bookTicket(ticket);
        } else {
            System.out.println("Error: Ticket data cannot be null.");
        }
    }

    public boolean cancelTicket(int ticketId) {
        Ticket ticket = ticketDAO.getTicketById(ticketId);
        if (ticket != null && ticket.getStatus() == TicketStatus.BOOKED) {
            ticket.setStatus(TicketStatus.CANCELLED);
            ticketDAO.cancelTicket(ticket.getId());
            System.out.println("Ticket cancelled successfully!");
            return true;
        } else {
            System.out.println("Error: Ticket not found or already cancelled.");
            return false;
        }
    }
}