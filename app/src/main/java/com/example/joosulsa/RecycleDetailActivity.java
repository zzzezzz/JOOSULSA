package com.example.joosulsa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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

        // 유튜브 영상 띄우는 코드
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                String videoId = "W8ZUHMd-MmQ"; //재생을 원하는 YouTube 비디오의 videoID
                youTubePlayer.loadVideo(videoId, 0);
            }
        });
    }


}
