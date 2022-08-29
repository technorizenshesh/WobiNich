package com.my.wobinichapp.utils;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClients {

    private static final String BASE_URL ="https://technorizen.com/wobinich/webservice/";
  //  private static final String BASE_URL ="https://myspotbh.com/wobinich/webservice/";
    private static RetrofitClients mInstance;
    private Retrofit retrofit;

    private RetrofitClients(){

      HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(1000, TimeUnit.MINUTES) // connect timeout
                .writeTimeout(1000, TimeUnit.MINUTES) // write timeout
                .readTimeout(1000, TimeUnit.MINUTES); // read timeout

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public static synchronized RetrofitClients getInstance(){

        if (mInstance == null){
           mInstance = new RetrofitClients();
        }
        return mInstance;
    }

  public Api getApi(){
   return retrofit.create(Api.class);
  }

}
