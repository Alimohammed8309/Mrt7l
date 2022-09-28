package com.mrt7l.ui.activity;

import java.io.Serializable;

public class ProfileResponse implements Serializable {
  private Mrt7al mrt7al;

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
      private String gender;

      private String passangerType;

      private String date_of_birth;

      private Integer nationality_id;

      private String passport_id;

      private Integer otherBrokerUserID;

      private String profilePhoto;

      private String authorized_companies;

      private String modified;

      private Integer id;

      private String passport_file;

      private String first_name;

      private String email;

      private Integer is_active;

      private String created;

      private String mobile;

      private String last_name;

      private String third_name;

      private String deviceToken;

      private String full_name;

      private String authorized_cities;

      private Nationality nationality;

      private String second_name;

      private Integer user_group_id;

      private Integer city_id;

      private String username;

      public String getGender() {
        return this.gender;
      }

      public void setGender(String gender) {
        this.gender = gender;
      }

      public String getPassangerType() {
        return this.passangerType;
      }

      public void setPassangerType(String passangerType) {
        this.passangerType = passangerType;
      }

      public String getDate_of_birth() {
        return this.date_of_birth;
      }

      public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
      }

      public Integer getNationality_id() {
        return this.nationality_id;
      }

      public void setNationality_id(Integer nationality_id) {
        this.nationality_id = nationality_id;
      }

      public String getPassport_id() {
        return this.passport_id;
      }

      public void setPassport_id(String passport_id) {
        this.passport_id = passport_id;
      }

      public Integer getOtherBrokerUserID() {
        return this.otherBrokerUserID;
      }

      public void setOtherBrokerUserID(Integer otherBrokerUserID) {
        this.otherBrokerUserID = otherBrokerUserID;
      }

      public String getProfilePhoto() {
        return this.profilePhoto;
      }

      public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
      }

      public String getAuthorized_companies() {
        return this.authorized_companies;
      }

      public void setAuthorized_companies(String authorized_companies) {
        this.authorized_companies = authorized_companies;
      }

      public String getModified() {
        return this.modified;
      }

      public void setModified(String modified) {
        this.modified = modified;
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

      public String getFirst_name() {
        return this.first_name;
      }

      public void setFirst_name(String first_name) {
        this.first_name = first_name;
      }

      public String getEmail() {
        return this.email;
      }

      public void setEmail(String email) {
        this.email = email;
      }

      public Integer getIs_active() {
        return this.is_active;
      }

      public void setIs_active(Integer is_active) {
        this.is_active = is_active;
      }

      public String getCreated() {
        return this.created;
      }

      public void setCreated(String created) {
        this.created = created;
      }

      public String getMobile() {
        return this.mobile;
      }

      public void setMobile(String mobile) {
        this.mobile = mobile;
      }

      public String getLast_name() {
        return this.last_name;
      }

      public void setLast_name(String last_name) {
        this.last_name = last_name;
      }

      public String getThird_name() {
        return this.third_name;
      }

      public void setThird_name(String third_name) {
        this.third_name = third_name;
      }

      public String getDeviceToken() {
        return this.deviceToken;
      }

      public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
      }

      public String getFull_name() {
        return this.full_name;
      }

      public void setFull_name(String full_name) {
        this.full_name = full_name;
      }

      public String getAuthorized_cities() {
        return this.authorized_cities;
      }

      public void setAuthorized_cities(String authorized_cities) {
        this.authorized_cities = authorized_cities;
      }

      public Nationality getNationality() {
        return this.nationality;
      }

      public void setNationality(Nationality nationality) {
        this.nationality = nationality;
      }

      public String getSecond_name() {
        return this.second_name;
      }

      public void setSecond_name(String second_name) {
        this.second_name = second_name;
      }

      public Integer getUser_group_id() {
        return this.user_group_id;
      }

      public void setUser_group_id(Integer user_group_id) {
        this.user_group_id = user_group_id;
      }

      public Integer getCity_id() {
        return this.city_id;
      }

      public void setCity_id(Integer city_id) {
        this.city_id = city_id;
      }

      public String getUsername() {
        return this.username;
      }

      public void setUsername(String username) {
        this.username = username;
      }

      public static class Nationality implements Serializable {
        private String name;

        private Integer id;

        public String getName() {
          return this.name;
        }

        public void setName(String name) {
          this.name = name;
        }

        public Integer getId() {
          return this.id;
        }

        public void setId(Integer id) {
          this.id = id;
        }
      }
    }
  }
}
