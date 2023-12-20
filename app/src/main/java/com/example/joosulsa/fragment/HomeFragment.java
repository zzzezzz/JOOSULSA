package com.example.joosulsa.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.joosulsa.R;
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
        binding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            // 검색 완료시 작동하는 메소드
            @Override
            public boolean onQueryTextSubmit(String query) {
                // 검색시 넘어갈 화면 정의(우리는 검색화면으로 넘어가기로 했으므로 여기로 보내겠음)
                Intent intent = new Intent(getActivity(), ActivitySearchBinding.class);
                // 검색어가 매개변수인데 그게 query라 query를 보내는거임
                intent.putExtra("search", query);
                startActivity(intent);
                return true;
            }

            // 검색창 내부 입력값이 변경될때 동작하는 메소드임 추후 실시간 연관검색 처리할때 사용할듯 일단 false로 비활성화
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });



        return binding.getRoot();
    }
}