package com.example.camille.testintent01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AppliIntent00 extends AppCompatActivity {

    private Button sendButton;
    private EditText sendEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appli_intent00);

        sendButton = findViewById(R.id.send);
        sendEdit = findViewById(R.id.sendEdit);

        sendButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String res = sendEdit.getText().toString();
                System.out.println(res);
                Intent intent = new Intent(AppliIntent00.this,SecondActivity.class);
                intent.putExtra("result",res);
                startActivity(intent);
            }
        });
    }
}
