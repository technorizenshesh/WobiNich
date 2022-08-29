package com.my.wobinichapp.act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.my.wobinichapp.R;
import com.my.wobinichapp.databinding.ActivityWobinichDetailsBinding;

public class WobinichDetailsActivity extends AppCompatActivity {

    ActivityWobinichDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_wobinich_details);

           binding.llLandScape.setOnClickListener(v -> {
                startActivity(new Intent(WobinichDetailsActivity.this,KolnActivityDetails.class));
            });

            binding.llMeal.setOnClickListener(v -> {
                startActivity(new Intent(WobinichDetailsActivity.this,MealDetailsActivity.class));
            });

            binding.llFreetime.setOnClickListener(v -> {
                startActivity(new Intent(WobinichDetailsActivity.this,FreeTimeDetailsActivity.class));
            });

            binding.llShop.setOnClickListener(v -> {
                startActivity(new Intent(WobinichDetailsActivity.this,ShopDetails.class));
            });

    }

}