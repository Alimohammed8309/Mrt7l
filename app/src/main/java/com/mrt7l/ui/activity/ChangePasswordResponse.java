package com.mrt7l.ui.activity;

import com.google.gson.annotations.SerializedName;

public class ChangePasswordResponse {

    @SerializedName("mrt7al")
    private Mrt7alBean mrt7al;

    public Mrt7alBean getMrt7al() {
        return mrt7al;
    }

    public void setMrt7al(Mrt7alBean mrt7al) {
        this.mrt7al = mrt7al;
    }

    public static class Mrt7alBean {
        @SerializedName("success")
        private Boolean success;
        @SerializedName("msg")
        private String msg;
        @SerializedName("data")
        private DataBean data;

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            @SerializedName("id")
            private Integer id;
            @SerializedName("mobile")
            private String mobile;
            @SerializedName("passport_id")
            private String passportId;
            @SerializedName("passport_file")
            private String passportFile;
            @SerializedName("first_name")
            private String firstName;
            @SerializedName("second_name")
            private String secondName;
            @SerializedName("third_name")
            private String thirdName;
            @SerializedName("last_name")
            private String lastName;
            @SerializedName("nationality_id")
            private Integer nationalityId;
            @SerializedName("gender")
            private String gender;
            @SerializedName("date_of_birth")
            private String dateOfBirth;
            @SerializedName("city_id")
            private Integer cityId;
            @SerializedName("user_group_id")
            private String userGroupId;
            @SerializedName("created")
            private String created;
            @SerializedName("modified")
            private String modified;
            @SerializedName("username")
            private String username;
            @SerializedName("email")
            private String email;
            @SerializedName("is_active")
            private Integer isActive;
            @SerializedName("profilePhoto")
            private String profilePhoto;
            @SerializedName("deviceToken")
            private String deviceToken;
            @SerializedName("passangerType")
            private String passangerType;
            @SerializedName("full_name")
            private String fullName;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getPassportId() {
                return passportId;
            }

            public void setPassportId(String passportId) {
                this.passportId = passportId;
            }

            public String getPassportFile() {
                return passportFile;
            }

            public void setPassportFile(String passportFile) {
                this.passportFile = passportFile;
            }

            public String getFirstName() {
                return firstName;
            }

            public void setFirstName(String firstName) {
                this.firstName = firstName;
            }

            public String getSecondName() {
                return secondName;
            }

            public void setSecondName(String secondName) {
                this.secondName = secondName;
            }

            public String getThirdName() {
                return thirdName;
            }

            public void setThirdName(String thirdName) {
                this.thirdName = thirdName;
            }

            public String getLastName() {
                return lastName;
            }

            public void setLastName(String lastName) {
                this.lastName = lastName;
            }

            public Integer getNationalityId() {
                return nationalityId;
            }

            public void setNationalityId(Integer nationalityId) {
                this.nationalityId = nationalityId;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getDateOfBirth() {
                return dateOfBirth;
            }

            public void setDateOfBirth(String dateOfBirth) {
                this.dateOfBirth = dateOfBirth;
            }

            public Integer getCityId() {
                return cityId;
            }

            public void setCityId(Integer cityId) {
                this.cityId = cityId;
            }

            public String getUserGroupId() {
                return userGroupId;
            }

            public void setUserGroupId(String userGroupId) {
                this.userGroupId = userGroupId;
            }

            public String getCreated() {
                return created;
            }

            public void setCreated(String created) {
                this.created = created;
            }

            public String getModified() {
                return modified;
            }

            public void setModified(String modified) {
                this.modified = modified;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public Integer getIsActive() {
                return isActive;
            }

            public void setIsActive(Integer isActive) {
                this.isActive = isActive;
            }

            public String getProfilePhoto() {
                return profilePhoto;
            }

            public void setProfilePhoto(String profilePhoto) {
                this.profilePhoto = profilePhoto;
            }

            public String getDeviceToken() {
                return deviceToken;
            }

            public void setDeviceToken(String deviceToken) {
                this.deviceToken = deviceToken;
            }

            public String getPassangerType() {
                return passangerType;
            }

            public void setPassangerType(String passangerType) {
                this.passangerType = passangerType;
            }

            public String getFullName() {
                return fullName;
            }

            public void setFullName(String fullName) {
                this.fullName = fullName;
            }
        }
    }
}
