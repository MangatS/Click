package com.example.sikandermangat.click.Views;

import android.animation.ArgbEvaluator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.sax.EndElementListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.sikandermangat.click.Fragments.ChatFragment;
import com.example.sikandermangat.click.R;

public class ClickTabsView extends FrameLayout implements ViewPager.OnPageChangeListener {


    private ImageView CameraImage;
    private ImageView ChatImage;
    private ImageView StoryImage;
    private ImageView GalleryImage;
    private ImageView front;
    private ImageView rear;
    private View indicator;
    private ArgbEvaluator argbEvaluator;
    private int CenterColor;
    private int SideColor;
    private int EndViewTransX;
    private int IndicatorTransX;
    private int CenterTransY;
    public ClickTabsView(@NonNull Context context) {
        this(context,null);
    }

    public ClickTabsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ClickTabsView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    public void setUpWithViewPager(ViewPager viewPager){

        viewPager.addOnPageChangeListener(this);

    }
    public void init() {

        LayoutInflater.from(getContext()).inflate(R.layout.view_tabs_click, this, true);

        front = (ImageView) findViewById(R.id.front);
        rear = (ImageView) findViewById(R.id.rear);
        CameraImage = (ImageView) findViewById(R.id.camera_image);
        ChatImage = (ImageView) findViewById(R.id.chat_image);
        StoryImage = (ImageView) findViewById(R.id.story_image);
        GalleryImage = (ImageView) findViewById(R.id.gallery_image);
        indicator = findViewById(R.id.indicator);
        CenterColor = ContextCompat.getColor(getContext(), R.color.white);
        SideColor = ContextCompat.getColor(getContext(), R.color.dark_grey);
        argbEvaluator = new ArgbEvaluator();
        IndicatorTransX = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, getResources().getDisplayMetrics());

        GalleryImage.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressLint("NewApi")
            @Override
            public void onGlobalLayout() {
                EndViewTransX= (int) ((GalleryImage.getX()- ChatImage.getX())-IndicatorTransX);
                GalleryImage.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                CenterTransY=getHeight()-GalleryImage.getBottom();
            }
        });

    }
        private void setColor(float fraction){

        int color=(int)argbEvaluator.evaluate(fraction,CenterColor,SideColor);

            Drawable cameraDrawable = getContext().getResources().getDrawable(R.drawable.camera_icon);
            Drawable chatDrawable = getContext().getResources().getDrawable(R.drawable.chat_bubble);
            Drawable storyDrawable = getContext().getResources().getDrawable(R.drawable.story_icon);

            cameraDrawable.setColorFilter(new
                    PorterDuffColorFilter(color,PorterDuff.Mode.MULTIPLY));
            chatDrawable.setColorFilter(new
                    PorterDuffColorFilter(color,PorterDuff.Mode.MULTIPLY));
            storyDrawable.setColorFilter(new
                    PorterDuffColorFilter(color,PorterDuff.Mode.MULTIPLY));

            CameraImage.setImageDrawable(cameraDrawable);
            ChatImage.setImageDrawable(chatDrawable);
            StoryImage.setImageDrawable(storyDrawable);


         }

         private void moveViews(float fraction){

           ChatImage.setTranslationX(fraction*EndViewTransX);
           StoryImage.setTranslationX(-fraction*EndViewTransX);
           indicator.setAlpha(fraction);
           indicator.setScaleX(fraction);

         }

         private  void moveScale(float fraction){

        float scale=.7f+((1-fraction)*.3f);

        CameraImage.setScaleX(scale);
        CameraImage.setScaleY(scale);

        int translation=(int)(fraction*CenterTransY);

        CameraImage.setTranslationY(translation);
        GalleryImage.setTranslationY(translation);

        GalleryImage.setAlpha(1-fraction);

         }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        if(position==0){
            setColor(1-positionOffset);
            moveViews(1-positionOffset);
            indicator.setTranslationX((positionOffset-1)*IndicatorTransX);
            moveScale(1-positionOffset);
            rear.setVisibility(View.GONE);
            front.setVisibility(View.GONE);
            }
            else if(position==1){

            setColor(positionOffset);
            moveViews(positionOffset);
            indicator.setTranslationX(positionOffset*IndicatorTransX);
            moveScale(positionOffset);
            front.setVisibility(View.VISIBLE);
            }
            else if(position==2){

            rear.setVisibility(View.GONE);
            front.setVisibility(View.GONE);

        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
