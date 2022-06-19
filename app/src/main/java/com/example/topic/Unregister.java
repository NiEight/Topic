package com.example.topic;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import org.w3c.dom.Text;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Unregister extends AppCompatActivity {

    private EditText edit_pw_check;
    private ImageButton unregisterBtn;
    private TextView forget_password;
    private Button backBtn;

    private String URL = "http://10.0.2.2/topick/unregister.php";
    private String email, password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unregister);

        Intent inIntent  = getIntent();
        email = inIntent.getStringExtra("email");

        edit_pw_check = findViewById(R.id.edit_pw_check);
        unregisterBtn = findViewById(R.id.unregisterBtn);
        forget_password = findViewById(R.id.forget_password);
        backBtn = findViewById(R.id.backBtn);

        //뒤로가기
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Unregister.this, FindPw.class);
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
                    unregisterBtn.setImageResource(R.drawable.activate_unregister_btn);
                } else {
                    unregisterBtn.setImageResource(R.drawable.deactivate_unregister_btn);
                }
            }
        });

        unregisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = edit_pw_check.getText().toString().trim();
                
                if(!password.equals("")) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("success")) {
                                Toast.makeText(Unregister.this, "토픽을 이용해 주셔서 감사합니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Unregister.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else if (response.equals("failure")) {
                                Toast.makeText(Unregister.this, "비밀번호가 잘못됐습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Unregister.this, error.toString().trim() ,Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Nullable
                        @Override
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
                    Toast.makeText(Unregister.this, "비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
