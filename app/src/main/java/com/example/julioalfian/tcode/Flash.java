package com.example.julioalfian.tcode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.security.Policy;



public class Flash extends AppCompatActivity {
    Button button;
    private Camera camera;
    private boolean isFlashOn;
    private boolean hasFlash;
    Parameters params;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);


        //button = (Button) findViewById(R.id.button);

        hasFlash = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

//        if(!hasFlash) {
//
//            AlertDialog alert = new AlertDialog.Builder(Flash.this).create();
//            alert.setTitle("Error");
//            alert.setMessage("Sorry, your device doesn't support flash light!");
//            alert.setButton("OK", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    finish();
//                }
//            });
//            alert.show();
//            return;
//        }

        //getCamera();

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (isFlashOn) {
//                    turnOffFlash();
//                    button.setText("ON");
//                } else {
//                    turnOnFlash();
//                    button.setText("OFF");
//                }
//
//            }
//        });
    }

    private void getCamera() {

        if (camera == null) {
            try {
                camera = Camera.open();
                params = camera.getParameters();
            }catch (Exception e) {

            }
        }

    }

    private void turnOnFlash() {

        if(!isFlashOn) {
            if(camera == null || params == null) {
                return;
            }

            params = camera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            isFlashOn = true;
        }

    }

//    private void turnOffFlash() {
//
//        if (isFlashOn) {
//            if (camera == null || params == null) {
//                return;
//            }
//
//            params = camera.getParameters();
//            params.setFlashMode(Parameters.FLASH_MODE_OFF);
//            camera.setParameters(params);
//            camera.stopPreview();
//            isFlashOn = false;
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        // on pause turn off the flash
//        turnOffFlash();
//    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//    }

    @Override
    protected void onResume() {
        super.onResume();

        // on resume turn on the flash
        if(hasFlash)
            turnOnFlash();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // on starting the app get the camera params
        getCamera();
    }

    @Override
    protected void onStop() {
        super.onStop();

        // on stop release the camera
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

}
