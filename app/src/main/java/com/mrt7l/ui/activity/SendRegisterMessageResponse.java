package com.mrt7l.ui.activity;

import java.util.Observable;

public class SendRegisterMessageResponse  extends Observable {

    private static SendRegisterMessageResponse instance;
    private Mrt7alBean mrt7al;

    public static SendRegisterMessageResponse getInstance(){
        if (instance == null){
            instance = new SendRegisterMessageResponse();
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
        private boolean isRegisterd;

        public boolean isRegisterd() {
            return isRegisterd;
        }

        public void setRegisterd(boolean registerd) {
            isRegisterd = registerd;
            setChanged();
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
    }
}
