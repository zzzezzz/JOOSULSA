package com.example.joosulsa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.joosulsa.databinding.ActivityMainBinding;
import com.example.joosulsa.fragment.HomeFragment;
import com.example.joosulsa.fragment.MypageFragment;
import com.example.joosulsa.fragment.RankFragment;
import com.example.joosulsa.fragment.ShopFragment;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction().replace(
                R.id.fl, new HomeFragment()
        ).commit();


        binding.bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // 매개변수 item : 내가 클릭한 item의 정보(속성, id, icon, title...)
                switch (item.getItemId()){
                    case R.id.tab1: //Fragment 갈아끼우는 코드
                        // fragmentManager의 도움을 받아 replace를 실행한다.
                        // fragmentmanager는 transaction의 도움을 받아 실행된다.
                        getSupportFragmentManager().beginTransaction().replace(
                                // 1) 어디에
                                R.id.fl,
                                // 2) 어떤 프레그먼트
                                new HomeFragment()
                        ).commit();
                        break;
                    case R.id.tab2: //Fragment 갈아끼우는 코드
                        getSupportFragmentManager().beginTransaction().replace(
                                // 1) 어디에
                                R.id.fl,
                                // 2) 어떤 프레그먼트
                                new RankFragment()
                        ).commit();
                        break;
                    case R.id.tab3: //Fragment 갈아끼우는 코드
                        getSupportFragmentManager().beginTransaction().replace(
                                // 1) 어디에
                                R.id.fl,
                                // 2) 어떤 프레그먼트
                                new ShopFragment()
                        ).commit();
                        break;
                    case R.id.tab4: //Fragment 갈아끼우는 코드
                        getSupportFragmentManager().beginTransaction().replace(
                                // 1) 어디에
                                R.id.fl,
                                // 2) 어떤 프레그먼트
                                new MypageFragment()
                        ).commit();
                        break;
                    default:
                        getSupportFragmentManager().beginTransaction().replace(
                                // 1) 어디에
                                R.id.fl,
                                // 2) 어떤 프레그먼트
                                new HomeFragment()
                        ).commit();
                        break;
                }

                // false : 클릭이 계속 되어지고 있음을 나타낸다.
                // true : 한번 클릭이 되면 그대로 종료
                return true;
            }
        });

    }






}