package com.akilloren.t2modus_android_test.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.akilloren.t2modus_android_test.R;
import com.akilloren.t2modus_android_test.misc.AppConst;
import com.akilloren.t2modus_android_test.model.Friend;
import com.akilloren.t2modus_android_test.views.CircleTransform;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;

public class DetailActivity extends AppCompatActivity {

    ImageView personPhoto;
    TextView personName;
    TextView personStatus;
    TextView personContactInfo;
    TextView personBioInfo;
    ProgressDialog progressDialog;
    ImageView statusIndicator;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();

        // get id from intent and call server to get details
        if (getIntent().hasExtra("id")) {
            int id = getIntent().getIntExtra("id", -1);
            if (id > -1) {
                getFriendDetails(id);
            }
        }

        if (getIntent().hasExtra("status")) {
            personStatus.setText(getIntent().getStringExtra("status"));
        }
    }

    /**
     * Initialize the views used in this activity
     */
    private void initViews() {

        progressDialog = new ProgressDialog(this);
        personPhoto = (ImageView) findViewById(R.id.personPhoto);
        personName = (TextView) findViewById(R.id.personName);
        personStatus = (TextView) findViewById(R.id.personStatus);
        personContactInfo = (TextView) findViewById(R.id.personContactInfo);
        personBioInfo = (TextView) findViewById(R.id.personBioInfo);
        statusIndicator = (ImageView) findViewById(R.id.status_indicator);
    }

    /**
     * Calls server API and returns details for a person - Uses OKHttpClient to save TONS of repetitive HTTP coding
     *
     * @param id Integer value representing the id of the person
     */
    private void getFriendDetails(final int id) {

        new AsyncTask<String, Void, Response>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showProgress(getString(R.string.text_loading));
            }

            @Override
            protected Response doInBackground(String... strings) {
                OkHttpClient client = new OkHttpClient();
                Response response;
                Request request = new Request.Builder().url(AppConst.BASE_FRIENDS_API_URL + "/" + id).build();

                try {
                    response = client.newCall(request).execute();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
                return response;
            }

            @Override
            protected void onPostExecute(Response response) {
                super.onPostExecute(response);
                hideProgress();

                if (response != null) {
                    String sResponse;
                    try {
                        sResponse = response.body().string();

                        //parse JSON
                        parseJSON(sResponse);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.execute();
    }

    /**
     * Helper method to parse thru returned JSON and populate Friend object
     *
     * @param json JSON String to parse
     */
    private void parseJSON(String json) {

        try {
            JSONObject jObj = new JSONObject(json);
            Friend friend = new Friend();
            friend.setFirst_name(jObj.getString("first_name"));
            friend.setLast_name(jObj.getString("last_name"));
            friend.setImg(jObj.getString("img"));
            friend.setPhone(jObj.getString("phone"));
            friend.setAddress_1(jObj.getString("address_1"));
            friend.setCity(jObj.getString("city"));
            friend.setState(jObj.getString("state"));
            friend.setZipcode(jObj.getString("zipcode"));
            friend.setBio(jObj.getString("bio"));
            friend.setAvailable(jObj.getBoolean("available"));

            //update UI
            updateUI(friend);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Helper method to update views with data from Friend object
     *
     * @param friend Friend object
     */
    private void updateUI(Friend friend) {

        Picasso.with(this).load(friend.getImg())
                .transform(new CircleTransform())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(personPhoto);
        personName.setText(friend.getFirst_name() + " " + friend.getLast_name());
        personContactInfo.setText(friend.getAddress_1()
                + "\n" + friend.getCity()
                + ", " + friend.getState().toUpperCase()
                + " " + friend.getZipcode()
                + "\n" + friend.getPhone());
        personBioInfo.setText(friend.getBio());
        if (friend.isAvailable()) {
            statusIndicator.setImageResource(R.drawable.online);
        } else {
            statusIndicator.setImageResource(R.drawable.offline);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Show a progress dialog
     *
     * @param msg String message to display
     */
    private void showProgress(String msg) {

        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    /**
     * Hide progress dialog
     */
    private void hideProgress() {
        progressDialog.dismiss();
    }
}
