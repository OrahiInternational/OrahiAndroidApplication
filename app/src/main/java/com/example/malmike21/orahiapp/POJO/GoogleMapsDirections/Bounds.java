package com.example.malmike21.orahiapp.POJO.GoogleMapsDirections;

/**
 * Created by malmike21 on 22/12/2016.
 */

import com.example.malmike21.orahiapp.POJO.GoogleMapsDirections.Northeast;
import com.example.malmike21.orahiapp.POJO.GoogleMapsDirections.Southwest;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bounds {

    @SerializedName("northeast")
    @Expose
    private Northeast northeast;
    @SerializedName("southwest")
    @Expose
    private Southwest southwest;

    public Northeast getNortheast() {
        return northeast;
    }

    public void setNortheast(Northeast northeast) {
        this.northeast = northeast;
    }

    public Southwest getSouthwest() {
        return southwest;
    }

    public void setSouthwest(Southwest southwest) {
        this.southwest = southwest;
    }

}