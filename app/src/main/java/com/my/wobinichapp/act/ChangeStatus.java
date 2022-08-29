package com.my.wobinichapp.act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.my.wobinichapp.Preference;
import com.my.wobinichapp.R;
import com.my.wobinichapp.databinding.ActivityChangeStatusBinding;
import com.my.wobinichapp.model.SignUpModel;
import com.my.wobinichapp.utils.RetrofitClients;
import com.my.wobinichapp.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeStatus extends AppCompatActivity {

    ActivityChangeStatusBinding binding;
    String status="";
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_change_status);

       sessionManager = new SessionManager(ChangeStatus.this);

       String Status= Preference.get(ChangeStatus.this,Preference.KEY_USER_status);

        binding.edtStatus.setText(""+Status);

        binding.RRback.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.RRSave.setOnClickListener(v -> {

            status= binding.edtStatus.getText().toString();

            if(status.equalsIgnoreCase(""))
            {
                Toast.makeText(this, "Please enter status.", Toast.LENGTH_SHORT).show();
            }else
            {
                    if (sessionManager.isNetworkAvailable()) {
                        binding.progressBar.setVisibility(View.VISIBLE);
                        ChangeStatusMethod();
                    }else {
                        Toast.makeText(ChangeStatus.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
                    }
            }
        });

    }

    private void ChangeStatusMethod(){

        String User_id= Preference.get(ChangeStatus.this,Preference.KEY_USER_ID);

        Call<SignUpModel> call = RetrofitClients.getInstance().getApi()
                .update_status(User_id,status);
        call.enqueue(new Callback<SignUpModel>() {
            @Override
            public void onResponse(Call<SignUpModel> call, Response<SignUpModel> response) {

                binding.progressBar.setVisibility(View.GONE);

                SignUpModel finallyPr = response.body();

                String status = finallyPr.getStatus();

                if (status.equalsIgnoreCase("1")) {

                    Toast.makeText(ChangeStatus.this, finallyPr.getMessage(), Toast.LENGTH_SHORT).show();

                    Preference.save(ChangeStatus.this,Preference.KEY_USER_status,finallyPr.getResult().getStatus());

                    finish();

                } else {

                    binding.progressBar.setVisibility(View.GONE);

                    Toast.makeText(ChangeStatus.this, finallyPr.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<SignUpModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(ChangeStatus.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}