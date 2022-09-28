package com.mrt7l.ui.fragment.company_details;

import java.util.ArrayList;
import java.util.Observable;

public class CompanyDetailsResponse extends Observable {

    private static CompanyDetailsResponse instance;
    private Mrt7alBean mrt7al;

    public static CompanyDetailsResponse getInstance(){
        if (instance == null){
            instance = new CompanyDetailsResponse();
        }
        return instance;
    }
    public static void removeInstance(){
        instance = null;
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
        private PaginationBean pagination;
        private ArrayList<DaySliderBean> day_slider;

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

        public PaginationBean getPagination() {
            return pagination;
        }

        public void setPagination(PaginationBean pagination) {
            this.pagination = pagination;
        }

        public ArrayList<DaySliderBean> getDay_slider() {
            return day_slider;
        }

        public void setDay_slider(ArrayList<DaySliderBean> day_slider) {
            this.day_slider = day_slider;
        }

        public static class DataBean {
            private int id;
            private String name;
            private String logo_pic;
            private String company_info;
            private int user_id;
            private String created;
            private String modified;
            private String bank_account;
            private String address;
            private String is_star;
            private String notes;
            private int is_block;
            private String reservation_policy;
            private FirstPhotoBean first_photo;
            private ArrayList<TripDatesBean> trip_dates;
            private ArrayList<FavoriteCompaniesBean> favorite_companies;
            private UserBean user;
            private ArrayList<CompanyPhotosBean> company_photos;
            private String stars;
            private String typeOfBuses;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

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

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getCreated() {
                return created;
            }

            public void setCreated(String created) {
                this.created = created;
            }

            public String getModified() {
                return modified;
            }

            public void setModified(String modified) {
                this.modified = modified;
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

            public String getIs_star() {
                return is_star;
            }

            public void setIs_star(String is_star) {
                this.is_star = is_star;
            }

            public String getNotes() {
                return notes;
            }

            public void setNotes(String notes) {
                this.notes = notes;
            }

            public int getIs_block() {
                return is_block;
            }

            public void setIs_block(int is_block) {
                this.is_block = is_block;
            }

            public String getReservation_policy() {
                return reservation_policy;
            }

            public void setReservation_policy(String reservation_policy) {
                this.reservation_policy = reservation_policy;
            }

            public FirstPhotoBean getFirst_photo() {
                return first_photo;
            }

            public void setFirst_photo(FirstPhotoBean first_photo) {
                this.first_photo = first_photo;
            }

            public ArrayList<TripDatesBean> getTrip_dates() {
                return trip_dates;
            }

            public void setTrip_dates(ArrayList<TripDatesBean> trip_dates) {
                this.trip_dates = trip_dates;
            }

            public ArrayList<FavoriteCompaniesBean> getFavorite_companies() {
                return favorite_companies;
            }

            public void setFavorite_companies(ArrayList<FavoriteCompaniesBean> favorite_companies) {
                this.favorite_companies = favorite_companies;
            }

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public ArrayList<CompanyPhotosBean> getCompany_photos() {
                return company_photos;
            }

            public void setCompany_photos(ArrayList<CompanyPhotosBean> company_photos) {
                this.company_photos = company_photos;
            }

            public String getStars() {
                return stars;
            }

            public void setStars(String stars) {
                this.stars = stars;
            }

            public String getTypeOfBuses() {
                return typeOfBuses;
            }

            public void setTypeOfBuses(String typeOfBuses) {
                this.typeOfBuses = typeOfBuses;
            }

            public static class FavoriteCompaniesBean extends Observable{
                /**
                 * company_id : 1
                 * status : like
                 */

                private int company_id;
                private String status;

                public int getCompany_id() {
                    return company_id;
                }

                public void setCompany_id(int company_id) {
                    this.company_id = company_id;
                    setChanged();
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                    setChanged();
                }
            }

            public static class FirstPhotoBean {
                private int id;
                private String photo;
                private int company_id;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getPhoto() {
                    return photo;
                }

                public void setPhoto(String photo) {
                    this.photo = photo;
                }

                public int getCompany_id() {
                    return company_id;
                }

                public void setCompany_id(int company_id) {
                    this.company_id = company_id;
                }
            }

            public static class UserBean {
                private int id;
                private String first_name;
                private String second_name;
                private String third_name;
                private int city_id;
                private CityBean city;
                private ArrayList<RatesBean> rates;
                private String full_name;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
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

                public int getCity_id() {
                    return city_id;
                }

                public void setCity_id(int city_id) {
                    this.city_id = city_id;
                }

                public CityBean getCity() {
                    return city;
                }

                public void setCity(CityBean city) {
                    this.city = city;
                }

                public ArrayList<RatesBean> getRates() {
                    return rates;
                }

                public void setRates(ArrayList<RatesBean> rates) {
                    this.rates = rates;
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
            public static class RatesBean extends Observable{
                /**
                 * user_id_service : 2
                 * rateSum : 5
                 * rateCounts : 1
                 */

                private int user_id_service;
                private double rateSum;
                private int rateCounts;
                private double netCount;

                public void setRateSum(double rateSum) {
                    this.rateSum = rateSum;
                }

                public double getNetCount() {
                    return netCount;
                }

                public void setNetCount(double netCount) {
                    this.netCount = netCount;
                }

                public int getUser_id_service() {
                    return user_id_service;
                }

                public void setUser_id_service(int user_id_service) {
                    this.user_id_service = user_id_service;
                    setChanged();
                }

                public double getRateSum() {
                    return rateSum;
                }

                public void setRateSum(int rateSum) {
                    this.rateSum = rateSum;
                    setChanged();
                }

                public int getRateCounts() {
                    return rateCounts;
                }

                public void setRateCounts(int rateCounts) {
                    this.rateCounts = rateCounts;
                    setChanged();
                }
            }

            public static class TripDatesBean {
                private int available_count;
                private int reservation_count;
                private int available_chairs;
                private int id;
                private int company_id;
                private String datetime_from;
                private String datetime_to;
                private String tripType;
                private int tripDateTime;
                private int waitingTime;
                private int trip_number;
                private StationBean station;
                private ToCityBean to_city;
                private FromCityBean from_city;
                private String type;
                private String bus_type;
                private int avab_chairs;
                private int price;
                private String trip_full_date;
                private String arrived_time;
                private String go_time;
                private String companyName;

                public int getTrip_number() {
                    return trip_number;
                }

                public void setTrip_number(int trip_number) {
                    this.trip_number = trip_number;
                }

                public String getCompanyName() {
                    return companyName;
                }

                public void setCompanyName(String companyName) {
                    this.companyName = companyName;
                }

                public int getAvailable_count() {
                    return available_count;
                }

                public void setAvailable_count(int available_count) {
                    this.available_count = available_count;
                }

                public int getReservation_count() {
                    return reservation_count;
                }

                public void setReservation_count(int reservation_count) {
                    this.reservation_count = reservation_count;
                }

                public int getAvailable_chairs() {
                    return available_chairs;
                }

                public void setAvailable_chairs(int available_chairs) {
                    this.available_chairs = available_chairs;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getCompany_id() {
                    return company_id;
                }

                public void setCompany_id(int company_id) {
                    this.company_id = company_id;
                }

                public String getDatetime_from() {
                    return datetime_from;
                }

                public void setDatetime_from(String datetime_from) {
                    this.datetime_from = datetime_from;
                }

                public String getDatetime_to() {
                    return datetime_to;
                }

                public void setDatetime_to(String datetime_to) {
                    this.datetime_to = datetime_to;
                }

                public String getTripType() {
                    return tripType;
                }

                public void setTripType(String tripType) {
                    this.tripType = tripType;
                }

                public int getTripDateTime() {
                    return tripDateTime;
                }

                public void setTripDateTime(int tripDateTime) {
                    this.tripDateTime = tripDateTime;
                }

                public int getWaitingTime() {
                    return waitingTime;
                }

                public void setWaitingTime(int waitingTime) {
                    this.waitingTime = waitingTime;
                }

                public StationBean getStation() {
                    return station;
                }

                public void setStation(StationBean station) {
                    this.station = station;
                }

                public ToCityBean getTo_city() {
                    return to_city;
                }

                public void setTo_city(ToCityBean to_city) {
                    this.to_city = to_city;
                }

                public FromCityBean getFrom_city() {
                    return from_city;
                }

                public void setFrom_city(FromCityBean from_city) {
                    this.from_city = from_city;
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

                public static class StationBean {
                    private int id;
                    private String name;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }
                }

                public static class ToCityBean {
                    private int id;
                    private String name;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }
                }

                public static class FromCityBean {
                    private int id;
                    private String name;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }
                }
            }

            public static class CompanyPhotosBean {
                private int id;
                private String photo;
                private int company_id;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getPhoto() {
                    return photo;
                }

                public void setPhoto(String photo) {
                    this.photo = photo;
                }

                public int getCompany_id() {
                    return company_id;
                }

                public void setCompany_id(int company_id) {
                    this.company_id = company_id;
                }
            }
        }

        public static class PaginationBean {
            private int count;
            private int current;
            private int perPage;
            private int pageCount;
            private int start;
            private int end;
            private Boolean prevPage;
            private Boolean nextPage;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getCurrent() {
                return current;
            }

            public void setCurrent(int current) {
                this.current = current;
            }

            public int getPerPage() {
                return perPage;
            }

            public void setPerPage(int perPage) {
                this.perPage = perPage;
            }

            public int getPageCount() {
                return pageCount;
            }

            public void setPageCount(int pageCount) {
                this.pageCount = pageCount;
            }

            public int getStart() {
                return start;
            }

            public void setStart(int start) {
                this.start = start;
            }

            public int getEnd() {
                return end;
            }

            public void setEnd(int end) {
                this.end = end;
            }

            public Boolean getPrevPage() {
                return prevPage;
            }

            public void setPrevPage(Boolean prevPage) {
                this.prevPage = prevPage;
            }

            public Boolean getNextPage() {
                return nextPage;
            }

            public void setNextPage(Boolean nextPage) {
                this.nextPage = nextPage;
            }
        }

        public static class DaySliderBean {
            private String day;
            private String date;
            private String count;

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }
        }
    }
}
