package com.example.joosulsa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.joosulsa.databinding.ActivityRegisterBinding;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;

    private RequestQueue requestQueue;

    // 우리 스프링 주소 넣어둘 변수
    private String springUrl = "나중에 스프링 켜지면 여기다 스프링 주소 넣으면 됨";

    // 회원가입이라 post로 했는데 아니면 바꾸죠 뭐
    int postMethod = Request.Method.POST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // join_back(뒤로가기) 눌렀을대 메인으로 이동하기


        // 회원가입시 입력 정보 보내는 로직 만들겠음

        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(this);
        }

        // 회원가입 버튼 클릭 이벤트
        binding.btnJoin.setOnClickListener(v -> {
            String userName = binding.joinName.getText().toString();
            String userID = binding.joinId.getText().toString();
            String userPw = binding.joinPw.getText().toString();
            String userNick = binding.joinNick.getText().toString();
            String userAddr = binding.joinAddress.getText().toString();

            registerRequest(userName, userID, userPw, userNick, userAddr);

            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);

        });


    }

    // 회원가입 데이터 보내는 메소드
    private void registerRequest(String userName, String userID, String userPw, String userNick, String userAddr){
        StringRequest request = new StringRequest(
                postMethod,
                springUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("LoginActivity", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("LoginActivity", error.toString());
                    }
                }
        ){
            @Nullable
            @Override
            // Map<String(key), String(value)>를
            // getParams를 사용해서 key와 value를 login?key=value 형태로 바꿔준다.
            protected Map<String, String> getParams() throws AuthFailureError {
                // 전송방식을 POST로 지정했을 때 사용하는 메소드
                // 데이터를 전송할 때 Map 형태로 구성하여 리턴해줘야 한다.
                Map<String, String> params = new HashMap<>();
                params.put("name", userName);
                params.put("id", userID);
                params.put("pw", userPw);
                params.put("nick", userNick);
                params.put("address", userAddr);

                return params;
            }
        };
    }
}