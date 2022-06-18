package com.example.topic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class Bookmark extends AppCompatActivity {


    Button option, search;

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Bookmark_YoutubeFragment bookmark_youtubeFragment;
    private Bookmark_ArticleFragment bookmark_articleFragment;
    String vidold, name, thumb_url, summary;
    int y_bookMark_count;

    String title, description, link, bookMark_count;
    int a_bookMark_count;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmark);

        //옵션 버튼과 검색 버튼
        option = findViewById(R.id.bookmark_option_button);
        search = findViewById(R.id.bookmark_search_button);


        Intent intent = getIntent();

        vidold = intent.getStringExtra("vidold");
        name = intent.getStringExtra("name");
        thumb_url = intent.getStringExtra("thumb_url");
        summary = intent.getStringExtra("summary");
        y_bookMark_count = intent.getIntExtra("bookMark_count",0);
        Log.d("str"," "+ y_bookMark_count);


        title = intent.getStringExtra("title");
        description = intent.getStringExtra("description");
        link= intent.getStringExtra("link");
        a_bookMark_count = intent.getIntExtra("a_bookMark_count",0);



        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Option.class);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Search.class);
                startActivity(intent);

            }
        });


        //북마크 탭 화면
        //탭 구현
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = findViewById(R.id.bookmark_view_pager);
        tabLayout = findViewById(R.id.bookmark_tab_layout);

        bookmark_youtubeFragment = new Bookmark_YoutubeFragment(vidold,name,thumb_url,summary,y_bookMark_count);

        bookmark_articleFragment = new Bookmark_ArticleFragment(title, description, link, a_bookMark_count);

        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(bookmark_youtubeFragment, "Yotube");
        viewPagerAdapter.addFragment(bookmark_articleFragment, "Article");
        viewPager.setAdapter(viewPagerAdapter);



    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitles = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title){
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
