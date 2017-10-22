package com.chembrovich.bsuir.topfilms.models.photos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhotosLinks {
    @SerializedName("html")
    @Expose
    private String htmlLink;
    @SerializedName("photos")
    @Expose
    private String photosLink;
}
