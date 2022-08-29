package com.my.wobinichapp.act;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.my.wobinichapp.Preference;
import com.my.wobinichapp.R;
import com.my.wobinichapp.databinding.ActivityCreateGrpNextBinding;
import com.my.wobinichapp.model.AddGrpModel;
import com.my.wobinichapp.model.SignUpModel;
import com.my.wobinichapp.utils.FileUtil;
import com.my.wobinichapp.utils.RealPathUtil;
import com.my.wobinichapp.utils.RetrofitClients;
import com.my.wobinichapp.utils.SessionManager;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateGrpNext extends AppCompatActivity {

    ActivityCreateGrpNextBinding binding;
    private Bitmap bitmap;
    private Uri resultUri;
    //private SessionManager sessionManager;
    public static File UserProfile_img, codmpressedImage, compressActualFile;
    boolean isProfileImage=false;

    String GrpName="";
    String GrpDescription="";
    String MemberId="";
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_create_grp_next);

        sessionManager = new SessionManager(CreateGrpNext.this);

        binding.RRback.setOnClickListener(v -> {
            onBackPressed();
        });

        Intent intent=getIntent();
        if(intent!=null)
        {
             MemberId=intent.getStringExtra("MemberId").toString();
        }

        binding.RRSave.setOnClickListener(v -> {

             GrpName=binding.edtGrpName.getText().toString();
             GrpDescription=binding.edtGrpDes.getText().toString();

            if(GrpName.equalsIgnoreCase(""))
            {
                Toast.makeText(this, "Please Enter Group Name.", Toast.LENGTH_SHORT).show();

            }else if(GrpDescription.equalsIgnoreCase(""))
            {
                Toast.makeText(this, "Please Enter Description", Toast.LENGTH_SHORT).show();

            }else if(!isProfileImage)
            {
                Toast.makeText(this, "Please selected Image", Toast.LENGTH_SHORT).show();
            }else
            {
                if (sessionManager.isNetworkAvailable()) {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    CreateGrpMehod();
                }else {
                    Toast.makeText(CreateGrpNext.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.RRImg.setOnClickListener(v ->
        {
            Dexter.withActivity(CreateGrpNext.this)
                    .withPermissions(Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            if (report.areAllPermissionsGranted()) {
                                showPictureDialog();
                            } else {
                                showSettingDialogue();
                            }
                        }
                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    }).check();
        });    }

    private void showSettingDialogue() {

        AlertDialog.Builder builder = new AlertDialog.Builder(CreateGrpNext.this);
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
                            Bundle extras = data.getExtras();
                            Bitmap bitmapNew = (Bitmap) extras.get("data");
                            Bitmap imageBitmap = BITMAP_RESIZER(bitmapNew, bitmapNew.getWidth(), bitmapNew.getHeight());

                            Glide.with(CreateGrpNext.this).load(imageBitmap).circleCrop().into(binding.imgeGrpImg);
//                            binding.image.setVisibility(View.VISIBLE);
                            Uri tempUri = getImageUri(CreateGrpNext.this, imageBitmap);
                            isProfileImage=true;
                            String imag = RealPathUtil.getRealPath(CreateGrpNext.this, tempUri);
                            UserProfile_img = new File(imag);
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

                        Bitmap bitmapNew = MediaStore.Images.Media.getBitmap(CreateGrpNext.this.getContentResolver(), selectedImage);
                        Bitmap bitmap = BITMAP_RESIZER(bitmapNew, bitmapNew.getWidth(), bitmapNew.getHeight());

                        Glide.with(CreateGrpNext.this).load(bitmap).circleCrop().into(binding.imgeGrpImg);


                        //                        binding.image.setVisibility(View.VISIBLE);
                        isProfileImage=true;
                        Uri tempUri = getImageUri(CreateGrpNext.this, bitmap);
                        String imag = RealPathUtil.getRealPath(CreateGrpNext.this, tempUri);
                        UserProfile_img = new File(imag);

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





    private void CreateGrpMehod(){

        String Userid= Preference.get(CreateGrpNext.this,Preference.KEY_USER_ID);

        MultipartBody.Part imgFile = null;

        if (UserProfile_img == null) {

        } else {
            RequestBody requestFileOne = RequestBody.create(MediaType.parse("image/*"),UserProfile_img);
            imgFile = MultipartBody.Part.createFormData("group_image",UserProfile_img.getName(), requestFileOne);
        }

        RequestBody UserId = RequestBody.create(MediaType.parse("text/plain"), Userid);
        RequestBody MemrId = RequestBody.create(MediaType.parse("text/plain"), MemberId);
        RequestBody Grpname = RequestBody.create(MediaType.parse("text/plain"), GrpName);
        RequestBody Grpdescription = RequestBody.create(MediaType.parse("text/plain"), GrpDescription);
        RequestBody Admin = RequestBody.create(MediaType.parse("text/plain"), "admin");

        Call<AddGrpModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .addgroupmember(UserId,MemrId,Grpname,Grpdescription,Admin,imgFile);
        call.enqueue(new Callback<AddGrpModel>() {
            @Override
            public void onResponse(Call<AddGrpModel> call, Response<AddGrpModel> response) {

                binding.progressBar.setVisibility(View.GONE);

                AddGrpModel finallyPr = response.body();

                String status = finallyPr.getStatus();

                if (status.equalsIgnoreCase("1")) {

                    Toast.makeText(CreateGrpNext.this, finallyPr.getMessage(), Toast.LENGTH_SHORT).show();
                   startActivity(new Intent(CreateGrpNext.this,HomeActivity.class));
                    finish();

                } else {
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(CreateGrpNext.this, finallyPr.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<AddGrpModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(CreateGrpNext.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }

}