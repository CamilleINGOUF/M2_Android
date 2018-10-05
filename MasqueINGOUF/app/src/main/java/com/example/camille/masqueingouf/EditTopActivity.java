package com.example.camille.masqueingouf;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditTopActivity extends AppCompatActivity {

    private EditText editName;
    private EditText editFirstName;
    private EditText editPhone;

    private Button cancelButton;
    private Button validButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_top);

        editName = findViewById(R.id.editTextName);
        editFirstName = findViewById(R.id.editTextFirstName);
        editPhone = findViewById(R.id.editTextPhoneNumber);
        cancelButton = findViewById(R.id.cancelTopEdit);
        validButton = findViewById(R.id.validTopEdit);

        Intent data = getIntent();
        editName.setText(data.getStringExtra("oldName"));
        editFirstName.setText(data.getStringExtra("oldFirstname"));
        editPhone.setText(data.getStringExtra("oldPhoneNumber"));

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditTopActivity.this,MainActivity.class);
                setResult(Activity.RESULT_CANCELED,intent);
                finish();
            }
        });

        validButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditTopActivity.this,MainActivity.class);
                intent.putExtra("newName",editName.getText().toString());
                intent.putExtra("newFirstName",editFirstName.getText().toString());
                intent.putExtra("newPhone",editPhone.getText().toString());
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
    }
}
