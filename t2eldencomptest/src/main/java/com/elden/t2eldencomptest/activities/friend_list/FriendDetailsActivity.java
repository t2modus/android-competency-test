package com.elden.t2eldencomptest.activities.friend_list;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.elden.t2eldencomptest.Extras;
import com.elden.t2eldencomptest.R;
import com.elden.t2eldencomptest.T2Application;
import com.elden.t2eldencomptest.model.FriendDetails;
import com.elden.t2eldencomptest.service_interfaces.FriendRetrofitInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendDetailsActivity extends AppCompatActivity {
    public static final String TAG = FriendDetailsActivity.class.getSimpleName();
    FriendRetrofitInterface retrofitInterface = T2Application.retrofit.create(FriendRetrofitInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_details);

        int friendId = getIntent().getIntExtra(Extras.FRIEND_ID, -1);

        Toast.makeText(this, "Here is the id: " + friendId, Toast.LENGTH_SHORT).show();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        retrofitInterface.getFriend(friendId).enqueue(new Callback<FriendDetails>() {
            @Override
            public void onResponse(Call<FriendDetails> call, Response<FriendDetails> response) {
                if(response.isSuccessful()){
                    Toast.makeText(FriendDetailsActivity.this, "First name is " + response.body().getFirstName(), Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "onResponse: Failure");
                }
            }

            @Override
            public void onFailure(Call<FriendDetails> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });

    }

}
