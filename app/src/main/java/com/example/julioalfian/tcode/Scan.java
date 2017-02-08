package com.example.julioalfian.tcode;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import info.vividcode.android.zxing.CaptureActivity;
import info.vividcode.android.zxing.CaptureActivityIntents;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class Scan extends AppCompatActivity {

    private TextView tvScanResult,tvcode;
    String Getcode;
    TextView getCode;
    String DataParseUrl = "http://192.168.137.1/barcode/insert-data.php" ;
    Boolean CheckEditText ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        // Membuat intent baru untuk memanggil CaptureActivity bawaan ZXing
        Intent captureIntent = new Intent(Scan.this, CaptureActivity.class);

        // Kemudian kita mengeset pesan yang akan ditampilkan ke user saat menjalankan QRCode scanning
        CaptureActivityIntents.setPromptMessage(captureIntent, "Barcode scanning...");

        ///flash


        // Melakukan startActivityForResult, untuk menangkap balikan hasil dari QR Code scanning
        startActivityForResult(captureIntent, 0);



        tvScanResult = (TextView) findViewById(R.id.tv_scanresult);



        Button btSent = (Button) findViewById(R.id.bt_sent);
        btSent.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){

                // TODO Auto-generated method stub

                GetCheckEditTextIsEmptyOrNot();

                if(CheckEditText){

                    SendDataToServer(Getcode);

                }
                else {

                    Toast.makeText(Scan.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();

                }

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                String value = data.getStringExtra("SCAN_RESULT");
                tvScanResult.setText(value);
                //tvcode.setText(value);
                Getcode = value;
            } else if (resultCode == Activity.RESULT_CANCELED) {
                tvScanResult.setText("Scanning Gagal, mohon coba lagi.");
            }
        } else {

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void GetCheckEditTextIsEmptyOrNot(){

        //Getcode = tvScanResult.getText().toString();
        //Getcode = "tes";

        if(TextUtils.isEmpty(Getcode))
        {

            CheckEditText = false;

        }
        else {

            CheckEditText = true ;
        }

    }

    public void SendDataToServer(final String code){
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                String QuickCode = code ;

//                String QuickEmail = email ;
//                String QuickPassword = password;

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("code", QuickCode));
                //nameValuePairs.add(new BasicNameValuePair("email", QuickEmail));
                //nameValuePairs.add(new BasicNameValuePair("password", QuickPassword));

                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost(DataParseUrl);

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();
                    //Toast.makeText(HasilActivity.this, "tes1", Toast.LENGTH_LONG).show();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "Data Submit Successfully";

            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                Toast.makeText(Scan.this, "Data Submit Successfully", Toast.LENGTH_LONG).show();

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(code);
    }




}
