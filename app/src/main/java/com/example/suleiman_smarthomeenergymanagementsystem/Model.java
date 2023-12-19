package com.example.suleiman_smarthomeenergymanagementsystem;

import com.google.firebase.database.Exclude;

public class Model {
    String title, date, time;
    public String mKey;
    public Model() {
    }
    public Model(String title, String date, String time) {
        this.title = title;
        this.date = date;
        this.time = time;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    @Exclude
    public String getKey() {
        return mKey;
    }
    @Exclude
    public void setKey(String Key) {
        mKey = Key;
    }
}
