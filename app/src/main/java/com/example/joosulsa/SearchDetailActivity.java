package com.example.joosulsa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.joosulsa.databinding.ActivitySearchDetailBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SearchDetailActivity extends AppCompatActivity {
    private ActivitySearchDetailBinding binding;
    private String springUrl = "http://192.168.219.62:8089/search";
    private String upPopUrl = "http://192.168.219.62:8089/upPop";

    // post
    int postMethod = Request.Method.POST;

    private RequestQueue requestQueue;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding=ActivitySearchDetailBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(SearchDetailActivity.this);
        }
        Log.d("downdowndown", "down?");
        upPopImg();
        Log.d("upupupupupupupupupup", "up?");

        // 뒤로가기
        binding.backSearchDetail.setOnClickListener(v -> {
            Intent intent = new Intent(SearchDetailActivity.this, SearchActivity.class);
            startActivity(intent);
        });

        // 엔터 이벤트
        binding.textSearchDetail.setOnEditorActionListener((textView, actionId, keyEvent) -> {

            if (actionId == EditorInfo.IME_ACTION_SEARCH || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                // 검색 이벤트 처리
                Log.d("엔터", "된다");
                performSearch();
                return true; // 이벤트 처리 완료
            }
            return false; // 이벤트 처리하지 않음
        });


    }

    // 검색 기능 로직 처리
    private void performSearch() {
        // 여기에 실제 검색에 대한 로직을 추가하세요
        // 예를 들어, 검색어를 가져와서 검색 결과 화면으로 이동하는 등의 동작을 수행합니다.

        String search = binding.textSearchDetail.getText().toString();
        // 검색 방법
        String searchMethod = "text";
        preferences = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);

        String autoId = preferences.getString("autoId", "1");
        if (autoId.equals("1")){
            searchMethod = "etc";
            Log.d("확인2", search + searchMethod);
            searchRequest(search, searchMethod, autoId);
        }else {
            Log.d("확인2", search + searchMethod);
            searchRequest(search, searchMethod, autoId);
        }


        // 검색어를 이용한 추가 동작 수행

    }

    // 검색 서버통신.
    private void searchRequest(String search, String searchMethod, String autoId) {
        StringRequest request = new StringRequest(
                postMethod,
                springUrl,
                response -> {
                    // 서버통신 성공시
                    Log.d("searchDetailCheck", response); // 로그

                    handSearch(response);

                    // 키워드에 따른 페이지 이동 이벤트
                    if(response.equals("")){
                        // DB에 없는 키워드 일때
                        Intent intent = new Intent(SearchDetailActivity.this, SearchDetailActivity.class);
                        startActivity(intent);
                    }else {
                        // DB에 있는 키워드가 있을때.
                        Intent intent = new Intent(SearchDetailActivity.this, RecycleDetailActivity.class);
                        startActivity(intent);
                    }
                },
                error -> {
                    // 서버통신 실패시
                    Log.d("검색실패 통신", "????");
                }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("search",search);
                params.put("method", searchMethod);
                params.put("user", autoId);
                long now =System.currentTimeMillis();
                Date today =new Date(now);
                SimpleDateFormat format =new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                String time = format.format(today);
                params.put("earnTime", time);
                Log.d("가냐..?",search);
                return params;
            }
        };
        // Request를 RequestQueue에 추가
        requestQueue.add(request);
    }
    // Spring 에서 받아온 재활용 데이터 처리 메소드
    private void handSearch(String response) {
//                sepaMethod = 분리수거 방법
//                sepaCaution = 분리수거 주의사항
//                sepaImg = 분리수거 이미지
//                sepaVideo = 분리수거 영상
//                recycleVideo = 업사이클 비디오
//                recycleImg = 업사이클 이미지
        try {
            // 데이터 파싱 작업
            // 데이터 파싱 작업
            JSONObject jsonResponse = new JSONObject(response);

            // searchCheck JSON 객체 가져오기
            JSONObject searchCheckObject = jsonResponse.getJSONObject("searchCheck");

            // searchCheck의 속성들 가져오기
            String trashName = searchCheckObject.getString("trashName");
            String sepaMethod = searchCheckObject.getString("sepaMethod");
            String sepaCaution = searchCheckObject.getString("sepaCaution");
            String sepaImg = searchCheckObject.getString("sepaImg");
            String sepaVideo = searchCheckObject.getString("sepaVideo");
            String recycleVideo = searchCheckObject.getString("recycleVideo");
            String recycleImg = searchCheckObject.getString("recycleImg");
            String recycleNum = searchCheckObject.getString("recycleNum");

            // totalPoints 가져오기
            int totalPoints = jsonResponse.getInt("totalPoints");

            // 확인
            Log.d("데이터 처리", "방법: " + sepaMethod + " 주의사항: " + sepaCaution +
                    " 이미지: " + sepaImg + " 분리수거 영상: " + sepaVideo + " 업사이클 영상: " + recycleVideo +
                    " 업사이클 이미지 : " + recycleImg);

            Log.d("데이터 처리", "Total Points: " + totalPoints);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("userPoint", totalPoints);
            editor.apply();
            // Intent에 값 넣어주기
            Intent textSearchIntent = new Intent(SearchDetailActivity.this, RecycleDetailActivity.class);
            textSearchIntent.putExtra("trashName", trashName);
            textSearchIntent.putExtra("sepaMethod",sepaMethod);
            textSearchIntent.putExtra("sepaCaution",sepaCaution);
            textSearchIntent.putExtra("sepaImg",sepaImg);
            textSearchIntent.putExtra("sepaVideo",sepaVideo);
            textSearchIntent.putExtra("recycleVideo",recycleVideo);
            textSearchIntent.putExtra("recycleImg",recycleImg);
            textSearchIntent.putExtra("searchmethod", "text");
            textSearchIntent.putExtra("recyNum", recycleNum);
            Log.d("searchWhy", "방법: " + sepaMethod + " 주의사항: " + sepaCaution +
                    " 이미지: " + sepaImg + " 분리수거 영상: " + sepaVideo + " 업사이클 영상: " + recycleVideo +
                    " 업사이클 이미지 : " + recycleImg);
            startActivity(textSearchIntent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void upPopImg(){
        Log.d("zxcbweferqfr4", "여긴오나?");
        StringRequest request = new StringRequest(

                postMethod,
                upPopUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("vczxczb ftgs", response);
                        upPopImgOut(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("cvbrtehwrbvsw", error.toString());
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String method = "etc";
                params.put("method", method);
                Log.d("seachDetailMethod", method);
                return params;
            }
        };
        requestQueue.add(request);

    }

    private void upPopImgOut(String response){

        List<Bitmap> bitmapList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            for (int i = 0; i <8; i++) {
                Log.d("0start?", jsonObject.getString("image0"));
                // 가정: "img" + i라는 키에 Base64로 인코딩된 이미지 데이터가 들어 있다고 가정
                String upPopEncode = jsonObject.getString("image" + i);
                Log.d("lengthCheck", Integer.toString(upPopEncode.length()));
                byte[] decodedBytes1 = Base64.decode(upPopEncode, Base64.DEFAULT);
                Bitmap bitmap1 = BitmapFactory.decodeByteArray(decodedBytes1, 0, decodedBytes1.length);
                Log.d("ascdbgwrfv", "dvsadc");
                bitmapList.add(bitmap1);
            }


        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        Random rd = new Random();
        List<Integer> rdNumList = new ArrayList<>();
        for (int j = 0; j<3; j++){
            int rdNum = rd.nextInt(7);
            Log.d("rdNumCheck", Integer.toString(rdNum));
            rdNumList.add(rdNum);
            String imgV = "popUpcy" + (j+1);
            Log.d("imgV", imgV);
            int imageViewId = getResources().getIdentifier(imgV, "id", getPackageName());
            ImageView imageView = findViewById(imageViewId);
            imageView.setImageBitmap(bitmapList.get(rdNumList.get(j)));
        }

    }

}