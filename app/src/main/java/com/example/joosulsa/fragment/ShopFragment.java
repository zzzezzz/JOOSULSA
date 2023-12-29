package com.example.joosulsa.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.joosulsa.R;
import com.example.joosulsa.databinding.FragmentShopBinding;
import com.example.joosulsa.shop.ShopListAdapter;
import com.example.joosulsa.shop.ShopListVO;

import java.util.ArrayList;

public class ShopFragment extends Fragment {

    private ArrayList<ShopListVO> dataset;
    private ShopListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentShopBinding binding = FragmentShopBinding.inflate(inflater,container,false);

        dataset = new ArrayList<>();

        dataset.add(new ShopListVO(R.drawable.backbtn,R.drawable.backbtn,"test","test","teszxt","test",123, 123));
        dataset.add(new ShopListVO(R.drawable.backbtn,R.drawable.backbtn,"test","test","teszxt","test",123, 123));
        dataset.add(new ShopListVO(R.drawable.backbtn,R.drawable.backbtn,"test","test","teszxt","test",123, 123));
        dataset.add(new ShopListVO(R.drawable.backbtn,R.drawable.backbtn,"test","test","teszxt","test",123, 123));

        // 레이아웃 보여주기
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());

        // 레이아웃 설정
        binding.proList.setLayoutManager(manager);

        // 어댑터 연결
        adapter = new ShopListAdapter(dataset);
        binding.proList.setAdapter(adapter);

        return binding.getRoot();
    }
}