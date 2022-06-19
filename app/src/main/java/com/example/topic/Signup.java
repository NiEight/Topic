package com.example.topic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Signup extends AppCompatActivity {
    private EditText signup_nameText, signup_idText, signup_pwText, singup_reenterPassword;
    private Button singup_okBtn;
    private String URL = "http://10.0.2.2/topick/register.php";
    private String name, email, password, reenterPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        signup_nameText = findViewById(R.id.signup_nameText);
        signup_idText = findViewById(R.id.signup_idText);
        signup_pwText = findViewById(R.id.signup_pwText);
        singup_reenterPassword = findViewById(R.id.singup_reenterPassword);
        singup_okBtn = findViewById(R.id.signup_okBtn);

        name = email = password = reenterPassword = "";
    }
    
    public void save(View view) {
        name = signup_nameText.getText().toString().trim();
        email = signup_idText.getText().toString().trim();
        password = signup_pwText.getText().toString().trim();
        reenterPassword = singup_reenterPassword.getText().toString().trim();

        //비밀번호와 비밀번호 확인 값이 동일한지 체크
        if(!password.equals(reenterPassword)) {
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
        }
        else if(!name.equals("") && !email.equals("") && !password.equals("")) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("success")) {
                        Toast.makeText(Signup.this, "회원가입 완료", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();

                    } else if (response.equals("failure")) {
                        Toast.makeText(Signup.this, "올바른 정보가 아닙니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString().trim() ,Toast.LENGTH_SHORT).show();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("name", name);
                    data.put("email", email);
                    data.put("password", password);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(this, "입력되지 않은 칸이 있습니다!", Toast.LENGTH_SHORT).show();
        }
    }

    public void login(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
