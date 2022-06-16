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


public class YoutubeFragment extends Fragment{

    ArrayList<Contents> contents;
    RecyclerView customListView;
    RecyclerView.LayoutManager layoutManager;
    CustomAdapter customAdapter;
    private Context ct;

    // TODO: Rename and change types of parameters

    public YoutubeFragment() {
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
        contents.add(new Contents("Robert Downey Jr.", "https://image.tmdb.org/t/p/w600_and_h900_bestv2/5qHNjhtjMD4YWH3UP0rm4tKwxCL.jpg", "로버트 존 다우니 2세는 미국의 배우, 영화 제작자, 극작가이자, 싱어송라이터, 코미디언이다. 스크린 데뷔작은 1970년 만 5세 때 아버지 로버트 다우니 시니어의 영화 작품 《파운드》이다."));
        contents.add(new Contents("Scarlett Johansson", "https://image.tmdb.org/t/p/w600_and_h900_bestv2/6NsMbJXRlDZuDzatN2akFdGuTvx.jpg", "1984년 뉴욕에서 태어난 스칼렛 요한슨은 여덟 살 때 에단 호크가 주연한 〈소피스트리〉라는 연극에 출연하면서 연기를 시작했다. 로버트 레드포드 감독의 〈호스 위스퍼러〉에서 경주 사고로 정신적인 충격을 받은 십대 소녀 그레이스를 연기해 전세계적으로 알려진 스칼렛 요한슨은 소피아 코폴라 감독의 〈사랑도 통역이 되나요?〉로 2003 베니스 영화제 여우주연상을 수상해 세계의 주목을 받는 기대주가 되었다."));
        contents.add(new Contents("Cho Yeo-jeong", "https://image.tmdb.org/t/p/w600_and_h900_bestv2/5MgWM8pkUiYkj9MEaEpO0Ir1FD9.jpg", "Cho Yeo-jeong (조여정) is a South Korean actress. Born on February 10, 1981, she began her career as a model in 1997 at the age of 16 and launched her acting career two years later. She is best known for her roles in the provocative period films “The Servant” (2010) and “The Concubine” (2012) and the television dramas “I Need Romance” (2011), “Haeundae Lovers” (2012), “Divorce Lawyer in Love” (2015) and “Perfect Wife” (2017)."));
        contents.add(new Contents("Scarlett Johansson", "https://image.tmdb.org/t/p/w600_and_h900_bestv2/6NsMbJXRlDZuDzatN2akFdGuTvx.jpg", "1984년 뉴욕에서 태어난 스칼렛 요한슨은 여덟 살 때 에단 호크가 주연한 〈소피스트리〉라는 연극에 출연하면서 연기를 시작했다. 로버트 레드포드 감독의 〈호스 위스퍼러〉에서 경주 사고로 정신적인 충격을 받은 십대 소녀 그레이스를 연기해 전세계적으로 알려진 스칼렛 요한슨은 소피아 코폴라 감독의 〈사랑도 통역이 되나요?〉로 2003 베니스 영화제 여우주연상을 수상해 세계의 주목을 받는 기대주가 되었다."));
        contents.add(new Contents("Robert Downey Jr.", "https://image.tmdb.org/t/p/w600_and_h900_bestv2/5qHNjhtjMD4YWH3UP0rm4tKwxCL.jpg", "로버트 존 다우니 2세는 미국의 배우, 영화 제작자, 극작가이자, 싱어송라이터, 코미디언이다. 스크린 데뷔작은 1970년 만 5세 때 아버지 로버트 다우니 시니어의 영화 작품 《파운드》이다."));


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

