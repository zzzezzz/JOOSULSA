package com.example.joosulsa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.joosulsa.databinding.ActivityBuyBinding;

public class BuyActivity extends AppCompatActivity {

    private ActivityBuyBinding binding;
    SharedPreferences preferences;

    int totalPoint = preferences.getInt("totalPoints",0);
    String point = String.valueOf(totalPoint);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBuyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();


        String title = intent.getStringExtra("title");
        String count = intent.getStringExtra("count");
        String price = intent.getStringExtra("price");
        String totalPrice = intent.getStringExtra("totalPrice");

        binding.buyName.setText(title);
        binding.buyCount.setText(count);
        binding.buyTotal.setText(totalPrice);
        binding.buyPrice.setText(price);
        binding.buyCount2.setText(count);
        binding.buyPoint.setText(point);
        binding.buyTotal2.setText(totalPrice);


    }


}