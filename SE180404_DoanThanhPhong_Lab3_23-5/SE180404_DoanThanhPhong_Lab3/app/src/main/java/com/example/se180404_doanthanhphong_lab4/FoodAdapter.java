package com.example.se180404_doanthanhphong_lab4;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class FoodAdapter extends ArrayAdapter<FoodDrinkItem> {
    Activity context;
    int layout;
    ArrayList<FoodDrinkItem> list;

    public FoodAdapter(Activity context, int layout, ArrayList<FoodDrinkItem> list) {
        super(context, layout, list);
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View row = inflater.inflate(layout, null);

        ImageView imgHinh = row.findViewById(R.id.imgHinh);
        TextView tvTen = row.findViewById(R.id.tvTen);
        TextView tvMoTa = row.findViewById(R.id.tvMoTa);
        TextView tvGia = row.findViewById(R.id.tvGia);

        FoodDrinkItem item = list.get(position);
        imgHinh.setImageResource(item.hinh);
        tvTen.setText(item.ten);
        tvMoTa.setText(item.moTa);
        tvGia.setText(item.gia);

        return row;
    }
}
