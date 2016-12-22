package com.example.malmike21.orahiapp.Rest;

import android.util.Log;

import com.example.malmike21.orahiapp.POJO.GeneralResponse;
import com.example.malmike21.orahiapp.Rest.interfaces.RequestInterface;
import com.example.malmike21.orahiapp.sessionManager.SharedInformation;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;

/**
 * Created by malmike21 on 18/12/2016.
 */

public class CustomInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        SharedInformation information = SharedInformation.getInstance();
        String token = SharedInformation.getInstance().getGeneralResponse().getToken();
        request = request.newBuilder()
                .addHeader("Accept", "Application/JSON")
                .addHeader("x-access-token", token)
                .build();
        Response response = chain.proceed(request);
        if(response.isSuccessful()) {
            return chain.proceed(request);
        }else{
            RetrofitBuilder retrofitBuilder = new RetrofitBuilder();
            RequestInterface requestInterface = retrofitBuilder.getRetrofitlogin().create(RequestInterface.class);
            Call<GeneralResponse> call = requestInterface.login(SharedInformation.getInstance().getUser());
            GeneralResponse generalResponse = call.execute().body();
            if(generalResponse != null){
                information.setGeneralResponse(generalResponse);
                Request newRequest = chain.request();
                newRequest = newRequest.newBuilder()
                        .addHeader("Accept", "Application/JSON")
                        .addHeader("x-access-token", generalResponse.getToken())
                        .build();
                return chain.proceed(newRequest);
            }else{
                generalResponse.setStatus("Failure");
                generalResponse.setMessage("Failure updating the token");
                information.setGeneralResponse(generalResponse);
                Request newRequest = chain.request();
                newRequest = newRequest.newBuilder()
                        .addHeader("Accept", "Application/JSON")
                        .build();
                return chain.proceed(newRequest);
            }
        }

    }
}
