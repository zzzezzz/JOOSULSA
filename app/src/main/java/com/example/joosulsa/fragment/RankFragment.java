package com.example.joosulsa.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.joosulsa.PersonRankActivity;
import com.example.joosulsa.R;
import com.example.joosulsa.TownRankActivity;
import com.example.joosulsa.databinding.FragmentRankBinding;


public class RankFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentRankBinding binding = FragmentRankBinding.inflate(inflater, container, false);

        binding.personRankBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PersonRankActivity.class);
            startActivity(intent);
            getActivity().finish();
        });

        binding.townRankBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), TownRankActivity.class);
            startActivity(intent);
            getActivity().finish();
        });

        return binding.getRoot();
    }
}