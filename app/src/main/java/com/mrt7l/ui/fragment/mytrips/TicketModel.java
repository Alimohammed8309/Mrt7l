package com.mrt7l.ui.fragment.mytrips;

import java.util.Observable;

public class TicketModel extends Observable {

    private static TicketModel instance;
    public static TicketModel getInstance(){
        if (instance == null){
            instance = new TicketModel();
        }
        return instance;
    }

    String tripNumber;
    String tripDate;
    String passengerName;
    String passportNumber;
    String ticketNumber;


    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getTripNumber() {
        return tripNumber;
    }

    public void setTripNumber(String tripNumber) {
        this.tripNumber = tripNumber;
    }

    public String getTripDate() {
        return tripDate;
    }

    public void setTripDate(String tripDate) {
        this.tripDate = tripDate;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }
}
