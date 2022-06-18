package com.example.topic;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class Bookmark_ArticleFragment extends Fragment {


    ArrayList<Contents> contents;
    RecyclerView customListView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter customAdapter;
    private Context ct;

    // TODO: Rename and change types of parameters

    public Bookmark_ArticleFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_youtube, container, false);
        customListView = (RecyclerView) rootView.findViewById(R.id.listView_custom);
        customListView.setHasFixedSize(true);           //리사이클러뷰 기존성능  강화
        ct = container.getContext();
        layoutManager = new LinearLayoutManager(ct);



        //data를 가져와서 어답터와 연결해 준다. 서버에서 가져오는게 대부분 이다.
        contents = new ArrayList<>();
        //북마크 기사 추가 해야함.

        customAdapter = new CustomAdapter(contents, ct);
        customListView.setAdapter(customAdapter);
        //customListView.setOn


        return rootView;


    }
}