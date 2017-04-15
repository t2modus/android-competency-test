package com.elden.t2eldencomptest.activities.friend_list;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.elden.t2eldencomptest.Extras;
import com.elden.t2eldencomptest.R;
import com.elden.t2eldencomptest.T2Application;
import com.elden.t2eldencomptest.databinding.ActivityFriendDetailsBinding;
import com.elden.t2eldencomptest.model.FriendDetails;
import com.elden.t2eldencomptest.service_interfaces.FriendRetrofitInterface;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendDetailsActivity extends AppCompatActivity {
    public static final String TAG = FriendDetailsActivity.class.getSimpleName();
    FriendRetrofitInterface retrofitInterface = T2Application.retrofit.create(FriendRetrofitInterface.class);
    ActivityFriendDetailsBinding friendDetailBinding;
    int friendId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        friendDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_friend_details);

        friendId = getIntent().getIntExtra(Extras.FRIEND_ID, -1);
        callGetFriendService();
    }

    public void onFabClick(View view){
        Toast.makeText(FriendDetailsActivity.this, "WIP: Send Message to Friend", Toast.LENGTH_SHORT).show();
    }

    private void callGetFriendService(){
        friendDetailBinding.progressBar.setVisibility(View.VISIBLE);
        retrofitInterface.getFriend(friendId).enqueue(new Callback<FriendDetails>() {
            @Override
            public void onResponse(Call<FriendDetails> call, Response<FriendDetails> response) {
                friendDetailBinding.progressBar.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    //// TODO: 4/14/2017 need image for when image call was unsuccesfull
                    Picasso.with(FriendDetailsActivity.this).load(response.body().getImg()).into(friendDetailBinding.ivProfilePic);
                    friendDetailBinding.setFriendDetail(response.body());

                    for(String currStatus : response.body().getStatuses()){
                        View cardView = LayoutInflater.from(FriendDetailsActivity.this).inflate(R.layout.status_card, null);
                        TextView text = (TextView) cardView.findViewById(R.id.tvStatus);
                        text.setText(currStatus);
                        friendDetailBinding.llStatusContainer.addView(cardView);
                    }
                } else {
                    Log.d(TAG, "onResponse: Failure");
                    Toast.makeText(FriendDetailsActivity.this, getString(R.string.default_error_message), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FriendDetails> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
                friendDetailBinding.progressBar.setVisibility(View.GONE);
                Toast.makeText(FriendDetailsActivity.this, getString(R.string.default_error_message), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
