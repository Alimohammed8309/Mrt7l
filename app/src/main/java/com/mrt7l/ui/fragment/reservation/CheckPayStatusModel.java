package com.mrt7l.ui.fragment.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CheckPayStatusModel {
    @JsonProperty("mrt7al")
    public Mrt7al getMrt7al() {
        return this.mrt7al; }
    public void setMrt7al(Mrt7al mrt7al) {
        this.mrt7al = mrt7al; }
    Mrt7al mrt7al;

    public class Data{
        @JsonProperty("code")
        public String getCode() {
            return this.code; }
        public void setCode(String code) {
            this.code = code; }
        String code;
        @JsonProperty("status")
        public String getStatus() {
            return this.status; }
        public void setStatus(String status) {
            this.status = status; }
        String status;
    }

    public class Mrt7al{
        @JsonProperty("success")
        public boolean getSuccess() {
            return this.success; }
        public void setSuccess(boolean success) {
            this.success = success; }
        boolean success;
        @JsonProperty("msg")
        public String getMsg() {
            return this.msg; }
        public void setMsg(String msg) {
            this.msg = msg; }
        String msg;
        @JsonProperty("data")
        public Data getData() {
            return this.data; }
        public void setData(Data data) {
            this.data = data; }
        Data data;
    }
}
