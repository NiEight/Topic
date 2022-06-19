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
//MainActivity에서 북마크 추가 결과를 보는 화면
//북마크 버튼을 통해 이동 가능
public class Bookmark extends AppCompatActivity {


    Button option, search;

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Bookmark_YoutubeFragment bookmark_youtubeFragment;
    private Bookmark_ArticleFragment bookmark_articleFragment;
    private String email;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmark);

        //옵션 버튼과 검색 버튼
        option = findViewById(R.id.bookmark_option_button);
        search = findViewById(R.id.bookmark_search_button);

        //로그인한 사용자의 이메일을 다음화면에 전송
        Intent inIntent = getIntent();
        email = inIntent.getStringExtra("email");

        //옵션 버튼 터치시 옵션 화면으로 이동
        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Option.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

        //검색 버튼 터치시 검색 화면으로 이동
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Search.class);
                startActivity(intent);

            }
        });


        //북마크 탭 화면
        //탭 구현
        //MainActivity와 동일
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = findViewById(R.id.bookmark_view_pager);
        tabLayout = findViewById(R.id.bookmark_tab_layout);

        bookmark_youtubeFragment = new Bookmark_YoutubeFragment();

        bookmark_articleFragment = new Bookmark_ArticleFragment();

        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(bookmark_youtubeFragment, "Yotube");
        viewPagerAdapter.addFragment(bookmark_articleFragment, "Article");
        viewPager.setAdapter(viewPagerAdapter);



    }

    //MainActivity와 동일
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
