package com.mrt7l.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Observable;

public class RegisterCollectionResponse  extends Observable {

    private static RegisterCollectionResponse instance;
    private Mrt7alBean mrt7al;


    public static RegisterCollectionResponse getInstance(){
        if (instance == null){
            instance = new RegisterCollectionResponse();
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


    public static class Mrt7alBean extends Observable {
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
            private ArrayList<CitiesBean> cities;
            private ArrayList<BusTypeClass> bus_type;

            private ArrayList<NationalitiesBean> nationalities;
            private GenderBean gender;
            private ArrayList<CompaniesBean> companies;
            public ArrayList<BusTypeClass> getBus_type() {
                return bus_type;
            }
            public void setBus_type(ArrayList<BusTypeClass> bus_typess) {
                this.bus_type = bus_typess;
                setChanged();
            }
            public ArrayList<CitiesBean> getCities() {
                return cities;
            }

            public void setCities(ArrayList<CitiesBean> cities) {
                this.cities = cities;
                setChanged();
            }

            public ArrayList<NationalitiesBean> getNationalities() {
                return nationalities;
            }

            public void setNationalities(ArrayList<NationalitiesBean> nationalities) {
                this.nationalities = nationalities;
                setChanged();
            }

            public GenderBean getGender() {
                return gender;
            }

            public void setGender(GenderBean gender) {
                this.gender = gender;
                setChanged();
            }

            public ArrayList<CompaniesBean> getCompanies() {
                return companies;
            }

            public void setCompanies(ArrayList<CompaniesBean> companies) {
                this.companies = companies;
                setChanged();
            }

            public static class GenderBean extends Observable{
                @SerializedName("1")
                private String _$1;
                @SerializedName("2")
                private String _$2;

                public String get_$1() {
                    return _$1;
                }

                public void set_$1(String _$1) {
                    this._$1 = _$1;
                    setChanged();
                }

                public String get_$2() {
                    return _$2;
                }

                public void set_$2(String _$2) {
                    this._$2 = _$2;
                    setChanged();
                }
            }
            public static class BusTypeClass extends Observable {
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
            public static class CitiesBean extends Observable{
                private int id;
                private String name;
                private Object created;
                private String modified;
                private double city_lat;
                private double city_long;
                private String location_desc;

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

                public Object getCreated() {
                    return created;
                }

                public void setCreated(Object created) {
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

                public double getCity_lat() {
                    return city_lat;
                }

                public void setCity_lat(double city_lat) {
                    this.city_lat = city_lat;
                    setChanged();
                }

                public double getCity_long() {
                    return city_long;
                }

                public void setCity_long(double city_long) {
                    this.city_long = city_long;
                    setChanged();
                }

                public String getLocation_desc() {
                    return location_desc;
                }

                public void setLocation_desc(String location_desc) {
                    this.location_desc = location_desc;
                    setChanged();
                }
            }

            public static class NationalitiesBean extends Observable{
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

            public static class CompaniesBean extends Observable{
                private int id;
                private String name;
                private int user_id;

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

                public int getUser_id() {
                    return user_id;
                }

                public void setUser_id(int user_id) {
                    this.user_id = user_id;
                    setChanged();
                }
            }
        }
    }
}
