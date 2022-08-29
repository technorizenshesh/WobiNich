package com.my.wobinichapp.act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.my.wobinichapp.R;
import com.my.wobinichapp.adapter.GroupImagePostAdapter;
import com.my.wobinichapp.adapter.YesPurplePostAdapter;
import com.my.wobinichapp.databinding.ActivityGroupImageBinding;
import com.my.wobinichapp.model.GetFolderModel;
import com.my.wobinichapp.model.GetPostBluVoilet;
import com.my.wobinichapp.model.HomModel;

import java.util.ArrayList;

public class GroupImage extends AppCompatActivity {

    GroupImagePostAdapter mAdapterPurple;
    private ArrayList<HomModel> modelList = new ArrayList<>();
    ActivityGroupImageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding= DataBindingUtil.setContentView(this,R.layout.activity_group_image);
    }


    private void setAdapter(ArrayList<HomModel> modelList1) {

        mAdapterPurple = new GroupImagePostAdapter(GroupImage.this, modelList1);
        binding.recyclerGrpImg.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(GroupImage.this);
        binding.recyclerGrpImg.setLayoutManager(linearLayoutManager);
        binding.recyclerGrpImg.setAdapter(mAdapterPurple);

        mAdapterPurple.SetOnItemClickListener(new GroupImagePostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, HomModel model){

                startActivity(new Intent(GroupImage.this, WobiNichCommentActivity.class));

            }
        });
    }
}