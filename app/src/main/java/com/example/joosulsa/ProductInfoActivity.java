package com.example.joosulsa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.joosulsa.databinding.ActivityProductInfoBinding;

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
                    productCount = productCount - 1;
                    binding.productCount.setText(String.valueOf(productCount));
                    binding.purchasePoint.setText(String.valueOf(productCount*cntPrice));
                }
            }
        });

        binding.purchaseUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(productCount>=1) {
                    int productCount = Integer.parseInt(binding.productCount.getText().toString());
                    productCount = productCount + 1;
                    binding.productCount.setText(String.valueOf(productCount));
                    binding.purchasePoint.setText(String.valueOf(productCount*cntPrice));
                }
            }
        });

        Intent intent2 = new Intent();
        intent2.putExtra("title",title);
        intent2.putExtra("count",productCount);
        intent2.putExtra("price",price);
        intent2.putExtra("totalPrice",String.valueOf(productCount*cntPrice));
        startActivity(intent2);

    }
}