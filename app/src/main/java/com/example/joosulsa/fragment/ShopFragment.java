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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopFragment extends Fragment {

    private ArrayList<ShopListVO> dataset;
    private ShopListAdapter adapter;

    int postMethod = Request.Method.POST;
    private RequestQueue requestQueue;
    private String producturl = "http://192.168.219.45:8089/shop";
    private String producturlImg = "http://192.168.219.45:8089/shopImg";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentShopBinding binding = FragmentShopBinding.inflate(inflater, container, false);

        // requestqueue없으면 만드는곳
        // 서버통신할거면 무조건 해라.
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getActivity());
        }
        // 서버통신 메서드 사용
        poingShop();
        ShopImg();

        // 뒤로가기
        binding.shopBack.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        });
        // 홈으로
        binding.shopHome.setOnClickListener(v -> {
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

    // 서버 통신(이미지 제외)
    private void poingShop() {
        StringRequest request = new StringRequest(
                postMethod,
                producturl,
                response -> {
                    Log.d("shop통신 성공", response);
                    handShop(response);
                },
                error -> {
                    Log.d("shop통신 실패", "실패");
                }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        requestQueue.add(request);
    }

    // 서버 통신(이미지 가져오기)
    private void ShopImg() {
        StringRequest request = new StringRequest(
                postMethod,
                producturlImg,
                response -> {
                    Log.d("Img 통신 성공", response);
                    shopImg(response);
                },
                error -> {
                    Log.d("Img 통신 실패", "실패");
                    if(error != null){
                        error.printStackTrace();
                    }

                }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        requestQueue.add(request);


    }

    // Spring 에서 받아온 재활용 데이터 처리 메소드(이미지 제외)
    private void handShop(String response) {
        // prodName = 상품 이름
        // prodPrice = 상품 가격
        // prodInfo = 상품 상세정보
        try {
            JSONArray jsonArray = new JSONArray(response);
            // 파싱 작업
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // UTF-8 디코딩 추가
                String prodName = new String(jsonObject.getString("prodName").getBytes("ISO-8859-1"), "UTF-8");
                String prodInfo = new String(jsonObject.getString("prodInfo").getBytes("ISO-8859-1"), "UTF-8");
                int prodPrice = jsonObject.getInt("prodPrice");

                ShopListVO vo = new ShopListVO(prodName, prodInfo, prodPrice);

                // 확인
                dataset.add(vo);
                Log.d("shop확인", "이름: " + prodName + "가격: " + prodPrice + "상세정보: " + prodInfo);
            }

            adapter.notifyDataSetChanged();

        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    // Spring 서버통신 이미지 데이터 가져오기
    private void shopImg(String response) {
        Log.d("?","가냐?");
        try {
            Log.d("오니?",String.valueOf(response.length()));
            JSONArray jsonArray = new JSONArray(response);

            // 이미지를 저장할 리스트
            List<Bitmap> imageList = new ArrayList<>();

            // 파싱 작업
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // 안드로이드 디코딩
                // 올바른 키를 사용하고, 값이 비어있으면 빈 문자열("")을 반환
                String base64Img = jsonObject.toString();
                Log.d("오니?2",base64Img);

                // 값이 비어있지 않은 경우에만 처리
                if (!base64Img.isEmpty()) {
                    Log.d("오니3","ㅎㅇ");
                    try {
                        // Base64로 디코딩하여 Bitmap 생성
                        Log.d("오니4","ㅎㅇ");
                        byte[] decodedBytes = Base64.decode(base64Img, Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

                        // 이미지를 리스트에 추가
                        imageList.add(bitmap);
                    } catch (Exception e) {
                        // 디코딩 에러가 발생한 경우 에러 메시지를 로그에 출력
                        Log.e("img이미지 확인", "이미지 디코딩 에러: " + e.getMessage(), e);

                        // 디코딩 에러가 발생하면 빈 이미지를 리스트에 추가하여 에러를 구분할 수 있도록 함
                        imageList.add(Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888));
                    }
                } else {
                    Log.e("img이미지 확인", "base64Img가 비어있습니다.");
                }
            }

            // 리스트에 있는 이미지들을 데이터셋에 설정
            for (int i = 0; i < imageList.size(); i++) {
                dataset.get(i).setImg(imageList.get(i));
            }

            // 어댑터에 변경된 데이터셋을 알림
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }






}