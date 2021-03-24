package com.example.smartcommunity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.smartcommunity.Interface.ApiRetrofit;
import com.example.smartcommunity.Utility.FeedData;
import com.example.smartcommunity.models.UserModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FeedData feedData = new FeedData();

        Retrofit retrofit = feedData.connectRetrofit("https://jsonplaceholder.typicode.com");

        ApiRetrofit apiRetrofit = retrofit.create(ApiRetrofit.class);
        Call<UserModel> call = apiRetrofit.getUser(5);

        call.enqueue((new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                Log.d("logUserResponse :",response.body().getUserId().toString());
                MainActivity.this.finish();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.d("logUserThrow ",t.getMessage());
                MainActivity.this.finish();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        }));

        //Retrofit retrofit1 = feedData.connectRetrofit("https://api.exchangeratesapi.io/history?start_at=2018-01-01&end_at=2018-09-01");

    }
}