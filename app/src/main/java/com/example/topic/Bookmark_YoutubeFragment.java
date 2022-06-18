package com.example.topic;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class Bookmark_YoutubeFragment extends Fragment{

    ArrayList<Contents> contents;
    RecyclerView customListView;
    RecyclerView.LayoutManager layoutManager;
    CustomAdapter customAdapter;
    private Context ct;
    int bookmark_count = 0;
    String[] vidold = new String[8];
    String[] name= new String[8];
    String[] thumb_url = new String[8];
    String[] summary = new String[8];


    // TODO: Rename and change types of parameters

    public Bookmark_YoutubeFragment(String vidold,  String name, String thumb_url, String summary, int bookmark_count) {
            this.bookmark_count = bookmark_count;
            this.vidold[bookmark_count] = vidold;
            this.name[bookmark_count] = name;
            this.thumb_url[bookmark_count] = thumb_url;
            this.summary[bookmark_count] = summary;



        }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bookmark__youtube, container, false);
        customListView = (RecyclerView) rootView.findViewById(R.id.listView_custom);
        customListView.setHasFixedSize(true);           //리사이클러뷰 기존성능  강화
        ct = container.getContext();
        layoutManager = new LinearLayoutManager(ct);



        //data를 가져와서 어답터와 연결
        contents = new ArrayList<>();
        int i =0;
        if(i <= bookmark_count && name[i]!=null) {
            contents.add(new Contents(vidold[i], stringToHtmlSign(name[i]), thumb_url[i], stringToHtmlSign(summary[i])));
            customAdapter = new CustomAdapter(contents, ct);
            customListView.setAdapter(customAdapter);
        }



        //북마크 콘텐츠 추가 해야함.


        return rootView;


    }
    //영상 제목을 받아올때 &quot; &#39; 문자가 그대로 출력되기 때문에 다른 문자로 대체 해주기 위해 사용하는 메서드
    private String stringToHtmlSign(String str) {

        return str.replaceAll("&amp;", "[&]")

                .replaceAll("[<]", "&lt;")

                .replaceAll("[>]", "&gt;")

                .replaceAll("&quot;", "'")

                .replaceAll("&#39;", "'");
    }


}

