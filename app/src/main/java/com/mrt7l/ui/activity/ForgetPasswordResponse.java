package com.mrt7l.ui.activity;

import com.google.gson.annotations.SerializedName;

public class ForgetPasswordResponse {


    /**
     * mrt7al : {"success":true,"data":{"mobile":"123321123321"},"msg":"الحساب موجود"}
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
         * data : {"mobile":"123321123321"}
         * msg : الحساب موجود
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
             * mobile : 123321123321
             */

            @SerializedName("mobile")
            private String mobile;

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }
        }
    }
}
