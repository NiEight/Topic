package com.example.topic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class FindPw extends AppCompatActivity {
    private EditText nameText, idText;
    private Button okBtn;

    private String name, email;
    private String URL = "http://10.0.2.2/topick/findpw.php";

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.findpw);

        nameText = findViewById(R.id.nameText);
        idText = findViewById(R.id.idText);
        okBtn = findViewById(R.id.okBtn);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameText.getText().toString().trim();
                email = idText.getText().toString().trim();

                //email과 name이 입력되었는지 확인
                if(!email.equals("") && !name.equals("")) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("success")) {   //DB에 일치하는 데이터가 있는 경우
                                Intent intent = new Intent(FindPw.this, ChangePassword.class);
                                intent.putExtra("email", email);
                                startActivity(intent);
                            } else if (response.equals("failure")) {    //DB에 일치하는 데이터가 없는 경우
                                Toast.makeText(FindPw.this, "일치하는 회원이 없습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(FindPw.this, error.toString().trim() ,Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> data = new HashMap<>();
                            data.put("name", name);
                            data.put("email", email);
                            return data;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                } else {
                    Toast.makeText(FindPw.this, "입력되지 않은 칸이 있습니다!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
