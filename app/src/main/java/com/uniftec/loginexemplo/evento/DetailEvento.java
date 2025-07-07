package com.uniftec.loginexemplo.evento;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.uniftec.loginexemplo.databinding.ActivityDetailEventoBinding;
import com.uniftec.loginexemplo.sql.eventos.Evento;
import com.uniftec.loginexemplo.sql.eventos.EventosDatabaseHelper;

public class DetailEvento extends AppCompatActivity {

    private ActivityDetailEventoBinding binding;
    private EventosDatabaseHelper eventosDb;
    private long pEVE_ID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailEventoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        eventosDb = new EventosDatabaseHelper(this);

        Intent intent = getIntent();
        if (intent != null) {
            pEVE_ID = intent.getLongExtra("pEVE_ID", -1L);
        }

        binding.btnVoltarDetalhesEvento.setOnClickListener(v -> onBackPressed());

        if (pEVE_ID != -1) {
            carregarDetalhesEvento(pEVE_ID);
        } else {
            Toast.makeText(this, "ID do evento não fornecido.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void carregarDetalhesEvento(long eventoId) {
        Evento evento = eventosDb.carregaDadosEvento(eventoId);
        if (evento != null) {
            binding.detailName.setText(evento.getNome());
            binding.detailDescricaoConteudo.setText(evento.getDescricao());

            String periodo = evento.getDataInicio() + " às " + evento.getHoraInicio() +
                    " até " + evento.getDataFim() + " às " + evento.getHoraFim();
            binding.detailPeriodoConteudo.setText(periodo);

            String enderecoCompleto = evento.getRua() + ", " + evento.getNumero() +
                    " - " + evento.getBairro() + ", " +
                    evento.getCidade() + "/" + evento.getUf();
            binding.detailLocalConteudo.setText(enderecoCompleto);

            StringBuilder categorias = new StringBuilder();
            if (evento.getCatSeguranca() == 1) {
                categorias.append("Segurança, ");
            }
            if (evento.getCatLimpeza() == 1) {
                categorias.append("Limpeza, ");
            }
            if (evento.getCatInfraestrutura() == 1) {
                categorias.append("Infraestrutura, ");
            }
            if (evento.getCatOutros() == 1) {
                categorias.append("Outros, ");
            }
            if (categorias.length() > 0) {
                categorias.setLength(categorias.length() - 2);
            } else {
                categorias.append("Nenhuma");
            }
            binding.detailCategoriasConteudo.setText(categorias.toString());

        } else {
            Toast.makeText(this, "Evento não encontrado no banco de dados.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}