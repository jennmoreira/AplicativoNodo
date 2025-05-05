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

public class FormEsqueceuSenha extends AppCompatActivity {

    private static final String email_valido = "grupo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_esqueceu_senha);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        EditText editEmail = findViewById(R.id.editEmail);
        Button btnEntrar = findViewById(R.id.btnEnviar);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_digitado = editEmail.getText().toString().trim();

                if(email_digitado.equals(email_valido)){
                    Intent intent = new Intent(FormEsqueceuSenha.this, ExampleSucessoLogin.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(FormEsqueceuSenha.this, ExampleErroLogin.class);
                    startActivity(intent);
                }
            }
        });
    }
}
