package com.my.wobinichapp.act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.my.wobinichapp.R;
import com.my.wobinichapp.databinding.ActivityAllGrpUserListBinding;

public class AllGrpUserList extends AppCompatActivity {

    ActivityAllGrpUserListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_all_grp_user_list);


        binding.RRback.setOnClickListener(v -> {
            onBackPressed();
        });


    }
}