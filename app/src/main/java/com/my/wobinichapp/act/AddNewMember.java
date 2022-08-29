package com.my.wobinichapp.act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.my.wobinichapp.Preference;
import com.my.wobinichapp.R;
import com.my.wobinichapp.adapter.AddNewMemebrsAdapter;
import com.my.wobinichapp.adapter.UserListAdapter;
import com.my.wobinichapp.databinding.ActivityAddNewMemberBinding;
import com.my.wobinichapp.model.AddGrpModel;
import com.my.wobinichapp.model.AddNewMemberModel;
import com.my.wobinichapp.model.GetUserModel;
import com.my.wobinichapp.model.UserModel;
import com.my.wobinichapp.utils.RetrofitClients;
import com.my.wobinichapp.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewMember extends AppCompatActivity {

    ActivityAddNewMemberBinding binding;
    AddNewMemebrsAdapter mAdapter;
    private ArrayList<GetUserModel.Result> modelList = new ArrayList<>();

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_add_new_member);

        sessionManager = new SessionManager(AddNewMember.this);

        binding.RRback.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.RRAddMember.setOnClickListener(v -> {

            List<String> list = new ArrayList<>();

            for (int i=0;i<modelList.size();i++)
            {
                if(modelList.get(i).isSelected())
                {
                    String MemberId=modelList.get(i).getId();
                    list.add(MemberId);
                }
            }

            String s = TextUtils.join(",", list);

            if(s.equalsIgnoreCase(""))
            {
                Toast.makeText(this, "Please Select Memeber..", Toast.LENGTH_SHORT).show();

            }else
            {
                if (sessionManager.isNetworkAvailable()) {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    AddNewMemberMehod(s);
                }else {
                    Toast.makeText(AddNewMember.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
                }
            }

        });

        if (sessionManager.isNetworkAvailable()) {
            binding.progressBar.setVisibility(View.VISIBLE);
            getusersMethod();
        }else {
            Toast.makeText(AddNewMember.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
        }
    }


   private void setAdapter(ArrayList<GetUserModel.Result> modelList) {

        mAdapter = new AddNewMemebrsAdapter(AddNewMember.this,modelList);
        //binding.recyclerGrp.setHasFixedSize(true);
        mAdapter.setHasStableIds(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddNewMember.this);
        binding.recyclerGrp.setLayoutManager(linearLayoutManager);
        binding.recyclerGrp.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new AddNewMemebrsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, GetUserModel.Result model) {

            }
        });

    }

    public void getusersMethod() {
        String User_id = Preference.get(AddNewMember.this,Preference.KEY_USER_ID);
        Call<GetUserModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .get_users(User_id);
        call.enqueue(new Callback<GetUserModel>() {
            @Override
            public void onResponse(Call<GetUserModel> call, Response<GetUserModel> response) {
                try {

                    binding.progressBar.setVisibility(View.GONE);

                    GetUserModel myclass = response.body();

                    String status = String.valueOf(myclass.getStatus());
                    String message = myclass.getMessage();

                    if (status.equalsIgnoreCase("1")) {


                        modelList = (ArrayList<GetUserModel.Result>) myclass.getResult();
                        setAdapter(modelList);

                    } else {
                        Toast.makeText(AddNewMember.this, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<GetUserModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(AddNewMember.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void getusersMethod1() {

        String Grp_id = Preference.get(AddNewMember.this,Preference.KEY_grp_id);

        Call<GetUserModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .get_new_users(Grp_id);
        call.enqueue(new Callback<GetUserModel>() {
            @Override
            public void onResponse(Call<GetUserModel> call, Response<GetUserModel> response) {
                try {

                    binding.progressBar.setVisibility(View.GONE);

                    GetUserModel myclass = response.body();

                    String status = String.valueOf(myclass.getStatus());
                    String message = myclass.getMessage();

                    if (status.equalsIgnoreCase("1")) {

                        modelList = (ArrayList<GetUserModel.Result>) myclass.getResult();

                        setAdapter(modelList);

                    }else {
                        Toast.makeText(AddNewMember.this, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<GetUserModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void AddNewMemberMehod(String MemberList){

        String Grp_id= Preference.get(AddNewMember.this,Preference.KEY_grp_id);

        Call<AddNewMemberModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .add_new_member(Grp_id,MemberList);
        call.enqueue(new Callback<AddNewMemberModel>() {
            @Override
            public void onResponse(Call<AddNewMemberModel> call, Response<AddNewMemberModel> response) {

                binding.progressBar.setVisibility(View.GONE);

                AddNewMemberModel finallyPr = response.body();

                String status = finallyPr.getStatus();

                if (status.equalsIgnoreCase("1")) {

                    Toast.makeText(AddNewMember.this, finallyPr.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();

                } else {
                    binding.progressBar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<AddNewMemberModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }
}