package com.example.topic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private YoutubeFragment youtubeFragment;
    private ArticleFragment articleFragment;

    private String originUrl, vodid = "";
    private String intentSearch;

    final String API_KEY = "이곳에 여러분의 youtube api 키를 넣어주세요";
    private String videoID = "";

    Button option, search, bookmark;

    Button tag_btn1, tag_btn2, tag_btn3;

    String tag = "뉴스";
    String[] sp = new String[1];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //버튼구현
        option = findViewById(R.id.option_button);
        search = findViewById(R.id.search_button);
        bookmark = findViewById(R.id.bookmark_button);

        //태그 버튼
        tag_btn1 = findViewById(R.id.tag_btn1);
        tag_btn2 = findViewById(R.id.tag_btn2);
        tag_btn3 = findViewById(R.id.tag_btn3);
        //옵션 버튼
        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   Intent intent = new Intent(getApplicationContext(), Name_rule.class);
                startActivity(intent);
                */

            }
        });
        //북마크 버튼
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), Bookmark.class);
                startActivity(intent);


            }
        });
        //검색 버튼
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Search.class);
                startActivity(intent);
            }
        });

        Intent intent= getIntent();
        tag = intent.getStringExtra("query");


        //태그 버튼 1,2,3

        tag_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp = tag_btn1.getText().toString().split("#");
                tag = sp[1];
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                intent.putExtra("query",tag);
                startActivity(intent);


            }
        });
        tag_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp = tag_btn2.getText().toString().split("#");
                tag = sp[1];
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                intent.putExtra("query",tag);
                startActivity(intent);


            }
        });
        tag_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp = tag_btn3.getText().toString().split("#");
                tag = sp[1];
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                intent.putExtra("query",tag);
                startActivity(intent);


            }
        });

        //탭 구현
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        youtubeFragment = new YoutubeFragment(tag);
        articleFragment = new ArticleFragment(tag);

        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(youtubeFragment, "Yotube");
        viewPagerAdapter.addFragment(articleFragment, "Article");
        viewPager.setAdapter(viewPagerAdapter);




    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitles = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            fragmentTitles.add(title);

        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles.get(position);
        }


    }


}

