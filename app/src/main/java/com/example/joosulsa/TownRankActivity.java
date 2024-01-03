package com.example.joosulsa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.example.joosulsa.databinding.ActivityTownRankBinding;

import net.daum.mf.map.api.MapView;

public class TownRankActivity extends AppCompatActivity {


    private MapView mapView;
    private ViewGroup mapViewcontainer;

    private ActivityTownRankBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTownRankBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 지도 띄우기
        mapView = new MapView(this);
        mapViewcontainer = (ViewGroup) findViewById(R.id.mapView);
        mapViewcontainer.addView(mapView);





    }
}