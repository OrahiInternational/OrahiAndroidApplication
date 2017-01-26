package com.example.malmike21.orahiapp.ExpandableListDataPump;

import com.example.malmike21.orahiapp.POJO.Category;
import com.example.malmike21.orahiapp.POJO.Service;
import com.example.malmike21.orahiapp.sessionManager.SharedInformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by malmike21 on 19/01/2017.
 */

public class CategoriesListDataPump {

    private static CategoriesListDataPump instance = new CategoriesListDataPump();

    private CategoriesListDataPump(){}

    public static CategoriesListDataPump getInstance(){
        return instance;
    }

    public HashMap<String, List<Service>> categoriesList;

    public void setCategoriesList() {
        HashMap<String, List<Service>> expandableListDetail = new HashMap<String, List<Service>>();
        List<Service> services = SharedInformation.getInstance().getServices();
        List<Category> categories = SharedInformation.getInstance().getCategories();

        expandableListDetail.put("Service List", new ArrayList<Service>());

        for (Category category: categories){
            expandableListDetail.put(category.getName(), new ArrayList<Service>());
        }

        for (Service service: services) {
            if (!service.getInCategory()) {
                expandableListDetail.get("Service List").add(service);
            } else {
                expandableListDetail.get(service.getCategory().getName()).add(service);
            }
        }
        this.categoriesList = expandableListDetail;

        for(String key: expandableListDetail.keySet()){
            if(expandableListDetail.get(key).size() <= 0){
                expandableListDetail.remove(key);
            }
        }
    }

    public HashMap<String, List<Service>> getCategoriesList() {
        return this.categoriesList;
    }
}
