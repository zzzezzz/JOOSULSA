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
import com.example.joosulsa.ProductInfoActivity;
import com.example.joosulsa.R;
import com.example.joosulsa.databinding.FragmentShopBinding;

import com.example.joosulsa.shop.ShopListAdapter;
import com.example.joosulsa.shop.ShopListVO;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopFragment extends Fragment {

    private ArrayList<ShopListVO> dataset;
    private ShopListAdapter adapter;

    int postMethod = Request.Method.POST;
    private RequestQueue requestQueue;
    private String producturl = "http://172.30.48.1:8089/shop";

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


        // 초기화
        dataset = new ArrayList<>();


        // 레이아웃 보여주기
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());

        // 레이아웃 설정



        // 어댑터 초기화
        adapter = new ShopListAdapter(getActivity(), dataset);
        binding.prodGriodView.setAdapter(adapter);

        return binding.getRoot();
    }

    // 서버 통신
    private void poingShop(String Shop){
        StringRequest request = new StringRequest(
                postMethod,
                producturl,
                response -> {
                    Log.d("shop통신 성공","성공");
                },
                error ->{
                    Log.d("shop통신 실패","실패");
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("shop",Shop);
                Log.d("shop통신 1",Shop);
                return params;
            }
        };
        requestQueue.add(request);
    }
    // Spring 에서 받아온 재활용 데이터 처리 메소드
    private void handShop(String response){
        // prodName = 상품 이름
        // prodPrice = 상품 가격
        // prodInfo = 상품 상세정보
        // prodImg = 상품 이미지
        try {
            JSONObject jsonResponse = new JSONObject(response);
            String stringProdImg = jsonResponse.getString("prodImg");
            int prodImg = Integer.parseInt(stringProdImg);// int 형이기에 형태 변환
            String prodName = jsonResponse.getString("prodName");
            String prodInfo = jsonResponse.getString("prodInfo");
            String stringProdPrice = jsonResponse.getString("prodPrice");
            int prodPrice = Integer.parseInt(stringProdPrice);// int 형이기에 형태 변환

            // 확인
            Log.d("shop확인","이름: "+ prodName +"가격: " + prodPrice + "상세정보: "+ prodInfo +"이미지"+prodImg);
            dataset.add(new ShopListVO(prodImg,prodName,prodInfo,prodPrice));

        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}