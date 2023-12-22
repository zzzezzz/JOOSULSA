package com.example.joosulsa.purchase_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.joosulsa.R;
import com.example.joosulsa.databinding.ActivityPurchaseListBinding;

import java.util.ArrayList;

public class PurchaseListActivity extends AppCompatActivity {

    private ArrayList<PurchaseListVO> dataset;
    private PurchaseListAdapter adapter;

    private ActivityPurchaseListBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPurchaseListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dataset = new ArrayList<>();

        dataset.add(new PurchaseListVO(R.id.correctBtn,"test","test"));
        dataset.add(new PurchaseListVO(R.id.correctBtn,"test","test"));
        dataset.add(new PurchaseListVO(R.id.correctBtn,"test","test"));
        dataset.add(new PurchaseListVO(R.id.correctBtn,"test","test"));
        dataset.add(new PurchaseListVO(R.id.correctBtn,"test","test"));
        dataset.add(new PurchaseListVO(R.id.correctBtn,"test","test"));

        // 레이아웃 보여주기
        LinearLayoutManager manager = new LinearLayoutManager(this);

        // 레이아웃 설정해주기
        binding.purchaseList.setLayoutManager(manager);

        // 어댑터를 연결해주기
        adapter = new PurchaseListAdapter(dataset);
        binding.purchaseList.setAdapter(adapter);


    }
}