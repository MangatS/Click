package com.example.sikandermangat.click.Model;

import android.widget.ImageView;

import java.io.Serializable;

public class StoryClass implements Serializable {

    private String storyImage;
    private String storyTitle;
    private String user_id;

    public StoryClass() {


    }

    public String getStoryImaage() {
        return storyImage;
    }

    public void setStoryImaage(String storyImage) {
        this.storyImage = storyImage;
    }

    public String getStoryTitle() {
        return storyTitle;
    }

    public void setStoryTitle(String storyTitle) {
        this.storyTitle = storyTitle;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }









}
