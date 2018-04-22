package com.example.sikandermangat.click;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.sikandermangat.click.Adapters.UserListAdapter;
import com.example.sikandermangat.click.Model.UserClass;
import com.example.sikandermangat.click.Services.FirebaseDataBaseReferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FriendsActivity extends AppCompatActivity {
    private ImageView close;
    private RecyclerView recyclerView;
    private List<UserClass> list=new ArrayList<>();
    private DatabaseReference ref;
    private FirebaseDataBaseReferences firebase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        firebase=new FirebaseDataBaseReferences();
        ref=firebase.getMyRef();

        final UserListAdapter userListAdapter=new  UserListAdapter(this,list);

        recyclerView=findViewById(R.id.newChat);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(userListAdapter);

        mLayoutManager.setItemPrefetchEnabled(false);

        close=findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

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
