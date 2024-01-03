package com.example.joosulsa.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.joosulsa.MainActivity;
import com.example.joosulsa.R;
import com.example.joosulsa.databinding.FragmentShopBinding;
import com.example.joosulsa.shop.ShopListAdapter;
import com.example.joosulsa.shop.ShopListVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShopFragment extends Fragment {

    private ArrayList<ShopListVO> dataset;
    private ShopListAdapter adapter;
    int postMethod = Request.Method.POST;
    private RequestQueue requestQueue;
    private String producturl = "http://172.30.48.1:8089/dataCheck";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentShopBinding binding = FragmentShopBinding.inflate(inflater,container,false);

        // 뒤로가기
        binding.shopBack.setOnClickListener(v->{
            getActivity().finish();
        });
        // 홈으로
        binding.shopHome.setOnClickListener(v->{
            getActivity().finish();
        });



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

    // 서버 통신
    private void poingShop(String Shop){
        StringRequest request = new StringRequest(
                postMethod,
                producturl,
                response -> {
                    Log.d("shop통신","성공");
                },
                error ->{
                    Log.d("shop통신","실패");
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        requestQueue.add(request);
    }
}