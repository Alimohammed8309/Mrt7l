package com.mrt7l.ui.activity;

import com.google.gson.annotations.SerializedName;

public class ConfirmPasswordResponse {


    /**
     * mrt7al : {"success":true,"data":{"id":36,"mobile":"123321123321","modified":"2021-02-04T15:37:34+03:00"},"msg":"تم تغيير كلمة المرور بنجاح"}
     */

    @SerializedName("mrt7al")
    private Mrt7alBean mrt7al;

    public Mrt7alBean getMrt7al() {
        return mrt7al;
    }

    public void setMrt7al(Mrt7alBean mrt7al) {
        this.mrt7al = mrt7al;
    }

    public static class Mrt7alBean {
        /**
         * success : true
         * data : {"id":36,"mobile":"123321123321","modified":"2021-02-04T15:37:34+03:00"}
         * msg : تم تغيير كلمة المرور بنجاح
         */

        @SerializedName("success")
        private Boolean success;
        @SerializedName("data")
        private DataBean data;
        @SerializedName("msg")
        private String msg;

        public Boolean isSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public static class DataBean {
            /**
             * id : 36
             * mobile : 123321123321
             * modified : 2021-02-04T15:37:34+03:00
             */

            @SerializedName("id")
            private Integer id;
            @SerializedName("mobile")
            private String mobile;
            @SerializedName("modified")
            private String modified;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getModified() {
                return modified;
            }

            public void setModified(String modified) {
                this.modified = modified;
            }
        }
    }
}
