package com.example.se180404_doanthanhphong_lab4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder> {

    private Context context;
    private ArrayList<FoodDrinkItem> dsDoUong;
    private int selectedPosition = -1;  // Để highlight item đang chọn

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public DrinkAdapter(Context context, ArrayList<FoodDrinkItem> dsDoUong) {
        this.context = context;
        this.dsDoUong = dsDoUong;
    }

    @NonNull
    @Override
    public DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food_drink, parent, false);
        return new DrinkViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull DrinkViewHolder holder, int position) {
        FoodDrinkItem item = dsDoUong.get(position);
        holder.imgHinh.setImageResource(item.hinh);
        holder.tvTen.setText(item.ten);
        holder.tvMoTa.setText(item.moTa);
        holder.tvGia.setText(item.gia);

        // Highlight item đang chọn
        if (selectedPosition == position) {
            holder.itemView.setBackgroundColor(0x9934B5E4); // màu xanh nhạt ví dụ
        } else {
            holder.itemView.setBackgroundColor(0x00000000); // trong suốt
        }
    }

    @Override
    public int getItemCount() {
        return dsDoUong.size();
    }

    public void setSelectedPosition(int pos) {
        selectedPosition = pos;
        notifyDataSetChanged();
    }

    public class DrinkViewHolder extends RecyclerView.ViewHolder {
        ImageView imgHinh;
        TextView tvTen, tvMoTa, tvGia;

        public DrinkViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            imgHinh = itemView.findViewById(R.id.imgHinh);
            tvTen = itemView.findViewById(R.id.tvTen);
            tvMoTa = itemView.findViewById(R.id.tvMoTa);
            tvGia = itemView.findViewById(R.id.tvGia);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }
}
