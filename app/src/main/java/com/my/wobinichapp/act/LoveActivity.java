package com.my.wobinichapp.act;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.my.wobinichapp.Preference;
import com.my.wobinichapp.R;
import com.my.wobinichapp.adapter.GerFolderAdapter;
import com.my.wobinichapp.databinding.ActivityLoveBinding;
import com.my.wobinichapp.model.CreateFolderModel;
import com.my.wobinichapp.model.GetFolderModel;
import com.my.wobinichapp.utils.RetrofitClients;
import com.my.wobinichapp.utils.SessionManager;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoveActivity extends AppCompatActivity {

    ActivityLoveBinding binding;

    boolean isImagShow=false;
    private View promptsView;
    private AlertDialog alertDialog;

    private SessionManager sessionManager;

    GerFolderAdapter mAdapter;
    private ArrayList<GetFolderModel.Result> modelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_love);

        sessionManager = new SessionManager(LoveActivity.this);

        binding.imgArrow.setOnClickListener(v -> {

            if(isImagShow)
            {
                binding.LLimg.setVisibility(View.VISIBLE);

                isImagShow =false;

            }else
            {
                binding.LLimg.setVisibility(View.GONE);

                isImagShow =true;
            }

        });

        binding.RRMenu.setOnClickListener(v -> {

            PopupMenu popup = new PopupMenu(LoveActivity.this, binding.RRMenu);
            //Inflating the Popup using xml file
            popup.getMenuInflater().inflate(R.menu.poupup_menu_love, popup.getMenu());
            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    int id=item.getItemId();
                    if(id==R.id.Add)
                    {
                        AlertCreateFolder();
                    }
                    return true;
                }
            });

            popup.show();

        });

        binding.imgClick.setOnClickListener(v -> {
            startActivity(new Intent(LoveActivity.this,WobinichDetailsActivity.class));

        });

        binding.img3.setOnClickListener(v -> {
            startActivity(new Intent(LoveActivity.this,WobinichDetailsActivity.class));

        });

        binding.img4.setOnClickListener(v -> {
            startActivity(new Intent(LoveActivity.this,WobinichDetailsActivity.class));

        });


        if (sessionManager.isNetworkAvailable()) {
            binding.progressBar.setVisibility(View.VISIBLE);
            getFolderMthod();
        }else {
            Toast.makeText(LoveActivity.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
        }
    }

    private void setAdapter(ArrayList<GetFolderModel.Result> modelList) {

        mAdapter = new GerFolderAdapter(LoveActivity.this, modelList);
        binding.recyclerGrp.setHasFixedSize(true);
        // use a linear layout manager
        binding.recyclerGrp.setLayoutManager(new GridLayoutManager(this,3));
        binding.recyclerGrp.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new GerFolderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, GetFolderModel.Result model){


            }
        });
    }

    private void AlertCreateFolder() {

        LayoutInflater li;
        TextView txtSave;
        TextView txtCancel;
        EditText edtName;
        AlertDialog.Builder alertDialogBuilder;
        li = LayoutInflater.from(LoveActivity.this);
        promptsView = li.inflate(R.layout.alert_folder_name, null);
        txtSave = (TextView) promptsView.findViewById(R.id.txtSave);
        txtCancel = (TextView) promptsView.findViewById(R.id.txtCancel);
        edtName = (EditText) promptsView.findViewById(R.id.edtName);
        alertDialogBuilder = new AlertDialog.Builder(LoveActivity.this);
        alertDialogBuilder.setView(promptsView);

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String FolderName=edtName.getText().toString();
                if(FolderName.equalsIgnoreCase(""))
                {
                    Toast.makeText(LoveActivity.this, "Please Enter Folder Name.", Toast.LENGTH_SHORT).show();

                }else
                {
                   if (sessionManager.isNetworkAvailable()) {
                        binding.progressBar.setVisibility(View.VISIBLE);
                        insertChatMethod(FolderName);
                    }else {
                        Toast.makeText(LoveActivity.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
                    }

                    alertDialog.dismiss();
                }
            }
        });

        txtCancel.setOnClickListener(v -> {
            alertDialog.dismiss();
        });

        alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    public void insertChatMethod(String FolderName)
    {
        String Type="love";

        String User_id= Preference.get(LoveActivity.this,Preference.KEY_USER_ID);

        Call<CreateFolderModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .create_folder(User_id,FolderName,Type,"Folder.png");
        call.enqueue(new Callback<CreateFolderModel>() {
            @Override
            public void onResponse(Call<CreateFolderModel> call, Response<CreateFolderModel> response) {
                try {
                    binding.progressBar.setVisibility(View.GONE);

                    CreateFolderModel myclass = response.body();

                    String status = myclass.getStatus();
                    String message = myclass.getMessage();

                    if (status.equalsIgnoreCase("1")) {

                        Toast.makeText(LoveActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                        binding.progressBar.setVisibility(View.GONE);
                        getFolderMthod();

                    } else {

                        binding.progressBar.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<CreateFolderModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void getFolderMthod() {

        String Type="love";

        String User_id= Preference.get(LoveActivity.this,Preference.KEY_USER_ID);

        Call<ResponseBody> call = RetrofitClients
                .getInstance()
                .getApi()
                .get_folder(User_id,Type);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
              /*  try {

                    binding.progressBar.setVisibility(View.GONE);

                    GetFolderModel myclass = response.body();

                    String status = String.valueOf(myclass.getStatus());
                    String message = myclass.getMessage();

                    if (status.equalsIgnoreCase("1")) {

                        modelList = (ArrayList<GetFolderModel.Result>) myclass.getResult();

                        if(modelList !=null)
                        {
                            setAdapter(modelList);
                        }

                    } else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
                binding.progressBar.setVisibility(View.GONE);
                try {
                    if (response.isSuccessful()) {
                        String responseData = response.body() != null ? response.body().string() : "";
                        JSONObject object = new JSONObject(responseData);
                        Log.e("Get All Response==", responseData);
                        if (object.getString("status").equalsIgnoreCase("1")) {
                          //  binding.ivFolder.setVisibility(View.VISIBLE);
                          //  binding.tvFolder.setVisibility(View.VISIBLE);
                          //  binding.llFolder.setVisibility(View.VISIBLE);
                            GetFolderModel model = new Gson().fromJson(responseData,GetFolderModel.class);
                            modelList.clear();
                            modelList.addAll(model.getResult());
                            if(modelList !=null)
                            {
                                setAdapter(modelList);
                            }

                        } else {
                            // Toast.makeText(YesActivity.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                          //  binding.ivFolder.setVisibility(View.GONE);
                          //  binding.tvFolder.setVisibility(View.GONE);
                          //  binding.llFolder.setVisibility(View.GONE);
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