package com.seuapp.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.seuapp.R

class CriarContaActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextConfirmPassword: EditText
    private lateinit var buttonCriarConta: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.conta_perfil)  // Alterado para "conta_perfil.xml"

        // Inicializar os componentes da tela
        editTextName = findViewById(R.id.editTextName)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword)
        buttonCriarConta = findViewById(R.id.buttonCriarConta)

        // Definir a ação para o botão "Criar Conta"
        buttonCriarConta.setOnClickListener {
            criarConta()
        }
    }

    private fun criarConta() {
        // Obter os dados inseridos pelo usuário
        val nome = editTextName.text.toString().trim()
        val email = editTextEmail.text.toString().trim()
        val senha = editTextPassword.text.toString().trim()
        val confirmarSenha = editTextConfirmPassword.text.toString().trim()

        // Validar os dados
        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
            Toast.makeText(this, "Todos os campos são obrigatórios!", Toast.LENGTH_SHORT).show()
            return
        }

        if (senha != confirmarSenha) {
            Toast.makeText(this, "As senhas não coincidem!", Toast.LENGTH_SHORT).show()
            return
        }

        // Lógica para criar a conta (exemplo, pode envolver chamada a um banco de dados ou API)
        Toast.makeText(this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show()

        // Limpar os campos após a criação (opcional)
        editTextName.text.clear()
        editTextEmail.text.clear()
        editTextPassword.text.clear()
        editTextConfirmPassword.text.clear()
    }
}
