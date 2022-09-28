package com.mrt7l.ui.fragment.reservation;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReservationPost {

//    private static ReservationPostModel instance;
//    public static ReservationPostModel getInstance(){
//        if (instance == null){
//            instance = new ReservationPostModel();
//        }
//        return instance;
//    }


    @SerializedName("sub_passanger_id")
    private ArrayList<Integer> sub_passanger_ids = new ArrayList<>();
    @SerializedName("payMethod")
    private String payMethod;

    @SerializedName("beforeConfirm")
    private String beforeConfirm;

    @SerializedName("promoCode")
    private String promoCode;

    @SerializedName("trip_date_id")
    private int trip_date_id;

    @SerializedName("mainPassanger")
    private String mainPassanger;


    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public String getBeforeConfirm() {
        return beforeConfirm;
    }

    public void setBeforeConfirm(String beforeConfirm) {
        this.beforeConfirm = beforeConfirm;
    }

    public String getMainPassanger() {
        return mainPassanger;
    }

    public void setMainPassanger(String mainPassanger) {
        this.mainPassanger = mainPassanger;

    }

    public ArrayList<Integer> getSub_passanger_ids() {
        return sub_passanger_ids;
    }

    public void setSub_passanger_ids(ArrayList<Integer> sub_passanger_ids) {
        this.sub_passanger_ids = sub_passanger_ids;

    }

    public int getTrip_date_id() {
        return trip_date_id;
    }

    public void setTrip_date_id(int trip_date_id) {
        this.trip_date_id = trip_date_id;

    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }
}
