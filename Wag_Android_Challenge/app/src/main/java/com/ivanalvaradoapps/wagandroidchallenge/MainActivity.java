package com.ivanalvaradoapps.wagandroidchallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.ivanalvaradoapps.wagandroidchallenge.adapters.StackOverflowUserListAdapter;
import com.ivanalvaradoapps.wagandroidchallenge.model.SOUsers;
import com.ivanalvaradoapps.wagandroidchallenge.rest.RestClient;

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

        fetchSoUsers();
    }

    private void fetchSoUsers() {
        RestClient rc = new RestClient();
        Call<SOUsers> call = rc.getsStackOverflowService().getUsers();

        call.enqueue(new Callback<SOUsers>() {
            @Override
            public void onResponse(Call<SOUsers> call, Response<SOUsers> response) {
                if (response.isSuccessful()) { // Success
                    Log.d(TAG, "Response Message: " + response.message());
                    Log.d(TAG, "count = " + response.body().getItems().size());

                    StackOverflowUserListAdapter listAdapter = new StackOverflowUserListAdapter(MainActivity.this, response.body().getItems());
                    ListView userList = MainActivity.this.findViewById(R.id.so_user_list);
                    userList.setAdapter(listAdapter);
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
