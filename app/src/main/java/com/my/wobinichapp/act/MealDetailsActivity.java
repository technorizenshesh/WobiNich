package com.my.wobinichapp.act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.my.wobinichapp.GoogleMapp;
import com.my.wobinichapp.Preference;
import com.my.wobinichapp.R;
import com.my.wobinichapp.adapter.LandScapeAdapter;
import com.my.wobinichapp.databinding.ActivityMealDetailsBinding;
import com.my.wobinichapp.model.GetTypeModel;
import com.my.wobinichapp.utils.RetrofitClients;
import com.my.wobinichapp.utils.SessionManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealDetailsActivity extends AppCompatActivity {

    ActivityMealDetailsBinding binding;
    LandScapeAdapter mAdapter;
    private ArrayList<GetTypeModel.Result> modelList = new ArrayList<>();

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_meal_details);

        sessionManager = new SessionManager(MealDetailsActivity.this);

       /* binding.RRFirst.setOnClickListener(v -> {
            startActivity(new Intent(MealDetailsActivity.this,WobinichDetailsActivity.class));
        });
*/
        binding.llLandScape.setOnClickListener(v -> {
            startActivity(new Intent(MealDetailsActivity.this,KolnActivityDetails.class));
        });

        binding.llFreeTime.setOnClickListener(v -> {
            startActivity(new Intent(MealDetailsActivity.this,FreeTimeDetailsActivity.class));
        });

        binding.llShop.setOnClickListener(v -> {
            startActivity(new Intent(MealDetailsActivity.this,ShopDetails.class));
        });

        modelList.clear();

        if (sessionManager.isNetworkAvailable()) {
            binding.progressBar.setVisibility(View.VISIBLE);
            getMethod();
        }else {
            Toast.makeText(MealDetailsActivity.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
        }

    }

    private void setAdapter(ArrayList<GetTypeModel.Result> modelList) {

        mAdapter = new LandScapeAdapter(MealDetailsActivity.this,modelList);
        // binding.recyclerGrp.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MealDetailsActivity.this);
        binding.recyclerType.setLayoutManager(linearLayoutManager);
        binding.recyclerType.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new LandScapeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, GetTypeModel.Result model) {

                startActivity(new Intent(MealDetailsActivity.this, GoogleMapp.class).putExtra("lan",model.getLat()).putExtra("lon",model.getLon()));


            }
        });
    }

    public void getMethod()
    {
         String USER_ID= Preference.get(MealDetailsActivity.this,Preference.KEY_USER_ID);

        Call<GetTypeModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .get_wobinich_type(USER_ID,"meal");
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
                        Toast.makeText(MealDetailsActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    binding.txtEmty.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<GetTypeModel> call, Throwable t)
            {
                binding.txtEmty.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }

}