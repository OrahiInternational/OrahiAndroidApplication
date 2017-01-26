package com.example.malmike21.orahiapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.malmike21.orahiapp.POJO.Service;
import com.example.malmike21.orahiapp.R;
import com.example.malmike21.orahiapp.fragments.ServiceFragment;
import com.example.malmike21.orahiapp.sessionManager.SharedInformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.malmike21.orahiapp.constants.Constants.IMAGE_URL;

public class ServiceProviderActivity extends AppCompatActivity implements ServiceFragment.OnListFragmentInteractionListener{
    SharedInformation information = SharedInformation.getInstance();
    ImageView imageView;
    List<String> finalImageURIs;
    private int imageNumber = 0;
    private int rotations = 1;
    private int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView header = (ImageView) findViewById(R.id.header);

        Picasso.with(getApplicationContext())
                .load(IMAGE_URL+""+information.getServiceProvider().getLogo())
                .into(header);

        header.setContentDescription(information.getServiceProvider().getLogo());

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(information.getServiceProvider().getName());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.emailButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Begin the transaction
        FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.category_frame, new ServiceFragment());

        ft.commit();
    }

    @Override
    public void onListFragmentInteractionGroupExpand(String expandableTitle) {
        Toast.makeText(getApplicationContext(), expandableTitle, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onListFragmentInteractionGroupCollapsed(String expandableTitle) {
        Toast.makeText(getApplicationContext(), expandableTitle, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onListFragmentInteractionChild(Service service) {
        Toast.makeText(getApplicationContext(), service.getServiceName() + " -> " + service.getServiceType(), Toast.LENGTH_SHORT).show();
        information.setService(service);
        List<String> imageURIs = new ArrayList<String>();


        if(service.getImage1() != null){
            imageURIs.add(i, IMAGE_URL+""+service.getImage1());
            i++;
        }
        if(service.getImage2() != null){
            imageURIs.add(i, IMAGE_URL+""+service.getImage2());
            i++;
        }
        if(service.getImage3() != null){
            imageURIs.add(i, IMAGE_URL+""+service.getImage3());
            i++;
        }
        if(service.getImage4() != null){
            imageURIs.add(i, IMAGE_URL+""+service.getImage4());
            i++;
        }
        if(service.getImage5() != null){
            imageURIs.add(i, IMAGE_URL+""+service.getImage5());
            i++;
        }

        information.setImageNumber(i);
        information.setImageURIs(imageURIs);
        callServiceActivity();

        /*final ProgressDialog progressDialog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        for(String imageURI : imageURIs){
            new ImageExists().execute(imageURI);
        }
        progressDialog.dismiss();*/

    }

    /*public void imageExistsResults(String imageURI, Boolean exists){
        if(exists){
            finalImageURIs.add(imageNumber,imageURI);
            imageNumber++;
        }
        if(rotations == i){
            information.setImageNumber(imageNumber);
            information.setImageURIs(finalImageURIs);
            callServiceActivity();
        }else{
            rotations++;
        }
    }*/

    public void callServiceActivity(){
        Intent intent= new Intent(this, ServiceActivity.class);
        startActivity(intent);
    }

}



