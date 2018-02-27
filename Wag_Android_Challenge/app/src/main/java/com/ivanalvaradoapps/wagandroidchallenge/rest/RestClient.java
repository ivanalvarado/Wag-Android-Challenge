package com.ivanalvaradoapps.wagandroidchallenge.rest;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ivanalvarado on 2/26/18.
 */

public class RestClient {

    private final String TAG = RestClient.class.getSimpleName();

    private final String BASE_URL = "https://api.stackexchange.com/2.2/";

    private StackOverflowService sStackOverflowService;

    public RestClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        sStackOverflowService = retrofit.create(StackOverflowService.class);
    }

    public StackOverflowService getsStackOverflowService() {
        return sStackOverflowService;
    }
}
