package com.my.wobinichapp.act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.my.wobinichapp.R;
import com.my.wobinichapp.databinding.ActivityGrpViewAllPostBinding;

public class GrpViewAllPost extends AppCompatActivity {

    ActivityGrpViewAllPostBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_grp_view_all_post);

        binding.RRBack.setOnClickListener(v ->
        {
            onBackPressed();

        });

    }
}