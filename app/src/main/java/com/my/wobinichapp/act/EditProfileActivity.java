package com.my.wobinichapp.act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.my.wobinichapp.Preference;
import com.my.wobinichapp.R;
import com.my.wobinichapp.databinding.ActivityEditProfileBinding;
import com.my.wobinichapp.model.SignUpModel;
import com.my.wobinichapp.utils.RetrofitClients;
import com.my.wobinichapp.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    ActivityEditProfileBinding binding;
    String UserName="";
    String Email="";
    String Mobile="";
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_edit_profile);

        sessionManager = new SessionManager(EditProfileActivity.this);

        binding.RRSave.setOnClickListener(v -> {
            Validation();
        });


         binding.RRback.setOnClickListener(v ->
         {
               onBackPressed();
        });


        if (sessionManager.isNetworkAvailable()) {
            binding.progressBar.setVisibility(View.VISIBLE);
            //UpdateUserMethod();
            getProfileMethod();
        }else {
            Toast.makeText(EditProfileActivity.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
        }
    }

    private void Validation() {

        UserName=binding.edtName.getText().toString();
        Email=binding.edtEmail.getText().toString();
        Mobile=binding.edtMobile.getText().toString();

        if(UserName.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Enter UserName", Toast.LENGTH_SHORT).show();

        }else if(Email.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();

        }else
        {
            if (sessionManager.isNetworkAvailable()) {
                binding.progressBar.setVisibility(View.VISIBLE);
                UpdateUserMethod();
            }else {
                Toast.makeText(EditProfileActivity.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getProfileMethod(){

        String User_id= Preference.get(EditProfileActivity.this,Preference.KEY_USER_ID);

        Call<SignUpModel> call = RetrofitClients.getInstance().getApi()
                .get_profile(User_id);
        call.enqueue(new Callback<SignUpModel>() {
            @Override
            public void onResponse(Call<SignUpModel> call, Response<SignUpModel> response) {

                binding.progressBar.setVisibility(View.GONE);

                SignUpModel finallyPr = response.body();

                String status = finallyPr.getStatus();

                if (status.equalsIgnoreCase("1"))
                {

                    binding.edtName.setText(finallyPr.getResult().getName());
                    binding.edtEmail.setText(finallyPr.getResult().getEmail());
                    binding.edtMobile.setText(finallyPr.getResult().getContact());

                }else{

                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(EditProfileActivity.this, finallyPr.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onFailure(Call<SignUpModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(EditProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void UpdateUserMethod(){

        String User_id= Preference.get(EditProfileActivity.this,Preference.KEY_USER_ID);

        Call<SignUpModel> call = RetrofitClients.getInstance().getApi()
                .update_user(User_id,UserName,Mobile,Email);
        call.enqueue(new Callback<SignUpModel>() {
            @Override
            public void onResponse(Call<SignUpModel> call, Response<SignUpModel> response) {

                binding.progressBar.setVisibility(View.GONE);

                SignUpModel finallyPr = response.body();

                String status = finallyPr.getStatus();

                if (status.equalsIgnoreCase("1")) {

                    startActivity(new Intent(EditProfileActivity.this,HomeActivity.class));
                    finish();

                } else {
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(EditProfileActivity.this, finallyPr.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<SignUpModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
            }
        });

    }
}