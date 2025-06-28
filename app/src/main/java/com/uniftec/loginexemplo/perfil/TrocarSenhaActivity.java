package com.uniftec.loginexemplo.perfil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.uniftec.loginexemplo.R;
import com.uniftec.loginexemplo.home.HomeActivity;
import com.uniftec.loginexemplo.login.LoginActivity;
import com.uniftec.loginexemplo.sql.UsuariosDatabaseHelper;


public class TrocarSenhaActivity extends AppCompatActivity {

    private EditText editSenhaAtual, editSenhaNova, editSenhaNovaConf;
    private Button btnVoltar, btnSalvar;
    private UsuariosDatabaseHelper dbHelper;
    private long USU_ID_SESSION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trocar_senha);

        dbHelper = new UsuariosDatabaseHelper(this);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("USU_ID_SESSION")) {
            USU_ID_SESSION = intent.getLongExtra("USU_ID_SESSION", -1L);
        }

        inicializarComponentes();
        configurarEventos();
    }

    private void inicializarComponentes() {
        editSenhaAtual = findViewById(R.id.editSenhaAtual);
        editSenhaNova = findViewById(R.id.editSenhaNova);
        editSenhaNovaConf = findViewById(R.id.editSenhaNovaConf);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnVoltar = findViewById(R.id.btnVoltar);
    }

    private void configurarEventos() {

        btnSalvar.setOnClickListener(v -> atualizaCadastro(USU_ID_SESSION));
        btnVoltar.setOnClickListener(v -> voltar(USU_ID_SESSION));
    }

    private void atualizaCadastro(long USU_ID_SESSION) {
        String senhaAtual = editSenhaAtual.getText().toString().trim();
        String senhaNova = editSenhaNova.getText().toString().trim();
        String senhaNovaConf = editSenhaNovaConf.getText().toString().trim();

        if (validarCampos(senhaAtual, senhaNova, senhaNovaConf, USU_ID_SESSION)) {
            Toast.makeText(this, R.string.perfil_atualizado_com_sucesso, Toast.LENGTH_LONG).show();

            dbHelper.atualizarSenhaUsuario(USU_ID_SESSION, senhaNova);
            Toast.makeText(this, R.string.perfil_atualizado_com_sucesso, Toast.LENGTH_LONG).show();
            voltar(USU_ID_SESSION);
        }
    }

    private boolean validarCampos(String senhaAtual, String senhaNova, String senhaNovaConf, long userId) {
        if (TextUtils.isEmpty(senhaAtual)) {
            editSenhaAtual.setError(getString(R.string.senha_atual_obrigatoria));
            return false;
        }

        if (TextUtils.isEmpty(senhaNova)) {
            editSenhaNova.setError(getString(R.string.senha_nova_obrigatoria));
            return false;
        }

        if (TextUtils.isEmpty(senhaNovaConf)) {
            editSenhaNovaConf.setError(getString(R.string.confirmacao_senha_obrigatoria));
            return false;
        }

        if (!senhaNova.equals(senhaNovaConf)) {
            editSenhaNova.setError(getString(R.string.senhas_nao_conferem));
            editSenhaNovaConf.setError(getString(R.string.senhas_nao_conferem));
            return false;
        }

        boolean senhaAtualCorreta = dbHelper.validaSenhaAtual(userId, senhaAtual);

        if (!senhaAtualCorreta) {
            editSenhaAtual.setError(getString(R.string.senha_invalida));
            return false;
        }

        return true;
    }

    private void abrirTelaLogin() {
        Intent intent = new Intent(TrocarSenhaActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void voltar(long USU_ID_SESSION) {
        Intent intent = new Intent(TrocarSenhaActivity.this, HomeActivity.class);
        intent.putExtra("USU_ID_SESSION", USU_ID_SESSION);
        startActivity(intent);
        finish();
    }
}