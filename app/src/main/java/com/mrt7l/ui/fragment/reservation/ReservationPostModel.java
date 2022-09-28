package com.mrt7l.ui.fragment.reservation;

import com.google.gson.annotations.SerializedName;
import com.mrt7l.ui.fragment.passengers.PassengersResponse;
import com.mrt7l.ui.fragment.reservation.addpassengers.AddPassengersFragment;

import java.util.ArrayList;
import java.util.Observable;

public class ReservationPostModel extends Observable {

    private static ReservationPostModel instance;
    public static ReservationPostModel getInstance(){
        if (instance == null){
            instance = new ReservationPostModel();
        }
        return instance;
    }
    public ArrayList<AddPassengersFragment.DataBean> subPassengers = new ArrayList<>();
    @SerializedName("sub_passanger_id")
    private ArrayList<Integer> sub_passanger_ids = new ArrayList<>();
    @SerializedName("payMethod")
    private int payMethod;

    @SerializedName("beforeConfirm")
    private String beforeConfirm;

    @SerializedName("promoCode")
    private String promoCode;

    @SerializedName("trip_date_id")
    private int trip_date_id;

    @SerializedName("mainPassanger")
    private String mainPassanger;


    public ArrayList<AddPassengersFragment.DataBean> getSubPassengers() {
        return subPassengers;
    }

    public void setSubPassengers(ArrayList<AddPassengersFragment.DataBean> subPassengers) {
        this.subPassengers = subPassengers;
        setChanged();
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
        setChanged();
    }

    public String getBeforeConfirm() {
        return beforeConfirm;
    }

    public void setBeforeConfirm(String beforeConfirm) {
        this.beforeConfirm = beforeConfirm;
        setChanged();
    }

    public String getMainPassanger() {
        return mainPassanger;
    }

    public void setMainPassanger(String mainPassanger) {
        this.mainPassanger = mainPassanger;
        setChanged();
    }

    public ArrayList<Integer> getSub_passanger_ids() {
        return sub_passanger_ids;
    }

    public void setSub_passanger_ids(ArrayList<Integer> sub_passanger_ids) {
        this.sub_passanger_ids = sub_passanger_ids;
        setChanged();
    }

    public int getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(int payMethod) {
        this.payMethod = payMethod;
        setChanged();
    }

    public int getTrip_date_id() {
        return trip_date_id;
    }

    public void setTrip_date_id(int trip_date_id) {
        this.trip_date_id = trip_date_id;
        setChanged();
    }
}
