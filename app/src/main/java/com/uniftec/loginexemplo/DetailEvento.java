package com.uniftec.loginexemplo;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.uniftec.loginexemplo.databinding.ActivityDetailEventoBinding;

public class DetailEvento extends AppCompatActivity {

    private ActivityDetailEventoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializa o binding corretamente
        binding = ActivityDetailEventoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Recupera os dados passados via Intent
        Intent intent = getIntent();
        if (intent != null) {
            String nomeEvento = intent.getStringExtra("nomeEvento");
            String descricao = intent.getStringExtra("descricaoEvento");
            String dataInicio = intent.getStringExtra("dataInicio");
            String horaInicio = intent.getStringExtra("horaInicio");
            String dataFinal = intent.getStringExtra("dataFinal");
            String horaFinal = intent.getStringExtra("horaFinal");
            String rua = intent.getStringExtra("rua");
            String numero = intent.getStringExtra("numero");
            String bairro = intent.getStringExtra("bairro");
            String cidade = intent.getStringExtra("cidade");
            String uf = intent.getStringExtra("uf");

            // Exibe os dados nos campos da tela usando o binding
            binding.detailName.setText(nomeEvento);
            binding.detailTime.setText(descricao);
            binding.detailIngredients.setText(dataInicio + " às " + horaInicio + " até " + dataFinal + " às " + horaFinal);
            binding.detailDesc.setText(rua + ", " + numero + " - " + bairro + ", " + cidade + "/" + uf);
        }
    }
}
