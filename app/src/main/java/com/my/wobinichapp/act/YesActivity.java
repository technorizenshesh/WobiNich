package com.my.wobinichapp.act;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.my.wobinichapp.Preference;
import com.my.wobinichapp.R;
import com.my.wobinichapp.adapter.GerFolderAdapter;
import com.my.wobinichapp.adapter.GrpMemberAdapter;
import com.my.wobinichapp.adapter.YesBluePostAdapter;
import com.my.wobinichapp.adapter.YesGreenPostAdapter;
import com.my.wobinichapp.adapter.YesMyPostAdapter;
import com.my.wobinichapp.adapter.YesPurplePostAdapter;
import com.my.wobinichapp.databinding.ActivityYesBinding;
import com.my.wobinichapp.model.CreateFolderModel;
import com.my.wobinichapp.model.GetFolderModel;
import com.my.wobinichapp.model.GetPostBluVoilet;
import com.my.wobinichapp.model.GetPostModel;
import com.my.wobinichapp.model.GetUserModel;
import com.my.wobinichapp.model.InsertGrpModel;
import com.my.wobinichapp.model.PostSeenModel;
import com.my.wobinichapp.utils.RealPathUtil;
import com.my.wobinichapp.utils.RetrofitClients;
import com.my.wobinichapp.utils.SessionManager;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YesActivity extends AppCompatActivity {

    ActivityYesBinding binding;
    boolean idImg = true;
    boolean idImgblue = true;
    boolean idImgpurple = true;
    boolean idImg1 = true;
    boolean idImg2 = true;

    private View promptsView;
    private AlertDialog alertDialog;

    GerFolderAdapter mAdapter;
    YesMyPostAdapter mAdapter1;
    YesBluePostAdapter mAdapterblue;
    YesPurplePostAdapter mAdapterPurple;
    YesGreenPostAdapter mAdapterGreen;
    private ArrayList<GetFolderModel.Result> modelList = new ArrayList<>();
    private ArrayList<GetPostModel.Result> modelList1 = new ArrayList<>();

    ArrayList<GetPostBluVoilet.Bluedatum> modelListBlue=new ArrayList<>();
    ArrayList<GetPostBluVoilet.Voiletdatum> modelListPurple=new ArrayList<>();
    ArrayList<GetPostBluVoilet.Greendatum> modelListGreen=new ArrayList<>();


    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_yes);

        sessionManager = new SessionManager(YesActivity.this);

       /* String PostCount = Preference.get(YesActivity.this,Preference.key_Post_count);
        if(PostCount.equalsIgnoreCase("0"))
        {
            binding.txtCount.setVisibility(View.GONE);
        }else
        {
            binding.txtCount.setVisibility(View.VISIBLE);
            binding.txtCount.setText(PostCount);
        }
*/
        binding.RRBack.setOnClickListener(v ->
        {
            onBackPressed();
        });


        binding.RRAdd.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(YesActivity.this, binding.RRAdd);
            //Inflating the Popup using xml file
            popup.getMenuInflater().inflate(R.menu.poupup_menu_yes, popup.getMenu());
            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    int id = item.getItemId();
                    if (id == R.id.CreateFolder) {
                        AlertCreateFolder();
                    }
                    return true;
                }
            });
            popup.show();
        });


        binding.img2.setOnClickListener(v -> {
            if(modelListGreen.size()>1) {
                if (idImg) {
                    binding.lloneGreen.setVisibility(View.GONE);
                    binding.recyclerGreen.setVisibility(View.VISIBLE);
                    idImg = false;
                } else {
                    binding.lloneGreen.setVisibility(View.VISIBLE);
                    binding.recyclerGreen.setVisibility(View.GONE);
                    idImg = true;
                }
            }
        });

        binding.imgblue1.setOnClickListener(v -> {
            if(modelListBlue.size()>1) {
                if (idImgblue) {
                    binding.lloneblue.setVisibility(View.GONE);
                    binding.recyclerblue.setVisibility(View.VISIBLE);
                    idImgblue = false;
                } else {
                    binding.lloneblue.setVisibility(View.VISIBLE);
                    binding.recyclerblue.setVisibility(View.GONE);
                    idImgblue = true;
                }
            }
        });

        binding.imgpurple.setOnClickListener(v -> {
          if(modelListPurple.size()>1) {
              if (idImgpurple) {
                  binding.llonepurple1.setVisibility(View.GONE);
                  binding.recyclerPurple.setVisibility(View.VISIBLE);
                  idImgpurple = false;
              } else {
                  binding.llonepurple1.setVisibility(View.VISIBLE);
                  binding.recyclerPurple.setVisibility(View.GONE);
                  idImgpurple = true;
              }
          }
        });

        binding.img4.setOnClickListener(v -> {
            if (idImg1) {
                binding.LLMainFileBlue.setVisibility(View.VISIBLE);
                idImg1 = false;
            }else {
                binding.LLMainFileBlue.setVisibility(View.GONE);
                idImg1 = true;
            }
        });





     /*   binding.img3.setOnClickListener(v -> {
            if (idImg2) {
                binding.LLMainFilePurple.setVisibility(View.VISIBLE);
                idImg2 = false;
            } else {
                binding.LLMainFilePurple.setVisibility(View.GONE);
                idImg2 = true;
            }
        });*/

        binding.lloneGreen.setOnClickListener(v -> {
                startActivity(new Intent(YesActivity.this, WobinichDetailsActivity.class)
                .putExtra("id",modelListGreen.get(0).getId()));
            });

        if (sessionManager.isNetworkAvailable()) {
            binding.progressBar.setVisibility(View.VISIBLE);
            getFolderMthod();
            get_post_data();
            post_seen();
        }   else {
            Toast.makeText(YesActivity.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
        }
    }


/*
    private void setAdapter1(ArrayList<GetPostModel.Result> modelList1) {

        mAdapter1 = new YesMyPostAdapter(YesActivity.this, modelList1);
        binding.recyclerMyPost.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(YesActivity.this);

        binding.recyclerMyPost.setLayoutManager(new GridLayoutManager(this,3));

     //   binding.recyclerMyPost.setLayoutManager(linearLayoutManager);
        binding.recyclerMyPost.setAdapter(mAdapter1);

        mAdapter1.SetOnItemClickListener(new YesMyPostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, GetPostModel.Result model){

                startActivity(new Intent(YesActivity.this, WobinichDetailsActivity.class));

            }
        });
    }
*/


    private void setAdapterBlue(ArrayList<GetPostBluVoilet.Bluedatum> modelList1) {

        mAdapterblue = new YesBluePostAdapter(YesActivity.this, modelList1);
        binding.recyclerblue.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(YesActivity.this);
        binding.recyclerblue.setLayoutManager(linearLayoutManager);
        binding.recyclerblue.setAdapter(mAdapterblue);

        mAdapterblue.SetOnItemClickListener(new YesBluePostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, GetPostBluVoilet.Bluedatum model){
             //   startActivity(new Intent(YesActivity.this, WobinichDetailsActivity.class)
             //   .putExtra("id",modelList1.get(position).getId()));
            }
        });
    }

    private void setAdapterPurple(ArrayList<GetPostBluVoilet.Voiletdatum> modelList1) {

        mAdapterPurple = new YesPurplePostAdapter(YesActivity.this, modelList1);
        binding.recyclerPurple.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(YesActivity.this);
        binding.recyclerPurple.setLayoutManager(linearLayoutManager);
        binding.recyclerPurple.setAdapter(mAdapterPurple);

        mAdapterPurple.SetOnItemClickListener(new YesPurplePostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, GetPostBluVoilet.Voiletdatum model){
               // startActivity(new Intent(YesActivity.this, WobinichDetailsActivity.class));
               // startActivity(new Intent(YesActivity.this, WobiNichCommentActivity.class));
                startActivity(new Intent(YesActivity.this, GroupImage.class));
            }
        });
    }


    private void setAdapterGreen(ArrayList<GetPostBluVoilet.Greendatum> modelList1) {

        mAdapterGreen = new YesGreenPostAdapter(YesActivity.this, modelList1);
        binding.recyclerGreen.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(YesActivity.this);
        binding.recyclerGreen.setLayoutManager(linearLayoutManager);
        binding.recyclerGreen.setAdapter(mAdapterGreen);

        mAdapterGreen.SetOnItemClickListener(new YesGreenPostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick( int position, GetPostBluVoilet.Greendatum model) {
                // startActivity(new Intent(YesActivity.this, WobinichDetailsActivity.class));
                // startActivity(new Intent(YesActivity.this, WobiNichCommentActivity.class));
                startActivity(new Intent(YesActivity.this, WobinichDetailsActivity.class)
                        .putExtra("id",model.getId()));
                Log.e("post_id",model.getId());

            }


        });
    }



       private void setAdapter(ArrayList<GetFolderModel.Result> modelList) {

        mAdapter = new GerFolderAdapter(YesActivity.this, modelList);
        binding.recyclerGrp.setHasFixedSize(true);
        // use a linear layout manager
        // binding.recyclerGrp.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
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
        EditText edtName;
        LinearLayout ll_image;
        AlertDialog.Builder alertDialogBuilder;
        li = LayoutInflater.from(YesActivity.this);
        promptsView = li.inflate(R.layout.alert_folder_name, null);
        txtSave = (TextView) promptsView.findViewById(R.id.txtSave);
         edtName = (EditText) promptsView.findViewById(R.id.edtName);
         ll_image = (LinearLayout) promptsView.findViewById(R.id.ll_image);
        alertDialogBuilder = new AlertDialog.Builder(YesActivity.this);
        alertDialogBuilder.setView(promptsView);

         ll_image.setOnClickListener(v -> {
             Dexter.withActivity(YesActivity.this)
                     .withPermissions(Manifest.permission.CAMERA,
                             Manifest.permission.READ_EXTERNAL_STORAGE,
                             Manifest.permission.WRITE_EXTERNAL_STORAGE)
                     .withListener(new MultiplePermissionsListener() {
                         @Override
                         public void onPermissionsChecked(MultiplePermissionsReport report) {
                             if (report.areAllPermissionsGranted()) {
                                 showPictureDialog();
                             /*  Intent intent = CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).getIntent(AddPost.this);
                               startActivityForResult(intent, 1);*/
                             } else {
                                 showSettingDialogue();
                             }
                         }

                         @Override
                         public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                             token.continuePermissionRequest();
                         }
                     }).check();
         });

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String FolderName=edtName.getText().toString();
                if(FolderName.equalsIgnoreCase(""))
                {
                    Toast.makeText(YesActivity.this, "Please Enter Folder Name.", Toast.LENGTH_SHORT).show();

                }else
                {
                    if (sessionManager.isNetworkAvailable()) {
                        binding.progressBar.setVisibility(View.VISIBLE);
                        insertChatMethod(FolderName);
                    }else {
                        Toast.makeText(YesActivity.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
                    }
                    alertDialog.dismiss();
                }

            }
        });

        alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }


    public void insertChatMethod(String FolderName)
    {
        String Type="yes";

        String User_id= Preference.get(YesActivity.this,Preference.KEY_USER_ID);

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

                        Toast.makeText(YesActivity.this, ""+message, Toast.LENGTH_SHORT).show();
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
        String Type="yes";

        String User_id= Preference.get(YesActivity.this,Preference.KEY_USER_ID);

        Call<ResponseBody> call = RetrofitClients
                .getInstance()
                .getApi()
                .get_folder(User_id,Type);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                binding.progressBar.setVisibility(View.GONE);
                try {
                    if (response.isSuccessful()) {
                        String responseData = response.body() != null ? response.body().string() : "";
                        JSONObject object = new JSONObject(responseData);
                        Log.e("Get All Response==", responseData);
                        if (object.getString("status").equalsIgnoreCase("1")) {
                            binding.ivFolder.setVisibility(View.VISIBLE);
                            binding.tvFolder.setVisibility(View.VISIBLE);
                            binding.llFolder.setVisibility(View.VISIBLE);
                            GetFolderModel model = new Gson().fromJson(responseData,GetFolderModel.class);
                            modelList.clear();
                            modelList.addAll(model.getResult());
                            if(modelList !=null)
                            {
                                setAdapter(modelList);
                            }

                        } else {
                           // Toast.makeText(YesActivity.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                            binding.ivFolder.setVisibility(View.GONE);
                            binding.tvFolder.setVisibility(View.GONE);
                            binding.llFolder.setVisibility(View.GONE);
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


    public void getPostMthod() {

        String User_id= Preference.get(YesActivity.this,Preference.KEY_USER_ID);
        //String User_id="14";
                Call<GetPostModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .get_post(User_id);
        call.enqueue(new Callback<GetPostModel>() {
            @Override
            public void onResponse(Call<GetPostModel> call, Response<GetPostModel> response) {
                try {

                    binding.progressBar.setVisibility(View.GONE);

                    GetPostModel myclass = response.body();

                    String status = String.valueOf(myclass.getStatus());
                    String message = myclass.getMessage();

                    if (status.equalsIgnoreCase("1")) {

                        modelList1 = (ArrayList<GetPostModel.Result>) myclass.getResult();

                        //setAdapter1(modelList1);

                    } else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<GetPostModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void get_post_data() {

       String User_id= Preference.get(YesActivity.this,Preference.KEY_USER_ID);
       Log.e("user_id",User_id+"");
       
        Call<ResponseBody> call = RetrofitClients
                .getInstance()
                .getApi()
                .get_post_data(User_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                binding.progressBar.setVisibility(View.GONE);

                try {
                    if (response.isSuccessful()) {
                        String responseData = response.body() != null ? response.body().string() : "";
                        JSONObject object = new JSONObject(responseData);
                        Log.e("GetMy Post Response==", responseData);
                        if (object.getString("status").equalsIgnoreCase("1")) {
                            GetPostBluVoilet model = new Gson().fromJson(responseData,GetPostBluVoilet.class);
                            modelListBlue.clear();
                            modelListPurple.clear();
                            modelListGreen.clear();
                            modelListBlue.addAll(model.getBluedata());
                            modelListPurple.addAll(model.getVoiletdata());
                            modelListGreen.addAll(model.getGreendata());

                            if (modelListBlue.size()!=0) {
                                binding.lloneblue.setVisibility(View.VISIBLE);
                                binding.tvBlueNotFound.setVisibility(View.GONE);
                                Glide.with(YesActivity.this).load(modelListBlue.get(0).getImage()).placeholder(R.drawable.frame).error(R.drawable.frame).into(binding.imgBlue);
                                binding.tvUserName.setText(modelListBlue.get(0).getUserName());
                                binding.tvComment.setText(modelListBlue.get(0).getMaps());
                                binding.tvDate.setText(modelListBlue.get(0).getDateTime());
                                setAdapterBlue(modelListBlue);
                            } else {
                                binding.tvBlueNotFound.setVisibility(View.VISIBLE);
                                binding.lloneblue.setVisibility(View.GONE);
                            }

                            if (modelListPurple.size()!=0) {
                                binding.llonepurple1.setVisibility(View.VISIBLE);
                                binding.tvVioletNotFound.setVisibility(View.GONE);
                                binding.txtGrpName.setText(modelListPurple.get(0).getGroupName());
                                Glide.with(YesActivity.this).load(modelListPurple.get(0).getImage()).placeholder(R.drawable.frame).error(R.drawable.frame).into(binding.imgpurple11);
                                setAdapterPurple(modelListPurple);
                            } else {
                                binding.tvVioletNotFound.setVisibility(View.VISIBLE);
                                binding.llonepurple1.setVisibility(View.GONE);
                            }

                            if (modelListGreen.size()!=0) {
                                binding.lloneGreen.setVisibility(View.VISIBLE);
                                binding.tvGreenNotFound.setVisibility(View.GONE);
                                Glide.with(YesActivity.this).load(modelListGreen.get(0).getImage()).placeholder(R.drawable.frame).error(R.drawable.frame).into(binding.imgGreen);
                                binding.tvGreenName.setText(modelListGreen.get(0).getGroupName());
                                binding.tvGreenComment.setText(modelListGreen.get(0).getMaps());
                                binding.tvGreenDate.setText(modelListGreen.get(0).getDateTime());
                                setAdapterGreen(modelListGreen);
                            } else {
                                binding.tvGreenNotFound.setVisibility(View.VISIBLE);
                                binding.lloneGreen.setVisibility(View.GONE);
                            }


                        }
                        else {
                            binding.tvBlueNotFound.setVisibility(View.VISIBLE);
                            binding.lloneblue.setVisibility(View.GONE);
                            binding.tvVioletNotFound.setVisibility(View.VISIBLE);
                            binding.llonepurple1.setVisibility(View.GONE);
                            binding.tvGreenNotFound.setVisibility(View.VISIBLE);
                            binding.lloneGreen.setVisibility(View.GONE);
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

    public void post_seen() {

       String User_id= Preference.get(YesActivity.this,Preference.KEY_USER_ID);

        Call<PostSeenModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .post_seen(User_id);
        call.enqueue(new Callback<PostSeenModel>() {
            @Override
            public void onResponse(Call<PostSeenModel> call, Response<PostSeenModel> response) {
                try {

                    binding.progressBar.setVisibility(View.GONE);

                    PostSeenModel myclass = response.body();

                    String status = String.valueOf(myclass.getStatus());
                    String message = myclass.getMessage();

                    if (status.equalsIgnoreCase("1")) {

                        Log.e("",""+"");


                    } else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<PostSeenModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void AlertDeleteFolder() {

        LayoutInflater li;
        TextView txtSave;
        EditText edtName;
        AlertDialog.Builder alertDialogBuilder;
        li = LayoutInflater.from(YesActivity.this);
        promptsView = li.inflate(R.layout.alert_folder_name, null);
        txtSave = (TextView) promptsView.findViewById(R.id.txtSave);
        edtName = (EditText) promptsView.findViewById(R.id.edtName);
        alertDialogBuilder = new AlertDialog.Builder(YesActivity.this);
        alertDialogBuilder.setView(promptsView);

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String FolderName=edtName.getText().toString();
                if(FolderName.equalsIgnoreCase(""))
                {
                    Toast.makeText(YesActivity.this, "Please Enter Folder Name.", Toast.LENGTH_SHORT).show();

                }else
                {
                    if (sessionManager.isNetworkAvailable()) {
                        binding.progressBar.setVisibility(View.VISIBLE);
                        insertChatMethod(FolderName);
                    }else {
                        Toast.makeText(YesActivity.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
                    }
                    alertDialog.dismiss();
                }
            }
        });

        alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle(R.string.select_action);
        String[] pictureDialogItems = {
                getString(R.string.select_photo_from_gallery),
                getString(R.string.capture_photo_from_camera)};
        pictureDialog.setItems(pictureDialogItems,
                (dialog, which) -> {
                    switch (which) {
                        case 0:
                            choosePhotoFromGallary();
                            break;
                        case 1:
                            takePhotoFromCamera();
                            break;
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {

        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 1);
    }

    private void takePhotoFromCamera() {

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null)
            startActivityForResult(cameraIntent, 0);

    }

    public Bitmap BITMAP_RESIZER(Bitmap bitmap, int newWidth, int newHeight) {
        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);

        float ratioX = newWidth / (float) bitmap.getWidth();
        float ratioY = newHeight / (float) bitmap.getHeight();
        float middleX = newWidth / 2.0f;
        float middleY = newHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, middleX - bitmap.getWidth() / 2, middleY - bitmap.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

        return scaledBitmap;
    }

    private void showSettingDialogue() {

        AlertDialog.Builder builder = new AlertDialog.Builder(YesActivity.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                openSetting();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.show();

    }

    private void openSetting() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", this.getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    try {
                        if (data != null) {
                           // binding.imgFully.setVisibility(View.VISIBLE);
                          //  binding.llImage.setVisibility(View.GONE);
                            Bundle extras = data.getExtras();
                            Bitmap bitmapNew = (Bitmap) extras.get("data");
                            Bitmap imageBitmap = BITMAP_RESIZER(bitmapNew, bitmapNew.getWidth(), bitmapNew.getHeight());
                         //   isImage=true;
                           // Glide.with(YesActivity.this).load(imageBitmap).into(binding.imgFully);
//                            binding.image.setVisibility(View.VISIBLE);
                            Uri tempUri = getImageUri(YesActivity.this, imageBitmap);
                         //   isProfileImage=true;
                            String imag = RealPathUtil.getRealPath(YesActivity.this, tempUri);
                          //  filePaths = imag;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    try {
                        Uri selectedImage = data.getData();
                   //     binding.llImage.setVisibility(View.GONE);
                    //    binding.imgFully.setVisibility(View.VISIBLE);
                     //   isImage=true;
                        Bitmap bitmapNew = MediaStore.Images.Media.getBitmap(YesActivity.this.getContentResolver(), selectedImage);
                        Bitmap bitmap = BITMAP_RESIZER(bitmapNew, bitmapNew.getWidth(), bitmapNew.getHeight());
                  //      Glide.with(AddPost.this).load(bitmap).into(binding.imgFully);
                  //      isProfileImage=true;
                        Uri tempUri = getImageUri(YesActivity.this, bitmap);
                        String imag = RealPathUtil.getRealPath(YesActivity.this, tempUri);
                    //    filePaths = imag;

                    } catch (IOException e) {
                        Log.i("TAG", "Some exception " + e);
                    }
                }

                break;
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title_" + System.currentTimeMillis(), null);
        return Uri.parse(path);
    }

    public void dialog() {
        if (sessionManager.isNetworkAvailable()) {
            binding.progressBar.setVisibility(View.VISIBLE);
            getFolderMthod();
        }   else {
            Toast.makeText(YesActivity.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
        }
    }
}