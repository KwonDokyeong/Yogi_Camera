package com.example.dkdk6.alwayon0906;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageClick extends Activity{
    private BitmapDrawable dr;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_click);
        Intent intent = getIntent();
        int image = intent.getExtras().getInt("image");
       // Bitmap bigPictureBitmap  = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo);
      //  Mat original_image = Highgui.imread()
        ImageView imageView = (ImageView)findViewById(R.id.R_image);
        Drawable drawable = getDrawable(image);
        imageView.setImageDrawable(drawable);
       // imageView.setImageBitmap(bigPictureBitmap);
        String s = String.valueOf(image);
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
}
