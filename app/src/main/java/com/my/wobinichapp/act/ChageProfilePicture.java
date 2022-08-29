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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.firebase.database.annotations.NotNull;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.my.wobinichapp.Preference;
import com.my.wobinichapp.R;
import com.my.wobinichapp.databinding.ActivityChageProfilePictureBinding;
import com.my.wobinichapp.model.SignUpModel;
import com.my.wobinichapp.utils.FileUtil;
import com.my.wobinichapp.utils.RealPathUtil;
import com.my.wobinichapp.utils.RetrofitClients;
import com.my.wobinichapp.utils.SessionManager;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChageProfilePicture extends AppCompatActivity {

    ActivityChageProfilePictureBinding binding;
    private Bitmap bitmap;
    private Uri resultUri;
    //private SessionManager sessionManager;
    public File UserProfile_img1, codmpressedImage, compressActualFile;
    boolean isProfileImage=false;
    private SessionManager sessionManager;
    private String filePaths;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_chage_profile_picture);

        sessionManager = new SessionManager(ChageProfilePicture.this);

        String UserProfile=Preference.get(ChageProfilePicture.this,Preference.KEY_USER_image);

        if(!UserProfile.equalsIgnoreCase(""))
        {
            Glide.with(ChageProfilePicture.this).load(UserProfile).error(R.drawable.user_default).placeholder(R.drawable.user_default).circleCrop().into(binding.imgeUSer);
        }

        binding.RRback.setOnClickListener(v -> {
            onBackPressed();
        });

        binding.RRSave.setOnClickListener(v -> {
            if(isProfileImage)
            {
                if (sessionManager.isNetworkAvailable()) {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    updateProfileApi();
                }else {
                    Toast.makeText(ChageProfilePicture.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
                }
            }else
            {
                Toast.makeText(this, "Please Select Image", Toast.LENGTH_SHORT).show();
            }
        });

        binding.RRImg.setOnClickListener(v ->
        {
            Dexter.withActivity(ChageProfilePicture.this)
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
        });

    }

    private void showSettingDialogue() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ChageProfilePicture.this);
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

                            Glide.with(ChageProfilePicture.this).load(imageBitmap).circleCrop().into(binding.imgeUSer);
//                            binding.image.setVisibility(View.VISIBLE);
                            Uri tempUri = getImageUri(ChageProfilePicture.this, imageBitmap);
                            isProfileImage=true;
                            String imag = RealPathUtil.getRealPath(ChageProfilePicture.this, tempUri);
                            filePaths = imag;
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

                        Bitmap bitmapNew = MediaStore.Images.Media.getBitmap(ChageProfilePicture.this.getContentResolver(), selectedImage);
                        Bitmap bitmap = BITMAP_RESIZER(bitmapNew, bitmapNew.getWidth(), bitmapNew.getHeight());

                        Glide.with(ChageProfilePicture.this).load(bitmap).circleCrop().into(binding.imgeUSer);


                        //                        binding.image.setVisibility(View.VISIBLE);
                        isProfileImage=true;
                        Uri tempUri = getImageUri(ChageProfilePicture.this, bitmap);
                        String imag = RealPathUtil.getRealPath(ChageProfilePicture.this, tempUri);
                        filePaths = imag;

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

/*

    private void updateProfileImageMehod(){

        String Userid= Preference.get(ChageProfilePicture.this,Preference.KEY_USER_ID);

        MultipartBody.Part imgFile = null;

        if (UserProfile_img1 == null) {

        } else {
            RequestBody requestFileOne = RequestBody.create(MediaType.parse("image/*"),UserProfile_img1);
            imgFile = MultipartBody.Part.createFormData("image",UserProfile_img1.getName(), requestFileOne);
        }

        RequestBody UserId = RequestBody.create(MediaType.parse("text/plain"), Userid);

        Call<SignUpModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .update_image(UserId,imgFile);
        call.enqueue(new Callback<SignUpModel>() {
            @Override
            public void onResponse(Call<SignUpModel> call, Response<SignUpModel> response) {

                binding.progressBar.setVisibility(View.GONE);

                SignUpModel finallyPr = response.body();

                String status = finallyPr.getStatus();

                if (status.equalsIgnoreCase("1")) {

                    if(!finallyPr.getResult().getImage().equalsIgnoreCase(""))
                    {
                        Glide.with(ChageProfilePicture.this).load(finallyPr.getResult().getImage()).circleCrop().into(binding.imgeUSer);
                    }

                    Preference.save(ChageProfilePicture.this,Preference.KEY_USER_image,finallyPr.getResult().getImage());

                    Toast.makeText(ChageProfilePicture.this, finallyPr.getMessage(), Toast.LENGTH_SHORT).show();
                           finish();

                } else {
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(ChageProfilePicture.this, finallyPr.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<SignUpModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(ChageProfilePicture.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
            
        });
    }

*/

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


    private void updateProfileApi() {

        String Userid= Preference.get(ChageProfilePicture.this,Preference.KEY_USER_ID);

        MultipartBody.Builder builder = new MultipartBody.Builder();

        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("user_id", Userid);

        if (!filePaths.equals("")) {
            File file = new File(filePaths);
            builder.addFormDataPart("image", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
        }

        MultipartBody requestBody = builder.build();

        Call<ResponseBody> call = RetrofitClients
                .getInstance()
                .getApi()
                .updateProfile(requestBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                   binding.progressBar.setVisibility(View.GONE);
                try {
                    if (response.isSuccessful()) {
                        String responseData = response.body() != null ? response.body().string() : "";
                        JSONObject object = new JSONObject(responseData);

                        if (object.optString("status").equals("1")) {

                            JSONObject  result=object.optJSONObject("result");

                            String imge=result.optString("image");
                            Preference.save(ChageProfilePicture.this,Preference.KEY_USER_image,imge);
                            Toast.makeText(ChageProfilePicture.this, ""+object.optString("message"), Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(ChageProfilePicture.this, ""+object.optString("result"), Toast.LENGTH_SHORT).show();
                           // showToast(object.optString("result"));
                        }
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                t.printStackTrace();
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }


}