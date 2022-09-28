package com.mrt7l.ui.activity;

import com.google.gson.annotations.SerializedName;

public class TokenObject {

    /**
     * mob :
     * exp : time()+300
     */

    @SerializedName("mob")
    private String mob;
    @SerializedName("exp")
    private String exp;

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }
}
