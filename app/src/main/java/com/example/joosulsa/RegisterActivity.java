package com.example.joosulsa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.joosulsa.databinding.ActivityRegisterBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;

    private RequestQueue requestQueue;

    // 우리 스프링 주소 넣어둘 변수
    private String springUrl = "http://192.168.219.44:8089/register";

    // 회원가입이라 post로 했는데 아니면 바꾸죠 뭐
    int postMethod = Request.Method.POST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 회원가입시 입력 정보 보내는 로직 만들겠음

        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(RegisterActivity.this);
        }

        EditText userIdEditText = findViewById(R.id.joinId); // 변경 필요

        userIdEditText.addTextChangedListener(new TextWatcher() {

            // int i = 시작하기전
            // int i1 = 과정
            // int i2 = 결과
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //                                                   변경 시작점, 변경 과정, 변경 결과
                // 텍스트 변경 전 호출
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //                                            변경 시작점, 변경 과정, 변경 결과
                // 텍스트가 변경될 때 호출
                String newText = charSequence.toString().trim();
                if (!newText.isEmpty()) {
                    // 입력된 텍스트가 비어 있지 않으면 서버에 확인 요청
                    checkUserIdAvailability(newText);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // 텍스트 변경 후 호출
            }
        });

        // 회원가입 버튼 클릭 이벤트
        binding.btnJoin.setOnClickListener(v -> {
            String userName = binding.joinName.getText().toString();
            String userID = binding.joinId.getText().toString();
            String userPw = binding.joinPw.getText().toString();
            String userNick = binding.joinNick.getText().toString();
            String userAddr = binding.joinAddress.getText().toString();

            registerRequest(userName, userID, userPw, userNick, userAddr);

        });


        // 뒤로가기 버튼 누르면 로그인으로
        binding.joinBack.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        // 홈 버튼 누르면 MainActivity로
        binding.joinHome.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
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
                        // 응답 처리
                        if ("success".equals(response)) {
                            // 성공 시 원하는 동작 수행
                            // 예: 회원가입 성공 메시지 표시
                            // Toast.makeText(RegisterActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                            Toast.makeText(RegisterActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // 실패 시 원하는 동작 수행
                            // 예: 회원가입 실패 메시지 표시
                            Toast.makeText(RegisterActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("RegisterActivity", error.toString());
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
                long now =System.currentTimeMillis();
                Date today =new Date(now);
                SimpleDateFormat format =new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                String time = format.format(today);
                params.put("jointime", time);
                Log.d("name",params.toString());
                return params;
            }
        };

        // 요청을 큐에 추가
        requestQueue.add(request);

    }

    // 아이디 중복 체크 요청 메소드
    private void checkUserIdAvailability(String userId) {
        String checkUrl = "http://192.168.219.44:8089/check/" + userId;

        StringRequest request = new StringRequest(
                Request.Method.POST,
                checkUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if ("DUPLICATED".equals(response)) {
                            // 이미 존재하는 아이디인 경우
                            // 사용자에게 메시지 표시 또는 다른 작업 수행
                            binding.checkDuplication.setText("이미 사용 중인 아이디입니다.");
                        } else if ("AVAILABLE".equals(response)) {
                            // 존재하지 않는 아이디인 경우
                            // 사용자에게 메시지 표시 또는 다른 작업 수행
                            binding.checkDuplication.setText("사용 가능한 아이디입니다.");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // 오류 발생 시 처리
                    }
                }
        );

        // 요청을 큐에 추가
        requestQueue.add(request);
    }

}