package com.example.topic;

import android.content.Intent;
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

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends AppCompatActivity {
    private EditText edit_pw_input, edit_pw_check;
    private ImageButton pwChangedBtn;
    private Button backBtn;
    private TextView statusText;

    private String email, password;
    private String URL = "http://10.0.2.2/topick/pwchange.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        edit_pw_input = findViewById(R.id.edit_pw_input);
        edit_pw_check = findViewById(R.id.edit_pw_check);
        pwChangedBtn = findViewById(R.id.pwChangedBtn);
        backBtn = findViewById(R.id.backBtn);
        statusText = findViewById(R.id.statusText);

        Intent inIntent = getIntent();
        email = inIntent.getStringExtra("email");

        //뒤로가기
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
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
                if(edit_pw_check.getText().toString().equals(edit_pw_input.getText().toString().trim())) {
                    pwChangedBtn.setImageResource(R.drawable.activate_btn_ok);
                    statusText.setText("비밀번호가 일치합니다.");
                } else {
                    pwChangedBtn.setImageResource(R.drawable.deactivate_btn_ok);
                    statusText.setText("비밀번호가 일치하지 않습니다.");
                }
            }
        });
        
        pwChangedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = edit_pw_check.getText().toString().trim();
                if(!edit_pw_input.getText().toString().trim().equals("") && !password.equals("")) {
                    if(edit_pw_input.getText().toString().trim().equals(password)) {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equals("success")) {
                                    Toast.makeText(ChangePassword.this, "비밀번호가 변경되었습니다. 다시 로그인하세요.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ChangePassword.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else if (response.equals("failure")) {
                                    Toast.makeText(ChangePassword.this, "비밀번호 변경 실패.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(ChangePassword.this, error.toString().trim() ,Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(ChangePassword.this, "비밀번호가 일치하지 안습니다.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ChangePassword.this, "입력되지 않은 정보가 있습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
