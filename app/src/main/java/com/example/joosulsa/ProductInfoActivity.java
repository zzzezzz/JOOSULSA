package com.example.joosulsa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.example.joosulsa.databinding.ActivityProductInfoBinding;
import com.example.joosulsa.fragment.ShopFragment;

public class ProductInfoActivity extends AppCompatActivity {
    private ActivityProductInfoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();

        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        String price = intent.getStringExtra("price");
        Bitmap img = intent.getExtra("prodImg");

        int cntPrice = Integer.parseInt(price);
        int productCount = Integer.parseInt(binding.productCount.getText().toString());


        binding.prodctName.setText(title);
        binding.prodctInfo.setText(content);
        binding.purchasePoint.setText(price);

        binding.purchaseDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int productCount = Integer.parseInt(binding.productCount.getText().toString());
                if(productCount>1) {
                    productCount -= 1;
                    binding.productCount.setText(String.valueOf(productCount));
                    int totalPrice = productCount*cntPrice;
                    binding.purchasePoint.setText(String.valueOf(totalPrice));
                }
            }
        });

        binding.purchaseUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(productCount>=1) {
                    int productCount = Integer.parseInt(binding.productCount.getText().toString());
                    productCount += 1;
                    binding.productCount.setText(String.valueOf(productCount));
                    int totalPrice = productCount*cntPrice;
                    binding.purchasePoint.setText(String.valueOf(totalPrice));
                }
            }
        });

        binding.prodctPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(ProductInfoActivity.this, BuyActivity.class);
                intent2.putExtra("title",title);
                intent2.putExtra("count",binding.productCount.getText().toString());
                intent2.putExtra("price",price);
                startActivity(intent2);
            }
        });

        binding.productBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.productHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductInfoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}