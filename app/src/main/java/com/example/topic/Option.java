package com.example.topic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Option extends AppCompatActivity {
    Button backBtn;
    TextView pwChangedBtn, logout, deleteMyAccount, apkInfo, support;

    private String email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option);

        Intent inIntent = getIntent();
        email = inIntent.getStringExtra("email");

        backBtn = findViewById(R.id.backBtn);

        pwChangedBtn = findViewById(R.id.pwChangedBtn);
        logout = findViewById(R.id.logout);
        deleteMyAccount = findViewById(R.id.deleteMyAccount);
        apkInfo = findViewById(R.id.apkInfo);
        support = findViewById(R.id.support);

        //뒤로가기
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //비밀번호 변경
        pwChangedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CheckPassword.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

        //로그아웃
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //회원탈퇴
        deleteMyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Unregister.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

        //앱 정보
        apkInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TopickInfo.class);
                startActivity(intent);
            }
        });

        //문의하기 ACTION_SEND를 통해 이메일 보내기 구현
        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("email","email: Send");

                Intent email = new Intent(Intent.ACTION_SEND);

                email.setType("plain/text");
                String[] address = {"98six.park@kakao.com"};
                email.putExtra(Intent.EXTRA_EMAIL, address);
                email.putExtra(Intent.EXTRA_TEXT, "Topick을 이용하면서 발생하신 문제점을 적어주세요.");
                startActivity(email);
            }
        });
    }
}
