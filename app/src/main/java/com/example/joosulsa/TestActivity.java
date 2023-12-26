package com.example.joosulsa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.joosulsa.databinding.ActivityTestBinding;

public class TestActivity extends AppCompatActivity {
    private ActivityTestBinding binding;
    private static final String urls = "127.0.0.1:5000"; // flask 호출 url
    private ImageView img; //사용자가 찍은 이미지를 받아올 변수
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        img = findViewById(R.id.inputImg);



    }

//    public void ClickButton1(View view){
//        sendServer();
//    }
//    public void sendServer(){
//        class sendData extends AsyncTask<Void,Void,String>{
//
//
//        }
//    }
}