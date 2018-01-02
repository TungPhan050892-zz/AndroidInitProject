package com.example.phant.rthchallenge.datalayer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by phant on 5/27/2017.
 */

public class Data {

    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("date")
    @Expose
    private String date;


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
