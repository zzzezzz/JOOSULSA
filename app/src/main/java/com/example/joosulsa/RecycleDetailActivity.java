package com.example.joosulsa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.joosulsa.databinding.ActivityRecycleDetailBinding;

// 유튜브 라이브러리 사용
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RecycleDetailActivity extends AppCompatActivity {
    private ActivityRecycleDetailBinding binding;

    private RequestQueue requestQueue;

    private String imageUrl = "http://192.168.219.62:8089/image";

    int postMethod = Request.Method.POST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecycleDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(RecycleDetailActivity.this);
        }



        // 이걸로 다시 받아요
        // 유튜브 영상 띄우는 코드
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);


        // Intent에서 YouTube 비디오의 ID를 가져오기
        Intent intent = getIntent();

        String trashName = getIntent().getStringExtra("trashName"); // 쓰레기 이름
        String youTub = getIntent().getStringExtra("sepaVideo"); // 유튜브 링크
        String recycledImg = getIntent().getStringExtra("sepaImg"); // 분리수거 이미지
        String way = getIntent().getStringExtra("sepaMethod"); // 분리수거 방법
        String caution = getIntent().getStringExtra("sepaCaution"); // 주의사항
        String upcycleVideo = getIntent().getStringExtra("recycleVideo"); // 업사이클 유트브 영상
        String UpcycleImg = getIntent().getStringExtra("sepaMethod"); // 업사이클 이미지 (썸네일)
        String recyNum1 = getIntent().getStringExtra("recyNum");
        imgRequest(recyNum1);
        Log.d("데이터 확인","링크: "+youTub +"방법: "+ way + "분리수거 이미지: "+ recycledImg +
                " 주의사항: "+ caution + " 업사이클 영상: "+ upcycleVideo + " 썸네일: "+UpcycleImg);
        
        // 동적 유튜브 비디오 만들기
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                youTubePlayer.cueVideo(youTub, 0);
            }
        });

        binding.recycleTitle.setText(trashName);

        binding.sepaText.setText(way);

        binding.caution.setText(caution);

        Intent intent1 = new Intent(RecycleDetailActivity.this, MainActivity.class);
        binding.btnBackRecycle.setOnClickListener(v -> {
            startActivity(intent1);
            finish();
        });

        binding.goHome.setOnClickListener(v -> {
            startActivity(intent1);
            finish();
        });




    }

    private void imgRequest(String recyNum1){
        Log.d("settingData", recyNum1);
        StringRequest request = new StringRequest(
                postMethod,
                imageUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            Log.d("qevqaiwnscdoaq", response);
                            handleImgData(response);
                        } else {
                            Log.e("VolleyError", "Response is null");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VolleyError", "Error in imgRequest: " + error.getMessage());
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("recycleNum", recyNum1);
                Log.d("imgSendData", recyNum1);
                return params;
            }
        };
        requestQueue.add(request);
    }

    private void handleImgData(String response){

        try {
            JSONObject jsonResponse = new JSONObject(response);

            String recyImg = jsonResponse.getString("recyImg");
            String sepaImg = jsonResponse.getString("sepaImg");
            byte[] decodedBytes1 = Base64.decode(recyImg, Base64.DEFAULT);
            Bitmap bitmap1 = BitmapFactory.decodeByteArray(decodedBytes1, 0, decodedBytes1.length);
            byte[] decodedBytes2 = Base64.decode(sepaImg, Base64.DEFAULT);
            Bitmap bitmap2 = BitmapFactory.decodeByteArray(decodedBytes2, 0, decodedBytes2.length);
            binding.upcycleImg.setImageBitmap(bitmap1);
            binding.sepaImg.setImageBitmap(bitmap2);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }


}
