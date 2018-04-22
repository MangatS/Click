package com.example.sikandermangat.click.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.example.sikandermangat.click.Adapters.StoryViewAdapter;
import com.example.sikandermangat.click.Model.StoryClass;
import com.example.sikandermangat.click.R;
import com.example.sikandermangat.click.Services.FirebaseDataBaseReferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StoryFragment extends BaseFragment {

    private GridView stories;
    private List<StoryClass> list=new ArrayList<>();
    private SearchView search;
    private TextView title;
    private FirebaseDataBaseReferences firbase;
    private DatabaseReference ref;
    public static StoryFragment create(){

        return new StoryFragment();

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_story;
    }

    @Override
    public void inOnCreateView(@NonNull View view, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {


        firbase=new FirebaseDataBaseReferences();
        ref=firbase.getMyRef();
        stories=view.findViewById(R.id.stories);
        search=view.findViewById(R.id.search);
        title=view.findViewById(R.id.title);
        final StoryViewAdapter storyViewAdapter=new StoryViewAdapter(getContext(),list);
        stories.setAdapter(storyViewAdapter);
        storyViewAdapter.notifyDataSetChanged();
        search.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title.setVisibility(View.GONE);
            }
        });


        search.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                title.setVisibility(View.VISIBLE);
                return false;
            }
        });


        ref.child("Stories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                list.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                    StoryClass storyClass=snapshot.getValue(StoryClass.class);

                    list.add(storyClass);
                    storyViewAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
