package com.example.tablayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.uniftec.loginexemplo.R;
import com.uniftec.loginexemplo.databinding.ActivityDetailEventoBinding;

public class DetailEvento extends AppCompatActivity {
    // Remova o generics incorreto e declare o binding corretamente
    private ActivityDetailEventoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Corrigir a forma de inflar o binding
        binding = ActivityDetailEventoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = this.getIntent();
        if (intent != null){

        }
    }
}
