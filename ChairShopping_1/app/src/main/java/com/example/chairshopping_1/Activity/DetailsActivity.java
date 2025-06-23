package com.example.chairshopping_1.Activity;


import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chairshopping_1.Fragment.BottomNavFragment;
import com.example.chairshopping_1.R;

public class DetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_product_detail); // Tên layout XML bạn vừa gửi

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_container, new BottomNavFragment())
                .commit();
    }
}
