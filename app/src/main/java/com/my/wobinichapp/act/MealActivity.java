package com.my.wobinichapp.act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.my.wobinichapp.R;
import com.my.wobinichapp.databinding.ActivityMealBinding;

public class MealActivity extends AppCompatActivity {

    ActivityMealBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_meal);

        binding.RROne.setOnClickListener(v -> {
            startActivity(new Intent(MealActivity.this,MealDetailsActivity.class));
        });

    }
}