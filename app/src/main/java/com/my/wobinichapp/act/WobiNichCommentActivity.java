package com.my.wobinichapp.act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.my.wobinichapp.R;
import com.my.wobinichapp.databinding.ActivityWobiNichCommentBinding;

public class WobiNichCommentActivity extends AppCompatActivity {

    ActivityWobiNichCommentBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_wobi_nich_comment);

        binding.RRback.setOnClickListener(v -> {
            onBackPressed();
        });
    }
}