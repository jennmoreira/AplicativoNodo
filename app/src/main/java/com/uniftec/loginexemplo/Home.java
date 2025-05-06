package com.uniftec.loginexemplo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Home extends AppCompatActivity {

    private TextView txtCadastro;
    private TextView txtEsqueceuSenha;
    private static final String email_valido = "grupo";
    private static final String senha_valido = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        EditText editEmail = findViewById(R.id.editEmail);
        EditText editSenha = findViewById(R.id.editSenha);
        Button btnEntrar = findViewById(R.id.btnEntrar);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_digitado = editEmail.getText().toString().trim();
                String senha_digitado = editSenha.getText().toString().trim();

                if(email_digitado.equals(email_valido) && senha_digitado.equals(senha_valido)){
                    Intent intent = new Intent(Home.this, ExampleSucessoLogin.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(Home.this, ExampleErroLogin.class);
                    startActivity(intent);
                }

            }
        });


        txtCadastro = findViewById(R.id.textCadastro);
        txtCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, ExampleFormCadastro.class);
                startActivity(intent);
            }
        });


        txtEsqueceuSenha = findViewById(R.id.textEsqueceuSenha);
        txtEsqueceuSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, FormEsqueceuSenha.class);
                startActivity(intent);
            }
        });
    }
}
