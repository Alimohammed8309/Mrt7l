package com.mrt7l.ui.fragment.passengers;

import java.util.Observable;

public class AddPassengerResponse extends Observable {

    private static AddPassengerResponse instance;
    private Mrt7alBean mrt7al;


    public static AddPassengerResponse getInstance(){
        if (instance == null){
            instance = new AddPassengerResponse();
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

        public static class DataBean extends Observable {
            private String full_name;
            private String date_of_birth;
            private int nationality_id;
            private String gender;
            private String passport_id;
            private String mobile;
            private int user_id;
            private String passangerType;
            private String passport_file;
            private String created;
            private int id;

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

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
                setChanged();
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
                setChanged();
            }

            public String getPassangerType() {
                return passangerType;
            }

            public void setPassangerType(String passangerType) {
                this.passangerType = passangerType;
                setChanged();
            }

            public String getPassport_file() {
                return passport_file;
            }

            public void setPassport_file(String passport_file) {
                this.passport_file = passport_file;
                setChanged();
            }

            public String getCreated() {
                return created;
            }

            public void setCreated(String created) {
                this.created = created;
                setChanged();
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
                setChanged();
            }
        }
    }
}
