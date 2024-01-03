package com.example.joosulsa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.os.Bundle;

import com.example.joosulsa.databinding.ActivityTownRankBinding;



public class TownRankActivity extends AppCompatActivity {


        private ActivityTownRankBinding binding;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = ActivityTownRankBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

        }




//
//        private CoordinatorLayout coord; //맵을 생성하기 위해 있는 parents View
//        private RelativeLayout mapViewContainer; //맵이 올라가는 container View
//
//        //인텐트가 넘어가는 부분에 추가///
//        //coord.removeView(rel_map);
//
//        public void onResume() {
//
//        super.onResume();
//        if (coord.getChildAt(0) == null) {
//            try {
//                mapViewContainer = new RelativeLayout(getApplicationContext());
//                mapViewContainer.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//                coord.addView(mapView);
//                mapView = new MapView(getApplicationContext());
//                mapViewContainer.addView(mapView);
//
//            } catch (RuntimeException re) {
//
//            }
//        }

    }
