package com.example.joosulsa.purchase_list;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.joosulsa.R;
import com.example.joosulsa.databinding.ActivityPurchaseListBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PurchaseListActivity extends AppCompatActivity {
    private ArrayList<PurchaseListVO> dataset;
    private PurchaseListAdapter adapter;
    int postMethod = Request.Method.POST;
    private RequestQueue requestQueue;
    private String producturl = "http://172.30.48.1:8089/purchList";

    private SharedPreferences spf;

    private ActivityPurchaseListBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPurchaseListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // requestqueue없으면 만드는곳
        // 서버통신할거면 무조건 해라.
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(this);
        }

        dataset = new ArrayList<>();


        // 레이아웃 보여주기
        LinearLayoutManager manager = new LinearLayoutManager(this);

        // 레이아웃 설정해주기
        binding.purchaseList.setLayoutManager(manager);

        // 어댑터를 연결해주기
        adapter = new PurchaseListAdapter(dataset);
        binding.purchaseList.setAdapter(adapter);
    }
    private void PurchList(){
        StringRequest request = new StringRequest(
                postMethod,
                producturl,
                response -> {
                    Log.d("구매내역 통신","성공");

                },
                error -> {
                    Log.d("구매내역 통신", "실패");
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
    // 데이터 가져기
    private void shopHistory(String response){
        try {
            for (int i=0; i<response.length(); i++){

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}