package com.example.joosulsa;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import android.os.Bundle;

import android.util.Base64;
import android.util.Log;

import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.joosulsa.databinding.ActivityTestBinding;

import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TestActivity extends AppCompatActivity {
    private static final String FLASK_SERVER_URL = "http://192.168.219.51:5000/upload_image";
    private ActivityTestBinding binding;

    private RequestQueue requestQueue;

    private String photoUrl = "http://192.168.219.62:8089/photoData";

    int postMethod = Request.Method.POST;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // gif 파일 처리
        Glide.with(this)
                .asGif()
                .load(R.raw.loading)
                .into(binding.loadingImg);

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
                            String specialRes = "";
                            // JSON 객체에서 원하는 값을 추출
                            String result1 = jsonResponse.getString("result1");
                            String result2 = jsonResponse.getString("result2");
                            String accuracy = jsonResponse.getString("accuracy");
                            double acc = Double.valueOf(accuracy);
                            int mathAcc = (int)Math.floor(acc);

                            // 추출한 값들을 사용하거나 출력
                            Log.d("Result1369", result1 + result2 + "/" + accuracy + "//" + mathAcc);
                            // Toast.makeText(TestActivity.this, "서버응답" +, Toast.LENGTH_SHORT).show();
                            // result값을 사용해서 tb_recycling에서 데이터 조회해서 detail로 넘겨주기
                            // 추가적으로 if문을 사용해서 조건 처리하기
                            if (result1.equals("3")) {
                                // 6번부터 특수데이터
                                if (mathAcc>30){
                                    if (result2.equals(0) || result2.equals(1)){
                                        specialRes ="6";
                                        handlePhotoSearch(specialRes);
                                    }else{
                                        specialRes = "7";
                                        handlePhotoSearch(specialRes);
                                    }
                                }else{
                                    handlePhotoSearch(result1);
                                }

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
            @Nullable
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
        Log.d("handleData", result);
        StringRequest request = new StringRequest(
                postMethod,
                photoUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("sdasdasczxcz", response);
                        photoDataSend(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("photoError", error.toString());
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                // 검색 방법
                String searchMethod = "video";
                preferences = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);

                String autoId = preferences.getString("autoId", "1");
                int sendNum;
                if (autoId.equals("1")){
                    searchMethod = "etc";
                    params.put("method", searchMethod);
                    sendNum = 17 + Integer.parseInt(result);
                    params.put("sendNum", Integer.toString(sendNum));
                }else {
                    params.put("method", searchMethod);
                    sendNum = 9 + Integer.parseInt(result);
                    params.put("sendNum", Integer.toString(sendNum));
                }
                params.put("userId", autoId);
                long now =System.currentTimeMillis();
                Date today =new Date(now);
                SimpleDateFormat format =new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                String time = format.format(today);
                params.put("earnTime", time);
                Log.d("handleSendData", sendNum + searchMethod + autoId + time);
                return params;
            }
        };
        requestQueue.add(request);
    }

    private void photoDataSend(String response){

        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONObject photoCheckObject = jsonResponse.getJSONObject("recyData");

            String recyNum = photoCheckObject.getString("recycleNum");
            String trashNm = photoCheckObject.getString("trashName");
            String sepaMethod = photoCheckObject.getString("sepaMethod");
            String sepaCaution = photoCheckObject.getString("sepaCaution");
            String sepaImg = photoCheckObject.getString("sepaImg");
            String sepaVideo = photoCheckObject.getString("sepaVideo");
            String recyViews = photoCheckObject.getString("recycleViews");
            String recyVideo = photoCheckObject.getString("recycleVideo");
            String recycleImg = photoCheckObject.getString("recycleImg");

            // totalPoints 가져오기
            int totalPoints = jsonResponse.getInt("totalPoints");
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("userPoint", totalPoints);
            editor.apply();

            Intent textSearchIntent = new Intent(TestActivity.this, RecycleDetailActivity.class);
            textSearchIntent.putExtra("trashName", trashNm);
            textSearchIntent.putExtra("sepaMethod",sepaMethod);
            textSearchIntent.putExtra("sepaCaution",sepaCaution);
            textSearchIntent.putExtra("sepaImg",sepaImg);
            textSearchIntent.putExtra("sepaVideo",sepaVideo);
            textSearchIntent.putExtra("recycleVideo",recyVideo);
            textSearchIntent.putExtra("recycleImg",recycleImg);
            textSearchIntent.putExtra("searchmethod", "text");
            textSearchIntent.putExtra("recyNum", recyNum);
            startActivity(textSearchIntent);
            finish();

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

}