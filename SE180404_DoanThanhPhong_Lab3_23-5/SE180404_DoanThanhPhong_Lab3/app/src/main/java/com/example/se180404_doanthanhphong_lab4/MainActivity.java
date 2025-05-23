package com.example.se180404_doanthanhphong_lab4;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnChonThucAn, btnChonDoUong, btnThoat;
    TextView tvKetQua;
    String tenMonAn = "", giaMonAn = "", tenDoUong = "", giaDoUong = "";

    final int REQUEST_THUC_AN = 1;
    final int REQUEST_DO_UONG = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // bạn đã có file này rồi

        btnChonThucAn = findViewById(R.id.btnChonThucAn);
        btnChonDoUong = findViewById(R.id.btnChonDoUong);
        btnThoat = findViewById(R.id.btnThoat);
        tvKetQua = findViewById(R.id.tvKetQua);

        btnChonThucAn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FoodActivity.class);
            startActivityForResult(intent, REQUEST_THUC_AN);
        });

        btnChonDoUong.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DrinkActivity.class);
            startActivityForResult(intent, REQUEST_DO_UONG);
        });

        btnThoat.setOnClickListener(v -> finish());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            String ten = data.getStringExtra("ten");
            String gia = data.getStringExtra("gia");

            if (requestCode == REQUEST_THUC_AN) {
                tenMonAn = ten;
                giaMonAn = gia;
            } else if (requestCode == REQUEST_DO_UONG) {
                tenDoUong = ten;
                giaDoUong = gia;
            }

            String ketQua = "";
            if (tenMonAn != null && !tenMonAn.isEmpty()) ketQua += tenMonAn + " (" + giaMonAn + "đ)";
            if (tenDoUong != null && !tenDoUong.isEmpty()) {
                if (!ketQua.isEmpty()) ketQua += " - ";
                ketQua += tenDoUong + " (" + giaDoUong + "đ)";
            }

            tvKetQua.setText(ketQua);
        }
    }
}
