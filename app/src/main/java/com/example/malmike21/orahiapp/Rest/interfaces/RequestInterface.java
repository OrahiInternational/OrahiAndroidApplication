package com.example.malmike21.orahiapp.Rest.interfaces;

import com.example.malmike21.orahiapp.POJO.Category;
import com.example.malmike21.orahiapp.POJO.GeneralResponse;
import com.example.malmike21.orahiapp.POJO.GoogleMapsDirections.DirectionResults;
import com.example.malmike21.orahiapp.POJO.Service;
import com.example.malmike21.orahiapp.POJO.ServiceProvider;
import com.example.malmike21.orahiapp.POJO.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * Created by malmike21 on 15/12/2016.
 */

public interface RequestInterface {
    /*
     * Retrofit get annotation with our URL
     * And our method that will return us details of student.
     */
   /* @GET("api/place/nearbysearch/json?sensor=true&key=AIzaSyDN7RJFmImYAca96elyZlE5s_fhX-MMuhk")
    Call<Example> getNearbyPlaces(@Query("type") String type,
                                  @Query("location") String location,
                                  @Query("radius") int radius);
*/
    // @GET("/2.2/questions?order=desc&sort=creation&site=stackoverflow")
    //Call<StackOverflowQuestions> loadQuestions(@Query("tagged") String
    // tags);
   /* @FormUrlEncoded
    @POST("api/user/authenticate")
    Call<LoginResponse> login(@Field("email") String email,
                              @Field("password") String password);


    @FormUrlEncoded
    @POST( "api/user/registerUser")
    Call<RegisterResponse> registerUser (@Field("email") String email,
                                         @Field("username") String username,
                                         @Field("password") String password);*/

    @POST("api/user/authenticate")
    Call<GeneralResponse> login(@Body User user);


    @POST( "api/user/registerUser")
    Call<GeneralResponse> registerUser (@Body User user);

    @POST( "api/user/makePayment")
    Call<GeneralResponse> makePayment (@Body Service service);

    //@POST( "api/user/registerUser")
    //Call<RegisterResponse> registerUser (@Body RegisterRequest registerRequest);

    @GET("api/user/getServiceProviders")
    Call<List<ServiceProvider>> serviceProviderList (@Query("serviceType") String serviceType);

    @GET("api/user/getServices")
    Call<List<Service>> serviceList (@Query("serviceProvider") String serviceProvider);

    @GET("api/user/getCategories")
    Call<List<Category>> categoriesList (@Query("serviceProvider") String serviceProvider);

    @GET("maps/api/directions/json")
    Call<DirectionResults> directionsResults (@Query("origin") String origin,
                                              @Query("destination") String destination,
                                              @Query("sensor") boolean sensor,
                                              @Query("mode") String mode,
                                              @Query("alternatives") boolean alternatives);



}
