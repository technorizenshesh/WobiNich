
package com.my.wobinichapp.act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.my.wobinichapp.R;
import com.my.wobinichapp.databinding.ActivityChangeStatusBinding;
import com.my.wobinichapp.databinding.ActivityChangeToneBinding;

public class ChangeTone extends AppCompatActivity {

    ActivityChangeToneBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_change_tone);

        binding.RRback.setOnClickListener(v -> {
            onBackPressed();
        });
    }
}