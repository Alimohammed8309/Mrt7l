package com.mrt7l.model;

import com.google.gson.annotations.SerializedName;

import java.util.Observable;

public class changePasswordResponse  extends Observable {

    public static changePasswordResponse instance;

    public static changePasswordResponse getInstance(){
        if (instance == null){
            instance = new changePasswordResponse();
        }
        return instance;
    }
    /**
     * mrt7al : {"msg":"تم تغيير كلمة المرور بنجاح","success":true,"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjEyLCJleHAiOjE1OTc0MjU2NjZ9.Ku1D0ZgWZ3dw1mPwtzNM0DPalbiKG_YIhWSM-AXWn0g"}
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
         * msg : تم تغيير كلمة المرور بنجاح
         * success : true
         * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjEyLCJleHAiOjE1OTc0MjU2NjZ9.Ku1D0ZgWZ3dw1mPwtzNM0DPalbiKG_YIhWSM-AXWn0g
         */

        @SerializedName("msg")
        private String msg;
        @SerializedName("success")
        private boolean success;
        @SerializedName("token")
        private String token;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
            setChanged();
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
            setChanged();
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
            setChanged();
        }
    }
}
