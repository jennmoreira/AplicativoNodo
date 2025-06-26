package com.uniftec.loginexemplo;

import android.os.Bundle;
import android.view.View;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivityNovoEvento3 extends AppCompatActivity {

    private EditText editRua, editNumero, editBairro, editCidade, editUF;
    private Button botaoProximaPagina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_novo_evento3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editRua = findViewById(R.id.editRua);
        editNumero = findViewById(R.id.editNumeroPredial);
        editBairro = findViewById(R.id.editBairro);
        editCidade = findViewById(R.id.editCidade);
        editUF = findViewById(R.id.editUF);
        botaoProximaPagina = findViewById(R.id.buttonProximaPagina);

        botaoProximaPagina.setOnClickListener(v -> validarEAvancar());
    }

    private void validarEAvancar() {
        if (editRua.getText().toString().trim().isEmpty()) {
            editRua.setError("Endereço incompleto. Informe a rua.");
            editRua.requestFocus();
            return;
        }
        if (editNumero.getText().toString().trim().isEmpty()) {
            editNumero.setError("Endereço incompleto. Informe o número predial.");
            editNumero.requestFocus();
            return;
        }
        if (editBairro.getText().toString().trim().isEmpty()) {
            editBairro.setError("Endereço incompleto. Informe o bairro.");
            editBairro.requestFocus();
            return;
        }
        if (editCidade.getText().toString().trim().isEmpty()) {
            editCidade.setError("Endereço incompleto. Informe a cidade.");
            editCidade.requestFocus();
            return;
        }
        if (editUF.getText().toString().trim().isEmpty()) {
            editUF.setError("Endereço incompleto. Informe a UF.");
            editUF.requestFocus();
            return;
        }


        Intent intent = new Intent(this, ActivityNovoEvento4.class);

// Pegando os dados das telas anteriores
        intent.putExtra("nomeEvento", getIntent().getStringExtra("nomeEvento"));
        intent.putExtra("descricaoEvento", getIntent().getStringExtra("descricaoEvento"));
        intent.putExtra("dataInicio", getIntent().getStringExtra("dataInicio"));
        intent.putExtra("horaInicio", getIntent().getStringExtra("horaInicio"));
        intent.putExtra("dataFinal", getIntent().getStringExtra("dataFinal"));
        intent.putExtra("horaFinal", getIntent().getStringExtra("horaFinal"));

// Pegando os dados atuais
        intent.putExtra("rua", editRua.getText().toString().trim());
        intent.putExtra("numero", editNumero.getText().toString().trim());
        intent.putExtra("bairro", editBairro.getText().toString().trim());
        intent.putExtra("cidade", editCidade.getText().toString().trim());
        intent.putExtra("uf", editUF.getText().toString().trim());

        startActivity(intent);
    }

    public void Voltar(View view) {
        finish();
    }

}