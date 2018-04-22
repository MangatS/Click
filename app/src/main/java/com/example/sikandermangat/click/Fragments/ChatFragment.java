package com.example.sikandermangat.click.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.sikandermangat.click.Adapters.UserListAdapter;
import com.example.sikandermangat.click.ChatActivity;
import com.example.sikandermangat.click.FriendsActivity;
import com.example.sikandermangat.click.InfoActivity;
import com.example.sikandermangat.click.Model.UserClass;
import com.example.sikandermangat.click.R;
import com.example.sikandermangat.click.Services.FirebaseDataBaseReferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends  BaseFragment {

    private ImageView chat;
    private ImageView profile;
    private RecyclerView recyclerView;
    private List<UserClass> list=new ArrayList<>();
    private DatabaseReference ref;
    private FirebaseDataBaseReferences firbase;
    private   UserListAdapter userListAdapter;
    public static ChatFragment create(){

        return new ChatFragment();

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_chat;
    }

    @Override
    public void inOnCreateView(@NonNull View view, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {

        firbase=new FirebaseDataBaseReferences();

        ref=firbase.getMyRef();

        userListAdapter=new  UserListAdapter(getContext(),list);

        recyclerView=view.findViewById(R.id.list_of_user);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(userListAdapter);

        chat=view.findViewById(R.id.chatAdd);
        mLayoutManager.setItemPrefetchEnabled(false);
        profile=view.findViewById(R.id.profile_pic);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent screen=new Intent(getContext(),InfoActivity.class);

                startActivity(screen);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent screen=new Intent(getContext(),FriendsActivity.class);

                startActivity(screen);
            }
        });


        ref.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                list.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                    UserClass userClass=snapshot.getValue(UserClass.class);

                    list.add(userClass);

                    userListAdapter.notifyDataSetChanged();

                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
