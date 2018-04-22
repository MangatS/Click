package com.example.sikandermangat.click;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Image extends AppCompatActivity {

    private ImageView storyView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        Bundle extras = getIntent().getExtras();
        byte[] byteArray = extras.getByteArray("picture");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        ImageView image = (ImageView) findViewById(R.id.storyView);
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        image.setImageBitmap(bmp);

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        int action = ev.getActionMasked();

        switch (action) {

        case MotionEvent.ACTION_DOWN:

            finish();

           break;



    }

    return super.onTouchEvent(ev);
    }
}
