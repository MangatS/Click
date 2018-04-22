package com.example.sikandermangat.click;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sikandermangat.click.Adapters.MessageAdapter;
import com.example.sikandermangat.click.Model.ChatMessage;
import com.example.sikandermangat.click.Model.UserClass;
import com.example.sikandermangat.click.Services.FirebaseDataBaseReferences;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {


    private List<ChatMessage> list=new ArrayList<>();
    private  RecyclerView recyclerView;
    private FirebaseDataBaseReferences firebaseDatabase;
    private DatabaseReference ref;
    MessageAdapter messageAdapter;
    private String friendId;
    private String friendName;
    private TextView name;
    private String myName;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent=getIntent();

        friendId=intent.getStringExtra("userId");

        friendName=intent.getStringExtra("userName");

        name=findViewById(R.id.friend_name);

        name.setText(friendName);

        firebaseDatabase=new FirebaseDataBaseReferences();
        ref=firebaseDatabase.getMyRef();

        messageAdapter=new MessageAdapter(list);

        recyclerView=findViewById(R.id.list_of_messages);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(messageAdapter);

        mLayoutManager.setItemPrefetchEnabled(false);



        ref.child("Messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                list.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                    ChatMessage chatMessage=snapshot.getValue(ChatMessage.class);

                    if(chatMessage.getMessageId().isEmpty()){



                    }
                    else {

                        if (chatMessage.getMessageId().contains(friendId + FirebaseAuth.getInstance().getCurrentUser().getUid())) {

                            list.add(chatMessage);

                            messageAdapter.notifyDataSetChanged();

                        }
                      else  if (chatMessage.getMessageId().contains(FirebaseAuth.getInstance().getCurrentUser().getUid()+friendId)) {

                            list.add(chatMessage);

                            messageAdapter.notifyDataSetChanged();

                        }
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        ref.child("Users").orderByChild("userId").equalTo(firebaseDatabase.getAuth().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()) {

                    UserClass userClass=snapshot.getValue(UserClass.class);

                    myName=userClass.getUserName();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        FloatingActionButton fab =
                (FloatingActionButton)findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                EditText input = (EditText)findViewById(R.id.input);

                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                FirebaseDatabase.getInstance()
                        .getReference()
                        .child("Messages")
                        .push()
                        .setValue(new ChatMessage(input.getText().toString(),myName,friendId+FirebaseAuth.getInstance().getCurrentUser().getUid())
                        );

                // Clear the input
                input.setText("");
            }
        });
    }




}
