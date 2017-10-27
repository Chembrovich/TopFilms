package com.chembrovich.bsuir.topfilms.models.photos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhotosUser {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("portfolio_url")
    @Expose
    private String portfolioUrl;
    @SerializedName("links")
    @Expose
    private PhotosLinks links;

    public String getName() {
        return name;
    }
}
