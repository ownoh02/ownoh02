package com.example.smartcommunity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ErrorCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.Result;

public class OutActivity extends AppCompatActivity {
    private  final int CAMERA_REQUEST_CODE = 101;

    private CodeScanner codeScanner;
    CodeScannerView scannerView;
    EditText edt_qrcode;
    Button btn_confirmout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out);
        bindWidget();
        showCamera();
        showDialogConfirmOut();

        edt_qrcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Toast.makeText(OutActivity.this," beforeScan success",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Toast.makeText(OutActivity.this,"on Scan success",Toast.LENGTH_LONG).show();
            }

            @Override
            public void afterTextChanged(Editable s) {
                Toast.makeText(OutActivity.this,"after Scan success",Toast.LENGTH_LONG).show();
            }
        });


    }

    private void showDialogConfirmOut() {
        btn_confirmout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(OutActivity.this);
                alert.setTitle("ออกประตู");
                alert.setMessage("ยืนยันการออกจากประตู");
                alert.setCancelable(false);
                alert.setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        OutActivity.this.finish();
                        //startActivity(new Intent(OutActivity.this, HomeActivity.class));
                    }
                });
                alert.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alert.show();
            }
        });
    }


    private void bindWidget() {
        scannerView = findViewById(R.id.scanner_view);
        edt_qrcode = findViewById(R.id.edt_qrcode);
        btn_confirmout = findViewById(R.id.btn_confirmout);
    }

    private void showCamera() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            requestCameraPermissions();
        }else {
            codeScanner();
        }
    }


    private void requestCameraPermissions() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)){
            Snackbar.make(scannerView,"ขออนุญาติเข้าถึงกล้องของคุณ",Snackbar.LENGTH_LONG)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ActivityCompat.requestPermissions(OutActivity.this,new String[]{Manifest.permission.CAMERA},CAMERA_REQUEST_CODE);
                        }
                    });
        }else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},CAMERA_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 101){
            if(grantResults.length == 1 ){
                codeScanner();
            }else{
                Snackbar.make(scannerView, "กรุณากดอนุญาติเพื่อใช้งานกล้อง",Snackbar.LENGTH_LONG).show();
            }
        }
    }

    private void codeScanner() {
        try {
            codeScanner = new CodeScanner(this, scannerView);
            codeScanner.setCamera(CodeScanner.CAMERA_BACK);
            codeScanner.setFormats(CodeScanner.ALL_FORMATS);
            codeScanner.setScanMode(ScanMode.SINGLE);
            codeScanner.setDecodeCallback(new DecodeCallback() {
                @Override
                public void onDecoded(@NonNull Result result) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Toast.makeText(OutActivity.this, "Scan result : " + result.getText(),Toast.LENGTH_LONG).show();
                            edt_qrcode.setText(result.getText().toString().trim());
                        }
                    });
                }
            });

            scannerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    codeScanner.startPreview();
                }
            });

            codeScanner.setErrorCallback(new ErrorCallback() {
                @Override
                public void onError(@NonNull Exception error) {
                    //Toast.makeText(OutActivity.this, "Error result : " + error.getMessage().toString(),Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception ex){
            Log.d("codeSCAN",ex.getMessage());
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(codeScanner != null){
            codeScanner.startPreview();
        }
    }

    @Override
    protected void onPause() {
        if(codeScanner != null){
            codeScanner.releaseResources();
        }
        super.onPause();
    }
}