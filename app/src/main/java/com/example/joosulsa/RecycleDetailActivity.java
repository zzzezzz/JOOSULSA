package com.example.joosulsa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.joosulsa.databinding.ActivityRecycleDetailBinding;

// 유튜브 라이브러리 사용
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;



public class RecycleDetailActivity extends AppCompatActivity {
    private ActivityRecycleDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecycleDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 검색 방법 받아오기
        String searchMethod = getIntent().getStringExtra("searchmethod");
        Log.d("searchMe", searchMethod);

        // 이걸로 다시 받아요
        // 유튜브 영상 띄우는 코드
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        // Intent에서 YouTube 비디오의 ID를 가져오기
        Intent intent = getIntent();

        String youTub = getIntent().getStringExtra("sepaVideo"); // 유튜브 링크
        String recycledImg = getIntent().getStringExtra("sepaImg"); // 분리수거 이미지
        String way = getIntent().getStringExtra("sepaMethod"); // 분리수거 방법
        String caution = getIntent().getStringExtra("sepaCaution"); // 주의사항
        String upcycleVideo = getIntent().getStringExtra("recycleVideo"); // 업사이클 유트브 영상
        String UpcycleImg = getIntent().getStringExtra("sepaMethod"); // 업사이클 이미지 (썸네일)

        Log.d("데이터 확인","링크: "+youTub +"방법: "+ way + "분리수거 이미지: "+ recycledImg +
                " 주의사항: "+ caution + " 업사이클 영상: "+ upcycleVideo + " 썸네일: "+UpcycleImg);
        
        // 동적 유뷰트 비디오 만들기
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(youTub, 0);
            }
        });

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


}
