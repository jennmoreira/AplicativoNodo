package com.uniftec.loginexemplo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.content.Intent;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.uniftec.loginexemplo.home.HomeActivity;

public class ActivityNovoEvento4 extends AppCompatActivity {

    private EditText editTituloVaga, editDescVaga;
    private RadioGroup rgCategoria;
    private Button botaoPublicar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_novo_evento4);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTituloVaga = findViewById(R.id.editTituloVaga);
        rgCategoria    = findViewById(R.id.radioGroupCategoria);
        editDescVaga   = findViewById(R.id.editTextDescricao);
        botaoPublicar    = findViewById(R.id.buttonPublicarEvento);

        botaoPublicar.setOnClickListener(v -> validarEConcluir());

    }

    private void validarEConcluir() {
        if (editTituloVaga.getText().toString().trim().isEmpty()) {
            editTituloVaga.setError("O título da vaga é obrigatório.");
            editTituloVaga.requestFocus();
            return;
        }
        if (rgCategoria.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Selecione a categoria da vaga.", Toast.LENGTH_SHORT).show();
            rgCategoria.requestFocus();
            return;
        }
        if (editDescVaga.getText().toString().trim().isEmpty()) {
            editDescVaga.setError("Descreva as atividades da vaga.");
            editDescVaga.requestFocus();
            return;
        }

        Toast.makeText(this, "Evento publicado!", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(ActivityNovoEvento4.this, HomeActivity.class);
        startActivity(intent);
        finish();

    }

    public void Voltar(View view) {
        finish();
    }
}