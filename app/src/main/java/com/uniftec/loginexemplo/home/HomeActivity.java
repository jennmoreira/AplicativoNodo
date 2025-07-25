package com.uniftec.loginexemplo.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

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
import com.uniftec.loginexemplo.R;
import com.uniftec.loginexemplo.home.home.FragmentHome;
import com.uniftec.loginexemplo.home.perfil.FragmentPerfil;
import com.uniftec.loginexemplo.home.eventos.FragmentEventos;


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

        FragmentHome initialFragment = new FragmentHome();
        Bundle bundleHome = new Bundle();
        bundleHome.putLong("USU_ID_SESSION", USU_ID_SESSION);
        initialFragment.setArguments(bundleHome);
        carregarFragmento(initialFragment);

        bottomNavigationView.setSelectedItemId(R.id.item_home);
    }

    private void inicializarComponentes(){
        bottomNavigationView = findViewById(R.id.btnNavega);
    }

    private void configurarEventos() {

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                int id = item.getItemId();

                if (id == R.id.item_home) {
                    selectedFragment = new FragmentHome();
                    Bundle bundleHome = new Bundle();
                    bundleHome.putLong("USU_ID_SESSION", USU_ID_SESSION);
                    selectedFragment.setArguments(bundleHome);
                } else if (id == R.id.item_eventos) {
                    selectedFragment = new FragmentEventos();
                    Bundle bundleHome = new Bundle();
                    bundleHome.putLong("USU_ID_SESSION", USU_ID_SESSION);
                    selectedFragment.setArguments(bundleHome);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (data != null && data.hasExtra("USU_ID_SESSION")) {
                    USU_ID_SESSION = data.getLongExtra("USU_ID_SESSION", -1L);
                }
            }
        }
    }
}