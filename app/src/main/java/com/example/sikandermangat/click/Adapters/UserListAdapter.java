package com.example.sikandermangat.click.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sikandermangat.click.ChatActivity;
import com.example.sikandermangat.click.Model.ChatMessage;
import com.example.sikandermangat.click.Model.UserClass;
import com.example.sikandermangat.click.R;
import com.example.sikandermangat.click.Services.FirebaseDataBaseReferences;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyHolder> {


   List<UserClass> list=new ArrayList<>();
   private Context context;
   public  UserListAdapter(Context context,List<UserClass> list){

       this.list=list;
       this.context=context;

   }


    @NonNull
    @Override
    public UserListAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row, parent, false);

        return new UserListAdapter.MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.MyHolder holder, int position) {

       final UserClass userClass=list.get(position);

       holder.userName.setText(userClass.getUserName());
       holder.userMessage.setText("Tap to pen");
       holder.layout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent screen=new Intent(context, ChatActivity.class);

               screen.putExtra("userName",userClass.getUserName());
               screen.putExtra("userId",userClass.getUserId());


               context.startActivity(screen);


           }
       });

        FirebaseDataBaseReferences firbase=new FirebaseDataBaseReferences();
        DatabaseReference ref=firbase.getMyRef();



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{


        TextView userName,userMessage;
        ImageView userPic;
        RelativeLayout layout;
        public MyHolder(View itemView) {
            super(itemView);

            userName=itemView.findViewById(R.id.user_name);
            userMessage=itemView.findViewById(R.id.user_message);
            userPic=itemView.findViewById(R.id.user_pic);
            layout=itemView.findViewById(R.id.user_list_layout);
        }
    }
}
