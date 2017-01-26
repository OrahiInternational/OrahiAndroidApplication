package com.example.malmike21.orahiapp.POJO;

import java.util.ArrayList;

/**
 * Created by malmike21 on 16/12/2016.
 */

public class CreateServiceCategories {

    private ArrayList<ServiceCategory> serviceCategories = new ArrayList<ServiceCategory>();

    private final String names[] = {
            "Hotel",
            "Event",
            "Restaurant",
            "House",
            "Cab",
            "Boda-Boda",
            "Delivery Service",
            "Foreign Exchange",
            "Bank",
            "Mobile Money Agent",
            "Police Station"
    };

    private final String imageURIs[] = {
            "http://mock.robotemplates.com/cityguide/epicure.jpg",
            "http://mock.robotemplates.com/cityguide/cobea.jpg",
            "http://mock.robotemplates.com/cityguide/lecinq.jpg",
            "http://mock.robotemplates.com/cityguide/publisher.jpg",
            "http://mock.robotemplates.com/cityguide/pouicpouic.jpg",
            "http://mock.robotemplates.com/cityguide/lediane.jpg",
            "http://mock.robotemplates.com/cityguide/legrandvefour.jpg",
            "http://mock.robotemplates.com/cityguide/lebristolparis.jpg",
            "http://mock.robotemplates.com/cityguide/mandarinoriental.jpg",
            "http://mock.robotemplates.com/cityguide/plazaathenee.jpg",
            "http://mock.robotemplates.com/cityguide/sacrecoeur.jpg",
            "http://mock.robotemplates.com/cityguide/vosges.jpg"
    };

    public CreateServiceCategories(){
        prepareData();
    }

    private void prepareData(){
        ArrayList<ServiceCategory> categoriesData = new ArrayList<>();
        for( int i=0; i<names.length; i++){
            ServiceCategory serviceCategory = new ServiceCategory();
            serviceCategory.setName(names[i]);
            serviceCategory.setImageURI(imageURIs[i]);
            categoriesData.add(serviceCategory);
        }
        setServiceCategories(categoriesData);
    }

    public ArrayList<ServiceCategory> getServiceCategories() {
        return serviceCategories;
    }

    private void setServiceCategories(ArrayList<ServiceCategory> categories) {
        this.serviceCategories = categories;
    }
}
