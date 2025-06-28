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
    private long USU_ID_SESSION = -1;

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

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("USU_ID_SESSION")) {
            USU_ID_SESSION = intent.getLongExtra("USU_ID_SESSION", -1L);
        }

        inicializarComponentes();
        configurarEventos();
        bottomNavigationView.setSelectedItemId(R.id.item_home);
    }

    private void inicializarComponentes(){
        bottomNavigationView = findViewById(R.id.btnNavega);
        evento1 = findViewById(R.id.evento1);
        evento2 = findViewById(R.id.evento2);
        vaga1 = findViewById(R.id.vaga1);
        vaga2 = findViewById(R.id.vaga2);
    }

    private void configurarEventos() {

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                int id = item.getItemId();

                if (id == R.id.item_home) {
                    selectedFragment = new FragmentHome();
                } else if (id == R.id.item_eventos) {
                    selectedFragment = new FragmentEventos();
                } else if (id == R.id.item_perfil) {
                    selectedFragment = new FragmentPerfil();
                    Bundle bundleHome = new Bundle();
                    bundleHome.putLong("USU_ID_SESSION", USU_ID_SESSION);
                    selectedFragment.setArguments(bundleHome);
                }


                if (selectedFragment != null) {
                    carregarFragmento(selectedFragment);
                    return true;
                }
                return false;
            }
        });
    }

    private void carregarFragmento(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

}
