package com.mrt7l.ui.activity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Observable;


public class ConfirmCodeResponse  extends Observable implements Serializable {


    /**
     * mrt7al : {"success":false,"msg":"الكود التفعيلى خاطىء"}
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

    public static class Mrt7alBean extends Observable {
        /**
         * success : false
         * msg : الكود التفعيلى خاطىء
         */

        @SerializedName("success")
        private Boolean success;
        @SerializedName("msg")
        private String msg;

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
    }
}
