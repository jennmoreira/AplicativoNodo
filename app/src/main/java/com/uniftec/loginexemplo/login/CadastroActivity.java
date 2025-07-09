package com.uniftec.loginexemplo.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.uniftec.loginexemplo.R;
import com.uniftec.loginexemplo.sql.AppDatabaseHelper;

public class CadastroActivity extends AppCompatActivity {

    private EditText editName, editEmail, editPassword, editConfirmPassword;
    private Button btnVoltar, btnCriar;
    private AppDatabaseHelper db;
    private RadioGroup radioGroupTipoUsuario;
    private RadioButton radioCriador, radioPrestador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);

        db = new AppDatabaseHelper(this);

        inicializarComponentes();
        configurarEventos();
    }

    private void inicializarComponentes() {
        editName = findViewById(R.id.editTextName);
        editEmail = findViewById(R.id.editTextEmail);
        editPassword = findViewById(R.id.editTextPassword);
        editConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        btnCriar = findViewById(R.id.btnCriar);
        btnVoltar = findViewById(R.id.btnVoltar);
        radioGroupTipoUsuario = findViewById(R.id.radioGroupTipoUsuario);
        radioCriador = findViewById(R.id.radioCriador);
        radioPrestador = findViewById(R.id.radioPrestador);
    }

    private void configurarEventos() {
        btnCriar.setOnClickListener(v -> realizarCadastro());
        btnVoltar.setOnClickListener(v -> abrirTelaLogin());
    }

    private void realizarCadastro() {
        String name = editName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String confirmPassword = editConfirmPassword.getText().toString().trim();

        String tipoUsuario;
        if (radioCriador.isChecked()) {
            tipoUsuario = "criador";
        } else if (radioPrestador.isChecked()) {
            tipoUsuario = "prestador";
        } else {
            Toast.makeText(this, getString(R.string.selecione_o_tipo_de_usuario), Toast.LENGTH_SHORT).show();
            return;
        }

        if (!validarCampos(name, email, password, confirmPassword)) {
            return;
        }

        if (salvarUsuario(name, email, password, tipoUsuario)) {
            Toast.makeText(this, getString(R.string.cadastro_realizado_com_suceso_faca_login_continuar), Toast.LENGTH_LONG).show();
            abrirTelaLogin();
            finish();
        } else {
            Toast.makeText(this, getString(R.string.ocorreu_erro_tente_novamente), Toast.LENGTH_LONG).show();
        }
    }

    private boolean validarCampos(String name, String email, String password, String confirmPassword) {
        if (TextUtils.isEmpty(name)) {
            editName.setError(getString(R.string.nome_obrigatorio));
            return false;
        }

        if (name.length() < 5) {
            editName.setError(getString(R.string.nome_deve_ter_minimo_5_caracteres));
            return false;
        }

        if (TextUtils.isEmpty(email)) {
            editEmail.setError(getString(R.string.email_obrigatorio));
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError(getString(R.string.email_invalido));
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            editPassword.setError(getString(R.string.senha_obrigatoria));
            return false;
        }

        if (password.length() < 6) {
            editPassword.setError(getString(R.string.senha_deve_ter_pelo_menos_6_caracteres));
            return false;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            editConfirmPassword.setError(getString(R.string.confirmacao_senha_obrigatoria));
            return false;
        }

        if (!password.equals(confirmPassword)) {
            editConfirmPassword.setError(getString(R.string.senhas_nao_coincidem));
            return false;
        }

        return true;
    }

    private boolean salvarUsuario(String name, String email, String password, String tipoUsuario) {
        try {
            if (db.validaEmailUtilizado(email)) {
                editEmail.setError(getString(R.string.email_ja_cadastrado));
                return false;
            }

            return db.criarUsuario(name, email, password, tipoUsuario);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.ocorreu_erro_tente_novamente), Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private void abrirTelaLogin() {
        Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}