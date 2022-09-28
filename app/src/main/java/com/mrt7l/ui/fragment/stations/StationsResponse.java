package com.mrt7l.ui.fragment.stations;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class StationsResponse extends Observable {

    private static StationsResponse instance;
    /**
     * mrt7al : {"success":true,"msg":"تم الحفظ بنجاح","data":[{"id":1,"name":"محطة الرمل","city_id":1,"long_at":"29.90308319999999","lat_at":"31.2029259","address":"محطة الرمل","distance":"137 mi"},{"id":2,"name":"محطة الجونة","city_id":1,"long_at":"33.6626435","lat_at":"27.3675075","address":"محطة الجونة","distance":"283 mi"}]}
     */

    private Mrt7alBean mrt7al;

    public static StationsResponse getInstance(){
        if (instance == null){
            instance = new StationsResponse();
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
        /**
         * success : true
         * msg : تم الحفظ بنجاح
         * data : [{"id":1,"name":"محطة الرمل","city_id":1,"long_at":"29.90308319999999","lat_at":"31.2029259","address":"محطة الرمل","distance":"137 mi"},{"id":2,"name":"محطة الجونة","city_id":1,"long_at":"33.6626435","lat_at":"27.3675075","address":"محطة الجونة","distance":"283 mi"}]
         */

        private boolean success;
        private String msg;
        private ArrayList<DataBean> data;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
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

        public static class DataBean extends Observable{

            private int id;
            private String name;
            private int city_id;
            private String long_at;
            private String lat_at;
            private String address;
            private String distance;

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

            public int getCity_id() {
                return city_id;
            }

            public void setCity_id(int city_id) {
                this.city_id = city_id;
                setChanged();
            }

            public String getLong_at() {
                return long_at;
            }

            public void setLong_at(String long_at) {
                this.long_at = long_at;
                setChanged();
            }

            public String getLat_at() {
                return lat_at;
            }

            public void setLat_at(String lat_at) {
                this.lat_at = lat_at;
                setChanged();
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
                setChanged();
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
                setChanged();
            }
        }
    }
}
