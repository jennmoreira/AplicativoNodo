package com.uniftec.loginexemplo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ExampleSucessoCadastro extends AppCompatActivity {

    TextView txtSucessoCad;
    Button btnSobre;
    String nome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.example_sucesso_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtSucessoCad = findViewById(R.id.txtSucessoCad);
        btnSobre = findViewById(R.id.btnSobre);
        nome = getIntent().getStringExtra("nomeUsuario");
        txtSucessoCad.setText("Ola " + nome + ". Cadastro realizado com sucesso.");

        btnSobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExampleSucessoCadastro.this, ExampleTelaSobre.class);
                startActivity(intent);
            }
        });



    }
}