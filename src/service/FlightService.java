package service;

import dao.FlightDAO;
import model.Flight;

import java.util.List;

public class FlightService {

    private final FlightDAO flightDAO;

    public FlightService() {
        this.flightDAO = new FlightDAO();
    }

    public void addFlight(Flight flight) {
        if (flight != null) {
            flightDAO.addFlight(flight);
        } else {
            System.out.println("Error: Flight data cannot be null.");
        }
    }

    public List<Flight> searchFlights(String origin, String destination) {
        return flightDAO.searchFlights(origin, destination);
    }

    public List<Flight> getAllFlights() {
        return flightDAO.getAllFlights();
    }

    public Flight getFlightById(int id) {
        return flightDAO.getFlightById(id);
    }
}