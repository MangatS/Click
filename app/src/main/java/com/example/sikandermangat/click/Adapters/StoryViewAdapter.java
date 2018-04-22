package com.example.sikandermangat.click.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sikandermangat.click.Image;
import com.example.sikandermangat.click.Model.StoryClass;
import com.example.sikandermangat.click.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class StoryViewAdapter extends BaseAdapter {

    private Context context;
    private FirebaseStorage firebaseStorage;
    private StorageReference ref;
    private ProgressBar progressBar;
    private ImageView  image;
    private TextView   storyTitle;
    private    Bitmap bmp;
    List<StoryClass> list=new ArrayList<>();

    public StoryViewAdapter(Context context,List<StoryClass> list) {

        this.context=context;
        this.list=list;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.stories_row, null);
        }

         storyTitle = (TextView)convertView.findViewById(R.id.storyTitle);
         image =  (ImageView)convertView.findViewById(R.id.story_image);
         progressBar=convertView.findViewById(R.id.storyProgress);
         RelativeLayout layout=(RelativeLayout)convertView.findViewById(R.id.story_list_layout);

         if(list.size()>0) {

             ref = firebaseStorage.getInstance().getReference().child(list.get(position).getStoryImaage());


             final long ONE_MEGABYTE = 2048 * 2048;
             ref.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                 @Override
                 public void onSuccess(byte[] bytes) {
                     bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                     image.setScaleType(ImageView.ScaleType.CENTER_CROP);
                     image.setImageBitmap(bmp);
                     //image.setImageBitmap(Bitmap.createScaledBitmap(bmp, image.getWidth(),image.getHeight(), false));
                     storyTitle.setText("Story");
                     progressBar.setVisibility(View.GONE);
                 }
             }).addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull Exception exception) {
                     // Handle any errors
                     progressBar.setVisibility(View.GONE);
                     Toast.makeText(context, "Unable to unload picture", Toast.LENGTH_SHORT);
                 }
             });
         }
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                Intent intent=new Intent(context,Image.class);
                intent.putExtra("picture", byteArray);
                context.startActivity(intent);

            }
        });

        return convertView;
    }
}
