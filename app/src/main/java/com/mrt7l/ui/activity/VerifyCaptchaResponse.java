package com.mrt7l.ui.activity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class VerifyCaptchaResponse {

    /**
     * success : true
     * challenge_ts : 
     * hostname : 
     * error-codes : ["missing-input-secret"]
     */

    @SerializedName("success")
    private Boolean success;
    @SerializedName("challenge_ts")
    private String challengeTs;
    @SerializedName("hostname")
    private String hostname;
    @SerializedName("error-codes")
    private ArrayList<String> errorcodes;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getChallengeTs() {
        return challengeTs;
    }

    public void setChallengeTs(String challengeTs) {
        this.challengeTs = challengeTs;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public ArrayList<String> getErrorcodes() {
        return errorcodes;
    }

    public void setErrorcodes(ArrayList<String> errorcodes) {
        this.errorcodes = errorcodes;
    }
}
