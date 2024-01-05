package com.example.joosulsa.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.joosulsa.MyPageEditActivity;
import com.example.joosulsa.R;
import com.example.joosulsa.databinding.FragmentMypageBinding;
import com.example.joosulsa.point.RePointHistroyActivity;
import com.example.joosulsa.purchase_list.PurchaseListActivity;


public class MypageFragment extends Fragment {

    private SharedPreferences preferences;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        preferences = requireActivity().getSharedPreferences("autoLogin", Context.MODE_PRIVATE);

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

        // 로그아웃 버튼 클릭시 이벤트
        binding.logout.setOnClickListener(v -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("autoId");
            editor.remove("autoPw");
            editor.remove("autoName");
            editor.remove("autoAddr");
            editor.remove("autoNick");
            editor.remove("quizBoolean");
            editor.remove("checkBoolean");
            editor.apply();
            requireActivity().getSupportFragmentManager().beginTransaction().replace(
                    R.id.fl,
                    new HomeFragment()
            ).commit();
        });

        return binding.getRoot();
    }
}