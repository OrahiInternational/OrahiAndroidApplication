package com.example.malmike21.orahiapp.sessionManager;

import com.example.malmike21.orahiapp.POJO.Category;
import com.example.malmike21.orahiapp.POJO.ServiceCategory;
import com.example.malmike21.orahiapp.POJO.GeneralResponse;
import com.example.malmike21.orahiapp.POJO.Service;
import com.example.malmike21.orahiapp.POJO.ServiceProvider;
import com.example.malmike21.orahiapp.POJO.User;

import java.util.List;

/**
 * Created by malmike21 on 16/12/2016.
 */

public class SharedInformation {

    /*private static SharedInformation instance = new SharedInformation();
    private User user;
    private List<ServiceProvider> serviceProviders;
    private GeneralResponse generalResponse;
    private int snippetChosen;
    private List<Service> services;
    private ServiceCategory serviceCategory;
    private List<ServiceCategory> categories;
    private ServiceProvider serviceProvider;
    private boolean searchProcess;
    private boolean callSearch;
    private String message;*/

    private static SharedInformation instance = new SharedInformation();
    private User user;
    private List<ServiceProvider> serviceProviders;
    private GeneralResponse generalResponse;
    private int snippetChosen;
    private List<Service> services;
    private ServiceCategory serviceCategory;
    private List<ServiceCategory> serviceCategories;
    private ServiceProvider serviceProvider;
    private boolean searchProcess;
    private boolean callSearch;
    private String message;
    private Service service;
    private List<String> imageURIs;
    private int imageNumber;
    private List<Category> categories;

    private SharedInformation(){}

    public static SharedInformation getInstance(){
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ServiceProvider> getServiceProviders() {
        return serviceProviders;
    }

    public void setServiceProviders(List<ServiceProvider> serviceProviders) {
        this.serviceProviders = serviceProviders;
    }
    public GeneralResponse getGeneralResponse() {
        return generalResponse;
    }
    public void setGeneralResponse(GeneralResponse generalResponse) {
        this.generalResponse = generalResponse;
    }

    public int getSnippetChosen() {
        return snippetChosen;
    }

    public void setSnippetChosen(int snippetChosen) {
        this.snippetChosen = snippetChosen;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public ServiceCategory getServiceCategory() {
        return serviceCategory;
    }

    public void setServiceCategory(ServiceCategory serviceCategory) {
        this.serviceCategory = serviceCategory;
    }

    public List<ServiceCategory> getServiceCategories() {
        return serviceCategories;
    }

    public void setServiceCategories(List<ServiceCategory> serviceCategories) {
        this.serviceCategories = serviceCategories;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public boolean isSearchProcess() {
        return searchProcess;
    }

    public void setSearchProcess(boolean searchProcess) {
        this.searchProcess = searchProcess;
    }

    public boolean isCallSearch() {
        return callSearch;
    }

    public void setCallSearch(boolean callSearch) {
        this.callSearch = callSearch;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getImageURIs() {
        return imageURIs;
    }

    public void setImageURIs(List<String> imageURIs) {
        this.imageURIs = imageURIs;
    }

    public int getImageNumber() {
        return imageNumber;
    }

    public void setImageNumber(int imageNumber) {
        this.imageNumber = imageNumber;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
