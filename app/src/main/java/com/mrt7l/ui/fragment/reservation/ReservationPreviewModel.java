package com.mrt7l.ui.fragment.reservation;

import java.util.ArrayList;
import java.util.Observable;

public class ReservationPreviewModel extends Observable{

    private static ReservationPreviewModel instance;
    public static ReservationPreviewModel getInstance(){
        if (instance == null){
            instance = new ReservationPreviewModel();
        }
        return instance;
    }


    private ArrayList<PassengerModel> passengerModels= new ArrayList<>();
    private String from = "";
    private String to= "";
    private String tripDate= "";
    private String companyImage= "";
    private String companyName= "";
    private String CompanyCity= "";
    private String companyAddress= "";
    private String bankAccountNumber= "";
    private int tripID= 0;
    private int pricePerPerson= 0;
    private int tax= 0;
    private int availableCount =0;
    private int companyID;

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
        setChanged();
    }

    public int getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(int availableCount) {
        this.availableCount = availableCount;
        setChanged();
    }

    public ArrayList<PassengerModel> getPassengerModels() {
        return passengerModels;
    }

    public void setPassengerModels(ArrayList<PassengerModel> passengerModels) {
        this.passengerModels = passengerModels;
        setChanged();

    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
        setChanged();

    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
        setChanged();

    }

    public String getTripDate() {
        return tripDate;
    }

    public void setTripDate(String tripDate) {
        this.tripDate = tripDate;
        setChanged();

    }

    public String getCompanyImage() {
        return companyImage;
    }

    public void setCompanyImage(String companyImage) {
        this.companyImage = companyImage;
        setChanged();

    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
        setChanged();

    }

    public String getCompanyCity() {
        return CompanyCity;
    }

    public void setCompanyCity(String companyCity) {
        CompanyCity = companyCity;
        setChanged();

    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
        setChanged();

    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
        setChanged();

    }

    public int getTripID() {
        return tripID;
    }

    public void setTripID(int tripID) {
        this.tripID = tripID;
        setChanged();

    }

    public int getPricePerPerson() {
        return pricePerPerson;
    }

    public void setPricePerPerson(int pricePerPerson) {
        this.pricePerPerson = pricePerPerson;
        setChanged();

    }

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
        setChanged();

    }
}
