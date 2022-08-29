package com.my.wobinichapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;

import com.my.wobinichapp.act.HomeActivity;
import com.my.wobinichapp.act.LoginActivity;

public class MainActivity extends AppCompatActivity {

    String User_id="";
    public static final int RequestPermissionCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        User_id=Preference.get(this,Preference.KEY_USER_ID);

        if (permissioncheck()){
            finds();
        }else {
            requestPermission();
        }

    }

    private void finds() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {

             /*   Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();*/

               if(User_id.equalsIgnoreCase("0"))
                {
                    Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else
                {
                   Intent intent=new Intent(MainActivity.this, HomeActivity.class);
                   startActivity(intent);
                   finish();
                }


            }
        }, 3000);

    }


    private boolean permissioncheck() {
        int FourthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int FifthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION);
        int Fifth1PermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
        return FourthPermissionResult ==
                PackageManager.PERMISSION_GRANTED && Fifth1PermissionResult ==
                PackageManager.PERMISSION_GRANTED && FifthPermissionResult == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]
                {
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.WRITE_CONTACTS,

                },RequestPermissionCode);

        if (permissioncheck())
        {
            finds();

        }else {
            requestPermission();
        }

    }


}