package com.mrt7l.model;


import com.google.gson.annotations.SerializedName;

import java.util.Observable;

public class ContactUsModel extends Observable {

    public static ContactUsModel instance;

    public static ContactUsModel getInstance(){
        if (instance == null){
            instance = new ContactUsModel();
        }
        return instance;
    }
    /**
     * mrt7al : {"success":true,"msg":"تم ارسال الطلب بنجاح  ","data":{"full_name":"312321321","mobile":"1234536123561234567123456","notes":"313123","created":"2020-08-07T14:54:09+00:00","id":2}}
     */

     @SerializedName("mrt7al")
    private Mrt7alBean mrt7al;

    public Mrt7alBean getMrt7al() {
        return mrt7al;
    }

    public void setMrt7al(Mrt7alBean mrt7al) {
        this.mrt7al = mrt7al;
        setChanged();
    }

    public static class Mrt7alBean extends Observable{
        /**
         * success : true
         * msg : تم ارسال الطلب بنجاح
         * data : {"full_name":"312321321","mobile":"1234536123561234567123456","notes":"313123","created":"2020-08-07T14:54:09+00:00","id":2}
         */

        @SerializedName("success")
        private boolean success;
        @SerializedName("msg")
        private String msg;
        @SerializedName("data")
        private DataBean data;

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

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
            setChanged();
        }

        public static class DataBean extends Observable{
            /**
             * full_name : 312321321
             * mobile : 1234536123561234567123456
             * notes : 313123
             * created : 2020-08-07T14:54:09+00:00
             * id : 2
             */

            @SerializedName("full_name")
            private String fullName;
            @SerializedName("mobile")
            private String mobile;
            @SerializedName("notes")
            private String notes;
            @SerializedName("created")
            private String created;
            @SerializedName("id")
            private int id;

            public String getFullName() {
                return fullName;
            }

            public void setFullName(String fullName) {
                this.fullName = fullName;
                setChanged();
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
                setChanged();
            }

            public String getNotes() {
                return notes;
            }

            public void setNotes(String notes) {
                this.notes = notes;
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
