package com.mrt7l.ui.fragment.explain_app;

import java.util.ArrayList;
import java.util.Observable;

public class VideosResponse extends Observable {

    private static VideosResponse instance;
    /**
     * mrt7al : {"success":true,"msg":"تم الحفظ بنجاح","data":[{"id":1,"title":"video1","description":"video1 description \r\nvideo1 description \r\nvideo1 description \r\nvideo1 description \r\nvideo1 description \r\nvideo1 description ","url":"https://www.youtube.com/embed/VosrdsG8V5E","created":"2020-09-26T07:55:40+03:00","modified":"2020-09-26T07:55:40+03:00"},{"id":2,"title":"أخبار نيسان ","description":"                    dsadsad","url":"https://www.youtube.com/embed/t1EEuy0G55Q","created":"2020-09-26T08:03:34+03:00","modified":"2020-09-26T08:03:34+03:00"},{"id":3,"title":"music","description":"relaxing music , concentration , sleep and study","url":"https://www.youtube.com/embed/vBJCetaAofU","created":"2020-09-28T04:59:35+03:00","modified":"2020-09-28T04:59:35+03:00"},{"id":4,"title":"123","description":"123456789","url":"https://www.youtube.com/embed/A-uF_X9tPw4","created":"2020-09-29T14:26:55+03:00","modified":"2020-09-29T14:26:55+03:00"}]}
     */

    private Mrt7alBean mrt7al;

    public static VideosResponse getInstance(){
        if (instance == null){
            instance = new VideosResponse();
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

            private int id;
            private String title;
            private String description;
            private String url;
            private String created;
            private String modified;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
                setChanged();
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
                setChanged();
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
                setChanged();
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
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
        }
    }
}
