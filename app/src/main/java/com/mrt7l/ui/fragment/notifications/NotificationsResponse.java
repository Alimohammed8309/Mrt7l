package com.mrt7l.ui.fragment.notifications;

import java.util.ArrayList;
import java.util.Observable;

public class NotificationsResponse extends Observable {

    private static NotificationsResponse instance;
    /**
     * mrt7al : {"success":true,"msg":"تم الحفظ بنجاح","data":[{"id":1,"noty_body":"تم تأكيد رحلتك رقم 3 من جدة للرياض","noty_type":"reservation","created":"2020-09-23T19:26:19+03:00","modified":"","title":"تم تأكيد رحلتك رقم 3 من جدة للرياض","fromUser":1,"toUser":3,"status":"unseen"}]}
     */

    private Mrt7alBean mrt7al;

    public static NotificationsResponse getInstance(){
        if(instance == null){
            instance = new NotificationsResponse();
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
        /**
         * success : true
         * msg : تم الحفظ بنجاح
         * data : [{"id":1,"noty_body":"تم تأكيد رحلتك رقم 3 من جدة للرياض","noty_type":"reservation","created":"2020-09-23T19:26:19+03:00","modified":"","title":"تم تأكيد رحلتك رقم 3 من جدة للرياض","fromUser":1,"toUser":3,"status":"unseen"}]
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
            /**
             * id : 1
             * noty_body : تم تأكيد رحلتك رقم 3 من جدة للرياض
             * noty_type : reservation
             * created : 2020-09-23T19:26:19+03:00
             * modified :
             * title : تم تأكيد رحلتك رقم 3 من جدة للرياض
             * fromUser : 1
             * toUser : 3
             * status : unseen
             */

            private int id;
            private String noty_body;
            private String noty_type;
            private String created;
            private String modified;
            private String title;
            private int fromUser;
            private int toUser;
            private String status;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
                setChanged();
            }

            public String getNoty_body() {
                return noty_body;
            }

            public void setNoty_body(String noty_body) {
                this.noty_body = noty_body;
                setChanged();
            }

            public String getNoty_type() {
                return noty_type;
            }

            public void setNoty_type(String noty_type) {
                this.noty_type = noty_type;
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

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
                setChanged();
            }

            public int getFromUser() {
                return fromUser;
            }

            public void setFromUser(int fromUser) {
                this.fromUser = fromUser;
                setChanged();
            }

            public int getToUser() {
                return toUser;
            }

            public void setToUser(int toUser) {
                this.toUser = toUser;
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
    }
}
