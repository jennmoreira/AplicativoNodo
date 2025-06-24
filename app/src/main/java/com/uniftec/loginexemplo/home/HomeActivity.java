package com.uniftec.loginexemplo.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.uniftec.loginexemplo.DetailEvento;
import com.uniftec.loginexemplo.FragmentEventos;
import com.uniftec.loginexemplo.R;

public class HomeActivity extends AppCompatActivity {

    private View vaga1, vaga2, evento1, evento2;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        inicializarComponentes();
        configurarEventos();
    }

    private void inicializarComponentes(){
        bottomNavigationView = findViewById(R.id.btnNavega);
        evento1 = findViewById(R.id.evento1);
        evento2 = findViewById(R.id.evento2);
        vaga1 = findViewById(R.id.vaga1);
        vaga2 = findViewById(R.id.vaga2);
    }

    private void configurarEventos() {
        /*
        evento1.setOnClickListener(v -> abrirEvento1());
        evento2.setOnClickListener(v -> abrirEvento2());
        vaga1.setOnClickListener(v -> abrirVaga1());
        vaga2.setOnClickListener(v -> abrirVaga2());
         */

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                Fragment selectedFragment = null;

                if (itemId == R.id.item_home) {
                } else if (itemId == R.id.item_eventos) {
                } else if (itemId == R.id.item_perfil) {
                }

                if (selectedFragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.listview, selectedFragment);
                    fragmentTransaction.commit();
                    return true;
                }
                return false;
            }
        });
    }
}
