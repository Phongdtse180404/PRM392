package com.example.demo_day3;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private int count = 0 ;
    private boolean isCounting = false;
    private TextView countText;
    private Button buttonCount;
    private Button buttonToast;
    private Button buttonReset;
    private Handler handler = new Handler(Looper.getMainLooper());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Link cac View trong layout su dung findViewById
        countText = findViewById(R.id.count_text);
        buttonCount = findViewById(R.id.button_count);
        buttonToast = findViewById(R.id.button_toast);
        buttonReset = findViewById(R.id.button_reset);

        //action khi nhan vao nut Count
        buttonCount.setOnClickListener(v -> {
            if (!isCounting){
                isCounting = true;
                startCounting();
            }else {
                isCounting=false;
            }
        });

        buttonToast.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Butoon Toast clicked!", Toast.LENGTH_SHORT).show();
        });

        buttonReset.setOnClickListener(v-> {
            isCounting = false; //Dung dem
            count = 0; //dat so dem ve 0
            countText.setText(String.valueOf(count)); // hien thi so den 0
        });

        //ap dung padding cho he thong bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
    //phuong thuc de bat dau dem
    private void startCounting() {
        new Thread(() -> {
            while(isCounting) {
                handler.post( () -> {
                    countText.setText(String.valueOf(count));
                    count++;
                });
                try {
                    Thread.sleep(1000); // dem moi giay
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();;
    }
}