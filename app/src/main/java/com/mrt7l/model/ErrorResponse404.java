package com.mrt7l.model;

import java.util.Observable;

public class ErrorResponse404  extends Observable {
    private static ErrorResponse404 instance;
    private String message;
    private String url;
    private Integer code;

    public static ErrorResponse404 getInstance(){
        if (instance == null){
            instance = new ErrorResponse404();
        }
        return instance;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
