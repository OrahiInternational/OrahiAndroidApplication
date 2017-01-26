package com.example.malmike21.orahiapp.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.malmike21.orahiapp.POJO.ServiceProvider;
import com.example.malmike21.orahiapp.R;
import com.example.malmike21.orahiapp.sessionManager.SharedInformation;

import java.util.List;

public class SearchResultsActivity extends Activity {
    int i = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());
        SharedInformation.getInstance().setCallSearch(true);

        boolean juk1 = SharedInformation.getInstance().isSearchProcess();
        boolean juk = SharedInformation.getInstance().isCallSearch();

        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
        startActivity(intent);

        this.finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY).trim();
            //use the query to search your data somehow
            List<ServiceProvider> serviceProviders = SharedInformation.getInstance().getServiceProviders();
            for(ServiceProvider serviceProvider: serviceProviders){
                String name = serviceProvider.getName();
                if(name.equalsIgnoreCase(query)){
                    SharedInformation.getInstance().setServiceProvider(serviceProvider);
                    SharedInformation.getInstance().setSearchProcess(true);
                    SharedInformation.getInstance().setMessage("Service Provider retrieved");
                    SharedInformation.getInstance().setSnippetChosen(i);
                    break;
                }
                i++;
            }
            if(!SharedInformation.getInstance().isSearchProcess()){
                SharedInformation.getInstance().setMessage("Service Provider does not exist in our database");
            }
        }
    }
}
