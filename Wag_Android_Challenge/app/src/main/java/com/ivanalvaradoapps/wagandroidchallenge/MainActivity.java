package com.ivanalvaradoapps.wagandroidchallenge;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ivanalvaradoapps.wagandroidchallenge.adapters.StackOverflowUserListAdapter;
import com.ivanalvaradoapps.wagandroidchallenge.model.SOUsers;
import com.ivanalvaradoapps.wagandroidchallenge.rest.RestClient;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();
    private final boolean PICASSO_DEBUG = true;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private TextView progressText;
    private ListView usersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.user_list_progress_bar);
        progressText = findViewById(R.id.user_list_progress_text_view);
        usersList = findViewById(R.id.so_user_list);

        setSwipeGestureResponse();
        buildPicassoSingletonInstance();
        fetchSoUsers();
    }

    /**
     * Sets a swipe listener to our SwipeRefreshLayout.
     */
    private void setSwipeGestureResponse() {
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Reset Progress Text just in case we updated it to an error message
                progressText.setText(R.string.fetching_users);

                usersList.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                progressText.setVisibility(View.VISIBLE);
                fetchSoUsers();
            }
        });
    }

    /**
     * Sets Global Singleton Picasso Instance.
     */
    private void buildPicassoSingletonInstance() {
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this,Integer.MAX_VALUE));
        Picasso built = builder.build();
        built.setIndicatorsEnabled(PICASSO_DEBUG);
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);
    }

    /**
     * Fetches the StackOverflow users list from the API endpoint using Retrofit.
     */
    private void fetchSoUsers() {
        RestClient rc = new RestClient();
        Call<SOUsers> call = rc.getsStackOverflowService().getUsers();

        call.enqueue(new Callback<SOUsers>() {
            @Override
            public void onResponse(Call<SOUsers> call, final Response<SOUsers> response) {
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);

                if (response.isSuccessful()) { // Success

                    progressText.setVisibility(View.GONE);

                    StackOverflowUserListAdapter listAdapter = new StackOverflowUserListAdapter(MainActivity.this, response.body().getItems());

                    usersList.setVisibility(View.VISIBLE);
                    usersList.setAdapter(listAdapter);
                    usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
                            intent.putExtra("user", response.body().getItems().get(i));
                            MainActivity.this.startActivity(intent);
                        }
                    });
                } else {
                    Log.e(TAG, "Error: " + response.errorBody());
                    try {
                        if (response.errorBody() != null) {
                            progressText.setText(response.errorBody().string());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SOUsers> call, Throwable t) {
                Log.e(TAG, "Failed to connect to server: " + t.getMessage());
                progressBar.setVisibility(View.GONE);
                progressText.setText(t.getMessage());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
