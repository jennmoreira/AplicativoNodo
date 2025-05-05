package com.uniftec.loginexemplo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class FormCadastro extends AppCompatActivity {

    private Button btnSalvar;
    private EditText editNome, editEnder, editNumber, editCep, editEmail, editSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.example_form_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnSalvar = findViewById(R.id.btnSalvar);
        editNome = findViewById(R.id.editNome);
        editEnder = findViewById(R.id.editEnder);
        editNumber = findViewById(R.id.editNumber);
        editCep = findViewById(R.id.editCep);
        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = editNome.getText().toString().trim();
                String endereco = editEnder.getText().toString().trim();
                String numero = editNumber.getText().toString().trim();
                String cep = editCep.getText().toString().trim();
                String email = editEmail.getText().toString().trim();
                String senha = editSenha.getText().toString().trim();
                ArrayList<String> camposVazios = new ArrayList<>();

                if (editNome.getText().toString().trim().isEmpty()) camposVazios.add("Nome");
                if (editEnder.getText().toString().trim().isEmpty()) camposVazios.add("Endereço");
                if (editNumber.getText().toString().trim().isEmpty()) camposVazios.add("Número");
                if (editCep.getText().toString().trim().isEmpty()) camposVazios.add("CEP");
                if (editEmail.getText().toString().trim().isEmpty()) camposVazios.add("Email");
                if (editSenha.getText().toString().trim().isEmpty()) camposVazios.add("Senha");

                if (!camposVazios.isEmpty()) {
                    // Chama a tela de erro, passando os campos faltantes
                    Intent intent = new Intent(FormCadastro.this, ErroCadastro.class);
                    intent.putStringArrayListExtra("camposFaltando", camposVazios);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(FormCadastro.this, SucessoCadastro.class);
                    intent.putExtra("nomeUsuario", nome); // nome capturado lá em cima
                    startActivity(intent);

                    finish();
                }
            }
        });
    }
}

