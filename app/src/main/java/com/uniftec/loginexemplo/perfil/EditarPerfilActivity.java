package com.uniftec.loginexemplo.perfil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.uniftec.loginexemplo.R;
import com.uniftec.loginexemplo.home.HomeActivity;
import com.uniftec.loginexemplo.login.LoginActivity;
import com.uniftec.loginexemplo.sql.usuarios.UsuariosDatabaseHelper;
import com.uniftec.loginexemplo.sql.usuarios.Usuario; // Assumindo que você tem essa classe


public class EditarPerfilActivity extends AppCompatActivity {

    private EditText editName, editEmail, editTelefone, editCidade, editEstado, editEndereco;
    private Button btnVoltar, btnSalvar;
    private UsuariosDatabaseHelper dbHelper;
    private long USU_ID_SESSION = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editar_perfil);

        dbHelper = new UsuariosDatabaseHelper(this);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("USU_ID_SESSION")) {
            USU_ID_SESSION = intent.getLongExtra("USU_ID_SESSION", -1L);
        }

        inicializarComponentes();
        if (USU_ID_SESSION != -1) {
            carregaDados(USU_ID_SESSION);
        } else {
            Toast.makeText(this, "ID do usuário não encontrado. Faça login novamente.", Toast.LENGTH_LONG).show();
            abrirTelaLogin();
            return;
        }
        configurarEventos(USU_ID_SESSION);

    }

    private void carregaDados(long USU_ID_SESSION) {

        Usuario usuario = dbHelper.carregaDadosUsuario(USU_ID_SESSION);

        if (usuario != null) {

            editName.setText(usuario.getNome());
            editEmail.setText(usuario.getEmail());
            editTelefone.setText(usuario.getTelefone());
            editEndereco.setText(usuario.getEndereco());
            editEstado.setText(usuario.getEstado());
            editCidade.setText(usuario.getCidade());
        } else {
            Toast.makeText(this, "Erro ao carregar dados do usuário.", Toast.LENGTH_SHORT).show();
            abrirTelaLogin();
        }
    }


    private void inicializarComponentes() {
        editName = findViewById(R.id.nome);
        editEmail = findViewById(R.id.email);
        editTelefone = findViewById(R.id.telefone);
        editEndereco = findViewById(R.id.endereco);
        editEstado = findViewById(R.id.estado);
        editCidade = findViewById(R.id.cidade);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnVoltar = findViewById(R.id.btnVoltar);
    }

    private void configurarEventos(long USU_ID_SESSION) {
        btnSalvar.setOnClickListener(v -> atualizaCadastro(USU_ID_SESSION));
        btnVoltar.setOnClickListener(v -> voltar(USU_ID_SESSION));
    }

    private void atualizaCadastro(long USU_ID_SESSION) {
        String name = editName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String telefone = editTelefone.getText().toString().trim();
        String endereco = editEndereco.getText().toString().trim();
        String estado = editEstado.getText().toString().trim();
        String cidade = editCidade.getText().toString().trim();

        if (!validarCampos(name, email)) {
            return;
        }

        boolean sucesso = dbHelper.atualizaUsuario(USU_ID_SESSION, name, email, telefone, endereco, estado, cidade);

        if (sucesso) {
            Toast.makeText(this, "Perfil atualizado com sucesso!", Toast.LENGTH_LONG).show();
            voltar(USU_ID_SESSION);
        } else {
            Toast.makeText(this, "Erro ao atualizar perfil. Tente novamente.", Toast.LENGTH_LONG).show();
        }
    }

    private boolean validarCampos(String name, String email) {
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

        return true;
    }

    private void abrirTelaLogin() {
        Intent intent = new Intent(EditarPerfilActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void voltar(long USU_ID_SESSION) {
        Intent intent = new Intent(EditarPerfilActivity.this, HomeActivity.class);
        intent.putExtra("USU_ID_SESSION", USU_ID_SESSION);
        startActivity(intent);
        finish();
    }
}