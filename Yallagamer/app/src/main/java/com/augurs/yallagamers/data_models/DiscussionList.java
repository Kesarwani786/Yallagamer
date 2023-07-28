package com.augurs.yallagamers.data_models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DiscussionList {

    @SerializedName("discussions")
    ArrayList<Discussion> discussions;

    public ArrayList<Discussion> getDiscussions() {
        return discussions;
    }

    public void setDiscussions(ArrayList<Discussion> discussions) {
        this.discussions = discussions;
    }
}
