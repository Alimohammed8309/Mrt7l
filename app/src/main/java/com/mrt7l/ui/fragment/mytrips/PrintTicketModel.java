package com.mrt7l.ui.fragment.mytrips;

import android.graphics.Bitmap;

import java.util.Observable;

public class PrintTicketModel extends Observable {

    private static PrintTicketModel instance;
    public static PrintTicketModel getInstance(){
        if (instance == null){
            instance = new PrintTicketModel();
        }
        return instance;
    }

    String tripNumber;
    String tripDate;
    String passengerName;
    String passportNumber;
    String ticketNumber;
    Bitmap bitmap;


    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

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
