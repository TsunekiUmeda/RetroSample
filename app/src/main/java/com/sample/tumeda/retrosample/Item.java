package com.sample.tumeda.retrosample;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("Item")
    @Expose
    private Item_ item;

    public Item_ getItem() {
        return item;
    }

    public void setItem(Item_ item) {
        this.item = item;
    }

}