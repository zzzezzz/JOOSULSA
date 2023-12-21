package com.example.joosulsa.purchase_list;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.joosulsa.databinding.ActivityPurchaseListBinding;

public class PurchaseListActivity extends AppCompatActivity {
    private ActivityPurchaseListBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPurchaseListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}