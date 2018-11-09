package com.example.camille.contentprovider01;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    public static final int CREATE_CONTACT = 1;

    EditText search;
    ListView contacts;
    Button create;
    LinearLayout noContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_CONTACTS},1);
        setContentView(R.layout.activity_main);
        contacts = findViewById(R.id.contact);
        search = findViewById(R.id.search);
        create = findViewById(R.id.create);
        noContact = findViewById(R.id.addContact);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateActivity.class);
                intent.putExtra("name", search.getText().toString());
                startActivityForResult(intent, CREATE_CONTACT);
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Cursor c = getContentResolver().query(
                        ContactsContract.Contacts.CONTENT_URI,
                        null,
                        ContactsContract.Contacts.DISPLAY_NAME + " LIKE ?",
                        new String[]{"%"+search.getText()+"%"},
                        ContactsContract.Contacts.DISPLAY_NAME + " ASC"
                );

                if (c != null && c.getCount() == 0) {
                    contacts.setVisibility(View.GONE);
                    noContact.setVisibility(View.VISIBLE);
                } else {
                    SimpleCursorAdapter ca = new SimpleCursorAdapter(
                            MainActivity.this,
                            android.R.layout.simple_list_item_1,
                            c,
                            new String[]{ContactsContract.Contacts.DISPLAY_NAME},
                            new int[]{android.R.id.text1},
                            0
                    );
                    noContact.setVisibility(View.GONE);
                    contacts.setVisibility(View.VISIBLE);
                    contacts.setAdapter(ca);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == CreateActivity.VALID) {
            search.setText(data.getStringExtra("name"));
        }
    }
}
