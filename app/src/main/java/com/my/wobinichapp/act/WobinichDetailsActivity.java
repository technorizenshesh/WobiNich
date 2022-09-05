package com.my.wobinichapp.act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.my.wobinichapp.Preference;
import com.my.wobinichapp.R;
import com.my.wobinichapp.databinding.ActivityWobinichDetailsBinding;
import com.my.wobinichapp.utils.RetrofitClients;
import com.my.wobinichapp.utils.SessionManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WobinichDetailsActivity extends AppCompatActivity {

    ActivityWobinichDetailsBinding binding;
    SessionManager sessionManager;
    String postId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_wobinich_details);
        sessionManager = new SessionManager(WobinichDetailsActivity.this);

       // if(getIntent()!=null) getPostDetail(getIntent().getStringExtra("id"));

        binding.llLandScape.setOnClickListener(v -> {
                startActivity(new Intent(WobinichDetailsActivity.this,KolnActivityDetails.class));
            });

            binding.llMeal.setOnClickListener(v -> {
                startActivity(new Intent(WobinichDetailsActivity.this,MealDetailsActivity.class));
            });

            binding.llFreetime.setOnClickListener(v -> {
                startActivity(new Intent(WobinichDetailsActivity.this,FreeTimeDetailsActivity.class));
            });

            binding.llShop.setOnClickListener(v -> {
                startActivity(new Intent(WobinichDetailsActivity.this,ShopDetails.class));
            });


        if (sessionManager.isNetworkAvailable()) {
            binding.progressBar.setVisibility(View.VISIBLE);
           if(getIntent()!=null) getPostDetail(getIntent().getStringExtra("id"));
        }   else {
            Toast.makeText(WobinichDetailsActivity.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
        }


        binding.ivFav.setOnClickListener(v -> {
            if (sessionManager.isNetworkAvailable()) {
                binding.progressBar.setVisibility(View.VISIBLE);
                addFav(postId);
            }   else {
                Toast.makeText(WobinichDetailsActivity.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getPostDetail(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("post_id", id);
        Log.e("getPost Detail  Request", map.toString());
        Call<ResponseBody> call = RetrofitClients.getInstance().getApi().post_detail(map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                binding.progressBar.setVisibility(View.GONE);

                try {
                    if (response.isSuccessful()) {
                        String responseData = response.body() != null ? response.body().string() : "";
                        JSONObject object = new JSONObject(responseData);
                        Log.e("getPost Detail Response==", responseData);
                        if (object.getString("status").equalsIgnoreCase("1")) {
                            JSONObject result = object.getJSONObject("result");
                            postId = result.getString("id");
                            String imgLink = result.getString("image");
                            binding.tvMaps.setText(result.getString("maps"));
                            binding.tvWebsite.setText(result.getString("website"));
                            binding.tvComment.setText(result.getString("comment"));
                            Glide.with(WobinichDetailsActivity.this).load(imgLink).placeholder(R.drawable.dummy).error(R.drawable.frame).into(binding.ivPostImg);

                        } else {
                            Toast.makeText(WobinichDetailsActivity.this, object.getString("message"), Toast.LENGTH_SHORT).show();

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Exception====", e.getStackTrace() + "");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }



    public void addFav(String id) {
        Map<String, String> map = new HashMap<>();
        String User_id= Preference.get(WobinichDetailsActivity.this,Preference.KEY_USER_ID);
        map.put("post_id", id);
        map.put("user_id", User_id);
        Log.e("AddFav  Request", map.toString());
        Call<ResponseBody> call = RetrofitClients.getInstance().getApi().postFav(map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                binding.progressBar.setVisibility(View.GONE);

                try {
                    if (response.isSuccessful()) {
                        String responseData = response.body() != null ? response.body().string() : "";
                        JSONObject object = new JSONObject(responseData);
                        Log.e("addPost Response==", responseData);
                        if (object.getString("status").equalsIgnoreCase("1")) {
                            Toast.makeText(WobinichDetailsActivity.this, getString(R.string.add_fav), Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(WobinichDetailsActivity.this, object.getString("message"), Toast.LENGTH_SHORT).show();

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Exception====", e.getStackTrace() + "");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }


}