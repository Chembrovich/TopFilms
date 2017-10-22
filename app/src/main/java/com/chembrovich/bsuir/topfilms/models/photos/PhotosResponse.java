package com.chembrovich.bsuir.topfilms.models.photos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhotosResponse {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("width")
    @Expose
    private int width;
    @SerializedName("height")
    @Expose
    private int height;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("user")
    @Expose
    private PhotosUser user;
    @SerializedName("urls")
    @Expose
    private PhotosUrls urls;
    @SerializedName("links")
    @Expose
    private PhotosLinks links;

    public PhotosUrls getUrls() {
        return urls;
    }
}
