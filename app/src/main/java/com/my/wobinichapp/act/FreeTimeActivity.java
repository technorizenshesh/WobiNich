package com.my.wobinichapp.act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.my.wobinichapp.R;
import com.my.wobinichapp.databinding.ActivityFreeTimeBinding;

public class FreeTimeActivity extends AppCompatActivity {

    ActivityFreeTimeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_free_time);


        binding.RRFirst.setOnClickListener(v -> {

            startActivity(new Intent(FreeTimeActivity.this,FreeTimeDetailsActivity.class));

        });

        binding.RRTwo.setOnClickListener(v -> {

            startActivity(new Intent(FreeTimeActivity.this,FreeTimeDetailsActivity.class));

        });

    }
}