package com.example.camille.testintent01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        result = findViewById(R.id.textView);

        Intent intent = getIntent();
        String res = intent.getStringExtra("result");
        if(!res.equals(""))
        {
            result.setText(res);
        }
        else
        {
            result.setText(R.string.empty);
        }
    }
}
