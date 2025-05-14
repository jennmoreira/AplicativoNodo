package com.uniftec.loginexemplo;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.uniftec.loginexemplo.databinding.TrocarSenhaBinding;

public class TrocarSenhaActivity extends AppCompatActivity {

    private TextView textSenhaAtual;
    private TextView textNovaSenha;
    private TextView textConfirmarNovaSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.trocar_senha);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText textSenhaAtual = findViewById(R.id.editTextSenhaAtual);
        EditText textNovaSenha = findViewById(R.id.editTextNovaSenha);
        EditText textConfirmarNovaSenha = findViewById(R.id.editTextConfirmarNovaSenha);
        Button btnConfirmar = findViewById(R.id.btnTrocarSenha);

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String atual = textSenhaAtual.getText().toString().trim();
                String senha = textNovaSenha.getText().toString().trim();
                String confirma = textConfirmarNovaSenha.getText().toString().trim();

                if(atual.isEmpty() || senha.isEmpty() || confirma.isEmpty()){
                    Toast.makeText(TrocarSenhaActivity.this, "Todos os campos são obrigatórios!", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(com.uniftec.loginexemplo.TrocarSenhaActivity.this, "Senha alterada!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(TrocarSenhaActivity.this, Home.class);
                    startActivity(intent);
                }
            }
        });
    }
}