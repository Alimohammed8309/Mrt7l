package com.mrt7l.ui.fragment.reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class ReservationResponse extends Observable {

    private static ReservationResponse instance;
    private Mrt7alBean mrt7al;
    private DataReqBean dataReq;

    public static ReservationResponse getInstance() {
        if (instance == null) {
            instance = new ReservationResponse();
        }
        return instance;
    }

    public Mrt7alBean getMrt7al() {
        return mrt7al;
    }

    public void setMrt7al(Mrt7alBean mrt7al) {
        this.mrt7al = mrt7al;
    }

    public DataReqBean getDataReq() {
        return dataReq;
    }

    public void setDataReq(DataReqBean dataReq) {
        this.dataReq = dataReq;
    }


    public static class Mrt7alBean {
        private boolean success;
        private String msg;
        private ArrayList<DataBean> data;

        public boolean getSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public ArrayList<DataBean> getData() {
            return data;
        }

        public void setData(ArrayList<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            private String first_name;
            private String second_name;
            private String third_name;
            private String mobile;
            private String passport_id;
            private String gender;
            private int id;
            private String passangerType;
            private ArrayList<SubPassangersBean> sub_passangers;
            private ArrayList<ReservationsBean> reservations;
            private String allowdReservation;
            private CompanyDetailsBean companyDetails;
            private StatisticsBean statistics;
            private String full_name;
            private int adults_count;
            private int children_count;
            private int babies_count;
            private int user_id;
            private int trip_date_id;
            private String status;
            private String mainPassanger;
            private int total_price;
            private int passenger_count;
            private String payMethod;
            private String ticket_number;
            private String created;

            public int getAdults_count() {
                return adults_count;
            }

            public void setAdults_count(int adults_count) {
                this.adults_count = adults_count;
            }

            public int getChildren_count() {
                return children_count;
            }

            public void setChildren_count(int children_count) {
                this.children_count = children_count;
            }

            public int getBabies_count() {
                return babies_count;
            }

            public void setBabies_count(int babies_count) {
                this.babies_count = babies_count;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public int getTrip_date_id() {
                return trip_date_id;
            }

            public void setTrip_date_id(int trip_date_id) {
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

            public int getTotal_price() {
                return total_price;
            }

            public void setTotal_price(int total_price) {
                this.total_price = total_price;
            }

            public int getPassenger_count() {
                return passenger_count;
            }

            public void setPassenger_count(int passenger_count) {
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

            public String getFirst_name() {
                return first_name;
            }

            public void setFirst_name(String first_name) {
                this.first_name = first_name;
            }

            public String getSecond_name() {
                return second_name;
            }

            public void setSecond_name(String second_name) {
                this.second_name = second_name;
            }

            public String getThird_name() {
                return third_name;
            }

            public void setThird_name(String third_name) {
                this.third_name = third_name;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getPassport_id() {
                return passport_id;
            }

            public void setPassport_id(String passport_id) {
                this.passport_id = passport_id;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getPassangerType() {
                return passangerType;
            }

            public void setPassangerType(String passangerType) {
                this.passangerType = passangerType;
            }

            public ArrayList<SubPassangersBean> getSub_passangers() {
                return sub_passangers;
            }

            public void setSub_passangers(ArrayList<SubPassangersBean> sub_passangers) {
                this.sub_passangers = sub_passangers;
            }

            public ArrayList<ReservationsBean> getReservations() {
                return reservations;
            }

            public void setReservations(ArrayList<ReservationsBean> reservations) {
                this.reservations = reservations;
            }

            public String getAllowdReservation() {
                return allowdReservation;
            }

            public void setAllowdReservation(String allowdReservation) {
                this.allowdReservation = allowdReservation;
            }

            public CompanyDetailsBean getCompanyDetails() {
                return companyDetails;
            }

            public void setCompanyDetails(CompanyDetailsBean companyDetails) {
                this.companyDetails = companyDetails;
            }

            public StatisticsBean getStatistics() {
                return statistics;
            }

            public void setStatistics(StatisticsBean statistics) {
                this.statistics = statistics;
            }

            public String getFull_name() {
                return full_name;
            }

            public void setFull_name(String full_name) {
                this.full_name = full_name;
            }

            public static class CompanyDetailsBean {
                private String name;
                private String logo_pic;
                private String company_info;
                private String bank_account;
                private String address;
                private int user_id;
                private String reservation_policy;
                private UserBean user;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getLogo_pic() {
                    return logo_pic;
                }

                public void setLogo_pic(String logo_pic) {
                    this.logo_pic = logo_pic;
                }

                public String getCompany_info() {
                    return company_info;
                }

                public void setCompany_info(String company_info) {
                    this.company_info = company_info;
                }

                public String getBank_account() {
                    return bank_account;
                }

                public void setBank_account(String bank_account) {
                    this.bank_account = bank_account;
                }

                public String getAddress() {
                    return address;
                }

                public void setAddress(String address) {
                    this.address = address;
                }

                public int getUser_id() {
                    return user_id;
                }

                public void setUser_id(int user_id) {
                    this.user_id = user_id;
                }

                public String getReservation_policy() {
                    return reservation_policy;
                }

                public void setReservation_policy(String reservation_policy) {
                    this.reservation_policy = reservation_policy;
                }

                public UserBean getUser() {
                    return user;
                }

                public void setUser(UserBean user) {
                    this.user = user;
                }

                public static class UserBean {
                    private int id;
                    private CityBean city;
                    private String full_name;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public CityBean getCity() {
                        return city;
                    }

                    public void setCity(CityBean city) {
                        this.city = city;
                    }

                    public String getFull_name() {
                        return full_name;
                    }

                    public void setFull_name(String full_name) {
                        this.full_name = full_name;
                    }

                    public static class CityBean {
                        private String name;

                        public String getName() {
                            return name;
                        }

                        public void setName(String name) {
                            this.name = name;
                        }
                    }
                }
            }

            public static class StatisticsBean {
                private FromCityBean fromCity;
                private ToCityBean toCity;
                private String tripDate;
                private String trip_full_date;
                private String arrivedTime;
                private String goTime;
                private int waitingTime;
                private String station;
                private int adults_count;
                private int children_count;
                private int babies_count;
                private int adultPrice;
                private int childPrice;
                private int babyPrice;
                private int discountPromo;
                private int user_id;
                private int trip_date_id;
                private String status;
                private String mainPassanger;
                private int total_ticket_price;
                private int total_price;
                private int passenger_count;
                private String priceBtn;

                public FromCityBean getFromCity() {
                    return fromCity;
                }

                public void setFromCity(FromCityBean fromCity) {
                    this.fromCity = fromCity;
                }

                public ToCityBean getToCity() {
                    return toCity;
                }

                public void setToCity(ToCityBean toCity) {
                    this.toCity = toCity;
                }

                public String getTripDate() {
                    return tripDate;
                }

                public void setTripDate(String tripDate) {
                    this.tripDate = tripDate;
                }

                public String getTrip_full_date() {
                    return trip_full_date;
                }

                public void setTrip_full_date(String trip_full_date) {
                    this.trip_full_date = trip_full_date;
                }

                public String getArrivedTime() {
                    return arrivedTime;
                }

                public void setArrivedTime(String arrivedTime) {
                    this.arrivedTime = arrivedTime;
                }

                public String getGoTime() {
                    return goTime;
                }

                public void setGoTime(String goTime) {
                    this.goTime = goTime;
                }

                public int getWaitingTime() {
                    return waitingTime;
                }

                public void setWaitingTime(int waitingTime) {
                    this.waitingTime = waitingTime;
                }

                public String getStation() {
                    return station;
                }

                public void setStation(String station) {
                    this.station = station;
                }

                public int getAdults_count() {
                    return adults_count;
                }

                public void setAdults_count(int adults_count) {
                    this.adults_count = adults_count;
                }

                public int getChildren_count() {
                    return children_count;
                }

                public void setChildren_count(int children_count) {
                    this.children_count = children_count;
                }

                public int getBabies_count() {
                    return babies_count;
                }

                public void setBabies_count(int babies_count) {
                    this.babies_count = babies_count;
                }

                public int getAdultPrice() {
                    return adultPrice;
                }

                public void setAdultPrice(int adultPrice) {
                    this.adultPrice = adultPrice;
                }

                public int getChildPrice() {
                    return childPrice;
                }

                public void setChildPrice(int childPrice) {
                    this.childPrice = childPrice;
                }

                public int getBabyPrice() {
                    return babyPrice;
                }

                public void setBabyPrice(int babyPrice) {
                    this.babyPrice = babyPrice;
                }

                public int getDiscountPromo() {
                    return discountPromo;
                }

                public void setDiscountPromo(int discountPromo) {
                    this.discountPromo = discountPromo;
                }

                public int getUser_id() {
                    return user_id;
                }

                public void setUser_id(int user_id) {
                    this.user_id = user_id;
                }

                public int getTrip_date_id() {
                    return trip_date_id;
                }

                public void setTrip_date_id(int trip_date_id) {
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

                public int getTotal_ticket_price() {
                    return total_ticket_price;
                }

                public void setTotal_ticket_price(int total_ticket_price) {
                    this.total_ticket_price = total_ticket_price;
                }

                public int getTotal_price() {
                    return total_price;
                }

                public void setTotal_price(int total_price) {
                    this.total_price = total_price;
                }

                public int getPassenger_count() {
                    return passenger_count;
                }

                public void setPassenger_count(int passenger_count) {
                    this.passenger_count = passenger_count;
                }

                public String getPriceBtn() {
                    return priceBtn;
                }

                public void setPriceBtn(String priceBtn) {
                    this.priceBtn = priceBtn;
                }

                public static class FromCityBean {
                    private String name;

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }
                }

                public static class ToCityBean {
                    private String name;

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }
                }
            }

            public static class SubPassangersBean {
                private String full_name;
                private String mobile;
                private String passport_id;
                private String gender;
                private int id;
                private int user_id;
                private String passangerType;
                private String allowdReservation;

                public String getFull_name() {
                    return full_name;
                }

                public void setFull_name(String full_name) {
                    this.full_name = full_name;
                }

                public String getMobile() {
                    return mobile;
                }

                public void setMobile(String mobile) {
                    this.mobile = mobile;
                }

                public String getPassport_id() {
                    return passport_id;
                }

                public void setPassport_id(String passport_id) {
                    this.passport_id = passport_id;
                }

                public String getGender() {
                    return gender;
                }

                public void setGender(String gender) {
                    this.gender = gender;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getUser_id() {
                    return user_id;
                }

                public void setUser_id(int user_id) {
                    this.user_id = user_id;
                }

                public String getPassangerType() {
                    return passangerType;
                }

                public void setPassangerType(String passangerType) {
                    this.passangerType = passangerType;
                }

                public String getAllowdReservation() {
                    return allowdReservation;
                }

                public void setAllowdReservation(String allowdReservation) {
                    this.allowdReservation = allowdReservation;
                }
            }

            public static class ReservationsBean {
                private String status;
                private int trip_date_id;
                private int id;
                private int user_id;
                private TripDateBean trip_date;

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public int getTrip_date_id() {
                    return trip_date_id;
                }

                public void setTrip_date_id(int trip_date_id) {
                    this.trip_date_id = trip_date_id;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getUser_id() {
                    return user_id;
                }

                public void setUser_id(int user_id) {
                    this.user_id = user_id;
                }

                public TripDateBean getTrip_date() {
                    return trip_date;
                }

                public void setTrip_date(TripDateBean trip_date) {
                    this.trip_date = trip_date;
                }

                public static class TripDateBean {
                    private int tripDateTime;
                    private int id;
                    private String type;
                    private String bus_type;
                    private int avab_chairs;
                    private int price;
                    private String trip_full_date;
                    private String arrived_time;
                    private String go_time;

                    public int getTripDateTime() {
                        return tripDateTime;
                    }

                    public void setTripDateTime(int tripDateTime) {
                        this.tripDateTime = tripDateTime;
                    }

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public String getBus_type() {
                        return bus_type;
                    }

                    public void setBus_type(String bus_type) {
                        this.bus_type = bus_type;
                    }

                    public int getAvab_chairs() {
                        return avab_chairs;
                    }

                    public void setAvab_chairs(int avab_chairs) {
                        this.avab_chairs = avab_chairs;
                    }

                    public int getPrice() {
                        return price;
                    }

                    public void setPrice(int price) {
                        this.price = price;
                    }

                    public String getTrip_full_date() {
                        return trip_full_date;
                    }

                    public void setTrip_full_date(String trip_full_date) {
                        this.trip_full_date = trip_full_date;
                    }

                    public String getArrived_time() {
                        return arrived_time;
                    }

                    public void setArrived_time(String arrived_time) {
                        this.arrived_time = arrived_time;
                    }

                    public String getGo_time() {
                        return go_time;
                    }

                    public void setGo_time(String go_time) {
                        this.go_time = go_time;
                    }
                }
            }
        }
    }

    public static class DataReqBean {
        private ArrayList<String> sub_passanger_id;
        private String payMethod;
        private String trip_date_id;
        private String mainPassanger;
        private String beforeConfirm;
        private Integer user_id;
        private ArrayList<String> passArr;

        public ArrayList<String> getSub_passanger_id() {
            return sub_passanger_id;
        }

        public void setSub_passanger_id(ArrayList<String> sub_passanger_id) {
            this.sub_passanger_id = sub_passanger_id;
        }

        public String getPayMethod() {
            return payMethod;
        }

        public void setPayMethod(String payMethod) {
            this.payMethod = payMethod;
        }

        public String getTrip_date_id() {
            return trip_date_id;
        }

        public void setTrip_date_id(String trip_date_id) {
            this.trip_date_id = trip_date_id;
        }

        public String getMainPassanger() {
            return mainPassanger;
        }

        public void setMainPassanger(String mainPassanger) {
            this.mainPassanger = mainPassanger;
        }

        public String getBeforeConfirm() {
            return beforeConfirm;
        }

        public void setBeforeConfirm(String beforeConfirm) {
            this.beforeConfirm = beforeConfirm;
        }

        public Integer getUser_id() {
            return user_id;
        }

        public void setUser_id(Integer user_id) {
            this.user_id = user_id;
        }

        public ArrayList<String> getPassArr() {
            return passArr;
        }

        public void setPassArr(ArrayList<String> passArr) {
            this.passArr = passArr;
        }
    }
}
