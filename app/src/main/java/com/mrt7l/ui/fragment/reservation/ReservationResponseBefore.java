package com.mrt7l.ui.fragment.reservation;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;


public class ReservationResponseBefore extends Observable {


    /**
     * mrt7al : {"success":true,"msg":"الفاتورة","data":[{"adults_count":1,"children_count":0,"babies_count":0,"ticketPrice":null,"discountPromo":0,"user_id":157,"trip_date_id":111125,"status":"pending","mainPassanger":"ON","total_price":0,"passenger_count":1,"priceBtn":"تكلفة الحجز 0ريال"}]}
     */

    @SerializedName("mrt7al")
    private Mrt7alBean mrt7al;

    public Mrt7alBean getMrt7al() {
        return mrt7al;
    }

    public void setMrt7al(Mrt7alBean mrt7al) {
        this.mrt7al = mrt7al;
        setChanged();
    }
    public static ReservationResponseBefore instance;
    public static ReservationResponseBefore getInstance(){
        if (instance == null){
            instance = new ReservationResponseBefore();
        }
        return instance;
    }

    public   class Mrt7alBean extends Observable {
        /**
         * success : true
         * msg : الفاتورة
         * data : [{"adults_count":1,"children_count":0,"babies_count":0,"ticketPrice":null,"discountPromo":0,"user_id":157,"trip_date_id":111125,"status":"pending","mainPassanger":"ON","total_price":0,"passenger_count":1,"priceBtn":"تكلفة الحجز 0ريال"}]
         */

        @SerializedName("success")
        private Boolean success;
        @SerializedName("msg")
        private String msg;
        @SerializedName("data")
        private ArrayList<DataBean> data;

        
        public   class DataBean extends Observable {
            /**
             * adults_count : 1
             * children_count : 0
             * babies_count : 0
             * ticketPrice : null
             * discountPromo : 0
             * user_id : 157
             * trip_date_id : 111125
             * status : pending
             * mainPassanger : ON
             * total_price : 0
             * passenger_count : 1
             * priceBtn : تكلفة الحجز 0ريال
             */

            @SerializedName("adults_count")
            private int adultsCount;
            @SerializedName("children_count")
            private int childrenCount;
            @SerializedName("babies_count")
            private int babiesCount;
            @SerializedName("ticketPrice")
            private int ticketPrice;
            @SerializedName("discountPromo")
            private int discountPromo;
            @SerializedName("user_id")
            private int userId;
            @SerializedName("trip_date_id")
            private int tripDateId;
            @SerializedName("status")
            private String status;
            @SerializedName("mainPassanger")
            private String mainPassanger;
            @SerializedName("total_price")
            private int totalPrice;
            @SerializedName("passenger_count")
            private int passengerCount;
            @SerializedName("priceBtn")
            private String priceBtn;

            public int getAdultsCount() {
                return adultsCount;
            }

            public void setAdultsCount(int adultsCount) {
                this.adultsCount = adultsCount;
            }

            public int getChildrenCount() {
                return childrenCount;
            }

            public void setChildrenCount(int childrenCount) {
                this.childrenCount = childrenCount;
            }

            public int getBabiesCount() {
                return babiesCount;
            }

            public void setBabiesCount(int babiesCount) {
                this.babiesCount = babiesCount;
            }

            public int getTicketPrice() {
                return ticketPrice;
            }

            public void setTicketPrice(int ticketPrice) {
                this.ticketPrice = ticketPrice;
            }

            public int getDiscountPromo() {
                return discountPromo;
            }

            public void setDiscountPromo(int discountPromo) {
                this.discountPromo = discountPromo;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public int getTripDateId() {
                return tripDateId;
            }

            public void setTripDateId(int tripDateId) {
                this.tripDateId = tripDateId;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getMainPassanger() {
                return mainPassanger;
            }

            public void setMainPassanger(String mainPassanger) {
                this.mainPassanger = mainPassanger;
            }

            public int getTotalPrice() {
                return totalPrice;
            }

            public void setTotalPrice(int totalPrice) {
                this.totalPrice = totalPrice;
            }

            public int getPassengerCount() {
                return passengerCount;
            }

            public void setPassengerCount(int passengerCount) {
                this.passengerCount = passengerCount;
            }

            public String getPriceBtn() {
                return priceBtn;
            }

            public void setPriceBtn(String priceBtn) {
                this.priceBtn = priceBtn;
            }
        }
    }
}
