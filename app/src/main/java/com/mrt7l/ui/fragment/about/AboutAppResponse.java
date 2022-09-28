package com.mrt7l.ui.fragment.about;

import java.util.Observable;

public class AboutAppResponse extends Observable {
    
    private static AboutAppResponse instance;
    private Mrt7alBean mrt7al;

    public static AboutAppResponse getInstance(){
        if (instance == null){
            instance = new AboutAppResponse();
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
            private String about_app;
            private String app_rules;
            private String app_info;
            private String reservation_rules;
            private String created;
            private String modifed;
            private String videoAPP;
            private String reservation_num;
            private String whats_num;
            private String support_num;
            private String customer_num;
            private String policy;
            private String mobile_whats;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
                setChanged();
            }

            public String getAbout_app() {
                return about_app;
            }

            public void setAbout_app(String about_app) {
                this.about_app = about_app;
                setChanged();
            }

            public String getApp_rules() {
                return app_rules;
            }

            public void setApp_rules(String app_rules) {
                this.app_rules = app_rules;
                setChanged();
            }

            public String getApp_info() {
                return app_info;
            }

            public void setApp_info(String app_info) {
                this.app_info = app_info;
                setChanged();
            }

            public String getReservation_rules() {
                return reservation_rules;
            }

            public void setReservation_rules(String reservation_rules) {
                this.reservation_rules = reservation_rules;
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

            public String getVideoAPP() {
                return videoAPP;
            }

            public void setVideoAPP(String videoAPP) {
                this.videoAPP = videoAPP;
                setChanged();
            }

            public String getReservation_num() {
                return reservation_num;
            }

            public void setReservation_num(String reservation_num) {
                this.reservation_num = reservation_num;
                setChanged();
            }

            public String getWhats_num() {
                return whats_num;
            }

            public void setWhats_num(String whats_num) {
                this.whats_num = whats_num;
                setChanged();
            }

            public String getSupport_num() {
                return support_num;
            }

            public void setSupport_num(String support_num) {
                this.support_num = support_num;
                setChanged();
            }

            public String getCustomer_num() {
                return customer_num;
            }

            public void setCustomer_num(String customer_num) {
                this.customer_num = customer_num;
                setChanged();
            }

            public String getPolicy() {
                return policy;
            }

            public void setPolicy(String policy) {
                this.policy = policy;
                setChanged();
            }

            public String getMobile_whats() {
                return mobile_whats;
            }

            public void setMobile_whats(String mobile_whats) {
                this.mobile_whats = mobile_whats;
                setChanged();
            }
        }
    }
}
