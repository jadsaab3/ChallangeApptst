package com.challangeapp.connections;

import android.content.Context;

import com.challangeapp.myproject.Config;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientApi {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(Context context) {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Config.Main_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
//    Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl(Config.Main_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build();


}
