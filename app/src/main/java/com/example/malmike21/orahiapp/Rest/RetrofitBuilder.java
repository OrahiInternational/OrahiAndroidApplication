package com.example.malmike21.orahiapp.Rest;

/**
 * Created by malmike21 on 15/12/2016.
 */
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.malmike21.orahiapp.constants.Constants.BASE_URL;
import static com.example.malmike21.orahiapp.constants.Constants.GOOGLE_MAPS_URL;

public class RetrofitBuilder {

    private Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create();

    private OkHttpClient defaultHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new CustomInterceptor())
            .build();

    private OkHttpClient loginHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    request = request.newBuilder()
                            .addHeader("Accept", "Application/JSON")
                            .build();
                    return chain.proceed(request);
                }
            })
            .build();

    private OkHttpClient mapsHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    request = request.newBuilder()
                            .addHeader("Accept", "Application/JSON")
                            .addHeader("key", "@string/google_maps_key")
                            .build();
                    return chain.proceed(request);
                }
            })
            .build();

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(defaultHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    private Retrofit retrofitLogin = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(loginHttpClient)
            .build();

    private Retrofit retrofitMapsDirection = new Retrofit.Builder()
            .baseUrl(GOOGLE_MAPS_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(mapsHttpClient)
            .build();

    public Retrofit getRetrofit(){
        return retrofit;
    }

    public Retrofit getRetrofitlogin(){
        return retrofitLogin;
    }

    public Retrofit getRetrofitMapsDirection() {
        return retrofitMapsDirection;
    }
}
