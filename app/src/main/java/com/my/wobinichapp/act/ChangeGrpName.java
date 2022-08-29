package com.my.wobinichapp.act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.my.wobinichapp.Preference;
import com.my.wobinichapp.R;
import com.my.wobinichapp.databinding.ActivityChangeGrpNameBinding;
import com.my.wobinichapp.model.SignUpModel;
import com.my.wobinichapp.model.UpdateGrpModel;
import com.my.wobinichapp.utils.RetrofitClients;
import com.my.wobinichapp.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeGrpName extends AppCompatActivity {

    ActivityChangeGrpNameBinding binding;
    String status="";
    private SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_change_grp_name);

        sessionManager = new SessionManager(ChangeGrpName.this);

        String GrpName=Preference.get(ChangeGrpName.this,Preference.KEY_grp_Name);
        binding.edtGrpName.setText(GrpName);

        binding.RRback.setOnClickListener(v -> {

            onBackPressed();

        });

        binding.RRSave.setOnClickListener(v -> {
            status= binding.edtGrpName.getText().toString();

            if(status.equalsIgnoreCase(""))
            {
                Toast.makeText(this, "Please enter status.", Toast.LENGTH_SHORT).show();
            }else
            {
                if (sessionManager.isNetworkAvailable()) {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    ChangeGrpNameMethod();
                }else {
                    Toast.makeText(ChangeGrpName.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void ChangeGrpNameMethod(){

        String grp_id= Preference.get(ChangeGrpName.this,Preference.KEY_grp_id);

        Call<UpdateGrpModel> call = RetrofitClients.getInstance().getApi()
                .change_group_name(grp_id,status);
        call.enqueue(new Callback<UpdateGrpModel>() {
            @Override
            public void onResponse(Call<UpdateGrpModel> call, Response<UpdateGrpModel> response) {

                binding.progressBar.setVisibility(View.GONE);

                UpdateGrpModel finallyPr = response.body();

                String status = finallyPr.getStatus();

                if (status.equalsIgnoreCase("1")) {

                    Toast.makeText(ChangeGrpName.this, finallyPr.getMessage(), Toast.LENGTH_SHORT).show();
                    Preference.save(ChangeGrpName.this,Preference.KEY_grp_Name,finallyPr.getResult().getGroupName());
                    finish();

                } else {

                    binding.progressBar.setVisibility(View.GONE);

                    Toast.makeText(ChangeGrpName.this, finallyPr.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<UpdateGrpModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(ChangeGrpName.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}