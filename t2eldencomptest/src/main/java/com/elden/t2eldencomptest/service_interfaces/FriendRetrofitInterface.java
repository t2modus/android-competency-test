package com.elden.t2eldencomptest.service_interfaces;

import com.elden.t2eldencomptest.model.Friend;
import com.elden.t2eldencomptest.response_models.FriendResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by elden_000 on 4/14/2017.
 */

public interface FriendRetrofitInterface {

    @GET("friends")
    Call<List<Friend>> getFriends();

    @GET("friends")
    Call<List<Friend>> getFriend(@Query("id") String id);


}
