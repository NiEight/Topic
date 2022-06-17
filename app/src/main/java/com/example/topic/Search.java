package com.example.topic;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;


import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {

    ListView listview;
    SearchView searchView;
    Button backButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ListViewAdapter adapter;
        // Adapter 생성
        adapter = new ListViewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.search_List);



       ;

        searchView = findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.addItem(ContextCompat.getDrawable(getApplicationContext(),R.drawable.search),
                        ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_baseline_clear_24) ,query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        listview.setAdapter(adapter);


    }


    }

