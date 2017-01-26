package com.example.malmike21.orahiapp.POJO.GoogleMapsDirections;

/**
 * Created by malmike21 on 22/12/2016.
 */
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MapsErrorResponse {

    @SerializedName("error_message")
    @Expose
    private String errorMessage;
    @SerializedName("routes")
    @Expose
    private List<Object> routes = null;
    @SerializedName("status")
    @Expose
    private String status;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<Object> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Object> routes) {
        this.routes = routes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}