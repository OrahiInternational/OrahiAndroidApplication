package com.example.malmike21.orahiapp.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceCategory {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("imageURI")
    @Expose
    private String imageURI;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }

}