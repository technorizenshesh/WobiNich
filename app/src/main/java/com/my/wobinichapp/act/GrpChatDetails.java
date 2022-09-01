package com.my.wobinichapp.act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.my.wobinichapp.Preference;
import com.my.wobinichapp.R;
import com.my.wobinichapp.adapter.Chat_Adapter;
import com.my.wobinichapp.adapter.GrpListAdapter;
import com.my.wobinichapp.adapter.GrpListDetailsAdapter;
import com.my.wobinichapp.adapter.GrpMemberAdapter;
import com.my.wobinichapp.databinding.ActivityGrpChatDetailsBinding;
import com.my.wobinichapp.model.ChallengeModel;
import com.my.wobinichapp.model.GetChatData;
import com.my.wobinichapp.model.GetGrpModel;
import com.my.wobinichapp.model.GetUserModel;
import com.my.wobinichapp.model.GrpChatModel;
import com.my.wobinichapp.model.HomModel;
import com.my.wobinichapp.model.InsertGrpModel;
import com.my.wobinichapp.model.LeaveModel;
import com.my.wobinichapp.utils.RetrofitClients;
import com.my.wobinichapp.utils.SessionManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GrpChatDetails extends AppCompatActivity {

    ActivityGrpChatDetailsBinding binding;
    GrpMemberAdapter mAdapter;
    Chat_Adapter mAdapter1;
    private ArrayList<GetUserModel.Result> modelList = new ArrayList<>();
    private ArrayList<GrpChatModel.Result> modelList_chat = new ArrayList<>();
    String GrpName="";
    String GrpImage="";
    private SessionManager sessionManager;
    private Timer timer;
    private LocalBroadcastManager lbm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_grp_chat_details);

        sessionManager = new SessionManager(GrpChatDetails.this);

         GrpName=Preference.get(GrpChatDetails.this,Preference.KEY_grp_Name);
         GrpImage=Preference.get(GrpChatDetails.this,Preference.KEY_grp_IMage);

        binding.RRback.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.ImgAddPost.setOnClickListener(v -> {
            startActivity(new Intent(GrpChatDetails.this,AddPost.class));
        });

      /*  binding.RRUser.setOnClickListener(v -> {
           startActivity(new Intent(GrpChatDetails.this,AllGrpUserList.class));
        }); */

        binding.ImgGrp.setOnClickListener(v -> {
          // startActivity(new Intent(GrpChatDetails.this,GrpViewAllPost.class));
           startActivity(new Intent(GrpChatDetails.this,AllPostView.class));
        });

        binding.TxtGrpName.setOnClickListener(v -> {
           startActivity(new Intent(GrpChatDetails.this,AllGrpUserList.class));
        });

        binding.RRAddIcon.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(GrpChatDetails.this, binding.RRAddIcon);
            //Inflating the Popup using xml file
            popup.getMenuInflater().inflate(R.menu.poupup_menu_details, popup.getMenu());
            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    int id=item.getItemId();
                    if(id==R.id.AddNewMeber)
                    {
                        startActivity(new Intent(GrpChatDetails.this,AddNewMember.class));

                    }else if(id==R.id.ChangeGrpPicture)
                    {
                        startActivity(new Intent(GrpChatDetails.this,ChangeGrpPicture.class));

                    }else if(id==R.id.ChangeName)
                    {
                        startActivity(new Intent(GrpChatDetails.this,ChangeGrpName.class));

                    }else if(id==R.id.LeaveGroup)
                    {
                        if (sessionManager.isNetworkAvailable()) {
                            binding.progressBar.setVisibility(View.VISIBLE);
                            leaveGroupMthod();
                        }else {
                            Toast.makeText(GrpChatDetails.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
                        }

                    }else if(id==R.id.AddPost)
                    {
                        startActivity(new Intent(GrpChatDetails.this,AddPost.class));

                    }else if(id==R.id.ViewAll)
                    {
                        startActivity(new Intent(GrpChatDetails.this,AllPostView.class));
                    }
                    return true;
                }
            });
            popup.show();
        });

        binding.LLSend.setOnClickListener(v -> {
            if(!binding.edtInsert.getText().toString().equals(""))
            {
                if (sessionManager.isNetworkAvailable()) {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    insertChatMethod();
                }else {
                    Toast.makeText(GrpChatDetails.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
                }
            }
            else Toast.makeText(GrpChatDetails.this, R.string.please_enter_message, Toast.LENGTH_SHORT).show();

        });

        if (sessionManager.isNetworkAvailable()) {
            binding.progressBar.setVisibility(View.VISIBLE);
            getGrpMemberMthod();
        }else {
            Toast.makeText(GrpChatDetails.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
        }

        lbm = LocalBroadcastManager.getInstance(this);
        lbm.registerReceiver(receiver, new IntentFilter("filter_string"));
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        binding.TxtGrpName.setText(GrpName);

        Glide.with(this)
                .load(GrpImage)
                .centerCrop()
                .circleCrop()
                .into(binding.ImgGrp);
     /*
        if(!GrpImage.equalsIgnoreCase(""))
        {
            Glide.with(GrpChatDetails.this).load(GrpImage).circleCrop().placeholder(R.drawable.defaultuser).error(R.drawable.defaultuser).into(binding.ImgGrp);
        }
*/
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (isLastVisible()) {
                    getGrpMemberMthod();
                }
            }
        }, 0, 5000);
    }

    private void setAdapter(ArrayList<GetUserModel.Result> modelList) {

        mAdapter = new GrpMemberAdapter(GrpChatDetails.this, modelList);
        binding.recyclerGrp.setHasFixedSize(true);
        // use a linear layout manager
        binding.recyclerGrp.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        binding.recyclerGrp.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new GrpMemberAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, GetUserModel.Result model){
                if(Preference.get(GrpChatDetails.this,Preference.KEY_USER_ID).equalsIgnoreCase(model.getId()))
                {
                    Toast.makeText(GrpChatDetails.this, "My Profile", Toast.LENGTH_SHORT).show();
                }else
                {
                    startActivity(new Intent(GrpChatDetails.this,PersonalChat.class).putExtra("MemberName",model.getName()).putExtra("MemberImage",model.getImage()).putExtra("ReceiverId",model.getId()));
                }
            }
        });
    }

  private void setAdapterChat(ArrayList<GrpChatModel.Result> modelList_chat) {

        mAdapter1 = new Chat_Adapter(GrpChatDetails.this,modelList_chat);
        binding.recyclerViewChat.setHasFixedSize(true);
        // use a linear layout manager
        binding.recyclerViewChat.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewChat.setAdapter(mAdapter1);

        mAdapter1.SetOnItemClickListener(new Chat_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, GrpChatModel.Result model){

            }
        });
    }


    public void getGrpMemberMthod() {

        String group_id= Preference.get(GrpChatDetails.this,Preference.KEY_grp_id);
        String USER_ID= Preference.get(GrpChatDetails.this,Preference.KEY_USER_ID);

        Call<ResponseBody> call = RetrofitClients.getInstance().getApi().get_group_member(group_id,USER_ID);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                binding.progressBar.setVisibility(View.GONE);
                try {

                    if (response.isSuccessful()) {
                        String responseData = response.body() != null ? response.body().string() : "";
                        JSONObject object = new JSONObject(responseData);
                        Log.e("Get All Grp Member Response==", responseData);
                        if (object.getString("status").equalsIgnoreCase("1")) {
                            GetUserModel model = new Gson().fromJson(responseData, GetUserModel.class);
                            binding.llOneLine.setVisibility(View.VISIBLE);
                            binding.relativeSearchBtn.setVisibility(View.VISIBLE);
                            modelList.clear();
                            modelList.addAll(model.getResult());
                            if (modelList != null) {
                                setAdapter(modelList);
                            }
                            binding.llOneLine.setVisibility(View.VISIBLE);
                            getChatAllpMethod();

                        } else {
                            binding.llOneLine.setVisibility(View.GONE);
                            binding.relativeSearchBtn.setVisibility(View.GONE);
                            Toast.makeText(GrpChatDetails.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                binding.llOneLine.setVisibility(View.GONE);
                binding.relativeSearchBtn.setVisibility(View.GONE);
                Toast.makeText(GrpChatDetails.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void getChatAllpMethod()
    {
        String group_id= Preference.get(GrpChatDetails.this,Preference.KEY_grp_id);
        String user_id= Preference.get(GrpChatDetails.this,Preference.KEY_USER_ID);

        Call<ResponseBody> call = RetrofitClients.getInstance().getApi().get_group_chat(group_id,user_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                binding.progressBar.setVisibility(View.GONE);
                try {
                    if (response.isSuccessful()) {
                    String responseData = response.body() != null ? response.body().string() : "";
                    JSONObject object = new JSONObject(responseData);
                    Log.e("Get All Grp Member Response==", responseData);
                    if (object.getString("status").equalsIgnoreCase("1")) {
                        GrpChatModel model = new Gson().fromJson(responseData, GrpChatModel.class);
                        binding.tvNotFound.setVisibility(View.GONE);
                        modelList_chat.clear();
                        modelList_chat.addAll(model.getResult());
                        setAdapterChat(modelList_chat);
                        binding.recyclerViewChat.scrollToPosition(modelList_chat.size() - 1);
                    } else {
                        binding.tvNotFound.setVisibility(View.VISIBLE);
                        modelList_chat.clear();
                        setAdapterChat(modelList_chat);

                    }
                }
            }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }


    public void insertChatMethod()
    {
        String Sender_id= Preference.get(GrpChatDetails.this,Preference.KEY_USER_ID);
        String group_id= Preference.get(GrpChatDetails.this,Preference.KEY_grp_id);

        Call<InsertGrpModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .insert_group_chat(Sender_id,group_id,binding.edtInsert.getText().toString());
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

                        Toast.makeText(GrpChatDetails.this, ""+message, Toast.LENGTH_SHORT).show();
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

    public void leaveGroupMthod(){

        String group_id= Preference.get(GrpChatDetails.this,Preference.KEY_grp_id);
        String Member_id= Preference.get(GrpChatDetails.this,Preference.KEY_USER_ID);

        Call<LeaveModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .leave_group_member(group_id,Member_id);
        call.enqueue(new Callback<LeaveModel>() {
            @Override
            public void onResponse(Call<LeaveModel> call, Response<LeaveModel> response) {
                try {

                    binding.progressBar.setVisibility(View.GONE);

                    LeaveModel myclass = response.body();

                    String status = String.valueOf(myclass.getStatus());
                    String message = myclass.getMessage();

                    if (status.equalsIgnoreCase("1")) {

                        Toast.makeText(GrpChatDetails.this, message, Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(GrpChatDetails.this,HomeActivity.class));
                               finish();

                    }else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<LeaveModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }

    private boolean isLastVisible() {
        if (modelList_chat != null) {
            try {
                LinearLayoutManager layoutManager = ((LinearLayoutManager) binding.recyclerViewChat.getLayoutManager());
                int pos = layoutManager.findLastCompletelyVisibleItemPosition();
                int numItems = binding.recyclerViewChat.getAdapter().getItemCount();
                return (pos >= numItems - 1);
            }catch (Exception e)
            {}
        }
        return false;
    }


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null)
            {
                String str = intent.getStringExtra("key");
                getGrpMemberMthod();
            }
        }
    };



}