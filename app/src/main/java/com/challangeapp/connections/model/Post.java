package com.challangeapp.connections.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Post {

    @SerializedName("items")
    private List<Items> items = null;

    public List<Items> getItems() {
        return items;
    }
    public void setItems(List<Items> items) {
        this.items = items;
    }

}
