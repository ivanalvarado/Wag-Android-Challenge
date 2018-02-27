package com.ivanalvaradoapps.wagandroidchallenge.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ivanalvarado on 2/26/18.
 */

public class BadgeCounts implements Serializable {

    @SerializedName("bronze")
    @Expose
    private Integer bronze;
    @SerializedName("silver")
    @Expose
    private Integer silver;
    @SerializedName("gold")
    @Expose
    private Integer gold;

    public Integer getBronze() {
        return bronze;
    }

    public void setBronze(Integer bronze) {
        this.bronze = bronze;
    }

    public Integer getSilver() {
        return silver;
    }

    public void setSilver(Integer silver) {
        this.silver = silver;
    }

    public Integer getGold() {
        return gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }
}
