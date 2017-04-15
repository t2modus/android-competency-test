package com.elden.t2eldencomptest.activities.friend_list;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.elden.t2eldencomptest.R;
import com.elden.t2eldencomptest.T2Application;
import com.elden.t2eldencomptest.activities.friend_list.recycler_view_adapters.FriendListAdapter;
import com.elden.t2eldencomptest.databinding.ActivityFriendListBinding;
import com.elden.t2eldencomptest.model.Friend;
import com.elden.t2eldencomptest.service_interfaces.FriendRetrofitInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Activity shows a list of friends, clicking on a row brings user to Friends Detail page
 */
public class FriendListActivity extends AppCompatActivity {
    public static final String TAG = FriendListActivity.class.getSimpleName();
    public static final String SOMETHING_WENT_WRONG = "Something went wrong";
    private FriendRetrofitInterface friendRetrofitInterface = T2Application.retrofit.create(FriendRetrofitInterface.class);
    ActivityFriendListBinding activityFriendListBinding;
    private FriendListAdapter friendListAdapter;
    private RecyclerView.LayoutManager rvLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFriendListBinding = DataBindingUtil.setContentView(this, R.layout.activity_friend_list);

        //Setup RecyclerView
        rvLayoutManager = new LinearLayoutManager(this);
        friendListAdapter = new FriendListAdapter(new ArrayList<Friend>());
        activityFriendListBinding.rvFriendList.setAdapter(friendListAdapter);
        activityFriendListBinding.rvFriendList.setLayoutManager(rvLayoutManager);

        callGetFriendService();
    }

    /**
     * Calls get friends api, returns an array of friends onSuccess and updates UI
     */
    private void callGetFriendService() {
        activityFriendListBinding.progressBar.setVisibility(View.VISIBLE);
        friendRetrofitInterface.getFriends().enqueue(new Callback<List<Friend>>() {
            @Override
            public void onResponse(Call<List<Friend>> call, Response<List<Friend>> response) {
                activityFriendListBinding.progressBar.setVisibility(View.GONE);

                if(response.isSuccessful()){
                    friendListAdapter.updateFriendList(response.body());
                } else {
                    Log.d(TAG, "On not Success: ");
                    Toast.makeText(FriendListActivity.this, SOMETHING_WENT_WRONG, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Friend>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
                activityFriendListBinding.progressBar.setVisibility(View.GONE);
                Toast.makeText(FriendListActivity.this, SOMETHING_WENT_WRONG, Toast.LENGTH_SHORT).show();

            }
        });
    }
}
