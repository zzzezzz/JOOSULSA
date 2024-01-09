package com.example.joosulsa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.joosulsa.databinding.ActivityBuyBinding;

public class BuyActivity extends AppCompatActivity {

    private ActivityBuyBinding binding;
    SharedPreferences preferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBuyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();

        preferences = getSharedPreferences("autoLogin",Context.MODE_PRIVATE);
        int point = preferences.getInt("totalPoints",0);
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
                if(point>intPrice*intcount){
                    
                }
            }
        });


    }


}