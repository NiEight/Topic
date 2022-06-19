package com.example.topic;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import android.content.Intent;
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
//검색 화면
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
        listview = (ListView) findViewById(R.id.search_List);



       ;

        searchView = findViewById(R.id.search_bar);
        //검색 결과 전달
        //어뎁터에를 통해 리스트뷰에 전달
        //검색 결과는 메인 화면에 전달
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.addItem(ContextCompat.getDrawable(getApplicationContext(),R.drawable.search),
                        ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_baseline_clear_24) ,query);
                listview.setAdapter(adapter);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                intent.putExtra("query",query);
                startActivity(intent);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });



    }


    }

