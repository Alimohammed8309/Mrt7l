package com.mrt7l.ui.fragment.reservation.addpassengers;

public class PassengerPriceModel {


//    private static PassengerPriceModel instance;
//    public static PassengerPriceModel getInstance(){
//        if (instance == null){
//            instance = new PassengerPriceModel();
//        }
//        return instance;
//    }

    private String Type;
    private String count;
    private String price;

    public PassengerPriceModel(String type,String count,String price) {
        Type = type;
        this.count = count;
        this.price = price;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
