package com.seuapp.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.seuapp.R

class TrocarSenhaActivity : AppCompatActivity() {

    private lateinit var editTextSenhaAtual: EditText
    private lateinit var editTextNovaSenha: EditText
    private lateinit var editTextConfirmarNovaSenha: EditText
    private lateinit var buttonTrocarSenha: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trocar_senha)

        editTextSenhaAtual = findViewById(R.id.editTextSenhaAtual)
        editTextNovaSenha = findViewById(R.id.editTextNovaSenha)
        editTextConfirmarNovaSenha = findViewById(R.id.editTextConfirmarNovaSenha)
        buttonTrocarSenha = findViewById(R.id.buttonTrocarSenha)

        buttonTrocarSenha.setOnClickListener {
            trocarSenha()
        }
    }

    private fun trocarSenha() {
        val senhaAtual = editTextSenhaAtual.text.toString().trim()
        val novaSenha = editTextNovaSenha.text.toString().trim()
        val confirmarNova = editTextConfirmarNovaSenha.text.toString().trim()

        if (senhaAtual.isEmpty() || novaSenha.isEmpty() || confirmarNova.isEmpty()) {
            Toast.makeText(this, "Todos os campos são obrigatórios!", Toast.LENGTH_SHORT).show()
            return
        }

        if (novaSenha != confirmarNova) {
            Toast.makeText(this, "As novas senhas não coincidem!", Toast.LENGTH_SHORT).show()
            return
        }

        // Aqui você faria a verificação real da senha atual e atualização (banco de dados ou API)
        Toast.makeText(this, "Senha alterada com sucesso!", Toast.LENGTH_SHORT).show()

        // Limpa os campos
        editTextSenhaAtual.text.clear()
        editTextNovaSenha.text.clear()
        editTextConfirmarNovaSenha.text.clear()
    }
}
