package com.example.joosulsa.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.joosulsa.R;
import com.example.joosulsa.SearchActivity;
import com.example.joosulsa.category.MainCategoryAdapter;
import com.example.joosulsa.category.MainCategoryVO;
import com.example.joosulsa.databinding.ActivitySearchBinding;
import com.example.joosulsa.databinding.FragmentHomeBinding;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    // 카테고리 데이터 연결
    private ArrayList<MainCategoryVO> dataset;
    // 카테고리 어댑터 연결
    private MainCategoryAdapter adapter;

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


        // 객체 생성
        dataset = new ArrayList<>();

        // 데이터 넣어주기
        dataset.add(new MainCategoryVO(R.drawable.tab1_home,"test01"));
        dataset.add(new MainCategoryVO(R.drawable.tab1_home,"test02"));
        dataset.add(new MainCategoryVO(R.drawable.tab1_home,"test03"));
        dataset.add(new MainCategoryVO(R.drawable.tab1_home,"test04"));
        dataset.add(new MainCategoryVO(R.drawable.tab1_home,"test05"));
        dataset.add(new MainCategoryVO(R.drawable.tab1_home,"test06"));
        dataset.add(new MainCategoryVO(R.drawable.tab1_home,"test07"));

        // 레이아웃 보여주기
        // manager : 레이아웃 정보를 갖고있음..
        LinearLayoutManager manager = new LinearLayoutManager(this);

        // 뿌려줄 xml파일에 recycleview 넣어주고 id 값을 꼭 지정해주기
        // 레이아웃을 설정해주기
        binding.categoryList.setLayoutManager(manager);

        // 어댑터를 연결해주기
        adapter = new MainCategoryAdapter(dataset);
        binding.categoryList.setAdapter(adapter); // setAdapter : 이걸 어댑터에 넣어준다고

        return binding.getRoot();
    }

}