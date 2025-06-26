package com.uniftec.loginexemplo;

import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivityNovoEvento2 extends AppCompatActivity {

    private EditText editDataInicio, editHoraInicio, editDataFinal, editHoraFinal;
    private Button botaoProximaPagina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_novo_evento2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editDataInicio = findViewById(R.id.editDataInicio);
        editHoraInicio = findViewById(R.id.editHoraInicio);
        editDataFinal = findViewById(R.id.editDataFinal);
        editHoraFinal = findViewById(R.id.editHoraFinal);
        botaoProximaPagina = findViewById(R.id.buttonProximaPagina);

        botaoProximaPagina.setOnClickListener(v -> validarEAvancar());

    }

    private void validarEAvancar() {
        String di = editDataInicio.getText().toString().trim();
        String hi = editHoraInicio.getText().toString().trim();
        String df = editDataFinal.getText().toString().trim();
        String hf = editHoraFinal.getText().toString().trim();

        if (di.isEmpty()) {
            editDataInicio.setError("A data de início do evento é obrigatória.");
            editDataInicio.requestFocus();
            return;
        }
        if (hi.isEmpty()) {
            editHoraInicio.setError("A hora de início do evento é obrigatória.");
            editHoraInicio.requestFocus();
            return;
        }
        if (df.isEmpty()) {
            editDataFinal.setError("A data final do evento é obrigatória");
            editDataFinal.requestFocus();
            return;
        }
        if (hf.isEmpty()) {
            editHoraFinal.setError("A hora final do evento é obrigatória");
            editHoraFinal.requestFocus();
            return;
        }

        // Recuperando os dados que vieram da tela anterior
        Intent dadosRecebidos = getIntent();
        String nome = dadosRecebidos.getStringExtra("nomeEvento");
        String descricao = dadosRecebidos.getStringExtra("descricaoEvento");

        // Criando intent para ir para a próxima tela
        Intent intent = new Intent(this, ActivityNovoEvento3.class);

        // Repassando os dados da tela anterior
        intent.putExtra("nomeEvento", nome);
        intent.putExtra("descricaoEvento", descricao);

        // Passando os dados desta tela
        intent.putExtra("dataInicio", di);
        intent.putExtra("horaInicio", hi);
        intent.putExtra("dataFinal", df);
        intent.putExtra("horaFinal", hf);

        startActivity(intent);
    }
}
