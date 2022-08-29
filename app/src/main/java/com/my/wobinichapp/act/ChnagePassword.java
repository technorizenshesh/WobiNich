package com.my.wobinichapp.act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.my.wobinichapp.Preference;
import com.my.wobinichapp.R;
import com.my.wobinichapp.databinding.ActivityChnagePasswordBinding;
import com.my.wobinichapp.model.SignUpModel;
import com.my.wobinichapp.utils.RetrofitClients;
import com.my.wobinichapp.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChnagePassword extends AppCompatActivity {

    ActivityChnagePasswordBinding binding;
    String Password="";
    private SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_chnage_password);

        sessionManager = new SessionManager(ChnagePassword.this);

        binding.RRSave.setOnClickListener(v -> {
             Password=binding.edtPassword.getText().toString();
            String CPassword=binding.edtCPassword.getText().toString();

            if(Password.equalsIgnoreCase(""))
            {
                Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();

            }else if(CPassword.equalsIgnoreCase(""))
            {
                Toast.makeText(this, "Please Enter Confirm Password", Toast.LENGTH_SHORT).show();

            }else if(!Password.equalsIgnoreCase(CPassword))
            {
                Toast.makeText(this, "Don't match Password.", Toast.LENGTH_SHORT).show();
            }else
            {
                if (sessionManager.isNetworkAvailable()) {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    ChangePasswordMethod();
                }else {
                    Toast.makeText(ChnagePassword.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
                }
                finish();
            }

        });
    }

    private void ChangePasswordMethod(){

        String User_id= Preference.get(ChnagePassword.this,Preference.KEY_USER_ID);

        Call<SignUpModel> call = RetrofitClients.getInstance().getApi()
                .change_password(User_id,Password);
        call.enqueue(new Callback<SignUpModel>() {
            @Override
            public void onResponse(Call<SignUpModel> call, Response<SignUpModel> response) {

                binding.progressBar.setVisibility(View.GONE);

                SignUpModel finallyPr = response.body();

                String status = finallyPr.getStatus();

                if (status.equalsIgnoreCase("1")) {

                    Toast.makeText(ChnagePassword.this, finallyPr.getMessage(), Toast.LENGTH_SHORT).show();

                    finish();

                } else {

                    binding.progressBar.setVisibility(View.GONE);

                    Toast.makeText(ChnagePassword.this, finallyPr.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<SignUpModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(ChnagePassword.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}