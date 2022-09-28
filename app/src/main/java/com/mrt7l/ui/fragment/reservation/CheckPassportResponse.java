package com.mrt7l.ui.fragment.reservation;

import java.io.Serializable;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;

public class CheckPassportResponse implements Serializable {
  private Mrt7al mrt7al;
  private static CheckPassportResponse instance;
  public static   CheckPassportResponse getInstance(){
    if (instance == null){
      instance = new CheckPassportResponse();
    }
    return instance;
  }
  public Mrt7al getMrt7al() {
    return this.mrt7al;
  }

  public void setMrt7al(Mrt7al mrt7al) {
    this.mrt7al = mrt7al;
  }

  public static class Mrt7al implements Serializable {
    private String msg;

    private Data data;

    private Boolean success;

    public String getMsg() {
      return this.msg;
    }

    public void setMsg(String msg) {
      this.msg = msg;
    }

    public Data getData() {
      return this.data;
    }

    public void setData(Data data) {
      this.data = data;
    }

    public Boolean getSuccess() {
      return this.success;
    }

    public void setSuccess(Boolean success) {
      this.success = success;
    }

    public static class Data implements Serializable {
      private String full_name;

      private Integer id;

      private String passport_file;

      private String passport_id;

      public String getFull_name() {
        return this.full_name;
      }

      public void setFull_name(String full_name) {
        this.full_name = full_name;
      }

      public Integer getId() {
        return this.id;
      }

      public void setId(Integer id) {
        this.id = id;
      }

      public String getPassport_file() {
        return this.passport_file;
      }

      public void setPassport_file(String passport_file) {
        this.passport_file = passport_file;
      }

      public String getPassport_id() {
        return this.passport_id;
      }

      public void setPassport_id(String passport_id) {
        this.passport_id = passport_id;
      }
    }
  }
}
