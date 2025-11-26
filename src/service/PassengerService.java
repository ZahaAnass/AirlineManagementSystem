package service;

import dao.PassengerDAO;
import model.Passenger;

import java.util.List;

public class PassengerService {

    private final PassengerDAO passengerDAO;

    public PassengerService() {
        this.passengerDAO = new PassengerDAO();
    }

    public void addPassenger(Passenger passenger) {
        if (passenger != null) {
            passengerDAO.addPassenger(passenger);
        } else {
            System.out.println("Error: Passenger data cannot be null.");
        }
    }

    public Passenger getPassengerByEmail(String email) {
        return passengerDAO.getPassengerByEmail(email);
    }
}