package com.uniftec.loginexemplo.home.perfil;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.uniftec.loginexemplo.R;
import com.uniftec.loginexemplo.login.LoginActivity;
import com.uniftec.loginexemplo.perfil.ConfPrivacidadeActivity;
import com.uniftec.loginexemplo.perfil.EditarPerfilActivity;
import com.uniftec.loginexemplo.perfil.SobreAplicativoActivity;
import com.uniftec.loginexemplo.perfil.TrocarSenhaActivity;
import com.uniftec.loginexemplo.sql.usuarios.UsuariosDatabaseHelper;

public class FragmentPerfil extends Fragment {

    private long USU_ID_SESSION = -1;
    private Button btnLogout, btnEditar, btnTrocarSenha, btnSobre, btnPrivacidade;


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
        configurarEventos();
        return view;
    }

    private void inicializarComponentes(View view) {
        TextView textUsuario = view.findViewById(R.id.textUsuario);

        if (USU_ID_SESSION != -1) {
            UsuariosDatabaseHelper dbHelper = new UsuariosDatabaseHelper(getContext());
            String USU_NOME = dbHelper.retornaNomeUsuario((int) USU_ID_SESSION);
            dbHelper.close();

            if (!USU_NOME.isEmpty()) {
                textUsuario.setText("Olá, " + USU_NOME + "!");
            } else {
                textUsuario.setText(R.string.ola);
            }
        } else {
            textUsuario.setText(R.string.ola);
        }

        btnLogout = view.findViewById(R.id.btnLogout);
        btnEditar = view.findViewById(R.id.btnEditar);
        btnTrocarSenha = view.findViewById(R.id.btnTrocarSenha);
        btnSobre = view.findViewById(R.id.btnSobre);
        btnPrivacidade = view.findViewById(R.id.btnPrivacidade);
    }

    private void configurarEventos(){
        btnLogout.setOnClickListener(v -> realizaLogout());
        btnEditar.setOnClickListener(v -> editarPerfil());
        btnTrocarSenha.setOnClickListener(v -> trocarSenha());
        btnSobre.setOnClickListener(v -> sobre());
        btnPrivacidade.setOnClickListener(v -> privacidade());
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

    private void trocarSenha() {
        Intent intent = new Intent(getActivity(), TrocarSenhaActivity.class);
        intent.putExtra("USU_ID_SESSION", USU_ID_SESSION);
        startActivity(intent);
    }

    private void sobre() {
        Intent intent = new Intent(getActivity(), SobreAplicativoActivity.class);
        intent.putExtra("USU_ID_SESSION", USU_ID_SESSION);
        startActivity(intent);
    }

    private void privacidade() {
        Intent intent = new Intent(getActivity(), ConfPrivacidadeActivity.class);
        intent.putExtra("USU_ID_SESSION", USU_ID_SESSION);
        startActivity(intent);
    }
}