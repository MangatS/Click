package com.example.sikandermangat.click;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.camera2.CameraDevice;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sikandermangat.click.Adapters.MainPageAdapter;
import com.example.sikandermangat.click.Model.CameraPreview;
import com.example.sikandermangat.click.Model.StoryClass;
import com.example.sikandermangat.click.Model.UserClass;
import com.example.sikandermangat.click.Services.FirebaseDataBaseReferences;
import com.example.sikandermangat.click.Views.ClickTabsView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.sikandermangat.click.Model.CameraPreview.getCameraInstance;

public class MainActivity extends AppCompatActivity {

    private Camera mCamera;
    private CameraPreview mPreview;
    private ImageView camerabtn;
    private ImageView chatbtn;
    private ImageView storybtn;
    private ImageView uploadImage;
    private ImageView sendImage;
    private ImageView closeImage;
    private ImageView rear;
    private ImageView front;
    private byte[] data;
    private ProgressDialog progressDialog;
    private ClickTabsView clickTabsView;
    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            MainActivity.this.data=data;
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            uploadImage.setRotation(90);
            uploadImage.setImageBitmap(bitmap);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCamera = getCameraInstance(0);
        mPreview = new CameraPreview(this, mCamera);
        final FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
        final View background=findViewById(R.id.background_view);
        front=findViewById(R.id.front);
        rear=findViewById(R.id.rear);
        rear.setVisibility(View.GONE);
        clickTabsView=findViewById(R.id.click_tab);
        camerabtn=findViewById(R.id.camera_image);
        chatbtn=findViewById(R.id.chat_image);
        storybtn=findViewById(R.id.story_image);
        uploadImage=findViewById(R.id.uploadImage);
        sendImage=findViewById(R.id.sendImage);
        closeImage=findViewById(R.id.closeImage);
        uploadImage.setVisibility(View.GONE);
        sendImage.setVisibility(View.GONE);
        closeImage.setVisibility(View.GONE);
        final ViewPager viewPager=findViewById(R.id.viewPager);
        MainPageAdapter adapter=new MainPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        final ClickTabsView clickTabsView=(ClickTabsView) findViewById(R.id.click_tab);
        clickTabsView.setUpWithViewPager(viewPager);
        final int colorBlue= ContextCompat.getColor(this,R.color.light_blue);
        final int colorPurple=ContextCompat.getColor(this,R.color.light_purple);
        viewPager.setCurrentItem(1);

        front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                front.setVisibility(View.GONE);
                rear.setVisibility(View.VISIBLE);
                mCamera.stopPreview();
                mCamera = getCameraInstance(1);
                mPreview = new CameraPreview(MainActivity.this, mCamera);
                final FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
                preview.addView(mPreview);

            }
        });

        rear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rear.setVisibility(View.GONE);
                front.setVisibility(View.VISIBLE);
                mCamera.stopPreview();
                mCamera = getCameraInstance(0);
                mPreview = new CameraPreview(MainActivity.this, mCamera);
                final FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
                preview.addView(mPreview);
            }
        });
        closeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                sendImage.setVisibility(View.GONE);
                closeImage.setVisibility(View.GONE);
                uploadImage.setVisibility(View.GONE);
                clickTabsView.setVisibility(View.VISIBLE);
                camerabtn.setVisibility(View.VISIBLE);
                chatbtn.setVisibility(View.VISIBLE);
                storybtn.setVisibility(View.VISIBLE);
                preview.setVisibility(View.VISIBLE);
                mCamera = getCameraInstance(0);
                mPreview = new CameraPreview(MainActivity.this, mCamera);
                FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
                preview.addView(mPreview);
            }
        });
        sendImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camerabtn.setVisibility(View.GONE);
                chatbtn.setVisibility(View.GONE);
                storybtn.setVisibility(View.GONE);
                preview.setVisibility(View.GONE);
                uploadImage.setVisibility(View.VISIBLE);
                sendImage.setVisibility(View.VISIBLE);
                closeImage.setVisibility(View.VISIBLE);
                preview.setVisibility(View.GONE);
                final FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageReference = storage.getReference();
                if(data!= null)
                {
                    final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();
                    String userid=FirebaseAuth.getInstance().getCurrentUser().getUid();

                    FirebaseDataBaseReferences dataBaseReferences=new FirebaseDataBaseReferences();
                    final DatabaseReference databaseReference=dataBaseReferences.getMyRef();
                    final List<UserClass> list=new ArrayList<>();
                    databaseReference.child("Users").orderByChild("userId").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                                UserClass   userClass=snapshot.getValue(UserClass.class);

                                list.add(userClass);

                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });



                    final String key=UUID.randomUUID().toString();
                    StorageReference ref = storageReference.child(key);
                    ref.putBytes(data)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    progressDialog.dismiss();
                                    Toast.makeText(MainActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                                    UserClass userClass=list.get(0);
                                    StoryClass storyClass=new StoryClass();
                                    storyClass.setStoryTitle(userClass.getUserName());
                                    storyClass.setStoryImaage(key);
                                    storyClass.setUser_id(userClass.getUserId());
                                    databaseReference.child("Stories").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(storyClass);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(MainActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                            .getTotalByteCount());
                                    progressDialog.setMessage("Uploaded "+(int)progress+"%");
                                }
                            });
                }

            }
        });
        camerabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
                if(viewPager.getCurrentItem()==1) {
                    mCamera.takePicture(null, null, mPicture);
                    camerabtn.setVisibility(View.GONE);
                    chatbtn.setVisibility(View.GONE);
                    storybtn.setVisibility(View.GONE);
                    preview.setVisibility(View.GONE);
                    uploadImage.setVisibility(View.VISIBLE);
                    sendImage.setVisibility(View.VISIBLE);
                    closeImage.setVisibility(View.VISIBLE);
                    preview.setVisibility(View.GONE);

                }
            }
        });

        chatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });
        storybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if(position==0){

                    background.setBackgroundColor(colorBlue);
                    background.setAlpha(1-positionOffset);
                }
                else if(position==1){

                    background.setBackgroundColor(colorPurple);
                    background.setAlpha(positionOffset);
                }


            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        // Get the Camera instance as the activity achieves full user focus
        if (mCamera == null) {
            mCamera = getCameraInstance(0);
            mPreview = new CameraPreview(this, mCamera);
            FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
            preview.addView(mPreview);// Local method to handle camera init
        }
    }

}
