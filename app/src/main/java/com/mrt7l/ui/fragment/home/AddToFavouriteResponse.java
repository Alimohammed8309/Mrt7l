package com.mrt7l.ui.fragment.home;

import java.util.Observable;

public class AddToFavouriteResponse extends Observable {
    private static AddToFavouriteResponse instance;
    /**
     * mrt7al : {"success":true,"msg":"تم الحفظ بنجاح","data":{
     * "id":19,"company_id":2,"user_id":1,"status":"dislike","created":
     * "2020-08-12T04:28:37+03:00"}}
     */

    private Mrt7alBean mrt7al;

    public static AddToFavouriteResponse getInstance(){
        if (instance == null){
            instance = new AddToFavouriteResponse();
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
         * data : {"id":19,"company_id":2,"user_id":1,"status":"dislike","created":"2020-08-12T04:28:37+03:00"}
         */

        private boolean success;
        private String msg;
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
             * id : 19
             * company_id : 2
             * user_id : 1
             * status : dislike
             * created : 2020-08-12T04:28:37+03:00
             */

            private int id;
            private int company_id;
            private int user_id;
            private String status;
            private String created;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
                setChanged();
            }

            public int getCompany_id() {
                return company_id;
            }

            public void setCompany_id(int company_id) {
                this.company_id = company_id;
                setChanged();
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
                setChanged();
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
                setChanged();
            }

            public String getCreated() {
                return created;
            }

            public void setCreated(String created) {
                this.created = created;
                setChanged();
            }
        }
    }
}
