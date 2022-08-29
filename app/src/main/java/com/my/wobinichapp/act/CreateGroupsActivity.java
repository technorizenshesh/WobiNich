package com.my.wobinichapp.act;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.my.wobinichapp.Preference;
import com.my.wobinichapp.R;
import com.my.wobinichapp.adapter.UserListAdapter;
import com.my.wobinichapp.databinding.ActivityCreateGroupsBinding;
import com.my.wobinichapp.model.GetUserModel;
import com.my.wobinichapp.model.UserModel;
import com.my.wobinichapp.utils.RetrofitClients;
import com.my.wobinichapp.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateGroupsActivity extends AppCompatActivity {

    ActivityCreateGroupsBinding binding;

    UserListAdapter mAdapter;
    private ArrayList<GetUserModel.Result> modelList = new ArrayList<>();
    private SessionManager sessionManager;
    String User_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_groups);

        sessionManager = new SessionManager(CreateGroupsActivity.this);

        User_id = Preference.get(CreateGroupsActivity.this,Preference.KEY_USER_ID);

        binding.RRback.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.RRCreate.setOnClickListener(v -> {

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

            Log.e("MemberIdFv1>>",""+s);

            if(s.equalsIgnoreCase(""))
            {
                Toast.makeText(this, "Please Select Memeber..", Toast.LENGTH_SHORT).show();

            }else
            {
                list.add(User_id);
                String s1 = TextUtils.join(",", list);
                startActivity(new Intent(CreateGroupsActivity.this, CreateGrpNext.class).putExtra("MemberId",s1));
            }
        });

        binding.RRInvite.setOnClickListener(v -> {

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Here is the share content body";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));

        });

        if (sessionManager.isNetworkAvailable()) {
            binding.progressBar.setVisibility(View.VISIBLE);
            getusersMethod();
        }else {
            Toast.makeText(CreateGroupsActivity.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setAdapter(ArrayList<GetUserModel.Result> modelList) {

        mAdapter = new UserListAdapter(CreateGroupsActivity.this,modelList);
        // binding.recyclerGrp.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CreateGroupsActivity.this);
        binding.recyclerGrp.setLayoutManager(linearLayoutManager);
        binding.recyclerGrp.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new UserListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, GetUserModel.Result model) {

            }
        });
    }

    public void getusersMethod() {

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
                        Toast.makeText(CreateGroupsActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<GetUserModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(CreateGroupsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}