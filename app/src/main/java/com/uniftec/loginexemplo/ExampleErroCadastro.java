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

import java.util.ArrayList;

public class ExampleErroCadastro extends AppCompatActivity {

    private TextView txtVerifique;
    private Button btnVoltar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.example_erro_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtVerifique = findViewById(R.id.txtVerifique);
        btnVoltar = findViewById(R.id.btnVoltar);
        ArrayList<String> camposFaltando = getIntent().getStringArrayListExtra("camposFaltando");
        if (camposFaltando != null && !camposFaltando.isEmpty()) {
            StringBuilder textoCampos = new StringBuilder();
            for (String campo : camposFaltando) {
                textoCampos.append("Erro no Cadastro.O campo ").append(campo).append(" não foi preenchido\n");
            }
            txtVerifique.setText(textoCampos.toString().trim());
        }
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExampleErroCadastro.this, ExampleFormCadastro.class);
                startActivity(intent);
                finish();
            }
        });

    }
}