package com.example.smartcommunity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.security.Permission;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfileActivity extends AppCompatActivity {

    Button mBtn_changePassword, mBtn_camera, mBtn_gallery;
    ImageView mImg_profile;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BindWidget();
        showDialogChangePassword();

        mBtn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCameraGallery(101);
            }
        });

        mBtn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCameraGallery(102);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                getContentResolver().notifyChange(uri,null);
                ContentResolver cr = getContentResolver();
                try{
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(cr, uri);
                    mImg_profile.setImageBitmap(bitmap);
                }catch (Exception ex){
                    Log.d("testCAMERA: ", ex.getMessage());
                }
            }
        }else if (requestCode == 102) {
            if (resultCode == RESULT_OK) {
                uri = data.getData();
                try{
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    mImg_profile.setImageBitmap(bitmap);
                }catch (Exception ex){
                    Log.d("testCAMERA: ", ex.getMessage());
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 101){
            if(grantResults.length == 1){
                showIntentCamera();
            }else{
                Toast.makeText(ProfileActivity.this, "ต้องทำการเปิดใช้งานกล้อง/คลังภาพ", Toast.LENGTH_LONG).show();
            }
        }else if(requestCode == 102){
            if(grantResults.length == 1){
                showIntentGalley();
            }else{
                Toast.makeText(ProfileActivity.this, "ต้องทำการเปิดใช้งานกล้อง/คลังภาพ", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void showCameraGallery(int requestCode) {
        if(requestCode == 101){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                requestPermissionsCamera();
                requestPermissionsGallery();
            }else{
                showIntentCamera();
            }
        }else if(requestCode == 102){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissionsGallery();

            }else{
                showIntentGalley();
            }
        }

    }

    private void showIntentGalley() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent,
                "Select photo from"),102);
    }

    private void requestPermissionsCamera() {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},101);
            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},101);
            }
    }
    private void requestPermissionsGallery(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE)){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},102);
        }else{
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},102);
        }
    }

    private void showIntentCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMG_"+timeStamp+".jpg";
        File f = new File(Environment.getExternalStorageDirectory(),"DCIM/Camera/"+imageFileName);
        uri = Uri.fromFile(f);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        startActivityForResult(Intent.createChooser(intent,
                "Take"),101);
    }

    private void BindWidget() {
        mBtn_changePassword = findViewById(R.id.btn_change_password);
        mBtn_camera = findViewById(R.id.btn_profile_camera);
        mBtn_gallery = findViewById(R.id.btn_profile_gallery);
        mImg_profile = findViewById(R.id.img_profile);
    }

    private void showDialogChangePassword() {
        mBtn_changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertPass = new AlertDialog.Builder(ProfileActivity.this);
                //LayoutInflater myLayoutInflater = LayoutInflater.from(ProfileActivity.this);
                View view = getLayoutInflater().inflate(R.layout.change_password, null);

                Button mBtn_cancel = view.findViewById(R.id.btn_change_password_cancel);
                Button mBtn_confirm = view.findViewById(R.id.btn_change_password_confirm);
                alertPass.setView(view);
                final AlertDialog alertDialog = alertPass.create();
                alertDialog.setCanceledOnTouchOutside(true);


                mBtn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                mBtn_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
    }
}