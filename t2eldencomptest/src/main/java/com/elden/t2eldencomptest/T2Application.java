package com.elden.t2eldencomptest;

import android.app.Application;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by elden_000 on 4/14/2017.
 */

public class T2Application extends Application {
    String baseUrl = "https://private-c1b9f-t2mobile.apiary-mock.com/";
    public static Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();
        retrofit = getNewSetupRetrofit(baseUrl);

    }

    /**
     * Returns a new retrofit instance that is configured
     * @return
     */
    public Retrofit getNewSetupRetrofit(String baseUrl){
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
