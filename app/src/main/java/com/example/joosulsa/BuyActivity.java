package com.example.joosulsa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.joosulsa.databinding.ActivityBuyBinding;

import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BuyActivity extends AppCompatActivity {

    private ActivityBuyBinding binding;
    SharedPreferences preferences;

    private final String springUrl = "http://192.168.219.62:8089/purchaseProduct";
    private final String springUrl2 = "http://192.168.219.51:8089/purchaseProduct";
    // post
    int postMethod = Request.Method.POST;

    private RequestQueue requestQueue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBuyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(BuyActivity.this);
        }

        Intent intent = getIntent();

        preferences = getSharedPreferences("autoLogin",Context.MODE_PRIVATE);
        int point = preferences.getInt("totalPoints",0);
        String autoId = preferences.getString("autoId", null);
        Log.d("포인트내놔",String.valueOf(point));

        String title = intent.getStringExtra("title");
        String count = intent.getStringExtra("count");
        String price = intent.getStringExtra("price");
        int intcount = Integer.parseInt(count);
        int intPrice = Integer.parseInt(price);
        String totalPrice = String.valueOf(intPrice*intcount);

        binding.buyName.setText(title);
        binding.buyCount.setText(count);
        binding.buyTotal.setText(totalPrice);
        binding.buyPrice.setText(price);
        binding.buyCount2.setText(count);
        binding.buyPoint.setText(String.valueOf(point));
        binding.buyTotal2.setText(totalPrice);

        binding.buyPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(point>=intcount*intPrice){
                    StringRequest request = new StringRequest(
                            postMethod,
                            springUrl2,
                            response -> {
                                Log.d("serverCheck",response);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putInt("totalPoints",Integer.parseInt(response));
                                Intent intent = new Intent(BuyActivity.this, PurchasePopupSuccessActivity.class);
                                startActivity(intent);
                                finish();
                            },
                            error -> {
                                // 서버통신 실패
                                Log.d("asldfjal","!!");
                            }

                    ) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError{
                            Map<String, String> params = new HashMap<>();
                            params.put("use_point",totalPrice);
                            long now =System.currentTimeMillis();
                            Date today =new Date(now);
                            SimpleDateFormat format =new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                            String time = format.format(today);
                            params.put("used_at",time);
                            params.put("user_id_user_id",autoId);
                            params.put("prod_name",title);
                            return params;
                        }

                    };
                    requestQueue.add(request);
                }
            }
        });

        binding.buyBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


}