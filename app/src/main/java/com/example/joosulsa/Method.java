package com.example.joosulsa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Method{

    // 페이지 이동 메서드
    public static void movePage(Context context, Class<?> destinationActivity){
        Intent intent = new Intent(context, destinationActivity);
        context.startActivity(intent);
    }

    // 데이터 전달하며 페이지 이동
    public static void moveDatePage(Context context, Class<?> destinationActivity, String key, String value) {
        Intent intent = new Intent(context, destinationActivity);
        intent.putExtra(key, value);
        context.startActivity(intent);
    }
}
