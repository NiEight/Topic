package com.example.topic;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class CheckPassword extends AppCompatActivity {
    private EditText edit_pw_check;
    private ImageButton pwCheckBtn;
    private TextView forget_password;
    private Button backBtn;

    private String email, password;
    private String URL = "http://10.0.2.2/topick/pwcheck.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_password);

        edit_pw_check = findViewById(R.id.edit_pw_check);
        pwCheckBtn = findViewById(R.id.pwCheckBtn);
        forget_password = findViewById(R.id.forget_password);
        backBtn = findViewById(R.id.backBtn);

        Intent inIntent = getIntent();
        email = inIntent.getStringExtra("email");

        //뒤로가기
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //비밀번호 찾기
        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckPassword.this, FindPw.class);
                startActivity(intent);
            }
        });

        //editText에 입력된 값이 있는지 체크
        edit_pw_check.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //입력하기 전에
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //입력되는 텍스트에 변화가 있을 때

            }

            @Override
            public void afterTextChanged(Editable s) {
                //입력이 끝났을 때
                if(!edit_pw_check.getText().toString().equals("")) {
                    pwCheckBtn.setImageResource(R.drawable.activate_btn_ok);
                } else {
                    pwCheckBtn.setImageResource(R.drawable.deactivate_btn_ok);
                }
            }
        });

        //비밀번호 확인 버튼
        pwCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = edit_pw_check.getText().toString().trim();

                //비밀번호가 입력되었는지 확인
                if(!password.equals("")) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("success")) {   //비밀번호가 일치할 경우
                                Intent intent = new Intent(CheckPassword.this, ChangePassword.class);
                                intent.putExtra("email", email);
                                startActivity(intent);
                            } else if (response.equals("failure")) {    //비밀번호가 틀린 경우
                                Toast.makeText(CheckPassword.this, "비밀번호가 잘못됐습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(CheckPassword.this, error.toString().trim() ,Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Nullable
                        @Override
                        //HashMap으로 php를 통해 DB에 데이터 전달
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> data = new HashMap<>();
                            data.put("email", email);
                            data.put("password", password);
                            return data;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                } else {
                    Toast.makeText(CheckPassword.this, "비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
