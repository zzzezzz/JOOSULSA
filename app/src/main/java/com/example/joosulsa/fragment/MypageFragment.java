package com.example.joosulsa.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.joosulsa.MyPageEditActivity;
import com.example.joosulsa.databinding.FragmentMypageBinding;
import com.example.joosulsa.point.RePointHistroyActivity;
import com.example.joosulsa.purchase_list.PurchaseListActivity;


public class MypageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentMypageBinding binding = FragmentMypageBinding.inflate(inflater, container, false);
        // 회원정보 수정 페이지 이동 이벤트
        binding.userInfoChange.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), MyPageEditActivity.class);
            startActivity(intent);
        });

        // 구매내역 페이지 이동
        binding.purchaseHistoryInquiry.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PurchaseListActivity.class);
            startActivity(intent);
        });
        // 포인트내역 페이지 이동
        binding.pointInquiry.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), RePointHistroyActivity.class);
            startActivity(intent);
        });

        return binding.getRoot();
    }
}