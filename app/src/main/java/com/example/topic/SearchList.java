package com.example.topic;

import android.graphics.drawable.Drawable;
//서치 리스트를 set하고 get하기 위함.
public class SearchList {

    private Drawable iconDrawble, clearicon;
    private String titleStr;

    public void setIcon(Drawable icon){
        iconDrawble = icon;
    }
    public void setClearIcon(Drawable icon){
        clearicon = icon;
    }

    public void setTitle(String title){
        titleStr = title;
    }
    public String getTitle(){
        return this.titleStr;
    }
    public Drawable getIcon(){
        return this.iconDrawble;
    }
    public Drawable getClearIcon(){
        return this.clearicon;
    }
}
