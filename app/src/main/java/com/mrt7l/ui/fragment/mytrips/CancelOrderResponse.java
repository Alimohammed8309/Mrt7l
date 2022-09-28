package com.mrt7l.ui.fragment.mytrips;

import java.util.Observable;

public class CancelOrderResponse extends Observable {


    private static CancelOrderResponse instance;
    private Mrt7alBean mrt7al;

    public static CancelOrderResponse getInstance(){
        if (instance == null){
            instance = new CancelOrderResponse();
        }
        return instance;
    }

    public Mrt7alBean getMrt7al() {
        return mrt7al;
    }

    public void setMrt7al(Mrt7alBean mrt7al) {
        this.mrt7al = mrt7al;
        setChanged();
    }


    public static class Mrt7alBean extends Observable{
        private Boolean success;
        private String msg;
        private DataBean data;

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
            setChanged();
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
            setChanged();
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
            setChanged();
        }

        public static class DataBean extends Observable{
            private Integer id;
            private Integer passenger_count;
            private Integer adults_count;
            private Integer children_count;
            private Integer babies_count;
            private Integer total_price;
            private Integer user_id;
            private String status;
            private String created;
            private Object modifed;
            private Integer trip_date_id;
            private Object ticket_number;
            private String mainPassanger;
            private Object promoCode;
            private String tripID;
            private Object receivedBy;
            private Object broker_name;
            private Object payMethod;
            private Object receipt_file;
            private Object confirmedUserID;
            private Integer canceledUserID;
            private TripDateBean trip_date;
            private UserBean user;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
                setChanged();
            }

            public Integer getPassenger_count() {
                return passenger_count;
            }

            public void setPassenger_count(Integer passenger_count) {
                this.passenger_count = passenger_count;
                setChanged();
            }

            public Integer getAdults_count() {
                return adults_count;
            }

            public void setAdults_count(Integer adults_count) {
                this.adults_count = adults_count;
                setChanged();
            }

            public Integer getChildren_count() {
                return children_count;
            }

            public void setChildren_count(Integer children_count) {
                this.children_count = children_count;
                setChanged();
            }

            public Integer getBabies_count() {
                return babies_count;
            }

            public void setBabies_count(Integer babies_count) {
                this.babies_count = babies_count;
                setChanged();
            }

            public Integer getTotal_price() {
                return total_price;
            }

            public void setTotal_price(Integer total_price) {
                this.total_price = total_price;
                setChanged();
            }

            public Integer getUser_id() {
                return user_id;
            }

            public void setUser_id(Integer user_id) {
                this.user_id = user_id;
                setChanged();
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
                setChanged();
            }

            public String getCreated() {
                return created;
            }

            public void setCreated(String created) {
                this.created = created;
                setChanged();
            }

            public Object getModifed() {
                return modifed;
            }

            public void setModifed(Object modifed) {
                this.modifed = modifed;
                setChanged();
            }

            public Integer getTrip_date_id() {
                return trip_date_id;
            }

            public void setTrip_date_id(Integer trip_date_id) {
                this.trip_date_id = trip_date_id;
                setChanged();
            }

            public Object getTicket_number() {
                return ticket_number;
            }

            public void setTicket_number(Object ticket_number) {
                this.ticket_number = ticket_number;
                setChanged();
            }

            public String getMainPassanger() {
                return mainPassanger;
            }

            public void setMainPassanger(String mainPassanger) {
                this.mainPassanger = mainPassanger;
                setChanged();
            }

            public Object getPromoCode() {
                return promoCode;
            }

            public void setPromoCode(Object promoCode) {
                this.promoCode = promoCode;
                setChanged();
            }

            public String getTripID() {
                return tripID;
            }

            public void setTripID(String tripID) {
                this.tripID = tripID;
                setChanged();
            }

            public Object getReceivedBy() {
                return receivedBy;
            }

            public void setReceivedBy(Object receivedBy) {
                this.receivedBy = receivedBy;
                setChanged();
            }

            public Object getBroker_name() {
                return broker_name;
            }

            public void setBroker_name(Object broker_name) {
                this.broker_name = broker_name;
                setChanged();
            }

            public Object getPayMethod() {
                return payMethod;
            }

            public void setPayMethod(Object payMethod) {
                this.payMethod = payMethod;
                setChanged();
            }

            public Object getReceipt_file() {
                return receipt_file;
            }

            public void setReceipt_file(Object receipt_file) {
                this.receipt_file = receipt_file;
                setChanged();
            }

            public Object getConfirmedUserID() {
                return confirmedUserID;
            }

            public void setConfirmedUserID(Object confirmedUserID) {
                this.confirmedUserID = confirmedUserID;
                setChanged();
            }

            public Integer getCanceledUserID() {
                return canceledUserID;
            }

            public void setCanceledUserID(Integer canceledUserID) {
                this.canceledUserID = canceledUserID;
                setChanged();
            }

            public TripDateBean getTrip_date() {
                return trip_date;
            }

            public void setTrip_date(TripDateBean trip_date) {
                this.trip_date = trip_date;
                setChanged();
            }

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
                setChanged();
            }

            public static class TripDateBean extends Observable{
                private Integer id;
                private Integer company_id;
                private FromCityBean from_city;
                private Integer to_city;
                private String datetime_from;
                private String datetime_to;
                private Integer tripDateTime;
                private Integer price;
                private Object tax;
                private String created;
                private String modified;
                private String tripType;
                private Integer available_count;
                private String trip_number;
                private Integer station_id;
                private String tenTrips;
                private String is_direct;
                private Integer duplication_count;
                private Integer child_price;
                private Integer baby_price;
                private Integer waitingTime;
                private Object company;
                private String type;
                private String bus_type;
                private Integer avab_chairs;
                private String trip_full_date;
                private String arrived_time;
                private String go_time;

                public Integer getId() {
                    return id;
                }

                public void setId(Integer id) {
                    this.id = id;
                    setChanged();
                }

                public Integer getCompany_id() {
                    return company_id;
                }

                public void setCompany_id(Integer company_id) {
                    this.company_id = company_id;
                    setChanged();
                }

                public FromCityBean getFrom_city() {
                    return from_city;
                }

                public void setFrom_city(FromCityBean from_city) {
                    this.from_city = from_city;
                    setChanged();
                }

                public Integer getTo_city() {
                    return to_city;
                }

                public void setTo_city(Integer to_city) {
                    this.to_city = to_city;
                    setChanged();
                }

                public String getDatetime_from() {
                    return datetime_from;
                }

                public void setDatetime_from(String datetime_from) {
                    this.datetime_from = datetime_from;
                    setChanged();
                }

                public String getDatetime_to() {
                    return datetime_to;
                }

                public void setDatetime_to(String datetime_to) {
                    this.datetime_to = datetime_to;
                    setChanged();
                }

                public Integer getTripDateTime() {
                    return tripDateTime;
                }

                public void setTripDateTime(Integer tripDateTime) {
                    this.tripDateTime = tripDateTime;
                    setChanged();
                }

                public Integer getPrice() {
                    return price;
                }

                public void setPrice(Integer price) {
                    this.price = price;
                    setChanged();
                }

                public Object getTax() {
                    return tax;
                }

                public void setTax(Object tax) {
                    this.tax = tax;
                    setChanged();
                }

                public String getCreated() {
                    return created;
                }

                public void setCreated(String created) {
                    this.created = created;
                    setChanged();
                }

                public String getModified() {
                    return modified;
                }

                public void setModified(String modified) {
                    this.modified = modified;
                    setChanged();
                }

                public String getTripType() {
                    return tripType;
                }

                public void setTripType(String tripType) {
                    this.tripType = tripType;
                    setChanged();
                }

                public Integer getAvailable_count() {
                    return available_count;
                }

                public void setAvailable_count(Integer available_count) {
                    this.available_count = available_count;
                    setChanged();
                }

                public String getTrip_number() {
                    return trip_number;
                }

                public void setTrip_number(String trip_number) {
                    this.trip_number = trip_number;
                    setChanged();
                }

                public Integer getStation_id() {
                    return station_id;
                }

                public void setStation_id(Integer station_id) {
                    this.station_id = station_id;
                    setChanged();
                }

                public String getTenTrips() {
                    return tenTrips;
                }

                public void setTenTrips(String tenTrips) {
                    this.tenTrips = tenTrips;
                    setChanged();
                }

                public String getIs_direct() {
                    return is_direct;
                }

                public void setIs_direct(String is_direct) {
                    this.is_direct = is_direct;
                    setChanged();
                }

                public Integer getDuplication_count() {
                    return duplication_count;
                }

                public void setDuplication_count(Integer duplication_count) {
                    this.duplication_count = duplication_count;
                    setChanged();
                }

                public Integer getChild_price() {
                    return child_price;
                }

                public void setChild_price(Integer child_price) {
                    this.child_price = child_price;
                    setChanged();
                }

                public Integer getBaby_price() {
                    return baby_price;
                }

                public void setBaby_price(Integer baby_price) {
                    this.baby_price = baby_price;
                    setChanged();
                }

                public Integer getWaitingTime() {
                    return waitingTime;
                }

                public void setWaitingTime(Integer waitingTime) {
                    this.waitingTime = waitingTime;
                    setChanged();
                }

                public Object getCompany() {
                    return company;
                }

                public void setCompany(Object company) {
                    this.company = company;
                    setChanged();
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                    setChanged();
                }

                public String getBus_type() {
                    return bus_type;
                }

                public void setBus_type(String bus_type) {
                    this.bus_type = bus_type;
                    setChanged();
                }

                public Integer getAvab_chairs() {
                    return avab_chairs;
                }

                public void setAvab_chairs(Integer avab_chairs) {
                    this.avab_chairs = avab_chairs;
                    setChanged();
                }

                public String getTrip_full_date() {
                    return trip_full_date;
                }

                public void setTrip_full_date(String trip_full_date) {
                    this.trip_full_date = trip_full_date;
                    setChanged();
                }

                public String getArrived_time() {
                    return arrived_time;
                }

                public void setArrived_time(String arrived_time) {
                    this.arrived_time = arrived_time;
                    setChanged();
                }

                public String getGo_time() {
                    return go_time;
                }

                public void setGo_time(String go_time) {
                    this.go_time = go_time;
                    setChanged();
                }

                public static class FromCityBean extends Observable{
                    private Integer id;
                    private String name;

                    public Integer getId() {
                        return id;
                    }

                    public void setId(Integer id) {
                        this.id = id;
                        setChanged();
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                        setChanged();
                    }
                }
            }

            public static class UserBean extends Observable{
                private String mobile;
                private String full_name;

                public String getMobile() {
                    return mobile;
                }

                public void setMobile(String mobile) {
                    this.mobile = mobile;
                    setChanged();
                }

                public String getFull_name() {
                    return full_name;
                }

                public void setFull_name(String full_name) {
                    this.full_name = full_name;
                    setChanged();
                }
            }
        }
    }
}
