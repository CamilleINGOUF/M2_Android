package com.example.camille.masqueingouf;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final int EDIT_TOP = 1;
    public static final int EDIT_BOTTOM = 2;

    private TextView nameView;
    private TextView firstnameView;
    private TextView phoneNumberView;
    private Button editTopButton;

    private TextView numberAddressView;
    private TextView streetView;
    private TextView postalCodeView;
    private TextView cityView;
    private Button editBottomButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameView = findViewById(R.id.textName);
        firstnameView = findViewById(R.id.textFirstname);
        phoneNumberView = findViewById(R.id.textNumber);
        editTopButton = findViewById(R.id.editTop);

        numberAddressView = findViewById(R.id.textNumberAddress);
        streetView = findViewById(R.id.textStreetName);
        postalCodeView = findViewById(R.id.textPostal);
        cityView = findViewById(R.id.textCity);
        editBottomButton = findViewById(R.id.editBottom);

        editTopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,EditTopActivity.class);
                intent.putExtra("oldName",nameView.getText().toString());
                intent.putExtra("oldFirstname",firstnameView.getText().toString());
                intent.putExtra("oldPhoneNumber",phoneNumberView.getText().toString());
                startActivityForResult(intent,EDIT_TOP);
            }
        });

        editBottomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,EditBottomActivity.class);
                intent.putExtra("oldNumberAddress",numberAddressView.getText().toString());
                intent.putExtra("oldStreetName",streetView.getText().toString());
                intent.putExtra("oldPostal",postalCodeView.getText().toString());
                intent.putExtra("oldCity",cityView.getText().toString());
                startActivityForResult(intent,EDIT_BOTTOM);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode == Activity.RESULT_CANCELED)
        {
            Toast.makeText(MainActivity.this,"Cancelled.",Toast.LENGTH_SHORT).show();
            return;
        }

        if(requestCode == EDIT_TOP)
        {
            nameView.setText(data.getStringExtra("newName"));
            firstnameView.setText(data.getStringExtra("newFirstName"));
            phoneNumberView.setText(data.getStringExtra("newPhone"));
        }
        else if(requestCode == EDIT_BOTTOM)
        {
            numberAddressView.setText(data.getStringExtra("newNumberAddress"));
            streetView.setText(data.getStringExtra("newStreet"));
            postalCodeView.setText(data.getStringExtra("newPostal"));
            cityView.setText(data.getStringExtra("newCity"));
        }
    }
}
