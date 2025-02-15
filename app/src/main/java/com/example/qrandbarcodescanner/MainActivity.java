 package com.example.qrandbarcodescanner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

 public class MainActivity extends AppCompatActivity implements View.OnClickListener{

     Button scanBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scanBtn = findViewById(R.id.scanBtn);
        scanBtn.setOnClickListener(this);
    }

     @Override
     public void onClick(View v) {
        scanCode();
     }

     private void scanCode(){
        /*
         * A utility class which helps ease integration with Barcode Scanner via Intents.
         * This is a simple way to invoke barcode scanning and receive the result,
         * without any need to integrate, modify, or learn the project's source code.
         */
         IntentIntegrator integrator = new IntentIntegrator(this);
         integrator.setCaptureActivity(CaptureAct.class);
         integrator.setOrientationLocked(false);
         integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
         //Text at the bottom
         integrator.setPrompt("Scanning...");
         integrator.initiateScan();
     }

     @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
         if (result != null){
             if (result.getContents() != null){
                 //A subclass of Dialog that can display one, two or three buttons.
                 AlertDialog.Builder builder = new AlertDialog.Builder(this);
                 builder.setMessage(result.getContents());
                 builder.setTitle("Scanning Result");
                 builder.setPositiveButton("Scan Again", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         scanCode();
                     }
                 }).setNegativeButton("Finish", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         finish();
                     }
                 });
                 AlertDialog dialog = builder.create();
                 dialog.show();
             }
             else {
                 Toast.makeText(this, "No Result", Toast.LENGTH_LONG).show();
             }
         } else {
                super.onActivityResult(requestCode, resultCode, data);
         }
     }
 }