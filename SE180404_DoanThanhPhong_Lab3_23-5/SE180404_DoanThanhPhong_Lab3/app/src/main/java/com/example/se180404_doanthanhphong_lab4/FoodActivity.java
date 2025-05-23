package com.example.se180404_doanthanhphong_lab4;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class FoodActivity extends AppCompatActivity {
    ListView lvFood;
    Button btnDatMon, btnThem, btnSua, btnXoa;
    ArrayList<FoodDrinkItem> dsMonAn;
    FoodAdapter adapter;
    int vitriChon = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        lvFood = findViewById(R.id.lvFood);
        btnDatMon = findViewById(R.id.btnDatMon);
        btnThem = findViewById(R.id.btnThem);
        btnSua = findViewById(R.id.btnSua);
        btnXoa = findViewById(R.id.btnXoa);

        dsMonAn = new ArrayList<>();
        dsMonAn.add(new FoodDrinkItem(R.drawable.pho, "Phở Hà Nội", "Nước dùng thanh, bánh phở mềm", "35000"));
        dsMonAn.add(new FoodDrinkItem(R.drawable.bunbo, "Bún Bò Huế", "Đậm đà hương vị Huế", "40000"));
        dsMonAn.add(new FoodDrinkItem(R.drawable.miquang, "Mì Quảng", "Đặc sản Quảng Nam", "30000"));
        dsMonAn.add(new FoodDrinkItem(R.drawable.hutieu, "Hủ Tíu Sài Gòn", "Thơm ngon miền Nam", "32000"));

        adapter = new FoodAdapter(this, R.layout.item_food_drink, dsMonAn);
        lvFood.setAdapter(adapter);

        lvFood.setOnItemClickListener((adapterView, view, i, l) -> vitriChon = i);

        btnDatMon.setOnClickListener(v -> {
            if (vitriChon != -1) {
                FoodDrinkItem item = dsMonAn.get(vitriChon);
                Intent intent = new Intent();
                intent.putExtra("ten", item.ten);
                intent.putExtra("gia", item.gia);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                Toast.makeText(this, "Vui lòng chọn món ăn", Toast.LENGTH_SHORT).show();
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
                dsMonAn.remove(vitriChon);
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Đã xóa món ăn", Toast.LENGTH_SHORT).show();
                vitriChon = -1;
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
                R.drawable.pho, R.drawable.bunbo,
                R.drawable.miquang, R.drawable.hutieu
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
            int hinh = (int) spinnerHinh.getSelectedItem();

            if (!ten.isEmpty() && !moTa.isEmpty() && !gia.isEmpty()) {
                dsMonAn.add(new FoodDrinkItem(hinh, ten, moTa, gia + " VND"));
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Đã thêm món ăn", Toast.LENGTH_SHORT).show();
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
                R.drawable.pho, R.drawable.bunbo,
                R.drawable.miquang, R.drawable.hutieu
        };

        ArrayAdapter<Integer> hinhAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dsHinh);
        hinhAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHinh.setAdapter(hinhAdapter);

        FoodDrinkItem item = dsMonAn.get(index);
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
            int hinhMoi = (int) spinnerHinh.getSelectedItem();

            if (!tenMoi.isEmpty() && !moTaMoi.isEmpty() && !giaMoi.isEmpty()) {
                dsMonAn.set(index, new FoodDrinkItem(hinhMoi, tenMoi, moTaMoi, giaMoi + " VND"));
                adapter.notifyDataSetChanged();
                vitriChon = -1;
                Toast.makeText(this, "Đã cập nhật món ăn", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Hủy", null);
        builder.create().show();
    }
}
