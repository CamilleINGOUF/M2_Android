package com.example.camille.tpimageingouf;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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

    private Bitmap image;

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
            image = bm;
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

    public Bitmap rotate90(Bitmap bm) {
        int w = bm.getWidth();
        int h = bm.getHeight();

        Bitmap newBM = Bitmap.createBitmap(h,w,Bitmap.Config.ARGB_8888);

        for(int x = 0; x < w; x++)
        {
            for(int y = 0; y < h; y++)
            {
                newBM.setPixel(h - 1 - y,x,bm.getPixel(x,y));
            }
        }

        return newBM;
    }

    public Bitmap rotate270(Bitmap bm) {
        int w = bm.getWidth();
        int h = bm.getHeight();

        Bitmap newBM = Bitmap.createBitmap(h,w,Bitmap.Config.ARGB_8888);

        for(int x = 0; x < w; x++)
        {
            for(int y = 0; y < h; y++)
            {
                newBM.setPixel(y,w - 1 - x,bm.getPixel(x,y));
            }
        }

        return newBM;
    }

    public void rotateHoraire()
    {
        if(!checkImage())
        {
            Toast.makeText(MainActivity.this,"No image loaded",Toast.LENGTH_SHORT);
            return;
        }
        Bitmap bm = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        Bitmap newBM = rotate90(bm);
        imageView.setImageBitmap(newBM);
    }

    public void rotateAntiHoraire()
    {
        if(!checkImage())
        {
            Toast.makeText(MainActivity.this,"No image loaded",Toast.LENGTH_SHORT);
            return;
        }
        Bitmap bm = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        Bitmap newBM = rotate270(bm);
        imageView.setImageBitmap(newBM);
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
            case R.id.horaire:
                rotateHoraire();
                break;
            case R.id.antihoraire:
                rotateAntiHoraire();
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

    private Bitmap getGreyscale(Bitmap bm)
    {
        int w = bm.getWidth();
        int h = bm.getHeight();

        Bitmap newBM = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);

        int color = -66;
        for(int x = 0; x < w; x++)
        {
            for(int y = 0; y < h; y++)
            {
                int pixel = bm.getPixel(x,y);
                int avg = (Color.red(pixel) + Color.blue(pixel) + Color.green(pixel)) / 3;
                color = Color.rgb(avg,avg,avg);
                newBM.setPixel(x, y, color);
            }
        }

        return newBM;
    }

    private void greyscale()
    {
        if(!checkImage())
        {
            Toast.makeText(MainActivity.this,"No image loaded",Toast.LENGTH_SHORT);
            return;
        }
        Bitmap bm = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        Bitmap newBM = getGreyscale(bm);
        imageView.setImageBitmap(newBM);
    }

    private Bitmap getInverseColor(Bitmap bm)
    {
        int w = bm.getWidth();
        int h = bm.getHeight();

        Bitmap newBM = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);

        for(int x = 0; x < w; x++)
        {
            for(int y = 0; y < h; y++)
            {
                int pixel = bm.getPixel(x,y);
                int r = 255 - Color.red(pixel);
                int g = 255 - Color.green(pixel);
                int b = 255 - Color.blue(pixel);
                newBM.setPixel(x, y, Color.rgb(r,g,b));
            }
        }

        return newBM;
    }

    private void inverseColors()
    {
        if(!checkImage())
        {
            Toast.makeText(MainActivity.this,"No image loaded",Toast.LENGTH_SHORT);
            return;
        }
        Bitmap bm = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        Bitmap newBM = getInverseColor(bm);
        imageView.setImageBitmap(newBM);
    }

    public void restore()
    {
        imageView.setImageBitmap(image);
    }

    private Bitmap getGreyscale2(Bitmap bm)
    {
        int w = bm.getWidth();
        int h = bm.getHeight();

        Bitmap newBM = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);

        int color = -66;
        for(int x = 0; x < w; x++)
        {
            for(int y = 0; y < h; y++)
            {
                int pixel = bm.getPixel(x,y);
                int r = Color.red(pixel);
                int g = Color.green(pixel);
                int b = Color.blue(pixel);
                int avg = Math.max(Math.max(r,g),b) + Math.min(Math.min(r,g),b);
                avg /= 2;
                color = Color.rgb(avg,avg,avg);
                newBM.setPixel(x, y, color);
            }
        }

        return newBM;
    }

    private Bitmap getGreyscale3(Bitmap bm)
    {
        int w = bm.getWidth();
        int h = bm.getHeight();

        Bitmap newBM = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);

        int color = -66;
        for(int x = 0; x < w; x++)
        {
            for(int y = 0; y < h; y++)
            {
                int pixel = bm.getPixel(x,y);
                int r = Color.red(pixel);
                int g = Color.green(pixel);
                int b = Color.blue(pixel);
                int avg = (int)(0.21*r + 0.72*g + 0.07*b);
                color = Color.rgb(avg,avg,avg);
                newBM.setPixel(x, y, color);
            }
        }

        return newBM;
    }

    public void greyscale2()
    {
        if(!checkImage())
        {
            Toast.makeText(MainActivity.this,"No image loaded",Toast.LENGTH_SHORT);
            return;
        }
        Bitmap bm = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        Bitmap newBM = getGreyscale2(bm);
        imageView.setImageBitmap(newBM);
    }

    public void greyscale3()
    {
        if(!checkImage())
        {
            Toast.makeText(MainActivity.this,"No image loaded",Toast.LENGTH_SHORT);
            return;
        }
        Bitmap bm = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        Bitmap newBM = getGreyscale3(bm);
        imageView.setImageBitmap(newBM);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_greyscale:
                greyscale();
                return true;
            case R.id.action_inverse_colors:
                inverseColors();
                return true;
            case R.id.action_restore:
                restore();
                return true;
            case R.id.greyscale2:
                greyscale2();
                return true;
            case R.id.greyscale3:
                greyscale3();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
