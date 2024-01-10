package com.example.joosulsa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.joosulsa.databinding.ActivityPurchasePopupSuccessBinding;

public class PurchasePopupSuccessActivity extends AppCompatActivity {

    private ActivityPurchasePopupSuccessBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPurchasePopupSuccessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // 팝업창 종료
        binding.purchaseHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PurchasePopupSuccessActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    // 팝업 밖 선택 시 닫힘 방지
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            return false;
        }
        return true;
    }
}