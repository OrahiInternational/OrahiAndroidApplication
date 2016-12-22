package com.example.malmike21.orahiapp.Rest;

import com.example.malmike21.orahiapp.POJO.GeneralResponse;
import com.example.malmike21.orahiapp.POJO.GoogleMapsDirections.MapsErrorResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

/**
 * Created by malmike21 on 15/12/2016.
 */

public class ErrorUtils {

    public static GeneralResponse parseError(RetrofitBuilder retrofitBuilder, Response<?> response) {

        Converter<ResponseBody, GeneralResponse> converter = retrofitBuilder.getRetrofit()
                .responseBodyConverter(GeneralResponse.class, new Annotation[0]);

        GeneralResponse error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new GeneralResponse();
        }

        return error;
    }

    public static MapsErrorResponse parseMapError(RetrofitBuilder retrofitBuilder, Response<?> response) {

        Converter<ResponseBody, MapsErrorResponse> converter = retrofitBuilder.getRetrofit()
                .responseBodyConverter(MapsErrorResponse.class, new Annotation[0]);

        MapsErrorResponse error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new MapsErrorResponse();
        }

        return error;
    }
}
