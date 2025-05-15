package com.uniftec.loginexemplo;

import android.os.Bundle;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivityNovoEvento extends AppCompatActivity {

    EditText editNome, editDescricao;
    Button botaoProximaPagina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_novo_evento);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editNome = findViewById(R.id.editNome);
        editDescricao = findViewById(R.id.editTextDescricao);
        botaoProximaPagina = findViewById(R.id.buttonProximaPagina);

        botaoProximaPagina.setOnClickListener(v -> validarEAvancar());
    }

    private void validarEAvancar() {
        String nome = editNome.getText().toString().trim();
        String descricao = editDescricao.getText().toString().trim();

        if (nome.isEmpty()) {
            editNome.setError("O nome do evento é obrigatório.");
            editNome.requestFocus();
            return;
        }

        if (descricao.isEmpty()) {
            editDescricao.setError("A descrição do evento é obrigatória.");
            editDescricao.requestFocus();
            return;
        }

        Intent intent = new Intent(ActivityNovoEvento.this, ActivityNovoEvento2.class);
        intent.putExtra("nomeEvento", nome);
        intent.putExtra("descricaoEvento", descricao);
        startActivity(intent);
    }

    public void Voltar(View view) {
        finish();
    }
}