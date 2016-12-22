package com.example.malmike21.orahiapp.POJO;

import java.util.ArrayList;

/**
 * Created by malmike21 on 16/12/2016.
 */

public class CreateCategories {

    private ArrayList<Category> categories = new ArrayList<Category>();

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

    public CreateCategories(){
        prepareData();
    }

    private void prepareData(){
        ArrayList<Category> categoriesData = new ArrayList<>();
        for( int i=0; i<names.length; i++){
            Category category = new Category();
            category.setName(names[i]);
            category.setImageURI(imageURIs[i]);
            categoriesData.add(category);
        }
        setCategories(categoriesData);
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    private void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }
}
