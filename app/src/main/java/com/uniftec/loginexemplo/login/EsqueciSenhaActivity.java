package com.uniftec.loginexemplo.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.uniftec.loginexemplo.R;
import com.uniftec.loginexemplo.sql.UsuariosDatabaseHelper;

public class EsqueciSenhaActivity extends AppCompatActivity {

    private EditText editEmail;
    private Button btnEnviar, btnVoltar;
    private UsuariosDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_esqueci_senha);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new UsuariosDatabaseHelper(this);

        inicializarComponentes();
        configurarEventos();
    }

    private void inicializarComponentes () {
        editEmail = findViewById(R.id.editEmail);
        btnEnviar = findViewById(R.id.btnEnviar);
        btnVoltar = findViewById(R.id.btnVoltar);
    }

    private void configurarEventos () {
        btnEnviar.setOnClickListener(v -> realizaEnvio());
        btnVoltar.setOnClickListener(v -> abrirTelaLogin());
    }

    private void realizaEnvio() {
        String email = editEmail.getText().toString().trim();

        if (!validarCampos(email)) {
            return;
        }

        if (dbHelper.validaEmailUtilizado(email)) {
            Toast.makeText(this, getString(R.string.email_enviado_verifique_sua_caixa_entrada), Toast.LENGTH_LONG).show();
            abrirTelaLogin();
        } else {
            Toast.makeText(this, getString(R.string.email_nao_encontrado), Toast.LENGTH_LONG).show();
        }
    }

    private boolean validarCampos (String email){
        if (TextUtils.isEmpty(email)) {
            editEmail.setError(getString(R.string.email_obrigatorio));
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError(getString(R.string.email_invalido));
            return false;
        }

        return true;
    }

    private void abrirTelaLogin(){
        Intent intent = new Intent(EsqueciSenhaActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
