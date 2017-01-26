package com.example.malmike21.orahiapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.malmike21.orahiapp.POJO.ServiceCategory;
import com.example.malmike21.orahiapp.POJO.GeneralResponse;
import com.example.malmike21.orahiapp.POJO.ServiceProvider;
import com.example.malmike21.orahiapp.R;
import com.example.malmike21.orahiapp.Rest.ErrorUtils;
import com.example.malmike21.orahiapp.Rest.RetrofitBuilder;
import com.example.malmike21.orahiapp.Rest.interfaces.RequestInterface;
import com.example.malmike21.orahiapp.constants.Constants;
import com.example.malmike21.orahiapp.fragments.CategoryFragment;
import com.example.malmike21.orahiapp.sessionManager.SharedInformation;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GridCategoryActivity extends AppCompatActivity implements CategoryFragment.OnListFragmentInteractionListener{

    RetrofitBuilder retrofitBuilder = new RetrofitBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_category);

        // Find the toolbar view and set as ActionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        // Remove default title text
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Get access to the custom title view
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Orahi");



        // add back arrow to toolbar
        /*if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }*/

        // Begin the transaction
        FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.category_frame, new CategoryFragment());
        ft.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //MenuItem action_search = menu.findItem(R.id.search);
        //action_search.setVisible(false);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


         switch(item.getItemId()){


             case R.id.action_wekume:
                 Intent intent = new Intent(this, EmergencyResponder.class);

                 startActivity(intent);
                 overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                 break;

             case R.id.search:
                 Intent intentSearch = new Intent(this, LoginActivity.class);
                 startActivity(intentSearch);
                 overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                 finish();
                 break;

         }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onListFragmentInteraction(ServiceCategory serviceCategory) {
        SharedInformation.getInstance().setServiceCategory(serviceCategory);
        final ProgressDialog progressDialog = new ProgressDialog(GridCategoryActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Searching data...");
        progressDialog.show();

        RequestInterface requestInterface = retrofitBuilder.getRetrofit().create(RequestInterface.class);
        Call<List<ServiceProvider>> response = requestInterface.serviceProviderList(serviceCategory.getName());
        response.enqueue(new Callback<List<ServiceProvider>>(){

            @Override
            public void onResponse(Call<List<ServiceProvider>> call, Response<List<ServiceProvider>> response) {
                if(response.isSuccessful()){
                    List<ServiceProvider> serviceProviders = response.body();
                    SharedInformation.getInstance().setServiceProviders(serviceProviders);

                    /*int size = serviceProviders.size();
                    Toast.makeText(getApplicationContext(), String.valueOf(size),Toast.LENGTH_LONG).show();*/
                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    progressDialog.dismiss();
                }
                else{
                    GeneralResponse resp = ErrorUtils.parseError(retrofitBuilder, response);
                    if(resp.getMessage() == null){
                        resp.setMessage("Error retrieving data");
                    }
                    Toast.makeText(getApplicationContext(), resp.getMessage(), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<ServiceProvider>> call, Throwable t) {
                Log.d(Constants.TAG,"failed");
                // Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
                Toast.makeText(getBaseContext(), "Data retrieval failed. Check your internet connection", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(false);
    }
}
