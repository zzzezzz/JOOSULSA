package com.example.joosulsa.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.joosulsa.R;
import com.example.joosulsa.SearchActivity;
import com.example.joosulsa.databinding.ActivitySearchBinding;
import com.example.joosulsa.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // binding으로 view내부 객체들 가져다 쓰려고 선언하는 코드임
        FragmentHomeBinding binding = FragmentHomeBinding.inflate(inflater, container, false);

        // homeFragment에 있는 요소 순서대로 이벤트 작성바람

        // 검색창 이벤트
        binding.search.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
        });



        return binding.getRoot();
    }
}