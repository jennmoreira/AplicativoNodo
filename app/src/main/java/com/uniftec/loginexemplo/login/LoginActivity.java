package com.uniftec.loginexemplo.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.uniftec.loginexemplo.home.HomeActivity;
import com.uniftec.loginexemplo.R;
import com.uniftec.loginexemplo.sql.usuarios.UsuariosDatabaseHelper;
import com.uniftec.loginexemplo.sql.eventos.EventosDatabaseHelper;
import com.uniftec.loginexemplo.sql.candidatos.CandidatoDatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    private EditText editEmail, editSenha;
    private Button btnEntrar;
    private TextView textCadastro, textEsqueceuSenha;
    private UsuariosDatabaseHelper usuariosDbHelper;
    private EventosDatabaseHelper eventosDbHelper;
    private CandidatoDatabaseHelper candidatosDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        usuariosDbHelper = new UsuariosDatabaseHelper(this);
        eventosDbHelper = new EventosDatabaseHelper(this);
        candidatosDbHelper = new CandidatoDatabaseHelper(this);

        inicializarComponentes();
        configurarEventos();

        editEmail.setText("c@gmail.com");
        editSenha.setText("111111");
    }

    private void inicializarComponentes() {
        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        btnEntrar = findViewById(R.id.btnEntrar);
        textCadastro = findViewById(R.id.textCadastro);
        textEsqueceuSenha = findViewById(R.id.textEsqueceuSenha);
    }

    private void configurarEventos() {
        btnEntrar.setOnClickListener(v -> realizarLogin());
        textCadastro.setOnClickListener(v -> abrirTelaCadastro());
        textEsqueceuSenha.setOnClickListener(v -> abrirTelaEsqueceuSenha());
    }

    private void realizarLogin() {
        String email = editEmail.getText().toString().trim();
        String password = editSenha.getText().toString().trim();

        if (!validarCampos(email, password)) {
            return;
        }

        long USU_ID_SESSION = usuariosDbHelper.realizaLogin(email, password);

        if (USU_ID_SESSION != -1) {
            Toast.makeText(this, getString(R.string.login_realizado), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.putExtra("USU_ID_SESSION", USU_ID_SESSION);
            startActivity(intent);
            finish();
        } else{
            Toast.makeText(this, getString(R.string.usuario_nao_encontrado), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validarCampos(String email, String senha) {
        if (TextUtils.isEmpty(email)) {
            editEmail.setError(getString(R.string.email_obrigatorio));
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError(getString(R.string.email_invalido));
            return false;
        }

        if (TextUtils.isEmpty(senha)) {
            editSenha.setError(getString(R.string.senha_obrigatoria));
            return false;
        }

        if (senha.length() < 6) {
            editSenha.setError(getString(R.string.senha_deve_ter_pelo_menos_6_caracteres));
            return false;
        }

        return true;
    }

    private void abrirTelaCadastro() {
        Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(intent);
    }

    private void abrirTelaEsqueceuSenha() {
        Intent intent = new Intent(LoginActivity.this, EsqueciSenhaActivity.class);
        startActivity(intent);
    }
}