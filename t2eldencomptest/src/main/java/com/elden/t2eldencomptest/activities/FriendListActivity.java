package com.elden.t2eldencomptest.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.elden.t2eldencomptest.R;
import com.elden.t2eldencomptest.T2Application;
import com.elden.t2eldencomptest.model.Friend;
import com.elden.t2eldencomptest.response_models.FriendResponse;
import com.elden.t2eldencomptest.service_interfaces.FriendRetrofitInterface;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FriendListActivity extends AppCompatActivity {
    public static final String TAG = FriendListActivity.class.getSimpleName();
    public static final String SOMETHING_WENT_WRONG = "Something went wrong";
    private FriendRetrofitInterface friendRetrofitInterface = T2Application.retrofit.create(FriendRetrofitInterface.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);


        friendRetrofitInterface.getFriends().enqueue(new Callback<List<Friend>>() {
            @Override
            public void onResponse(Call<List<Friend>> call, Response<List<Friend>> response) {
                if(response.isSuccessful()){
                    Toast.makeText(FriendListActivity.this, response.body().get(0).getFirstName(), Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "On not Success: ");
                    Toast.makeText(FriendListActivity.this, SOMETHING_WENT_WRONG, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Friend>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
                Toast.makeText(FriendListActivity.this, SOMETHING_WENT_WRONG, Toast.LENGTH_SHORT).show();

            }
        });


    }




}
