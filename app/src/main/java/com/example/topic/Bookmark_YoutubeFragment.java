package com.example.topic;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    // TODO: Rename and change types of parameters

    public Bookmark_YoutubeFragment() {
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



        //data를 가져와서 어답터와 연결
        contents = new ArrayList<>();
        //북마크 콘텐츠 추가 해야함.

        customAdapter = new CustomAdapter(contents);

        customAdapter.setOnItemClickListener(new CustomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                //클릭 이벤트 구현
            }
        });
        customListView.setAdapter(customAdapter);
        return rootView;


    }

}

