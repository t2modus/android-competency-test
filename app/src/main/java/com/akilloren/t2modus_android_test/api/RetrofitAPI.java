package com.akilloren.t2modus_android_test.api;


import com.akilloren.t2modus_android_test.model.Friend;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;

/**
 * The Retrofit interface and endpoint definintions
 * @author alank
 */

public interface RetrofitAPI {

    @GET("friends")
    Call<List<Friend>> getFriendList();

    //add more endpoints/calls here

}