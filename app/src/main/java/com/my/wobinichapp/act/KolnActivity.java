package com.my.wobinichapp.act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.my.wobinichapp.R;
import com.my.wobinichapp.databinding.ActivityKolnBinding;

public class KolnActivity extends AppCompatActivity {

    ActivityKolnBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_koln);

        binding.RRFirst.setOnClickListener(v -> {

            startActivity(new Intent(KolnActivity.this,KolnActivityDetails.class));

        });

        binding.RRTwo.setOnClickListener(v -> {

            startActivity(new Intent(KolnActivity.this,KolnActivityDetails.class));

        });
    }
}