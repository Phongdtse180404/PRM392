package com.example.chairshopping_1.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.chairshopping_1.Activity.CartActivity;
import com.example.chairshopping_1.Activity.MainActivity;
import com.example.chairshopping_1.Activity.DetailsActivity;
import com.example.chairshopping_1.Activity.LoginActivity;
import com.example.chairshopping_1.R;

public class BottomNavFragment extends Fragment {

    public BottomNavFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bottom_nav, container, false);

        ImageView menuIcon = view.findViewById(R.id.menu_icon);
        menuIcon.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(getContext(), v);
            popupMenu.getMenuInflater().inflate(R.menu.menu_bottom_nav, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {
                Context context = getContext();
                int id = item.getItemId();

                if (id == R.id.action_login) {
                    context.startActivity(new Intent(context, LoginActivity.class));
                    return true;
                } else if (id == R.id.action_details) {
                    context.startActivity(new Intent(context, DetailsActivity.class));
                    return true;
                } else if (id == R.id.action_cart) {
                    context.startActivity(new Intent(context, CartActivity.class));
                    return true;
                }

                return false;
            });


            popupMenu.show();
        });

        // Xử lý khi ấn vào app_title để về màn hình chính
        TextView appTitle = view.findViewById(R.id.app_title);
        appTitle.setOnClickListener(v -> {
            Context context = getContext();
            if (context != null) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent);
            }
        });

        return view;
    }
}
