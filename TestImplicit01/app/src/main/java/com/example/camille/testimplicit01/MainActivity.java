package com.example.camille.testimplicit01;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button smsButton;
    private Button mmsButton;
    private Button callButton;
    private Button webButton;
    private Button mapButton;

    private EditText editPhoneNumber;
    private EditText editURL;
    private EditText editLongitude;
    private EditText editLatitude;

    private View.OnClickListener listenerButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            Intent intent;
            String phoneNumber = editPhoneNumber.getText().toString();
            String urlStr = editURL.getText().toString();
            String longitude = editLongitude.getText().toString();
            String latitude = editLatitude.getText().toString();
            switch (id) {
                case R.id.smsButton:
                    if (phoneNumber.equals("")) {
                        Toast.makeText(MainActivity.this, R.string.error_noPhoneNumber, Toast.LENGTH_LONG).show();
                        return;
                    }

                    if(!Patterns.PHONE.matcher(phoneNumber).matches())
                    {
                        Toast.makeText(MainActivity.this, R.string.error_phoneNumberNoMatch, Toast.LENGTH_LONG).show();
                        return;
                    }
                    intent = new Intent(Intent.ACTION_SENDTO);
                    Uri sms = Uri.parse("sms:" + phoneNumber);
                    intent.setData(sms);
                    startActivity(intent);
                    System.out.print(smsButton.getText());
                    break;
                case R.id.mmsButton:
                    if (phoneNumber.equals("")) {
                        Toast.makeText(MainActivity.this, R.string.error_noPhoneNumber, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(!Patterns.PHONE.matcher(phoneNumber).matches())
                    {
                        Toast.makeText(MainActivity.this, R.string.error_phoneNumberNoMatch, Toast.LENGTH_LONG).show();
                        return;
                    }
                    intent = new Intent(Intent.ACTION_SENDTO);
                    Uri mms = Uri.parse("mms:" + phoneNumber);
                    intent.setData(mms);
                    startActivity(intent);
                    System.out.print(mmsButton.getText());
                    break;
                case R.id.callButton:
                    if (phoneNumber.equals("")) {
                        Toast.makeText(MainActivity.this, R.string.error_noPhoneNumber, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(!Patterns.PHONE.matcher(phoneNumber).matches())
                    {
                        Toast.makeText(MainActivity.this, R.string.error_phoneNumberNoMatch, Toast.LENGTH_LONG).show();
                        return;
                    }
                    intent = new Intent(Intent.ACTION_DIAL);
                    Uri tel = Uri.parse("tel:" + phoneNumber);
                    intent.setData(tel);
                    startActivity(intent);
                    System.out.print(callButton.getText());
                    break;
                case R.id.webButton:
                    if (urlStr.equals("")){
                        Toast.makeText(MainActivity.this, R.string.error_noUrl, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(!Patterns.WEB_URL.matcher(urlStr).matches())
                    {
                        Toast.makeText(MainActivity.this, R.string.error_urlNoMatch, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    intent = new Intent(Intent.ACTION_VIEW);
                    Uri web = Uri.parse(urlStr);
                    intent.setData(web);
                    startActivity(intent);
                    System.out.print(webButton.getText());
                    break;
                case R.id.mapButton:
                    map(latitude,longitude);
                    break;
                default:
                    System.out.println("Default");
                    return;
            }
        }
    };

    private void map(String latitude,String longitude)
    {
        if(longitude.equals("") || latitude.equals("")) {
            Toast.makeText(MainActivity.this, R.string.error_noLongitudeLatitude, Toast.LENGTH_SHORT).show();
            return;
        }

        if(Float.parseFloat(latitude) < -90f || Float.parseFloat(latitude) > 90f)
        {
            Toast.makeText(MainActivity.this, R.string.error_latitudeValue, Toast.LENGTH_SHORT).show();
            return;
        }

        if(Float.parseFloat(longitude) < -180f || Float.parseFloat(longitude) > 180f)
        {
            Toast.makeText(MainActivity.this, R.string.error_longitudeValue, Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri map = Uri.parse("geo:"+latitude+","+longitude+"?q="+latitude+","+longitude);
        intent.setData(map);
        startActivity(intent);
        System.out.print(mapButton.getText());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editPhoneNumber = findViewById(R.id.editPhone);
        editURL = findViewById(R.id.editURL);
        editLongitude = findViewById(R.id.editLongitude);
        editLatitude = findViewById(R.id.editLatitude);

        smsButton = findViewById(R.id.smsButton);
        mmsButton = findViewById(R.id.mmsButton);
        callButton = findViewById(R.id.callButton);
        webButton = findViewById(R.id.webButton);
        mapButton = findViewById(R.id.mapButton);

        smsButton.setOnClickListener(listenerButton);
        mmsButton.setOnClickListener(listenerButton);
        callButton.setOnClickListener(listenerButton);
        webButton.setOnClickListener(listenerButton);
        mapButton.setOnClickListener(listenerButton);
    }
}
