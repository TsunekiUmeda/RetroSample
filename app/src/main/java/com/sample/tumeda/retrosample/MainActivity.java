package com.sample.tumeda.retrosample;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ApiInterface myapi;
    private ListView mListView;
    private final String BASE_URL = "https://app.rakuten.co.jp/";
    private final String APP_ID = "xxxxxxxxxx";
    private final String OP = "BooksBookSearch";
    private final String HITS = "20";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.listView);

        //Retrofit インスタンス
        //addConverterFactory レスポンスを処理するConverterを指定
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myapi = retrofit.create(ApiInterface.class);
        getItems();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void getItems() {
        Log.d("TEST", "getItems");

        Call<BookItems> call = myapi.getItems(APP_ID, OP, HITS);

        //        同期的にリクエストする execute()
        //        非同期的にリクエストする enqueue(Callback<T> callback)
        call.enqueue(new Callback<BookItems>() {
            @Override
            public void onResponse(Call<BookItems> call, Response<BookItems> response) {
                Log.d("TEST", "Succeed to request");
                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1);

                BookItems bookItems = response.body();
                List<Item> items = bookItems.getItems();

                for (Item i : items) {
                    if (i.getItem().getTitle().length() > 0) {
                        adapter.add(i.getItem().getTitle());
                        Log.d("TEST", i.getItem().getTitle());
                    }
                }

                mListView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<BookItems> call, Throwable t) {
                Log.d("TEST network", "" + isNetworkAvailable());
                Log.d("TEST", t.getMessage());
            }
        });
    }

}