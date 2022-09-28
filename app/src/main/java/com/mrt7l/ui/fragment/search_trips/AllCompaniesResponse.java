package com.mrt7l.ui.fragment.search_trips;

import java.util.ArrayList;
import java.util.Observable;

public class AllCompaniesResponse  extends Observable {
    
    public static AllCompaniesResponse instance;
    private Mrt7alBean mrt7al;

    public static AllCompaniesResponse getInstance(){
        if (instance == null){
            instance = new AllCompaniesResponse();
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

        public static class DataBean extends Observable{
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
