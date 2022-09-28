package com.mrt7l.ui.fragment.reservation;

public class PassengerModel {

    public static PassengerModel instance;
    public static PassengerModel getInstance(){
        if (instance == null){
            instance = new PassengerModel();
        }
        return instance;
    }

    int id;
    String name;
    String phone;
    String passportNumber;
    String gender;
    String passengerType;


    public String getPassengerType() {
        return passengerType;
    }

    public void setPassengerType(String passengerType) {
        this.passengerType = passengerType;
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
        
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
        
    }
}
