package com.mrt7l.ui.fragment.mytrips;

import java.util.ArrayList;
import java.util.Observable;

public class CurrentOrdersResponse extends Observable {

    private static CurrentOrdersResponse instance;
    private Mrt7alBean mrt7al;

    public static CurrentOrdersResponse getInstance(){
        if (instance == null){
            instance = new CurrentOrdersResponse();
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
        private ArrayList<DataBean> data;
        private PaginationBean pagination;

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

        public ArrayList<DataBean> getData() {
            return data;
        }

        public void setData(ArrayList<DataBean> data) {
            this.data = data;
            setChanged();
        }

        public PaginationBean getPagination() {
            return pagination;
        }

        public void setPagination(PaginationBean pagination) {
            this.pagination = pagination;
            setChanged();
        }

        public static class PaginationBean extends  Observable{
            private int count;
            private int current;
            private int perPage;
            private int page;
            private int requestedPage;
            private int pageCount;
            private int start;
            private int end;
            private Boolean prevPage;
            private Boolean nextPage;
            private String sort;
            private String direction;
            private Boolean sortDefault;
            private Boolean directionDefault;
            private ArrayList<?> completeSort;
            private String limit;
            private String scope;
            private String finder;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
                setChanged();
            }

            public int getCurrent() {
                return current;
            }

            public void setCurrent(int current) {
                this.current = current;
                setChanged();
            }

            public int getPerPage() {
                return perPage;
            }

            public void setPerPage(int perPage) {
                this.perPage = perPage;
                setChanged();
            }

            public int getPage() {
                return page;
            }

            public void setPage(int page) {
                this.page = page;
                setChanged();
            }

            public int getRequestedPage() {
                return requestedPage;
            }

            public void setRequestedPage(int requestedPage) {
                this.requestedPage = requestedPage;
                setChanged();
            }

            public int getPageCount() {
                return pageCount;
            }

            public void setPageCount(int pageCount) {
                this.pageCount = pageCount;
                setChanged();
            }

            public int getStart() {
                return start;
            }

            public void setStart(int start) {
                this.start = start;
                setChanged();
            }

            public int getEnd() {
                return end;
            }

            public void setEnd(int end) {
                this.end = end;
                setChanged();
            }

            public Boolean getPrevPage() {
                return prevPage;
            }

            public void setPrevPage(Boolean prevPage) {
                this.prevPage = prevPage;
                setChanged();
            }

            public Boolean getNextPage() {
                return nextPage;
            }

            public void setNextPage(Boolean nextPage) {
                this.nextPage = nextPage;
                setChanged();
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
                setChanged();
            }

            public String getDirection() {
                return direction;
            }

            public void setDirection(String direction) {
                this.direction = direction;
                setChanged();
            }

            public Boolean getSortDefault() {
                return sortDefault;
            }

            public void setSortDefault(Boolean sortDefault) {
                this.sortDefault = sortDefault;
                setChanged();
            }

            public Boolean getDirectionDefault() {
                return directionDefault;
            }

            public void setDirectionDefault(Boolean directionDefault) {
                this.directionDefault = directionDefault;
                setChanged();
            }

            public ArrayList<?> getCompleteSort() {
                return completeSort;
            }

            public void setCompleteSort(ArrayList<?> completeSort) {
                this.completeSort = completeSort;
                setChanged();
            }

            public String getLimit() {
                return limit;
            }

            public void setLimit(String limit) {
                this.limit = limit;
                setChanged();
            }

            public String getScope() {
                return scope;
            }

            public void setScope(String scope) {
                this.scope = scope;
                setChanged();
            }

            public String getFinder() {
                return finder;
            }

            public void setFinder(String finder) {
                this.finder = finder;
                setChanged();
            }
        }

        public static class DataBean extends Observable{
            private boolean selected;
            private String tripID;
            private int id;
            private String payMethod;
            private String receipt_file;
            private String ticket_number;
            private int passenger_count;
            private int children_count;
            private int babies_count;
            private int adults_count;
            private String promoCode;
            private int total_price;
            private int trip_date_id;
            private int user_id;
            private String status;
            private ArrayList<CancelReqsBean> cancel_reqs;
            private ArrayList<ResPassangersBean> res_passangers;
            private UserBean user;
            private TripDateBean trip_date;
            private String discountPromo;

            public String getDiscountPromo() {
                return discountPromo;
            }

            public void setDiscountPromo(String discountPromo) {
                this.discountPromo = discountPromo;
                setChanged();
            }

            public boolean isSelected() {
                return selected;
            }

            public void setSelected(boolean selected) {
                this.selected = selected;
                setChanged();
            }

            public String getTripID() {
                return tripID;
            }

            public void setTripID(String tripID) {
                this.tripID = tripID;
                setChanged();
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
                setChanged();
            }

            public String getPayMethod() {
                return payMethod;
            }

            public void setPayMethod(String payMethod) {
                this.payMethod = payMethod;
                setChanged();
            }

            public String getReceipt_file() {
                return receipt_file;
            }

            public void setReceipt_file(String receipt_file) {
                this.receipt_file = receipt_file;
                setChanged();
            }

            public String getTicket_number() {
                return ticket_number;
            }

            public void setTicket_number(String ticket_number) {
                this.ticket_number = ticket_number;
                setChanged();
            }

            public int getPassenger_count() {
                return passenger_count;
            }

            public void setPassenger_count(int passenger_count) {
                this.passenger_count = passenger_count;
                setChanged();
            }

            public int getChildren_count() {
                return children_count;
            }

            public void setChildren_count(int children_count) {
                this.children_count = children_count;
                setChanged();
            }

            public int getBabies_count() {
                return babies_count;
            }

            public void setBabies_count(int babies_count) {
                this.babies_count = babies_count;
                setChanged();
            }

            public int getAdults_count() {
                return adults_count;
            }

            public void setAdults_count(int adults_count) {
                this.adults_count = adults_count;
                setChanged();
            }

            public String getPromoCode() {
                return promoCode;
            }

            public void setPromoCode(String promoCode) {
                this.promoCode = promoCode;
                setChanged();
            }

            public int getTotal_price() {
                return total_price;
            }

            public void setTotal_price(int total_price) {
                this.total_price = total_price;
                setChanged();
            }

            public int getTrip_date_id() {
                return trip_date_id;
            }

            public void setTrip_date_id(int trip_date_id) {
                this.trip_date_id = trip_date_id;
                setChanged();
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
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

            public ArrayList<CancelReqsBean> getCancel_reqs() {
                return cancel_reqs;
            }

            public void setCancel_reqs(ArrayList<CancelReqsBean> cancel_reqs) {
                this.cancel_reqs = cancel_reqs;
                setChanged();
            }

            public ArrayList<ResPassangersBean> getRes_passangers() {
                return res_passangers;
            }

            public void setRes_passangers(ArrayList<ResPassangersBean> res_passangers) {
                this.res_passangers = res_passangers;
                setChanged();
            }

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
                setChanged();
            }

            public TripDateBean getTrip_date() {
                return trip_date;
            }

            public void setTrip_date(TripDateBean trip_date) {
                this.trip_date = trip_date;
                setChanged();
            }

            public static class UserBean extends Observable{
                private int id;
                private String username;
                private String date_of_birth;
                private int nationality_id;
                private String gender;
                private String mobile;
                private String passport_id;
                private int city_id;
                private String passport_file;
                private CityBean city;
                private NationalityBean nationality;
                private String full_name;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                    setChanged();
                }

                public String getUsername() {
                    return username;
                }

                public void setUsername(String username) {
                    this.username = username;
                    setChanged();
                }

                public String getDate_of_birth() {
                    return date_of_birth;
                }

                public void setDate_of_birth(String date_of_birth) {
                    this.date_of_birth = date_of_birth;
                    setChanged();
                }

                public int getNationality_id() {
                    return nationality_id;
                }

                public void setNationality_id(int nationality_id) {
                    this.nationality_id = nationality_id;
                    setChanged();
                }

                public String getGender() {
                    return gender;
                }

                public void setGender(String gender) {
                    this.gender = gender;
                    setChanged();
                }

                public String getMobile() {
                    return mobile;
                }

                public void setMobile(String mobile) {
                    this.mobile = mobile;
                    setChanged();
                }

                public String getPassport_id() {
                    return passport_id;
                }

                public void setPassport_id(String passport_id) {
                    this.passport_id = passport_id;
                    setChanged();
                }

                public int getCity_id() {
                    return city_id;
                }

                public void setCity_id(int city_id) {
                    this.city_id = city_id;
                    setChanged();
                }

                public String getPassport_file() {
                    return passport_file;
                }

                public void setPassport_file(String passport_file) {
                    this.passport_file = passport_file;
                    setChanged();
                }

                public CityBean getCity() {
                    return city;
                }

                public void setCity(CityBean city) {
                    this.city = city;
                    setChanged();
                }

                public NationalityBean getNationality() {
                    return nationality;
                }

                public void setNationality(NationalityBean nationality) {
                    this.nationality = nationality;
                    setChanged();
                }

                public String getFull_name() {
                    return full_name;
                }

                public void setFull_name(String full_name) {
                    this.full_name = full_name;
                    setChanged();
                }

                public static class CityBean extends Observable{
                    private int id;
                    private String name;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
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

                public static class NationalityBean extends Observable{
                    private int id;
                    private String name;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
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

            public static class TripDateBean extends Observable{
                private int id;
                private int company_id;
                private int waitingTime;
                private String datetime_from;
                private String datetime_to;
                private FromCityBean from_city;
                private ToCityBean to_city;
                private String tripType;
                private int station_id;
                private int tripDateTime;
                private int price;
                private int child_price;
                private int baby_price;
                private CompanyBean company;
                private StationBean station;
                private String type;
                private String bus_type;
                private int avab_chairs;
                private String trip_full_date;
                private String arrived_time;
                private String go_time;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                    setChanged();
                }

                public int getCompany_id() {
                    return company_id;
                }

                public void setCompany_id(int company_id) {
                    this.company_id = company_id;
                    setChanged();
                }

                public int getWaitingTime() {
                    return waitingTime;
                }

                public void setWaitingTime(int waitingTime) {
                    this.waitingTime = waitingTime;
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

                public FromCityBean getFrom_city() {
                    return from_city;
                }

                public void setFrom_city(FromCityBean from_city) {
                    this.from_city = from_city;
                    setChanged();
                }

                public ToCityBean getTo_city() {
                    return to_city;
                }

                public void setTo_city(ToCityBean to_city) {
                    this.to_city = to_city;
                    setChanged();
                }

                public String getTripType() {
                    return tripType;
                }

                public void setTripType(String tripType) {
                    this.tripType = tripType;
                    setChanged();
                }

                public int getStation_id() {
                    return station_id;
                }

                public void setStation_id(int station_id) {
                    this.station_id = station_id;
                    setChanged();
                }

                public int getTripDateTime() {
                    return tripDateTime;
                }

                public void setTripDateTime(int tripDateTime) {
                    this.tripDateTime = tripDateTime;
                    setChanged();
                }

                public int getPrice() {
                    return price;
                }

                public void setPrice(int price) {
                    this.price = price;
                    setChanged();
                }

                public int getChild_price() {
                    return child_price;
                }

                public void setChild_price(int child_price) {
                    this.child_price = child_price;
                    setChanged();
                }

                public int getBaby_price() {
                    return baby_price;
                }

                public void setBaby_price(int baby_price) {
                    this.baby_price = baby_price;
                    setChanged();
                }

                public CompanyBean getCompany() {
                    return company;
                }

                public void setCompany(CompanyBean company) {
                    this.company = company;
                    setChanged();
                }

                public StationBean getStation() {
                    return station;
                }

                public void setStation(StationBean station) {
                    this.station = station;
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

                public int getAvab_chairs() {
                    return avab_chairs;
                }

                public void setAvab_chairs(int avab_chairs) {
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
                    private int id;
                    private String name;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
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

                public static class ToCityBean extends Observable{
                    private int id;
                    private String name;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
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

                public static class CompanyBean extends Observable{
                    private int id;
                    private String bank_account;
                    private String address;
                    private String logo_pic;
                    private String name;
                    private String reservation_policy;

                    public String getReservation_policy() {
                        return reservation_policy;
                    }

                    public void setReservation_policy(String reservation_policy) {
                        this.reservation_policy = reservation_policy;
                    }

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                        setChanged();
                    }

                    public String getBank_account() {
                        return bank_account;
                    }

                    public void setBank_account(String bank_account) {
                        this.bank_account = bank_account;
                        setChanged();
                    }

                    public String getAddress() {
                        return address;
                    }

                    public void setAddress(String address) {
                        this.address = address;
                        setChanged();
                    }

                    public String getLogo_pic() {
                        return logo_pic;
                    }

                    public void setLogo_pic(String logo_pic) {
                        this.logo_pic = logo_pic;
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

                public static class StationBean extends Observable{
                    private int id;
                    private String name;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
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

            public static class CancelReqsBean extends Observable{
                private int id;
                private int reservation_id;
                private String cancel_reason;
                private String created;
                private String modifed;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                    setChanged();
                }

                public int getReservation_id() {
                    return reservation_id;
                }

                public void setReservation_id(int reservation_id) {
                    this.reservation_id = reservation_id;
                    setChanged();
                }

                public String getCancel_reason() {
                    return cancel_reason;
                }

                public void setCancel_reason(String cancel_reason) {
                    this.cancel_reason = cancel_reason;
                    setChanged();
                }

                public String getCreated() {
                    return created;
                }

                public void setCreated(String created) {
                    this.created = created;
                    setChanged();
                }

                public String getModifed() {
                    return modifed;
                }

                public void setModifed(String modifed) {
                    this.modifed = modifed;
                    setChanged();
                }
            }

            public static class ResPassangersBean extends Observable{
                private int id;
                private int reservation_id;
                private int sub_passanger_id;
                private SubPassangerBean sub_passanger;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                    setChanged();
                }

                public int getReservation_id() {
                    return reservation_id;
                }

                public void setReservation_id(int reservation_id) {
                    this.reservation_id = reservation_id;
                    setChanged();
                }

                public int getSub_passanger_id() {
                    return sub_passanger_id;
                }

                public void setSub_passanger_id(int sub_passanger_id) {
                    this.sub_passanger_id = sub_passanger_id;
                    setChanged();
                }

                public SubPassangerBean getSub_passanger() {
                    return sub_passanger;
                }

                public void setSub_passanger(SubPassangerBean sub_passanger) {
                    this.sub_passanger = sub_passanger;
                    setChanged();
                }

                public static class SubPassangerBean extends Observable{
                    private int id;
                    private int user_id;
                    private String full_name;
                    private String date_of_birth;
                    private int nationality_id;
                    private String gender;
                    private String passport_id;
                    private String passport_file;
                    private String mobile;
                    private String created;
                    private String modifed;
                    private String passangerType;
                    private String city_id;
                    private int is_active;
                    private String isMain;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                        setChanged();
                    }

                    public int getUser_id() {
                        return user_id;
                    }

                    public void setUser_id(int user_id) {
                        this.user_id = user_id;
                        setChanged();
                    }

                    public String getFull_name() {
                        return full_name;
                    }

                    public void setFull_name(String full_name) {
                        this.full_name = full_name;
                        setChanged();
                    }

                    public String getDate_of_birth() {
                        return date_of_birth;
                    }

                    public void setDate_of_birth(String date_of_birth) {
                        this.date_of_birth = date_of_birth;
                        setChanged();
                    }

                    public int getNationality_id() {
                        return nationality_id;
                    }

                    public void setNationality_id(int nationality_id) {
                        this.nationality_id = nationality_id;
                        setChanged();
                    }

                    public String getGender() {
                        return gender;
                    }

                    public void setGender(String gender) {
                        this.gender = gender;
                        setChanged();
                    }

                    public String getPassport_id() {
                        return passport_id;
                    }

                    public void setPassport_id(String passport_id) {
                        this.passport_id = passport_id;
                        setChanged();
                    }

                    public String getPassport_file() {
                        return passport_file;
                    }

                    public void setPassport_file(String passport_file) {
                        this.passport_file = passport_file;
                        setChanged();
                    }

                    public String getMobile() {
                        return mobile;
                    }

                    public void setMobile(String mobile) {
                        this.mobile = mobile;
                        setChanged();
                    }

                    public String getCreated() {
                        return created;
                    }

                    public void setCreated(String created) {
                        this.created = created;
                        setChanged();
                    }

                    public String getModifed() {
                        return modifed;
                    }

                    public void setModifed(String modifed) {
                        this.modifed = modifed;
                        setChanged();
                    }

                    public String getPassangerType() {
                        return passangerType;
                    }

                    public void setPassangerType(String passangerType) {
                        this.passangerType = passangerType;
                        setChanged();
                    }

                    public String getCity_id() {
                        return city_id;
                    }

                    public void setCity_id(String city_id) {
                        this.city_id = city_id;
                        setChanged();
                    }

                    public int getIs_active() {
                        return is_active;
                    }

                    public void setIs_active(int is_active) {
                        this.is_active = is_active;
                        setChanged();
                    }

                    public String getIsMain() {
                        return isMain;
                    }

                    public void setIsMain(String isMain) {
                        this.isMain = isMain;
                        setChanged();
                    }
                }
            }
        }
    }
}
