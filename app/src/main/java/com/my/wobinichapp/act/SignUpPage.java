package com.my.wobinichapp.act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.my.wobinichapp.R;
import com.my.wobinichapp.databinding.ActivityLoginBinding;
import com.my.wobinichapp.databinding.ActivitySignUpPageBinding;
import com.my.wobinichapp.model.SignUpModel;
import com.my.wobinichapp.utils.RetrofitClients;
import com.my.wobinichapp.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpPage extends AppCompatActivity {

    ActivitySignUpPageBinding binding;
    private SessionManager sessionManager;
    String UserName="";
    String Email="";
    String Mobile="";
    String PassWord="";
    String CPassWord="";
    String token="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_sign_up_page);

        sessionManager = new SessionManager(SignUpPage.this);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(runnable -> {
            token = runnable.getToken();
            Log.e( "Tokennnn" ,token);
        });

        binding.txtLogin.setOnClickListener(v -> {
            startActivity(new Intent(SignUpPage.this,LoginActivity.class));
        });

        binding.RRSigup.setOnClickListener(v -> {
            Validation();
        });

    }

    private void Validation() {

         UserName=binding.edtName.getText().toString();
         Email=binding.edtEmail.getText().toString();
         Mobile=binding.edtMobile.getText().toString();
         PassWord=binding.edtPassWord.getText().toString();
         CPassWord=binding.edtConfirmPassWord.getText().toString();

         if(UserName.equalsIgnoreCase(""))
         {
             Toast.makeText(this, "Please Enter UserName", Toast.LENGTH_SHORT).show();

         }else if(Email.equalsIgnoreCase("") && Mobile.equalsIgnoreCase(""))
         {
             Toast.makeText(this, "Please Enter Email and password", Toast.LENGTH_SHORT).show();

         }else if(PassWord.equalsIgnoreCase(""))
         {
             Toast.makeText(this, "Please Enter PassWord", Toast.LENGTH_SHORT).show();

         }else if(CPassWord.equalsIgnoreCase(""))
         {
             Toast.makeText(this, "Please Enter Confirm PassWord", Toast.LENGTH_SHORT).show();

         }else if(!CPassWord.equalsIgnoreCase(PassWord))
         {
             Toast.makeText(this, "Don't match Password.", Toast.LENGTH_SHORT).show();

         }else
         {
            if (sessionManager.isNetworkAvailable()) {

                 binding.progressBar.setVisibility(View.VISIBLE);

                 SignUpMethod();

             }else {
                 Toast.makeText(SignUpPage.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
             }
         }

    }

    private void SignUpMethod(){

        Call<SignUpModel> call = RetrofitClients.getInstance().getApi()
                .signup(UserName,Mobile,Email,PassWord,token);
        call.enqueue(new Callback<SignUpModel>() {
            @Override
            public void onResponse(Call<SignUpModel> call, Response<SignUpModel> response) {

                binding.progressBar.setVisibility(View.GONE);

                SignUpModel finallyPr = response.body();

                String status = finallyPr.getStatus();

                if (status.equalsIgnoreCase("1")) {

                    startActivity(new Intent(SignUpPage.this,LoginActivity.class));
                    finish();

                } else {

                    binding.progressBar.setVisibility(View.GONE);

                    Toast.makeText(SignUpPage.this, finallyPr.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<SignUpModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(SignUpPage.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}