package com.example.tablayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tablayout.databinding.ActivityDetailEventoBinding;

public class DetailEvento<DetailEventoBinding> extends AppCompatActivity {
    DetailEvento binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDetailEventoBinding binding = DetailEvento.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = this.getIntent();
        if (intent != null){
            String name = intent.getStringExtra("name");
            String time = intent.getStringExtra("time");
            int ingredients = intent.getIntExtra("ingredients", R.string.maggiIngredients);
            int desc = intent.getIntExtra("desc", R.string.maggieDesc);
            int image = intent.getIntExtra("image", R.drawable.maggi);
            binding.detailName.setText(name);
            binding.detailTime.setText(time);
            binding.detailDesc.setText(desc);
            binding.detailIngredients.setText(ingredients);
            binding.detailImage.setImageResource(image);
        }
    }




}