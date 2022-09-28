package com.mrt7l.ui.fragment.mytrips;

import java.util.Observable;

public class RequestPdfResponse  extends Observable {

    private static RequestPdfResponse instance;
    private Mrt7alBean mrt7al;

    public static RequestPdfResponse getInstance(){
        if (instance == null){
            instance = new RequestPdfResponse();
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
            private String pdfLink;

            public String getPdfLink() {
                return pdfLink;
            }

            public void setPdfLink(String pdfLink) {
                this.pdfLink = pdfLink;
                setChanged();
            }
        }
    }
}
