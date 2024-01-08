package com.example.joosulsa;

import androidx.appcompat.app.AppCompatActivity;


import android.graphics.Bitmap;

import android.os.Bundle;

import android.util.Base64;
import android.util.Log;

import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.joosulsa.databinding.ActivityTestBinding;

import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import java.util.HashMap;
import java.util.Map;

public class TestActivity extends AppCompatActivity {
    private static final String FLASK_SERVER_URL = "http://192.168.219.51:5000/upload_image";
    private ActivityTestBinding binding;

    private RequestQueue requestQueue;

    private String photoUrl = "http://192.168.219.62:8089/photoData";

    int postMethod = Request.Method.POST;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bitmap bitmap = getIntent().getParcelableExtra("TestImg");
        Log.d("확인",bitmap.toString());
        String base64Image = encodeToBase64(bitmap, Bitmap.CompressFormat.PNG,100);
        Log.d("변환",base64Image.toString());

        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(TestActivity.this);
        }

        uploadImageToServer(base64Image);

    }

    private String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(compressFormat,quality,byteArrayOutputStream);
        return Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);
    }

    private void uploadImageToServer(String base64Image){


        StringRequest request = new StringRequest(
                Request.Method.POST,
                FLASK_SERVER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("확인11", response.toString());
                            JSONObject jsonResponse = new JSONObject(response);

                            // JSON 객체에서 원하는 값을 추출
                            String result1 = jsonResponse.getString("result1");
                            String result2 = jsonResponse.getString("result2");

                            // 추출한 값들을 사용하거나 출력
                            Log.d("Result1", result1 + result2);
                            // Toast.makeText(TestActivity.this, "서버응답" +, Toast.LENGTH_SHORT).show();
                            // result값을 사용해서 tb_recycling에서 데이터 조회해서 detail로 넘겨주기
                            // 추가적으로 if문을 사용해서 조건 처리하기
                            if (result1.equals(3)) {
                                // 6번부터 특수데이터
                                int resultCast = Integer.parseInt(result2)+6;
                                String specialRes = Integer.toString(resultCast);
                                handlePhotoSearch(specialRes);
                            }else {
                                handlePhotoSearch(result1);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VolleyError", error.toString());
                        Toast.makeText(TestActivity.this, "서버오류", Toast.LENGTH_SHORT).show();
                    }
                }

        ){
            @androidx.annotation.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("image", base64Image);
                return params;
            }
        };
        requestQueue.add(request);
    }

    private void handlePhotoSearch(String result){

        StringRequest request = new StringRequest(
                postMethod,
                photoUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("sdasdasczxcz", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ){
            @androidx.annotation.Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("photoNum", result);
                return params;
            }
        };
        requestQueue.add(request);
    }

}