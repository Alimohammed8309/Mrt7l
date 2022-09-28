package com.mrt7l.ui.fragment.home;

import java.util.ArrayList;
import java.util.Observable;

public class HomeResponse extends Observable {
    private static HomeResponse instance;


    public static HomeResponse getInstance(){
        if (instance == null){
            instance = new HomeResponse();
        }
        return instance;
    }
    private Mrt7alBean mrt7al;

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
        private ArrayList<DaySliderBean> day_slider;

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

        public ArrayList<DaySliderBean> getDay_slider() {
            return day_slider;
        }

        public void setDay_slider(ArrayList<DaySliderBean> day_slider) {
            this.day_slider = day_slider;
            setChanged();
        }

        public static class PaginationBean extends Observable{
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
            private int id;
            private int available_count;
            private int company_id;
            private String datetime_from;
            private String datetime_to;
            private String arrived_time;
            private String go_time;
            private String tripType;
            private int price;
            private String actualCount;
            private int adult_price;
            private FromCityBean from_city;
            private ToCityBean to_city;
            private int tripDateTime;
            private String tax;
            private String created;
            private String modified;
            private String trip_number;
            private int station_id;
            private String tenTrips;
            private String is_direct;
            private int duplication_count;
            private int child_price;
            private int baby_price;
            private int waitingTime;
            private CompanyBean company;
            private int passengerActualCount;
            private int passengerCountOnReservation;
            private int passengerNetAvailable;
            private String type;
            private String bus_type;
            private int avab_chairs;

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

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
                setChanged();
            }

            public int getAvailable_count() {
                return available_count;
            }

            public void setAvailable_count(int available_count) {
                this.available_count = available_count;
                setChanged();
            }

            public int getCompany_id() {
                return company_id;
            }

            public void setCompany_id(int company_id) {
                this.company_id = company_id;
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

            public String getTripType() {
                return tripType;
            }

            public void setTripType(String tripType) {
                this.tripType = tripType;
                setChanged();
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
                setChanged();
            }

            public String getActualCount() {
                return actualCount;
            }

            public void setActualCount(String actualCount) {
                this.actualCount = actualCount;
                setChanged();
            }

            public int getAdult_price() {
                return adult_price;
            }

            public void setAdult_price(int adult_price) {
                this.adult_price = adult_price;
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

            public int getTripDateTime() {
                return tripDateTime;
            }

            public void setTripDateTime(int tripDateTime) {
                this.tripDateTime = tripDateTime;
                setChanged();
            }

            public String getTax() {
                return tax;
            }

            public void setTax(String tax) {
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

            public String getTrip_number() {
                return trip_number;
            }

            public void setTrip_number(String trip_number) {
                this.trip_number = trip_number;
                setChanged();
            }

            public int getStation_id() {
                return station_id;
            }

            public void setStation_id(int station_id) {
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

            public int getDuplication_count() {
                return duplication_count;
            }

            public void setDuplication_count(int duplication_count) {
                this.duplication_count = duplication_count;
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

            public int getWaitingTime() {
                return waitingTime;
            }

            public void setWaitingTime(int waitingTime) {
                this.waitingTime = waitingTime;
                setChanged();
            }

            public CompanyBean getCompany() {
                return company;
            }

            public void setCompany(CompanyBean company) {
                this.company = company;
                setChanged();
            }

            public int getPassengerActualCount() {
                return passengerActualCount;
            }

            public void setPassengerActualCount(int passengerActualCount) {
                this.passengerActualCount = passengerActualCount;
                setChanged();
            }

            public int getPassengerCountOnReservation() {
                return passengerCountOnReservation;
            }

            public void setPassengerCountOnReservation(int passengerCountOnReservation) {
                this.passengerCountOnReservation = passengerCountOnReservation;
                setChanged();
            }

            public int getPassengerNetAvailable() {
                return passengerNetAvailable;
            }

            public void setPassengerNetAvailable(int passengerNetAvailable) {
                this.passengerNetAvailable = passengerNetAvailable;
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

            public static class FromCityBean extends Observable{
                private String name;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                    setChanged();
                }
            }

            public static class ToCityBean extends Observable{
                private String name;

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
                private String name;
                private String logo_pic;
                private String company_info;
                private int user_id;
                private String bank_account;
                private String address;
                private String is_star;
                private String notes;
                private int is_block;
                private ArrayList<CompanyPhotosBean> company_photos;
//                private UserBean user;
                private String reservation_policy;

                public String getReservation_policy() {
                    return reservation_policy;
                }

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

                public String getLogo_pic() {
                    return logo_pic;
                }

                public void setLogo_pic(String logo_pic) {
                    this.logo_pic = logo_pic;
                    setChanged();
                }

                public String getCompany_info() {
                    return company_info;
                }

                public void setCompany_info(String company_info) {
                    this.company_info = company_info;
                    setChanged();
                }

                public int getUser_id() {
                    return user_id;
                }

                public void setUser_id(int user_id) {
                    this.user_id = user_id;
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

                public String getIs_star() {
                    return is_star;
                }

                public void setIs_star(String is_star) {
                    this.is_star = is_star;
                    setChanged();
                }

                public String getNotes() {
                    return notes;
                }

                public void setNotes(String notes) {
                    this.notes = notes;
                    setChanged();
                }

                public int getIs_block() {
                    return is_block;
                }

                public void setIs_block(int is_block) {
                    this.is_block = is_block;
                    setChanged();
                }

                public ArrayList<CompanyPhotosBean> getCompany_photos() {
                    return company_photos;
                }

                public void setCompany_photos(ArrayList<CompanyPhotosBean> company_photos) {
                    this.company_photos = company_photos;
                    setChanged();
                }

//                public UserBean getUser() {
//                    return user;
//                }
//
//                public void setUser(UserBean user) {
//                    this.user = user;
//                    setChanged();
//                }

//                public static class UserBean extends Observable{
//                    private int id;
//                    private String city;
//                    private String full_name;
//
//                    public int getId() {
//                        return id;
//                    }
//
//                    public void setId(int id) {
//                        this.id = id;
//                        setChanged();
//                    }
//
//                    public String getCity() {
//                        return city;
//                    }
//
//                    public void setCity(String city) {
//                        this.city = city;
//                        setChanged();
//                    }
//
//                    public String getFull_name() {
//                        return full_name;
//                    }
//
//                    public void setFull_name(String full_name) {
//                        this.full_name = full_name;
//                        setChanged();
//                    }
//                }

                public static class CompanyPhotosBean extends Observable{
                    private int id;
                    private String photo;
                    private int company_id;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                        setChanged();
                    }

                    public String getPhoto() {
                        return photo;
                    }

                    public void setPhoto(String photo) {
                        this.photo = photo;
                        setChanged();
                    }

                    public int getCompany_id() {
                        return company_id;
                    }

                    public void setCompany_id(int company_id) {
                        this.company_id = company_id;
                        setChanged();
                    }
                }
            }
        }

        public static class DaySliderBean extends Observable{
            private String day;
            private String date;
            private String count;

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
                setChanged();
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
                setChanged();
            }

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
                setChanged();
            }
        }
    }
}
