package com.my.wobinichapp.act;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.my.wobinichapp.GPSTracker;
import com.my.wobinichapp.MainActivity;
import com.my.wobinichapp.Preference;
import com.my.wobinichapp.R;
import com.my.wobinichapp.databinding.ActivityAddPostBinding;
import com.my.wobinichapp.model.AddGrpModel;
import com.my.wobinichapp.model.AddPostModel;
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
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Part;

public class AddPost extends AppCompatActivity {

    ActivityAddPostBinding binding;
    //private SessionManager sessionManager;
    public static File UserProfile_img, codmpressedImage, compressActualFile;
    boolean isProfileImage=false;

    String comment="false";
    String maps="false";
    String website="";
    String type="false";
    String landscape="false";
    String meal="false";
    String places="false";
    String shop="false";
    String all_world="false";
    private SessionManager sessionManager;
    private Bitmap bitmap;
    private Uri resultUri;
    String latitude="";
    String longitude="";
    boolean isImage=false;
    GPSTracker gpsTracker;

    int AUTOCOMPLETE_REQUEST_CODE_ADDRESS1 = 102;
    String AddRess="";
    private String filePaths;
    String addressStreet = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_add_post);

        sessionManager = new SessionManager(AddPost.this);

        if (!Places.isInitialized()) {
            Places.initialize(AddPost.this, getString(R.string.place_api_key));
        }


        binding.RRback.setOnClickListener(v -> {
           onBackPressed();
       });

       binding.imgCheck.setOnClickListener(v -> {
           Validation();
       });


       binding.txtMap.setOnClickListener(v -> {

           List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS);
           // Start the autocomplete intent.
           Intent intent = new Autocomplete.IntentBuilder(
                   AutocompleteActivityMode.FULLSCREEN, fields)
                   .build(AddPost.this);

           startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE_ADDRESS1);

       });

       binding.llImage.setOnClickListener(v -> {
           Dexter.withActivity(AddPost.this)
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

        //Gps Lat Long
        gpsTracker=new GPSTracker(AddPost.this);
        if(gpsTracker.canGetLocation()){
            latitude = String.valueOf(gpsTracker.getLatitude());
            longitude = String.valueOf(gpsTracker.getLongitude());
            getAddress(this,gpsTracker.getLatitude(),gpsTracker.getLongitude());
        }else{
            gpsTracker.showSettingsAlert();
        }

    }

    private void Validation() {

        website =binding.edtWebSide.getText().toString();

        if(!isImage)
        {
            Toast.makeText(this, "Please Select Image", Toast.LENGTH_SHORT).show();

        }else if(binding.edtTitle.getText().toString().equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Enter Dist du.?", Toast.LENGTH_SHORT).show();

        }else if(binding.edtCommint.getText().toString().equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Enter Comment..", Toast.LENGTH_SHORT).show();

        }else if(website.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Select WebSIde Box..", Toast.LENGTH_SHORT).show();

        }else
        {
           if (sessionManager.isNetworkAvailable()) {
                binding.progressBar.setVisibility(View.VISIBLE);
                AddPostMehod();
            }else {
                Toast.makeText(AddPost.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void showSettingDialogue() {

        AlertDialog.Builder builder = new AlertDialog.Builder(AddPost.this);
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
                            binding.imgFully.setVisibility(View.VISIBLE);
                            binding.llImage.setVisibility(View.GONE);
                            Bundle extras = data.getExtras();
                            Bitmap bitmapNew = (Bitmap) extras.get("data");
                            Bitmap imageBitmap = BITMAP_RESIZER(bitmapNew, bitmapNew.getWidth(), bitmapNew.getHeight());
                            isImage=true;
                            Glide.with(AddPost.this).load(imageBitmap).into(binding.imgFully);
//                            binding.image.setVisibility(View.VISIBLE);
                            Uri tempUri = getImageUri(AddPost.this, imageBitmap);
                            isProfileImage=true;
                            String imag = RealPathUtil.getRealPath(AddPost.this, tempUri);
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
                        binding.llImage.setVisibility(View.GONE);
                        binding.imgFully.setVisibility(View.VISIBLE);
                        isImage=true;
                        Bitmap bitmapNew = MediaStore.Images.Media.getBitmap(AddPost.this.getContentResolver(), selectedImage);
                        Bitmap bitmap = BITMAP_RESIZER(bitmapNew, bitmapNew.getWidth(), bitmapNew.getHeight());
                        Glide.with(AddPost.this).load(bitmap).into(binding.imgFully);
//                        binding.image.setVisibility(View.VISIBLE);
                        isProfileImage=true;
                        Uri tempUri = getImageUri(AddPost.this, bitmap);
                        String imag = RealPathUtil.getRealPath(AddPost.this, tempUri);
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



    private void AddPostMehod(){

        landscape= String.valueOf(binding.checkLand.isChecked());
        meal= String.valueOf(binding.checkMeal.isChecked());
        places= String.valueOf(binding.checkFree.isChecked());
        shop= String.valueOf(binding.checkShop.isChecked());
        all_world= String.valueOf(binding.ChecAllWord.isChecked());

        String user_id= Preference.get(AddPost.this,Preference.KEY_USER_ID);
        String group_id= Preference.get(AddPost.this,Preference.KEY_grp_id);

       /* MultipartBody.Part imgFile = null;

        if (UserProfile_img == null) {

        } else {
            RequestBody requestFileOne = RequestBody.create(MediaType.parse("image/*"),UserProfile_img);
            imgFile = MultipartBody.Part.createFormData("image",UserProfile_img.getName(), requestFileOne);
        }

        RequestBody User_id = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody Group_id = RequestBody.create(MediaType.parse("text/plain"), group_id);
        RequestBody lat = RequestBody.create(MediaType.parse("text/plain"), latitude);
        RequestBody lon = RequestBody.create(MediaType.parse("text/plain"), longitude);
        RequestBody Comment = RequestBody.create(MediaType.parse("text/plain"), binding.edtCommint.getText().toString());
        RequestBody Maps = RequestBody.create(MediaType.parse("text/plain"), AddRess);
        RequestBody Website = RequestBody.create(MediaType.parse("text/plain"), website);
        RequestBody Type = RequestBody.create(MediaType.parse("text/plain"), type);
        RequestBody Landscape = RequestBody.create(MediaType.parse("text/plain"), landscape);
        RequestBody Meal = RequestBody.create(MediaType.parse("text/plain"), meal);
        RequestBody Places = RequestBody.create(MediaType.parse("text/plain"), places);
        RequestBody Shop = RequestBody.create(MediaType.parse("text/plain"), shop);*/


        String Userid= Preference.get(AddPost.this,Preference.KEY_USER_ID);

        MultipartBody.Builder builder = new MultipartBody.Builder();

        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("user_id", user_id);
        builder.addFormDataPart("group_id", group_id);
        builder.addFormDataPart("lat", latitude);
        builder.addFormDataPart("lon", longitude);
        builder.addFormDataPart("comment", binding.edtCommint.getText().toString());
        builder.addFormDataPart("maps", addressStreet);
        builder.addFormDataPart("website", website);
        builder.addFormDataPart("type", type);
        builder.addFormDataPart("landscape", landscape);
        builder.addFormDataPart("meal", meal);
        builder.addFormDataPart("places", places);
        builder.addFormDataPart("shop", shop);
        builder.addFormDataPart("all_world", all_world);

        if (!filePaths.equals("")) {
            File file = new File(filePaths);
            builder.addFormDataPart("image", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
        }

        MultipartBody requestBody = builder.build();

        Call<ResponseBody> call = RetrofitClients
                .getInstance()
                .getApi()
                .add_post(requestBody);
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

                            Toast.makeText(AddPost.this, "Add Post SuccesFully", Toast.LENGTH_SHORT).show();
                            finish();

                        } else {
                            Toast.makeText(AddPost.this, ""+object.optString("result"), Toast.LENGTH_SHORT).show();
                            // showToast(object.optString("result"));
                        }
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(AddPost.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
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

    public String getAddress(Context context, double latitude, double longitute) {

        List<Address> addresses;
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latitude, longitute, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            addressStreet = addresses.get(0).getAddressLine(0);
            // address2 = addresses.get(0).getAddressLine(1); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            //  city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String region = addresses.get(0).getAdminArea();
            binding.txtMap.setText(""+addressStreet);
         //Preference.save(mContext, Preference.KEY_address, addressStreet);
            Log.e("region====", region);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return addressStreet;
    }

}