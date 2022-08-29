package com.my.wobinichapp.act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.my.wobinichapp.GoogleMapp;
import com.my.wobinichapp.Preference;
import com.my.wobinichapp.R;
import com.my.wobinichapp.adapter.LandScapeAdapter;
import com.my.wobinichapp.adapter.UserListAdapter;
import com.my.wobinichapp.databinding.ActivityKolnDetailsBinding;
import com.my.wobinichapp.model.GetTypeModel;
import com.my.wobinichapp.model.GetUserModel;
import com.my.wobinichapp.model.GrpChatModel;
import com.my.wobinichapp.utils.RetrofitClients;
import com.my.wobinichapp.utils.SessionManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KolnActivityDetails extends AppCompatActivity {

    ActivityKolnDetailsBinding binding;
    LandScapeAdapter mAdapter;
    private ArrayList<GetTypeModel.Result> modelList = new ArrayList<>();
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_koln_details);

        sessionManager = new SessionManager(KolnActivityDetails.this);

        binding.llMeal.setOnClickListener(v -> {
            startActivity(new Intent(KolnActivityDetails.this,MealDetailsActivity.class));
        });

        binding.llFreeTime.setOnClickListener(v -> {
            startActivity(new Intent(KolnActivityDetails.this,FreeTimeDetailsActivity.class));
        });

        binding.llShop.setOnClickListener(v -> {
            startActivity(new Intent(KolnActivityDetails.this,ShopDetails.class));
        });

        modelList.clear();

        if (sessionManager.isNetworkAvailable()) {
            binding.progressBar.setVisibility(View.VISIBLE);
            getMethod();
        }else {
            Toast.makeText(KolnActivityDetails.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
        }

    }


    private void setAdapter(ArrayList<GetTypeModel.Result> modelList) {

        mAdapter = new LandScapeAdapter(KolnActivityDetails.this,modelList);
        // binding.recyclerGrp.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(KolnActivityDetails.this);
        binding.recyclerType.setLayoutManager(linearLayoutManager);
        binding.recyclerType.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new LandScapeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, GetTypeModel.Result model) {

                startActivity(new Intent(KolnActivityDetails.this, GoogleMapp.class).putExtra("lan",model.getLat()).putExtra("lon",model.getLon()));
            }
        });
    }

    public void getMethod()
    {
        String USER_ID = Preference.get(KolnActivityDetails.this,Preference.KEY_USER_ID);

        Call<GetTypeModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .get_wobinich_type(USER_ID,"landscape");
        call.enqueue(new Callback<GetTypeModel>() {
            @Override
            public void onResponse(Call<GetTypeModel> call, Response<GetTypeModel> response) {
                try {
                    binding.progressBar.setVisibility(View.GONE);
                    GetTypeModel myclass = response.body();
                    String status = String.valueOf(myclass.getStatus());
                    String message = myclass.getMessage();
                    if (status.equalsIgnoreCase("1")) {
                        binding.txtEmty.setVisibility(View.GONE);
                        modelList = (ArrayList<GetTypeModel.Result>) myclass.getResult();
                        setAdapter(modelList);
                    } else {
                        binding.txtEmty.setVisibility(View.VISIBLE);
                        Toast.makeText(KolnActivityDetails.this, ""+message, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    binding.txtEmty.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<GetTypeModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                binding.txtEmty.setVisibility(View.VISIBLE);
            }
        });
    }


}