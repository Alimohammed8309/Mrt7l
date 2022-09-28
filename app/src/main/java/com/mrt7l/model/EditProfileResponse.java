package com.mrt7l.model;

import java.util.Observable;

public class EditProfileResponse  extends Observable {

    public static EditProfileResponse instance;
    private Mrt7alBean mrt7al;

    public static EditProfileResponse getInstance(){
        if (instance == null){
            instance = new EditProfileResponse();
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
            private String id;
            private String mobile;
            private String passport_id;
            private String passport_file;
            private String first_name;
            private String second_name;
            private String third_name;
            private String last_name;
            private Integer nationality_id;
            private String gender;
            private String date_of_birth;
            private Integer city_id;
            private Integer user_group_id;
            private String created;
            private String modified;
            private String username;
            private String email;
            private Integer is_active;
            private String deviceToken;
            private String passangerType;
            private String confirm_password;
            private String full_name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public String getPassport_file() {
                return passport_file;
            }

            public void setPassport_file(String passport_file) {
                this.passport_file = passport_file;
                setChanged();
            }

            public String getFirst_name() {
                return first_name;
            }

            public void setFirst_name(String first_name) {
                this.first_name = first_name;
                setChanged();
            }

            public String getSecond_name() {
                return second_name;
            }

            public void setSecond_name(String second_name) {
                this.second_name = second_name;
                setChanged();
            }

            public String getThird_name() {
                return third_name;
            }

            public void setThird_name(String third_name) {
                this.third_name = third_name;
                setChanged();
            }

            public String getLast_name() {
                return last_name;
            }

            public void setLast_name(String last_name) {
                this.last_name = last_name;
                setChanged();
            }

            public Integer getNationality_id() {
                return nationality_id;
            }

            public void setNationality_id(Integer nationality_id) {
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

            public String getDate_of_birth() {
                return date_of_birth;
            }

            public void setDate_of_birth(String date_of_birth) {
                this.date_of_birth = date_of_birth;
                setChanged();
            }

            public Integer getCity_id() {
                return city_id;
            }

            public void setCity_id(Integer city_id) {
                this.city_id = city_id;
                setChanged();
            }

            public Integer getUser_group_id() {
                return user_group_id;
            }

            public void setUser_group_id(Integer user_group_id) {
                this.user_group_id = user_group_id;
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

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
                setChanged();
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
                setChanged();
            }

            public Integer getIs_active() {
                return is_active;
            }

            public void setIs_active(Integer is_active) {
                this.is_active = is_active;
                setChanged();
            }

            public String getDeviceToken() {
                return deviceToken;
            }

            public void setDeviceToken(String deviceToken) {
                this.deviceToken = deviceToken;
                setChanged();
            }

            public String getPassangerType() {
                return passangerType;
            }

            public void setPassangerType(String passangerType) {
                this.passangerType = passangerType;
                setChanged();
            }

            public String getConfirm_password() {
                return confirm_password;
            }

            public void setConfirm_password(String confirm_password) {
                this.confirm_password = confirm_password;
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
