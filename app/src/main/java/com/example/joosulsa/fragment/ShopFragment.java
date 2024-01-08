package com.example.joosulsa.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.joosulsa.MainActivity;
import com.example.joosulsa.ProductInfoActivity;
import com.example.joosulsa.R;
import com.example.joosulsa.databinding.FragmentShopBinding;

import com.example.joosulsa.shop.ShopListAdapter;
import com.example.joosulsa.shop.ShopListVO;

import org.json.JSONArray;
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
    private String producturl = "http://192.168.219.62:8089/shop";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentShopBinding binding = FragmentShopBinding.inflate(inflater,container,false);

        // requestqueue없으면 만드는곳
        // 서버통신할거면 무조건 해라.
        if(requestQueue==null){
            requestQueue = Volley.newRequestQueue(getActivity());
        }
        // 서버통신 메서드 사용
        poingShop();

        // 뒤로가기
        binding.shopBack.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        });
        // 홈으로
        binding.shopHome.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        });


        // 초기화
        dataset = new ArrayList<>();


        // 어댑터 초기화
        adapter = new ShopListAdapter(getActivity(), dataset);
        binding.prodGriodView.setAdapter(adapter);

        return binding.getRoot();
    }

    // 서버 통신
    private void poingShop(){
        StringRequest request = new StringRequest(
                postMethod,
                producturl,
                response -> {
                    Log.d("shop통신 성공",response);
                    handShop(response);
                    Log.d("1","가냐?");

                    Log.d("check", response.toString());
                },
                error ->{
                    Log.d("shop통신 실패","실패");
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
    // Spring 에서 받아온 재활용 데이터 처리 메소드
    private void handShop(String response){
        // prodName = 상품 이름
        // prodPrice = 상품 가격
        // prodInfo = 상품 상세정보
        // prodImg = 상품 이미지
        try {
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String prodName = jsonObject.getString("prodName");
                String prodInfo = jsonObject.getString("prodInfo");
                String stringProdPrice = jsonObject.getString("prodPrice");
                int prodPrice = Integer.parseInt(stringProdPrice);

                ShopListVO vo = new ShopListVO(prodName, prodInfo, prodPrice);


                // 확인
                dataset.add(vo);
                Log.d("shop확인", "이름: " + prodName + "가격: " + prodPrice + "상세정보: " + prodInfo + "이미지: " + prodImg + "Bitmap: " + bitmpImg);

            }

            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}