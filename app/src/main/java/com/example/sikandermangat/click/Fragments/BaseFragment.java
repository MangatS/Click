package com.example.sikandermangat.click.Fragments;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         view=inflater.inflate(getLayoutResId(),container,false);

         inOnCreateView(view,container,savedInstanceState);
         return view;
    }

    @LayoutRes
    public  abstract int getLayoutResId();

    public  abstract  void inOnCreateView(@NonNull View view,@NonNull ViewGroup container,@NonNull Bundle savedInstanceState);
}
