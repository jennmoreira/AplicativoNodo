package com.uniftec.loginexemplo.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.uniftec.loginexemplo.R;
import com.uniftec.loginexemplo.login.CadastroActivity;
import com.uniftec.loginexemplo.login.LoginActivity;
import com.uniftec.loginexemplo.perfil.EditarPerfilActivity;
import com.uniftec.loginexemplo.sql.UsuariosDatabaseHelper;

public class FragmentPerfil extends Fragment {

    private TextView textUsuario;
    private long USU_ID_SESSION = -1;
    private Button btnLogout, btnEditar;


    public FragmentPerfil() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            USU_ID_SESSION = getArguments().getLong("USU_ID_SESSION", -1L);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        inicializarComponentes(view);
        configurarEventos(view);
        return view;
    }

    private void inicializarComponentes(View view) {
        textUsuario = view.findViewById(R.id.textUsuario);

        if (USU_ID_SESSION != -1) {
            UsuariosDatabaseHelper dbHelper = new UsuariosDatabaseHelper(getContext());
            String USU_NOME = dbHelper.retornaNomeUsuario((int) USU_ID_SESSION);
            dbHelper.close();

            if (!USU_NOME.isEmpty()) {
                textUsuario.setText("Olá, " + USU_NOME + "!");
            } else {
                textUsuario.setText("Olá!");
            }
        } else {
            textUsuario.setText("Olá!");
        }

        btnLogout = view.findViewById(R.id.btnLogout);
        btnEditar = view.findViewById(R.id.btnEditar);
    }

    private void configurarEventos(View view){
        btnLogout.setOnClickListener(v -> realizaLogout());
        btnEditar.setOnClickListener(v -> editarPerfil());
    }

    private void realizaLogout() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    private void editarPerfil() {
        Intent intent = new Intent(getActivity(), EditarPerfilActivity.class);
        intent.putExtra("USU_ID_SESSION", USU_ID_SESSION);
        startActivity(intent);
    }
}