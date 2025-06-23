package com.example.chairshopping_1.Activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chairshopping_1.Fragment.BottomNavFragment;
import com.example.chairshopping_1.ProductAdapter;
import com.example.chairshopping_1.R;
import com.example.chairshopping_1.models.Product;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        ImageView menuIcon = findViewById(R.id.menu_icon);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_container, new BottomNavFragment());
        transaction.commit();


        recyclerProducts = findViewById(R.id.recycler_products);

        List<Product> productList = new ArrayList<>();
        productList.add(new Product("Wooden Chair", "$25", R.drawable.sofa_background));
        productList.add(new Product("Soft Sofa", "$55", R.drawable.sofa_background));
        productList.add(new Product("Office Table", "$120", R.drawable.sofa_background));

        ProductAdapter adapter = new ProductAdapter(productList);
        recyclerProducts.setLayoutManager(new LinearLayoutManager(this));
        recyclerProducts.setAdapter(adapter);
    }
}
