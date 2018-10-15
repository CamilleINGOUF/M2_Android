package com.example.camille.tpimageingouf;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button loadButton;
    private TextView textPath;
    private ImageView imageView;

    public static final int LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadButton = findViewById(R.id.buttonLoad);
        textPath = findViewById(R.id.pathView);
        imageView = findViewById(R.id.imageView);

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Loading image.");
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"Choose an image."), LOAD_IMAGE);
            }
        });

        registerForContextMenu(imageView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri imageURI = data.getData();
        textPath.setText(imageURI.toString());

        BitmapFactory.Options option = new BitmapFactory.Options();
        try
        {
            Bitmap bm = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageURI), null, option);
            imageView.setImageBitmap(bm);
        }
        catch(Exception e)
        {

        }
    }

    private Bitmap getHorizontalMirror(Bitmap bm)
    {
        int w = bm.getWidth();
        int h = bm.getHeight();


        Bitmap newBM = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);

        for(int x = 0; x < w; x++)
        {
            for(int y = 0; y < h; y++)
            {
                newBM.setPixel(w - x - 1, y, bm.getPixel(x,y));
            }
        }

        return newBM;
    }

    private Bitmap getVerticalMirror(Bitmap bm)
    {
        int w = bm.getWidth();
        int h = bm.getHeight();

        Bitmap newBM = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);

        for(int x = 0; x < w; x++)
        {
            for(int y = 0; y < h; y++)
            {
                newBM.setPixel(x, h - y - 1, bm.getPixel(x,y));
            }
        }

        return newBM;
    }

    private boolean checkImage()
    {
        try
        {
            ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        }
        catch(Exception e)
        {
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mon_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id)
        {
            case R.id.action_mirror_horizontal:
                horizontal();
                break;
            case R.id.action_mirror_vertical:
                vertical();
                break;
        }
        return true;
    }

    private void horizontal()
    {
        if(!checkImage())
        {
            Toast.makeText(MainActivity.this,"No image loaded",Toast.LENGTH_SHORT);
            return;
        }
        Bitmap bm = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        Bitmap newBM = getHorizontalMirror(bm);
        imageView.setImageBitmap(newBM);
    }

    private void vertical()
    {
        if(!checkImage())
        {
            Toast.makeText(MainActivity.this,"No image loaded",Toast.LENGTH_SHORT);
            return;
        }
        Bitmap bm = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        Bitmap newBM = getVerticalMirror(bm);
        imageView.setImageBitmap(newBM);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }
}
