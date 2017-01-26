package com.example.malmike21.orahiapp.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.graphics.Color;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.malmike21.orahiapp.POJO.Category;
import com.example.malmike21.orahiapp.POJO.GeneralResponse;
import com.example.malmike21.orahiapp.POJO.GoogleMapsDirections.DirectionResults;
import com.example.malmike21.orahiapp.POJO.GoogleMapsDirections.MapsErrorResponse;
import com.example.malmike21.orahiapp.POJO.GoogleMapsDirections.Route;
import com.example.malmike21.orahiapp.POJO.GoogleMapsDirections.Step;
import com.example.malmike21.orahiapp.POJO.Location;
import com.example.malmike21.orahiapp.POJO.Service;
import com.example.malmike21.orahiapp.POJO.ServiceProvider;
import com.example.malmike21.orahiapp.R;

import com.example.malmike21.orahiapp.Rest.ErrorUtils;
import com.example.malmike21.orahiapp.Rest.RetrofitBuilder;
import com.example.malmike21.orahiapp.Rest.interfaces.RequestInterface;
import com.example.malmike21.orahiapp.constants.Constants;
import com.example.malmike21.orahiapp.directionTasks.RouteDecode;
import com.example.malmike21.orahiapp.sessionManager.SharedInformation;
import com.google.android.gms.common.ConnectionResult;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MapsActivity extends AppCompatActivity implements   View.OnClickListener,
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMarkerClickListener{

    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    private int PROXIMITY_RADIUS = 10000;
    LatLng latLng;
    GoogleMap mGoogleMap;
    SupportMapFragment mFragment;
    Marker currLocationMarker;
    int i = 0;
    RetrofitBuilder retrofitBuilder = new RetrofitBuilder();
    ServiceProvider resultServiceProvider;

    FloatingActionButton map_refresh, directions, my_location, show_fab;
    Animation fab_open, fab_close, rotate_forward, rotate_backward;
    LatLng destLatLng;
    boolean isFabOpen = false;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), GridCategoryActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Find the toolbar view and set as ActionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        // Remove default title text
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Get access to the custom title view
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setVisibility(View.INVISIBLE);

        ImageView mImage = (ImageView) toolbar.findViewById(R.id.toolbar_icon);
        mImage.setVisibility(View.INVISIBLE);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mFragment.getMapAsync(this);


        map_refresh = (FloatingActionButton) findViewById(R.id.map_refresh);
        directions = (FloatingActionButton) findViewById(R.id.directions);
        my_location = (FloatingActionButton) findViewById(R.id.my_location);
        show_fab = (FloatingActionButton) findViewById(R.id.show_fab);

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);

        map_refresh.setOnClickListener(this);
        directions.setOnClickListener(this);
        my_location.setOnClickListener(this);
        show_fab.setOnClickListener(this);

        if(SharedInformation.getInstance().getMessage() != null || SharedInformation.getInstance().getMessage() != ""){

            Toast.makeText(this, SharedInformation.getInstance().getMessage(), Toast.LENGTH_LONG);
        }

    }


    @Override
    public void onClick(View v){
        int id = v.getId();
        switch (id){
            case R.id.map_refresh:
                    mapRefreshClicked();
                break;
            case R.id.directions:
                directionsClicked(false, "driving", true);
                break;
            case R.id.my_location:
                myLocationClicked();
                break;
            case R.id.show_fab:
                showFabClicked();
                break;
        }
    }

    private void mapRefreshClicked(){
        finish();
        startActivity(getIntent());
    }

    private void directionsClicked(boolean sensor, String mode, boolean alternatives){
        if(destLatLng != null && latLng != null) {

            final ProgressDialog progressDialog = new ProgressDialog(MapsActivity.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Getting Directions...");
            progressDialog.show();

            String origin = latLng.latitude+","+latLng.longitude;
            String destination = destLatLng.latitude+","+destLatLng.longitude;

            String url_test = "https://maps.googleapis.com/maps/api/directions/json?origin="+origin+"" +
                    "&&destination="+destination+"&&sensor="+sensor+"&&mode="+mode+"&&alternatives="+alternatives;
            RequestInterface requestInterface = retrofitBuilder.getRetrofitMapsDirection().create(RequestInterface.class);
            Call<DirectionResults> directionResultsCall = requestInterface.directionsResults
                    (origin, destination, sensor, mode, alternatives);


            directionResultsCall.enqueue(new Callback<DirectionResults> () {
                @Override
                public void onResponse(Call<DirectionResults> call, Response<DirectionResults> response) {
                    if(response.isSuccessful()){
                        DirectionResults resultsDirections = response.body();
                        /*String message = "Getting directions Passed";
                        String message2 = ""+resultsDirections.getRoutes().size();
                        Toast.makeText(getApplicationContext(), ""+resultsDirections.getRoutes().size(), Toast.LENGTH_LONG).show();*/
                        computeDirections(resultsDirections);
                        progressDialog.dismiss();

                    }
                    else{
                        String message;
                        MapsErrorResponse resp = ErrorUtils.parseMapError(retrofitBuilder, response);
                        if(resp.getErrorMessage() != null){  message = resp.getErrorMessage();}
                        else{ message = "Getting directions Failed!!!";}
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<DirectionResults> call, Throwable t) {
                    String message = "Getting directions Failed, Check your internet connection";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            });
        }
    }


    public void computeDirections(DirectionResults directionResults){
        Log.i("Orahi", "inside on success" +directionResults.getRoutes().size());
        ArrayList<LatLng> routelist = new ArrayList<LatLng>();
        if(directionResults.getRoutes().size()>0){
            ArrayList<LatLng> decodelist;
            Route routeA = directionResults.getRoutes().get(0);
            Log.i("Orahi", "Legs length : "+routeA.getLegs().size());
            if(routeA.getLegs().size()>0){
                List<Step> steps= routeA.getLegs().get(0).getSteps();
                Log.i("Orahi","Steps size :"+steps.size());
                Step step;
                Location location = new Location();
                String polyline;
                for(int i=0 ; i<steps.size();i++){
                    step = steps.get(i);
                    location.setLat(""+step.getStartLocation().getLat());
                    location.setLong(""+step.getStartLocation().getLng());
                    
                    routelist.add(new LatLng(step.getStartLocation().getLat(), step.getStartLocation().getLng()));
                    Log.i("Orahi", "Start Location :" + location.getLat() + ", " + location.getLong());
                    polyline = step.getPolyline().getPoints();
                    decodelist = RouteDecode.decodePoly(polyline);
                    routelist.addAll(decodelist);
                    
                    location.setLat(""+step.getEndLocation().getLat());
                    location.setLong(""+step.getEndLocation().getLng());
                    
                    routelist.add(new LatLng(step.getEndLocation().getLat() ,step.getEndLocation().getLng()));
                    Log.i("Orahi","End Location :"+location.getLat() +", "+location.getLong());
                }
            }
        }
        Log.i("Orahi","routelist size : "+routelist.size());
        if(routelist.size()>0){
            PolylineOptions rectLine = new PolylineOptions().width(10).color(
                    Color.RED);

            for (int i = 0; i < routelist.size(); i++) {
                rectLine.add(routelist.get(i));
            }
            // Adding route on the map
            mGoogleMap.clear();
            mGoogleMap.addPolyline(rectLine);
            MarkerOptions markerOptions = new MarkerOptions();

            MarkerOptions myLocationMarker = new MarkerOptions();
            myLocationMarker.position(latLng);
            myLocationMarker.title("Current Position");
            myLocationMarker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            mGoogleMap.addMarker(myLocationMarker);

            markerOptions.position(destLatLng);
            markerOptions.draggable(true);
            mGoogleMap.addMarker(markerOptions);
        }
    }
    private void myLocationClicked(){
        if(mGoogleMap != null && latLng != null){
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(13));
        }
    }

    private void showFabClicked(){
        if(isFabOpen){
            show_fab.startAnimation(rotate_backward);
            map_refresh.startAnimation(fab_close);
            directions.startAnimation(fab_close);
            my_location.startAnimation(fab_close);
            map_refresh.setClickable(false);
            directions.setClickable(false);
            my_location.setClickable(false);
            isFabOpen = false;
        }else{
            show_fab.startAnimation(rotate_forward);
            map_refresh.startAnimation(fab_open);
            directions.startAnimation(fab_open);
            my_location.startAnimation(fab_open);
            map_refresh.setClickable(true);
            directions.setClickable(true);
            my_location.setClickable(true);
            isFabOpen = true;
        }
    }

    /*public String makeURL (double sourcelat, double sourcelog, double destlat, double destlog ){
        StringBuilder urlString = new StringBuilder();
        urlString.append("http://maps.googleapis.com/maps/api/directions/json");
        urlString.append("?origin=");// from
        urlString.append(Double.toString(sourcelat));
        urlString.append(",");
        urlString
                .append(Double.toString( sourcelog));
        urlString.append("&destination=");// to
        urlString
                .append(Double.toString( destlat));
        urlString.append(",");
        urlString.append(Double.toString( destlog));
        urlString.append("&sensor=false&mode=driving&alternatives=true");
        urlString.append("&key=YOUR_API_KEY");
        return urlString.toString();
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        mGoogleMap.setMyLocationEnabled(true);

        buildGoogleApiClient();

        mGoogleApiClient.connect();

        if(SharedInformation.getInstance().isCallSearch()){
            if(SharedInformation.getInstance().isSearchProcess()){
                createMarker();
                SharedInformation.getInstance().setCallSearch(false);
            }else{
                createMarkers();
                SharedInformation.getInstance().setCallSearch(false);
            }
        }else{
            createMarkers();
        }

        mGoogleMap.setOnMarkerClickListener(this);
        mGoogleMap.setOnInfoWindowClickListener(this);

    }

    private void createMarkers( ){
        List<ServiceProvider> serviceProviders = SharedInformation.getInstance().getServiceProviders();


        for(ServiceProvider serviceProvider : serviceProviders){
            double latitude = Double.parseDouble(serviceProvider.getLocation().getLat());
            double longitude = Double.parseDouble(serviceProvider.getLocation().getLong());
            String name = serviceProvider.getName();

            MarkerOptions markerOptions = new MarkerOptions();
            LatLng latLng = new LatLng(latitude, longitude);
            // Position of Marker on Map
            markerOptions.position(latLng);
            markerOptions.snippet(""+i);
            // Adding Title to the Marker
            markerOptions.title(name);
            // Adding Marker to the Camera.
            Marker m = mGoogleMap.addMarker(markerOptions);
            // Adding colour to the marker
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            i++;
        }

        // Add a marker in Sydney and move the camera
        /*LatLng grandImperial = new LatLng(0.31524,32.5783853);
        mGoogleMap.addMarker(new MarkerOptions().position(grandImperial).title("Grand Imperial Hotel"));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(grandImperial));*/
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        destLatLng = marker.getPosition();
        Toast.makeText(this, "("+destLatLng.latitude+","+destLatLng.longitude+")", Toast.LENGTH_LONG).show();
        return false;
    }

    private void createMarker(){
        ServiceProvider serviceProvider = SharedInformation.getInstance().getServiceProvider();
        double latitude = Double.parseDouble(serviceProvider.getLocation().getLat());
        double longitude = Double.parseDouble(serviceProvider.getLocation().getLong());
        MarkerOptions markerOptions = new MarkerOptions();
        LatLng latLng = new LatLng(latitude, longitude);
        // Position of Marker on Map
        markerOptions.position(latLng);
        markerOptions.snippet(""+i);
        // Adding Title to the Marker
        markerOptions.title(serviceProvider.getName());
        // Adding Marker to the Camera.
        Marker m = mGoogleMap.addMarker(markerOptions);
        // Adding colour to the marker
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(13));
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        android.location.Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            //place marker at current position
            //mGoogleMap.clear();
            latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Current Position");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            currLocationMarker = mGoogleMap.addMarker(markerOptions);
            // move map camera
            if(!SharedInformation.getInstance().isSearchProcess()) {
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(13));
            }else{
                SharedInformation.getInstance().setSearchProcess(false);
            }
        }

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000); //5 seconds
        mLocationRequest.setFastestInterval(3000); //3 seconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }


    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this,"onConnectionSuspended",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this,"onConnectionFailed",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        //place marker at current position
        //mGoogleMap.clear();
        if (currLocationMarker != null) {
            currLocationMarker.remove();
        }
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        currLocationMarker = mGoogleMap.addMarker(markerOptions);

        Toast.makeText(this,"Location Changed",Toast.LENGTH_SHORT).show();

        //zoom to current position:
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng).zoom(14).build();

        mGoogleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));

        //If you only need one location, unregister the listener
        //LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

    }



    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        //Toast.makeText(this, marker.getSnippet(), Toast.LENGTH_LONG).show();
        final SharedInformation information = SharedInformation.getInstance();

        if(i > 0) {
            information.setSnippetChosen(Integer.parseInt(marker.getSnippet()));

            resultServiceProvider = information.getServiceProviders().get(information.getSnippetChosen());

            information.setServiceProvider(resultServiceProvider);
        }else {
            resultServiceProvider = information.getServiceProvider();
        }

        fixedService();
    }


    public void fixedService(){
        final ProgressDialog progressDialog = new ProgressDialog(MapsActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Searching data...");
        progressDialog.show();

        RequestInterface requestInterface = retrofitBuilder.getRetrofit().create(RequestInterface.class);
        Call<List<Service>> servicesCall = requestInterface.serviceList(SharedInformation.getInstance().getServiceProvider().getId());
        servicesCall.enqueue(new Callback<List<Service>>() {
            @Override
            public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {
                if(response.isSuccessful()){
                    List<Service> services = response.body();
                    SharedInformation.getInstance().setServices(services);
                    Toast.makeText(getApplicationContext(),"Service list of length "+ services.size() +" got",Toast.LENGTH_LONG).show();
                    fixedCategory();
                    progressDialog.dismiss();
                }
                else{
                    String message;
                    GeneralResponse resp = ErrorUtils.parseError(retrofitBuilder, response);
                    if(resp.getMessage() != null){  message = resp.getMessage();}
                    else{ message = "Failed to get service provider";}
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<Service>> call, Throwable t) {
                Log.d(Constants.TAG,"failed");
                Toast.makeText(getBaseContext(), "Failed to get services, check your internet connection", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }

    public void fixedCategory(){
        final ProgressDialog progressDialog = new ProgressDialog(MapsActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Getting categories...");
        progressDialog.show();

        RequestInterface requestInterface = retrofitBuilder.getRetrofit().create(RequestInterface.class);
        Call<List<Category>> categoryCall = requestInterface.categoriesList(SharedInformation.getInstance().getServiceProvider().getId());
        categoryCall.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.isSuccessful()){
                    List<Category> categories = response.body();
                    SharedInformation.getInstance().setCategories(categories);
                    Toast.makeText(getApplicationContext(),"Service list of length "+ categories.size() +" got",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), ServiceProviderActivity.class);
                    startActivity(intent);
                    progressDialog.dismiss();
                }
                else{
                    String message;
                    GeneralResponse resp = ErrorUtils.parseError(retrofitBuilder, response);
                    if(resp.getMessage() != null){  message = resp.getMessage();}
                    else{ message = "Failed to get service category";}
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.d(Constants.TAG,"failed");
                Toast.makeText(getBaseContext(), "Failed to get categories, check your internet connection", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }

}
