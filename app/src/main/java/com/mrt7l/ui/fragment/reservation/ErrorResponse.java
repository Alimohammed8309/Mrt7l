package com.mrt7l.ui.fragment.reservation;

import com.google.gson.annotations.SerializedName;

import java.util.Observable;

public class ErrorResponse extends Observable {


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
        @SerializedName("success")
        private Boolean success;
        @SerializedName("msg")
        private String msg;
        @SerializedName("data")
        private Object data;
        @SerializedName("isRegisterd")
        private boolean isRegisterd;
        @SerializedName("msgUrl")
        private String msgUrl;

        public String getMsgUrl() {
            return msgUrl;
        }

        public void setMsgUrl(String msgUrl) {
            this.msgUrl = msgUrl;
        }

        public boolean isRegisterd() {
            return isRegisterd;
        }

        public void setRegisterd(boolean registerd) {
            isRegisterd = registerd;
        }

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

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
            setChanged();
        }
    }
}
