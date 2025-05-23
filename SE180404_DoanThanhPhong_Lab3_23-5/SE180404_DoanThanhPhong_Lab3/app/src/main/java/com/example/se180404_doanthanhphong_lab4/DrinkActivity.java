package com.example.se180404_doanthanhphong_lab4;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DrinkActivity extends AppCompatActivity {
    RecyclerView rvDrink;
    Button btnDatMon, btnThem, btnSua, btnXoa;
    ArrayList<FoodDrinkItem> dsDoUong;
    DrinkAdapter adapter;
    int vitriChon = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        rvDrink = findViewById(R.id.rvDrink);
        btnDatMon = findViewById(R.id.btnDatMon);
        btnThem = findViewById(R.id.btnThem);
        btnSua = findViewById(R.id.btnSua);
        btnXoa = findViewById(R.id.btnXoa);

        dsDoUong = new ArrayList<>();
        dsDoUong.add(new FoodDrinkItem(R.drawable.pepsi, "Pepsi", "Nước ngọt có gas", "12000"));
        dsDoUong.add(new FoodDrinkItem(R.drawable.sting, "Sting", "Nước ngọt giải khát", "20000"));
        dsDoUong.add(new FoodDrinkItem(R.drawable.coca, "Coca", "Nước ngọt coca", "18000"));
        dsDoUong.add(new FoodDrinkItem(R.drawable.tiger, "Tiger", "Bia Tiger", "25000"));

        adapter = new DrinkAdapter(this, dsDoUong);
        rvDrink.setAdapter(adapter);
        rvDrink.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnItemClickListener(position -> vitriChon = position);

        btnDatMon.setOnClickListener(v -> {
            if (vitriChon != -1) {
                FoodDrinkItem item = dsDoUong.get(vitriChon);
                Intent intent = new Intent();
                intent.putExtra("ten", item.ten);
                intent.putExtra("gia", item.gia);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                Toast.makeText(this, "Vui lòng chọn đồ uống", Toast.LENGTH_SHORT).show();
            }
        });

        btnThem.setOnClickListener(v -> showDialogThem());

        btnSua.setOnClickListener(v -> {
            if (vitriChon != -1) {
                showDialogSua(vitriChon);
            } else {
                Toast.makeText(this, "Vui lòng chọn món để sửa", Toast.LENGTH_SHORT).show();
            }
        });

        btnXoa.setOnClickListener(v -> {
            if (vitriChon != -1) {
                dsDoUong.remove(vitriChon);
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Đã xóa món ăn", Toast.LENGTH_SHORT).show();
                vitriChon = -1;
                adapter.setSelectedPosition(-1);
            } else {
                Toast.makeText(this, "Vui lòng chọn món để xóa", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDialogThem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_them_xoa_sua, null);
        builder.setView(view);

        EditText etTen = view.findViewById(R.id.etTen);
        EditText etMoTa = view.findViewById(R.id.etMoTa);
        EditText etGia = view.findViewById(R.id.etGia);
        Spinner spinnerHinh = view.findViewById(R.id.spinnerHinh);

        Integer[] dsHinh = {
                R.drawable.pepsi, R.drawable.coca,
                R.drawable.sting, R.drawable.tiger
        };

        ArrayAdapter<Integer> hinhAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dsHinh);
        hinhAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHinh.setAdapter(hinhAdapter);

        // Reset input
        etTen.setText("");
        etMoTa.setText("");
        etGia.setText("");
        spinnerHinh.setSelection(0);

        builder.setPositiveButton("Thêm", (dialogInterface, i) -> {
            String ten = etTen.getText().toString().trim();
            String moTa = etMoTa.getText().toString().trim();
            String gia = etGia.getText().toString().trim();
            int hinh = dsHinh[spinnerHinh.getSelectedItemPosition()];  // Lấy đúng ảnh từ vị trí spinner

            if (!ten.isEmpty() && !moTa.isEmpty() && !gia.isEmpty()) {
                dsDoUong.add(new FoodDrinkItem(hinh, ten, moTa, gia + " VND"));
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Đã thêm món ăn", Toast.LENGTH_SHORT).show();
                vitriChon = -1;  // Reset vị trí chọn
                adapter.setSelectedPosition(-1);
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Hủy", null);
        builder.create().show();
    }

    private void showDialogSua(int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_them_xoa_sua, null);
        builder.setView(view);

        EditText etTen = view.findViewById(R.id.etTen);
        EditText etMoTa = view.findViewById(R.id.etMoTa);
        EditText etGia = view.findViewById(R.id.etGia);
        Spinner spinnerHinh = view.findViewById(R.id.spinnerHinh);

        Integer[] dsHinh = {
                R.drawable.pepsi, R.drawable.coca,
                R.drawable.tiger, R.drawable.sting
        };

        ArrayAdapter<Integer> hinhAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dsHinh);
        hinhAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHinh.setAdapter(hinhAdapter);

        FoodDrinkItem item = dsDoUong.get(index);
        etTen.setText(item.ten);
        etMoTa.setText(item.moTa);
        etGia.setText(item.gia.replace(" VND", ""));

        for (int i = 0; i < dsHinh.length; i++) {
            if (dsHinh[i] == item.hinh) {
                spinnerHinh.setSelection(i);
                break;
            }
        }

        builder.setPositiveButton("Cập nhật", (dialogInterface, i) -> {
            String tenMoi = etTen.getText().toString().trim();
            String moTaMoi = etMoTa.getText().toString().trim();
            String giaMoi = etGia.getText().toString().trim();
            int hinhMoi = dsHinh[spinnerHinh.getSelectedItemPosition()];

            if (!tenMoi.isEmpty() && !moTaMoi.isEmpty() && !giaMoi.isEmpty()) {
                dsDoUong.set(index, new FoodDrinkItem(hinhMoi, tenMoi, moTaMoi, giaMoi + " VND"));
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Đã cập nhật món ăn", Toast.LENGTH_SHORT).show();
                vitriChon = -1;
                adapter.setSelectedPosition(-1);
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Hủy", null);
        builder.create().show();
    }
}
