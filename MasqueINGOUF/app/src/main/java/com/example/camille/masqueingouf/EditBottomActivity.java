package com.example.camille.masqueingouf;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditBottomActivity extends AppCompatActivity {

    private EditText editNumber;
    private EditText editStreet;
    private EditText editPostal;
    private EditText editCity;

    private Button valid;
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bottom);

        editNumber = findViewById(R.id.editNumberAddress);
        editStreet = findViewById(R.id.editStreet);
        editPostal = findViewById(R.id.editPostal);
        editCity = findViewById(R.id.editCity);

        valid = findViewById(R.id.buttonValidBottom);
        cancel = findViewById(R.id.buttonCancelBottom);

        Intent data = getIntent();

        editNumber.setText(data.getStringExtra("oldNumberAddress"));
        editStreet.setText(data.getStringExtra("oldStreetName"));
        editPostal.setText(data.getStringExtra("oldPostal"));
        editCity.setText(data.getStringExtra("oldCity"));

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditBottomActivity.this,MainActivity.class);
                setResult(Activity.RESULT_CANCELED,intent);
                finish();
            }
        });

        valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditBottomActivity.this,MainActivity.class);
                intent.putExtra("newNumberAddress",editNumber.getText().toString());
                intent.putExtra("newStreet",editStreet.getText().toString());
                intent.putExtra("newPostal",editPostal.getText().toString());
                intent.putExtra("newCity",editCity.getText().toString());
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
    }
}
