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
import com.my.wobinichapp.Preference;
import com.my.wobinichapp.R;
import com.my.wobinichapp.adapter.Chat_Adapter;
import com.my.wobinichapp.adapter.Chat_Adapter_single;
import com.my.wobinichapp.databinding.ActivityPersonalChatBinding;
import com.my.wobinichapp.model.GrpChatModel;
import com.my.wobinichapp.model.InsertGrpModel;
import com.my.wobinichapp.model.SingleGetChatModel;
import com.my.wobinichapp.utils.RetrofitClients;
import com.my.wobinichapp.utils.SessionManager;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalChat extends AppCompatActivity {

    ActivityPersonalChatBinding binding;
    Chat_Adapter_single mAdapter1;
    private ArrayList<SingleGetChatModel.Result> modelList_chat = new ArrayList<>();
    String ReceiverId="",senderId="";
    private SessionManager sessionManager;

    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_personal_chat);

        sessionManager = new SessionManager(PersonalChat.this);

        Intent intent=getIntent();

        if(intent!=null)
        {
              String MemberName=intent.getStringExtra("MemberName").toString();
              binding.txtMemberName.setText(MemberName);
              String MemberImage=intent.getStringExtra("MemberImage").toString();
              ReceiverId=intent.getStringExtra("ReceiverId").toString();
            senderId = intent.getStringExtra("senderId").toString();

            if(!MemberImage.equalsIgnoreCase(""))
            {
                Glide.with(this).load(MemberImage).circleCrop().placeholder(R.drawable.defaultuser).error(R.drawable.defaultuser).into(binding.imgMemberImg);
            }

        }

         binding.RRback.setOnClickListener(v ->
         {
           onBackPressed();

         });

        binding.LLSend.setOnClickListener(v -> {
            if(!binding.edtInsert.getText().toString().equals(""))
            {
                if (sessionManager.isNetworkAvailable()) {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    insertChatMethod();
                }else {
                    Toast.makeText(PersonalChat.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
                }
            }
            else Toast.makeText(PersonalChat.this, R.string.please_enter_message, Toast.LENGTH_SHORT).show();

        });

        if (sessionManager.isNetworkAvailable()) {
            binding.progressBar.setVisibility(View.VISIBLE);
            getChatAllpMethod();
        }else {
            Toast.makeText(PersonalChat.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (isLastVisible()) {
                    getChatAllpMethod();
                }
            }
        }, 0, 5000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }


    private void setAdapterChat(ArrayList<SingleGetChatModel.Result> modelList_chat) {

        mAdapter1 = new Chat_Adapter_single(PersonalChat.this,modelList_chat);
        binding.recyclerViewChat.setHasFixedSize(true);
        // use a linear layout manager
        binding.recyclerViewChat.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewChat.setAdapter(mAdapter1);

        mAdapter1.SetOnItemClickListener(new Chat_Adapter_single.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, SingleGetChatModel.Result model){

            }
        });
    }


    public void insertChatMethod()
    {
      //  String Sender_id= Preference.get(PersonalChat.this,Preference.KEY_USER_ID);

        Call<InsertGrpModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .insert_chat(senderId,ReceiverId,binding.edtInsert.getText().toString());
        Log.e("sender_id",senderId);
        Log.e("ReceiverId",ReceiverId);

        call.enqueue(new Callback<InsertGrpModel>() {
            @Override
            public void onResponse(Call<InsertGrpModel> call, Response<InsertGrpModel> response) {
                try {
                    binding.edtInsert.setText("");
                    binding.progressBar.setVisibility(View.GONE);

                    InsertGrpModel myclass = response.body();

                    String status = myclass.getStatus();
                    String message = myclass.getResult();

                    if (status.equalsIgnoreCase("1")) {

                        Toast.makeText(PersonalChat.this, ""+message, Toast.LENGTH_SHORT).show();
                        binding.progressBar.setVisibility(View.VISIBLE);
                        getChatAllpMethod();

                    } else {
                        binding.progressBar.setVisibility(View.VISIBLE);
                        getChatAllpMethod();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    binding.edtInsert.setText("");
                }
            }
            @Override
            public void onFailure(Call<InsertGrpModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                binding.edtInsert.setText("");
            }
        });

    }

    public void getChatAllpMethod()
    {
        String Sender_ID= Preference.get(PersonalChat.this,Preference.KEY_USER_ID);

        Call<SingleGetChatModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .get_chat(senderId,ReceiverId);
        call.enqueue(new Callback<SingleGetChatModel>() {
            @Override
            public void onResponse(Call<SingleGetChatModel> call, Response<SingleGetChatModel> response) {
                try {

                    binding.progressBar.setVisibility(View.GONE);

                    SingleGetChatModel myclass = response.body();

                    String status = String.valueOf(myclass.getStatus());
                    String message = myclass.getMessage();

                    if (status.equalsIgnoreCase("1")) {
                        binding.tvNotFound.setVisibility(View.GONE);
                        modelList_chat = (ArrayList<SingleGetChatModel.Result>) myclass.getResult();
                        setAdapterChat(modelList_chat);
                        binding.recyclerViewChat.scrollToPosition(modelList_chat.size() - 1);

                    } else {
                        binding.tvNotFound.setVisibility(View.VISIBLE);
                        modelList_chat.clear();
                        mAdapter1.notifyDataSetChanged();
                        //Toast.makeText(PersonalChat.this, ""+message, Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(PersonalChat.this, ""+e, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<SingleGetChatModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(PersonalChat.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    private boolean isLastVisible() {

        try {
            if (modelList_chat != null) {
                LinearLayoutManager layoutManager = ((LinearLayoutManager) binding.recyclerViewChat.getLayoutManager());
                int pos = layoutManager.findLastCompletelyVisibleItemPosition();
                int numItems = binding.recyclerViewChat.getAdapter().getItemCount();
                return (pos >= numItems - 1);
            }
        }catch (Exception e)
        {

        }
        return false;
    }

}