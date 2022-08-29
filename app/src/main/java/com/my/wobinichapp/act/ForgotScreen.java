package com.my.wobinichapp.act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.my.wobinichapp.Preference;
import com.my.wobinichapp.R;
import com.my.wobinichapp.databinding.ActivityForgotScreenBinding;
import com.my.wobinichapp.model.ForgotPasword;
import com.my.wobinichapp.model.InsertGrpModel;
import com.my.wobinichapp.utils.RetrofitClients;
import com.my.wobinichapp.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotScreen extends AppCompatActivity {

    ActivityForgotScreenBinding binding;
    private SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding= DataBindingUtil.setContentView(this,R.layout.activity_forgot_screen);

        sessionManager = new SessionManager(ForgotScreen.this);

       binding.RRSave.setOnClickListener(v -> {

           String email=binding.edtEmail.getText().toString();

           if(!email.equalsIgnoreCase(""))
           {
               if (sessionManager.isNetworkAvailable()) {
                   binding.progressBar.setVisibility(View.VISIBLE);
                   ForogotPasswordMethod(email);
               }else {
                   Toast.makeText(ForgotScreen.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
               }

           }else
           {
               Toast.makeText(this, "Please Enter email..", Toast.LENGTH_SHORT).show();
           }
       });

    }

    public void ForogotPasswordMethod(String email)
    {
        Call<ForgotPasword> call = RetrofitClients
                .getInstance()
                .getApi()
                .forgot_password(email);
        call.enqueue(new Callback<ForgotPasword>() {
            @Override
            public void onResponse(Call<ForgotPasword> call, Response<ForgotPasword> response) {
                try {
                    binding.edtEmail.setText("");
                    binding.progressBar.setVisibility(View.GONE);

                    ForgotPasword myclass = response.body();

                    String status = myclass.getStatus();
                    String message = myclass.getResult();

                    if (status.equalsIgnoreCase("1")) {

                        Toast.makeText(ForgotScreen.this, "Please check Your mail..", Toast.LENGTH_SHORT).show();

                    } else {
                        binding.progressBar.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    binding.edtEmail.setText("");
                }
            }
            @Override
            public void onFailure(Call<ForgotPasword> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                binding.edtEmail.setText("");
            }
        });

    }

}