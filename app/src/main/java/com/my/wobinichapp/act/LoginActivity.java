package com.my.wobinichapp.act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.my.wobinichapp.Preference;
import com.my.wobinichapp.R;
import com.my.wobinichapp.databinding.ActivityLoginBinding;
import com.my.wobinichapp.model.LoginModel;
import com.my.wobinichapp.model.SignUpModel;
import com.my.wobinichapp.utils.RetrofitClients;
import com.my.wobinichapp.utils.SessionManager;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    private SessionManager sessionManager;

    String Email="";
    String PassWord="";

    String token="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);

        sessionManager = new SessionManager(LoginActivity.this);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(runnable -> {
            token = runnable.getToken();
            Log.e( "Tokennnn" ,token);
        });

        binding.RRLogin.setOnClickListener(v -> {
            //startActivity(new Intent(LoginActivity.this,HomeActivity.class));
              Validation();
        });

        binding.txtForogtPassword.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this,ForgotScreen.class));
        });

        binding.txtreg.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this,SignUpPage.class));
        });

    }

    private void Validation() {

        Email=binding.edtEmail.getText().toString();
        PassWord=binding.edtPassWord.getText().toString();

       if(Email.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
        }else if(PassWord.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Enter PassWord", Toast.LENGTH_SHORT).show();
        }else
        {
            if (sessionManager.isNetworkAvailable()) {
                binding.progressBar.setVisibility(View.VISIBLE);
                LoginMethod();
            }else {
                Toast.makeText(LoginActivity.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void LoginMethod(){
        Call<ResponseBody> call = RetrofitClients.getInstance().getApi()
                .login(Email,PassWord,token);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                binding.progressBar.setVisibility(View.GONE);
                try {
                    String Value = response.body().string();
                    Log.e("harshit>>>>>>", "" + Value);
                    LoginModel finallyPr = new Gson().fromJson(Value, LoginModel.class);
                    Log.e("harshit>>>>>>", "" + finallyPr.getStatus());
                    String status = finallyPr.getStatus();
                    if(status.equalsIgnoreCase("1"))
                    {
                        Preference.save(LoginActivity.this,Preference.KEY_USER_ID,finallyPr.getResult().getId());
                        Preference.save(LoginActivity.this,Preference.KEY_USER_status,finallyPr.getResult().getStatus());
                        Preference.save(LoginActivity.this,Preference.KEY_USER_image,finallyPr.getResult().getImage());
                        startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                    }else{
                        Toast.makeText(LoginActivity.this, ""+finallyPr.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e)
                {
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }

}