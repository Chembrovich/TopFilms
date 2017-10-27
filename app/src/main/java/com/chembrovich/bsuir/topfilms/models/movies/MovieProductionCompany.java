package com.chembrovich.bsuir.topfilms.models.movies;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieProductionCompany {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}