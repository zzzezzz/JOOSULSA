package com.example.joosulsa.category;

import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.joosulsa.MainActivity;
import com.example.joosulsa.R; // 이 부분은 프로젝트에 맞게 수정해주세요.
import com.example.joosulsa.RecycleDetailActivity;
import com.example.joosulsa.SearchActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainCategoryAdapter extends RecyclerView.Adapter<MainCategoryViewHolder> {

    private List<MainCategoryVO> dataset;

    private RequestQueue requestQueue;

    public MainCategoryAdapter(List<MainCategoryVO> dataset) {
        this.dataset = dataset;
    }

    @NonNull
    @Override
    public MainCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_view, parent, false);
        return new MainCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainCategoryViewHolder holder, int position) {
        String title = dataset.get(position).getTitle();
        int img = dataset.get(position).getImg();
        Log.d("qwcxnyfgev", title);
        holder.getIconCategory().setImageResource(img);
        holder.getTitCategory().setText(title);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clickedTitle = dataset.get(position).getTitle();
                Log.d("aboutHere", clickedTitle);
                if (requestQueue == null) {
                    requestQueue = Volley.newRequestQueue(v.getContext());
                }
                categoryRecySend(clickedTitle, v);
            }
        });


    }


    @Override
    public int getItemCount() {
        return dataset.size();
    }


    // 클릭한 카테고리 이름 받아와서 스프링에 조회하고 데이터 전달
    private void categoryRecySend(String clickedTitle, View v){
        String cateRecySendUrl = "http://192.168.219.62:8089/cateRecySend";
        int postMethod = Request.Method.POST;
        String method = "etc";
        Log.d("여긴옴?", cateRecySendUrl + postMethod + method + clickedTitle);
        StringRequest request = new StringRequest(
                postMethod,
                cateRecySendUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("cateRecySend", response);
                        handleCateData(response, v);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("cateRecySendERR", error.toString());
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("clickedTitle", clickedTitle);
                params.put("method", method);
                Log.d("SendDataChecking", clickedTitle + method);
                return params;
            }
        };
        requestQueue.add(request);
    }

    private void handleCateData(String response, View v){
        try {
            // 데이터 파싱 작업
            // 데이터 파싱 작업
            JSONObject jsonResponse = new JSONObject(response);

            // searchCheck JSON 객체 가져오기
            JSONObject searchCheckObject = jsonResponse.getJSONObject("searchCheck");

            // searchCheck의 속성들 가져오기
            String trashName = searchCheckObject.getString("trashName");
            String sepaMethod = searchCheckObject.getString("sepaMethod");
            String sepaCaution = searchCheckObject.getString("sepaCaution");
            String sepaImg = searchCheckObject.getString("sepaImg");
            String sepaVideo = searchCheckObject.getString("sepaVideo");
            String recycleVideo = searchCheckObject.getString("recycleVideo");
            String recycleImg = searchCheckObject.getString("recycleImg");
            String recycleNum = searchCheckObject.getString("recycleNum");


            // 확인
            Log.d("recyDataHandle", "방법: " + sepaMethod + " 주의사항: " + sepaCaution +
                    " 이미지: " + sepaImg + " 분리수거 영상: " + sepaVideo + " 업사이클 영상: " + recycleVideo +
                    " 업사이클 이미지 : " + recycleImg);
            // Intent에 값 넣어주기
            Intent textSearchIntent = new Intent(v.getContext(), RecycleDetailActivity.class);
            textSearchIntent.putExtra("trashName", trashName);
            textSearchIntent.putExtra("sepaMethod",sepaMethod);
            textSearchIntent.putExtra("sepaCaution",sepaCaution);
            textSearchIntent.putExtra("sepaImg",sepaImg);
            textSearchIntent.putExtra("sepaVideo",sepaVideo);
            textSearchIntent.putExtra("recycleVideo",recycleVideo);
            textSearchIntent.putExtra("recycleImg",recycleImg);
            textSearchIntent.putExtra("searchmethod", "etc");
            textSearchIntent.putExtra("recyNum", recycleNum);
            Log.d("searchWhy", "방법: " + sepaMethod + " 주의사항: " + sepaCaution +
                    " 이미지: " + sepaImg + " 분리수거 영상: " + sepaVideo + " 업사이클 영상: " + recycleVideo +
                    " 업사이클 이미지 : " + recycleImg);
            v.getContext().startActivity(textSearchIntent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
