package com.akilloren.t2modus_android_test.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.akilloren.t2modus_android_test.R;
import com.akilloren.t2modus_android_test.adapter.FriendAdapter;
import com.akilloren.t2modus_android_test.api.RetrofitAPI;
import com.akilloren.t2modus_android_test.misc.AppConst;
import com.akilloren.t2modus_android_test.model.Friend;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    private Call<List<Friend>> call;
    private RecyclerView recyclerView;
    private FriendAdapter adapter;
    private List<Friend> friends;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        getData();
    }

    /**
     * Initialize all the views that will be used in this activity
     */
    private void initViews() {

        progressDialog = new ProgressDialog(this);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);

        //The following line can be uncommented if you are not using a CardView in the layout and just want a regular "listview"
        //recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));
    }

    /**
     * Calls server API and returns populated list from JSON - Uses Retrofit/GSON in one call
     */
    private void getData() {

        Log.i(AppConst.TAG, "Getting data using Retrofit...");
        showProgress(getString(R.string.text_loading));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConst.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI service = retrofit.create(RetrofitAPI.class);

        call = service.getFriendList();
        call.enqueue(new Callback<List<Friend>>() {

            @Override
            public void onResponse(Response<List<Friend>> response, Retrofit retrofit) {
                hideProgress();
                friends = response.body();
                if (friends != null && friends.size() > 0) {
                    getAdapter();
                    Log.i(AppConst.TAG, "Data received and list populated.");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                hideProgress();
                Toast.makeText(MainActivity.this, "Problem occurred while retrieving data", Toast.LENGTH_SHORT).show();
                Log.e(AppConst.TAG, "Failed to receive data using Retrofit");
            }
        });
    }

    /**
     * Initializes the data adapter
     */
    private void getAdapter() {

        adapter = new FriendAdapter(this, friends);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        call.cancel();//cancel any calls to the server if we exit
    }

    private void showProgress(String msg) {

        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    private void hideProgress() {
        progressDialog.dismiss();
    }
}