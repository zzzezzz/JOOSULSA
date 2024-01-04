package com.example.joosulsa.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.joosulsa.R;
import com.example.joosulsa.databinding.FragmentRankBinding;


public class RankFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentRankBinding binding = FragmentRankBinding.inflate(inflater, container, false);



        return binding.getRoot();
    }
}