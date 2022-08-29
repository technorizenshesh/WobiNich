package com.my.wobinichapp.act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.my.wobinichapp.Preference;
import com.my.wobinichapp.R;
import com.my.wobinichapp.adapter.AdapterPost;
import com.my.wobinichapp.adapter.GetAllPostAdapter;
import com.my.wobinichapp.adapter.GetAllWordAdapter;
import com.my.wobinichapp.adapter.GrpListAdapter;
import com.my.wobinichapp.databinding.ActivityEmailBinding;
import com.my.wobinichapp.listener.OnAnswerListener;
import com.my.wobinichapp.model.ChallengeModel;
import com.my.wobinichapp.model.GetAllWordModel;
import com.my.wobinichapp.model.GetGrpModel;
import com.my.wobinichapp.model.SingleGetChatModel;
import com.my.wobinichapp.utils.RetrofitClients;
import com.my.wobinichapp.utils.SessionManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailActivity extends AppCompatActivity implements OnAnswerListener {

    ActivityEmailBinding binding;
    AdapterPost adapter;
    private ArrayList<ChallengeModel.Result> arrayList;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_email);
        initViews();


    }

    private void initViews() {
        arrayList = new ArrayList<>();
        sessionManager = new SessionManager(EmailActivity.this);
        adapter = new AdapterPost(EmailActivity.this, arrayList, EmailActivity.this);
        binding.rvAnswer.setAdapter(adapter);


        if (sessionManager.isNetworkAvailable()) {
            binding.progressBar.setVisibility(View.VISIBLE);
            getAllAnswer();
        } else {
            Toast.makeText(EmailActivity.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
        }
    }


    public void getAllAnswer() {
        String Userid_ID = Preference.get(EmailActivity.this, Preference.KEY_USER_ID);
        Call<ResponseBody> call = RetrofitClients.getInstance().getApi().get_all_answer(Userid_ID);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                binding.progressBar.setVisibility(View.GONE);
                try {
                    if (response.isSuccessful()) {
                        String responseData = response.body() != null ? response.body().string() : "";
                        JSONObject object = new JSONObject(responseData);
                        Log.e("Get All Answer==", responseData);
                        if (object.getString("status").equalsIgnoreCase("1")) {
                            ChallengeModel challengeModel = new Gson().fromJson(responseData, ChallengeModel.class);
                            binding.tvNotFound.setVisibility(View.GONE);
                            arrayList.clear();
                            arrayList.addAll(challengeModel.getResult());
                            adapter.notifyDataSetChanged();

                        } else {
                            binding.tvNotFound.setVisibility(View.VISIBLE);
                            arrayList.clear();
                            adapter.notifyDataSetChanged();

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onAnswer(int position, String answer) {
        if (answer.equals(""))
            Toast.makeText(EmailActivity.this, getText(R.string.please_enter_your_answer), Toast.LENGTH_SHORT).show();
        else {


    /*    for (int i =0 ;i < arrayList.get(position).getUserAnswer().size() ;i++){

        }*/


            if (sessionManager.isNetworkAvailable()) {
                binding.progressBar.setVisibility(View.VISIBLE);
                submitAnswer(position, answer);
            } else {
                Toast.makeText(EmailActivity.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
            }
        }

    }


    public void submitAnswer(int position, String answer) {
        String Userid_ID = Preference.get(EmailActivity.this, Preference.KEY_USER_ID);
        Map<String, String> map = new HashMap<>();
        map.put("post_id", arrayList.get(position).getId());
        map.put("post_by", arrayList.get(position).getUserId());
        map.put("answer_by", Userid_ID);
        map.put("group_id", arrayList.get(position).getGroupId());
        map.put("answer", answer);
        Log.e("Give Ans Challege Request", map.toString());
        Call<ResponseBody> call = RetrofitClients.getInstance().getApi().submit_answer(map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                binding.progressBar.setVisibility(View.GONE);

                try {
                    if (response.isSuccessful()) {
                        String responseData = response.body() != null ? response.body().string() : "";
                        JSONObject object = new JSONObject(responseData);
                        Log.e("Give Ans Challege Response==", responseData);
                        if (object.getString("status").equalsIgnoreCase("1")) {
                            if (sessionManager.isNetworkAvailable()) {
                                binding.progressBar.setVisibility(View.VISIBLE);
                                getAllAnswer();
                            } else {
                                Toast.makeText(EmailActivity.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(EmailActivity.this, object.getString("message"), Toast.LENGTH_SHORT).show();

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }
}