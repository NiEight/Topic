package com.example.topic;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
//Youtube API를 사용한 Youtube 재생 클래스
public class UtubePlay extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener {

    private YouTubePlayerView ytpv;
    private YouTubePlayer ytp;
    final String serverKey="4791172370340c7aecacc84ec506ed4f"; //콘솔에서 받아온 서버키

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utubeplay);
        ytpv = (YouTubePlayerView) findViewById(R.id.playerView);
        //서버키를 적용하여 뷰어에 재생
        ytpv.initialize(serverKey, this);
    }



    //재생 실패
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider arg0,
                                        YouTubeInitializationResult arg1) {
        Toast.makeText(this,"Initialization Fail", Toast.LENGTH_LONG).show();
    }

    //재생 성공
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer player, boolean wasrestored) {
        ytp = player;

        Intent gt = getIntent();
        ytp.loadVideo(gt.getStringExtra("id"));
    }

}