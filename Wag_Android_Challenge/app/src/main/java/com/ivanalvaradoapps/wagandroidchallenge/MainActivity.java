package com.ivanalvaradoapps.wagandroidchallenge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ivanalvaradoapps.wagandroidchallenge.adapters.StackOverflowUserListAdapter;
import com.ivanalvaradoapps.wagandroidchallenge.model.SOUsers;
import com.ivanalvaradoapps.wagandroidchallenge.rest.RestClient;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sets Global Singleton Picasso Instance
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this,Integer.MAX_VALUE));
        Picasso built = builder.build();
        built.setIndicatorsEnabled(true);
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);

        fetchSoUsers();
    }

    private void fetchSoUsers() {
        RestClient rc = new RestClient();
        Call<SOUsers> call = rc.getsStackOverflowService().getUsers();

        call.enqueue(new Callback<SOUsers>() {
            @Override
            public void onResponse(Call<SOUsers> call, final Response<SOUsers> response) {
                if (response.isSuccessful()) { // Success
                    Log.d(TAG, "Response Message: " + response.message());
                    Log.d(TAG, "count = " + response.body().getItems().size());

                    StackOverflowUserListAdapter listAdapter = new StackOverflowUserListAdapter(MainActivity.this, response.body().getItems());
                    ListView userList = MainActivity.this.findViewById(R.id.so_user_list);
                    userList.setAdapter(listAdapter);
                    userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
                            intent.putExtra("user", response.body().getItems().get(i));
                            MainActivity.this.startActivity(intent);
                        }
                    });
                } else {
                    Log.e(TAG, "Error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<SOUsers> call, Throwable t) {
                Log.e(TAG, "Failed to connect to server: " + t.getMessage());

            }
        });
    }
}
