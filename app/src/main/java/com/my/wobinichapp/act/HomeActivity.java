package com.my.wobinichapp.act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.google.gson.Gson;
import com.my.wobinichapp.Preference;
import com.my.wobinichapp.R;
import com.my.wobinichapp.adapter.GrpListAdapter;
import com.my.wobinichapp.databinding.ActivityHomeBinding;
import com.my.wobinichapp.model.GetGrpModel;
import com.my.wobinichapp.model.GetUserModel;
import com.my.wobinichapp.model.HomModel;
import com.my.wobinichapp.model.LoginModel;
import com.my.wobinichapp.model.SignUpModel;
import com.my.wobinichapp.utils.RetrofitClients;
import com.my.wobinichapp.utils.SessionManager;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;

    GrpListAdapter mAdapter;
    private ArrayList<GetGrpModel.Result> modelList = new ArrayList<>();
    private SessionManager sessionManager;
    private LocalBroadcastManager lbm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);

       binding= DataBindingUtil.setContentView(this,R.layout.activity_home);

        setUpUi();

        lbm = LocalBroadcastManager.getInstance(this);
        lbm.registerReceiver(receiver, new IntentFilter("filter_string"));
        
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sessionManager.isNetworkAvailable()) {
            binding.progressBar.setVisibility(View.VISIBLE);
            //getProfileMethod();
            getAllGrpMethod();
        }else {
            Toast.makeText(HomeActivity.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
        }

        String UserStatus=Preference.get(this,Preference.KEY_USER_status);
        String UserImage=Preference.get(this,Preference.KEY_USER_image);

        if(!UserImage.equalsIgnoreCase(""))
        {
            Glide.with(HomeActivity.this).load(UserImage).circleCrop().placeholder(R.drawable.defaultuser).error(R.drawable.defaultuser).into(binding.imgProfile);
        }

        if(UserStatus.equalsIgnoreCase(""))
        {
            binding.txtStatus.setText("null");
        }else
        { binding.txtStatus.setText(UserStatus); }

    }

    private void setUpUi() {

        sessionManager = new SessionManager(HomeActivity.this);

        binding.RRKotlin.setOnClickListener(v -> {
            //  startActivity(new Intent(HomeActivity.this,KolnActivity.class));
            startActivity(new Intent(HomeActivity.this,KolnActivityDetails.class));
        });

        binding.imgProfile.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this,ChageProfilePicture.class));
        });

        binding.RRShop.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this,ShopDetails.class));
        });

        binding.RRMenu.setOnClickListener(v -> {

            PopupMenu popup = new PopupMenu(HomeActivity.this, binding.RRMenu);
            //Inflating the Popup using xml file
            popup.getMenuInflater().inflate(R.menu.poupup_menu, popup.getMenu());
            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {

                    int id=item.getItemId();

                    if(id==R.id.Add)
                    {
                        startActivity(new Intent(HomeActivity.this,CreateGroupsActivity.class));

                    }else if(id==R.id.Mypicture)
                    {
                        startActivity(new Intent(HomeActivity.this,ChageProfilePicture.class));

                    }else if(id==R.id.MyStatus)
                    {
                        startActivity(new Intent(HomeActivity.this,ChangeStatus.class));

                    }else if(id==R.id.Tone)
                    {
                        startActivity(new Intent(HomeActivity.this,ChangeTone.class));

                    }else if(id==R.id.Edit_Profile)
                    {
                        startActivity(new Intent(HomeActivity.this,EditProfileActivity.class));

                    }else if(id==R.id.changePassword)
                    {
                        startActivity(new Intent(HomeActivity.this,ChnagePassword.class));

                    }else if(id==R.id.logout)
                    {
                        Preference.clearPreference(HomeActivity.this);
                        startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                        finish();
                    }
                    return true;
                }
            });

            popup.show();

        });

        binding.RRYes.setOnClickListener(v -> {

            startActivity(new Intent(HomeActivity.this,YesActivity.class));

        });

        binding.RRLove.setOnClickListener(v -> {

            startActivity(new Intent(HomeActivity.this,LoveActivity.class));

        });

        binding.RRMeal.setOnClickListener(v -> {
            // startActivity(new Intent(HomeActivity.this,MealActivity.class));
            startActivity(new Intent(HomeActivity.this,MealDetailsActivity.class));

        });

        binding.RRFreeTime.setOnClickListener(v -> {
            //startActivity(new Intent(HomeActivity.this,FreeTimeActivity.class));
            startActivity(new Intent(HomeActivity.this,FreeTimeDetailsActivity.class));

        });

        binding.RREmail.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this,EmailActivity.class));
        });
    }

    private void setAdapter(ArrayList<GetGrpModel.Result> modelList) {

        mAdapter = new GrpListAdapter(HomeActivity.this, this.modelList);
        binding.recyclerGrp.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HomeActivity.this);
        binding.recyclerGrp.setLayoutManager(linearLayoutManager);
        binding.recyclerGrp.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new GrpListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, GetGrpModel.Result model)
            {
                Preference.save(HomeActivity.this,Preference.KEY_grp_id,model.getGroupId());
                Preference.save(HomeActivity.this,Preference.KEY_grp_Name,model.getGroupName());
                Preference.save(HomeActivity.this,Preference.KEY_grp_IMage,model.getGroupImage());
                startActivity(new Intent(HomeActivity.this,GrpChatDetails.class));

            }
        });
    }


    private void getProfileMethod(){

        String User_id= Preference.get(HomeActivity.this,Preference.KEY_USER_ID);

        Call<SignUpModel> call = RetrofitClients.getInstance().getApi()
                .get_profile(User_id);
        call.enqueue(new Callback<SignUpModel>() {
            @Override
            public void onResponse(Call<SignUpModel> call, Response<SignUpModel> response) {

                binding.progressBar.setVisibility(View.GONE);

                SignUpModel finallyPr = response.body();

                String status = finallyPr.getStatus();

                if (status.equalsIgnoreCase("1")) {

                    if(!finallyPr.getResult().getImage().equalsIgnoreCase(""))
                    {
                        Glide.with(HomeActivity.this).load(finallyPr.getResult().getImage()).circleCrop().placeholder(R.drawable.defaultuser).error(R.drawable.defaultuser).into(binding.imgProfile);
                    }

                    if(finallyPr.getResult().getStatus().equalsIgnoreCase(""))
                    {
                        binding.txtStatus.setText("null");

                    }else
                    {
                        binding.txtStatus.setText(finallyPr.getResult().getStatus());
                    }

                } else {

                    binding.progressBar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<SignUpModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getAllGrpMethod()
    {
        String User_id= Preference.get(HomeActivity.this,Preference.KEY_USER_ID);

        Call<ResponseBody> call = RetrofitClients
                .getInstance()
                .getApi()
                .get_group(User_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    binding.progressBar.setVisibility(View.GONE);

                    String Value = response.body().string();

                    Log.e("harshit>", "" + Value);

                    GetGrpModel myclass = new Gson().fromJson(Value, GetGrpModel.class);

                    Log.e("harshit>>>>>>1", "" + myclass.getStatus());

                  //  GetGrpModel myclass = response.body();

                   String status = String.valueOf(myclass.getStatus());
                    String message = myclass.getMessage();

                    if (status.equalsIgnoreCase("1")) {
                        binding.txtEmty.setVisibility(View.GONE);
                        modelList = (ArrayList<GetGrpModel.Result>) myclass.getResult();

                        String PostCount = myclass.getPostCount()+"";

                        Preference.save(HomeActivity.this,Preference.key_Post_count,PostCount);

                        if(PostCount.equalsIgnoreCase("0"))
                        {
                            binding.txtCount.setVisibility(View.GONE);

                        }else
                        {
                            binding.txtCount.setVisibility(View.VISIBLE);
                            binding.txtCount.setText(PostCount);
                        }

                        Log.e("jkhgjkgjcnkb>",""+modelList);

                       setAdapter(modelList);

                    } else {

                        binding.txtEmty.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                binding.txtEmty.setVisibility(View.VISIBLE);
            }
        });
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null)
            {
                String str = intent.getStringExtra("key");
                getAllGrpMethod();
            }
        }
    };

}