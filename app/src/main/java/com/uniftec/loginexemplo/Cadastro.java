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

public class Cadastro extends AppCompatActivity {

    private TextView textName;
    private TextView textEmail;
    private TextView textPassword;
    private TextView textConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText textName = findViewById(R.id.editTextName);
        EditText textEmail = findViewById(R.id.editTextEmail);
        EditText textPassword = findViewById(R.id.editTextPassword);
        EditText textConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        Button btnCriar = findViewById(R.id.buttonCriarConta);

        btnCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = textName.getText().toString().trim();
                String email = textEmail.getText().toString().trim();
                String pasword = textPassword.getText().toString().trim();
                String confirmPassword = textConfirmPassword.getText().toString().trim();

                if(name.isEmpty() || email.isEmpty() || pasword.isEmpty() || confirmPassword.isEmpty()){
                    Toast.makeText(Cadastro.this, "Todos os campos são obrigatórios!", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(Cadastro.this, "Registro salvo!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Cadastro.this, Home.class);
                    startActivity(intent);
                }
            }
        });
    }
}
