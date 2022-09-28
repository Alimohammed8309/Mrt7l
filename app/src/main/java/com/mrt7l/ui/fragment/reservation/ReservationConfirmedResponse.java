package com.mrt7l.ui.fragment.reservation;

import java.util.Observable;

public class ReservationConfirmedResponse extends Observable {

    private static ReservationConfirmedResponse instance;
    private Mrt7alBean mrt7al;

    public static   ReservationConfirmedResponse getInstance(){
        if (instance == null){
            instance = new ReservationConfirmedResponse();
        }
        return instance;
    }

    public Mrt7alBean getMrt7al() {
        return mrt7al;
    }

    public void setMrt7al(Mrt7alBean mrt7al) {
        this.mrt7al = mrt7al;
    }


    public static class Mrt7alBean {
        private Boolean success;
        private String msg;
        private DataBean data;

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            private Integer adults_count;
            private Integer children_count;
            private Integer babies_count;
            private Integer user_id;
            private Integer trip_date_id;
            private String status;
            private String mainPassanger;
            private Integer total_price;
            private Integer passenger_count;
            private String payMethod;
            private String ticket_number;
            private String created;
            private Integer id;

            public Integer getAdults_count() {
                return adults_count;
            }

            public void setAdults_count(Integer adults_count) {
                this.adults_count = adults_count;
            }

            public Integer getChildren_count() {
                return children_count;
            }

            public void setChildren_count(Integer children_count) {
                this.children_count = children_count;
            }

            public Integer getBabies_count() {
                return babies_count;
            }

            public void setBabies_count(Integer babies_count) {
                this.babies_count = babies_count;
            }

            public Integer getUser_id() {
                return user_id;
            }

            public void setUser_id(Integer user_id) {
                this.user_id = user_id;
            }

            public Integer getTrip_date_id() {
                return trip_date_id;
            }

            public void setTrip_date_id(Integer trip_date_id) {
                this.trip_date_id = trip_date_id;
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

            public Integer getTotal_price() {
                return total_price;
            }

            public void setTotal_price(Integer total_price) {
                this.total_price = total_price;
            }

            public Integer getPassenger_count() {
                return passenger_count;
            }

            public void setPassenger_count(Integer passenger_count) {
                this.passenger_count = passenger_count;
            }

            public String getPayMethod() {
                return payMethod;
            }

            public void setPayMethod(String payMethod) {
                this.payMethod = payMethod;
            }

            public String getTicket_number() {
                return ticket_number;
            }

            public void setTicket_number(String ticket_number) {
                this.ticket_number = ticket_number;
            }

            public String getCreated() {
                return created;
            }

            public void setCreated(String created) {
                this.created = created;
            }

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }
        }
    }
}
