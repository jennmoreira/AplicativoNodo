package com.uniftec.loginexemplo.evento;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.uniftec.loginexemplo.databinding.ActivityDetailEventoBinding;
import com.uniftec.loginexemplo.sql.AppDatabaseHelper;
import com.uniftec.loginexemplo.sql.eventos.Evento;

public class DetailEvento extends AppCompatActivity {

    private ActivityDetailEventoBinding binding;
    private AppDatabaseHelper db;
    private long pEVE_ID = -1;
    private long USU_ID_SESSION = -1;

    private static final String TAG = "DetailEvento";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            binding = ActivityDetailEventoBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });

            db = new AppDatabaseHelper(this);

            Intent intent = getIntent();
            if (intent != null) {
                pEVE_ID = intent.getLongExtra("pEVE_ID", -1L);
                USU_ID_SESSION = intent.getLongExtra("USU_ID_SESSION", -1L);
            }

            binding.btnVoltarDetalhesEvento.setOnClickListener(v -> onBackPressed());

            binding.btnCandidatar.setOnClickListener(v -> candidatarSe(pEVE_ID, USU_ID_SESSION));

            if (pEVE_ID != -1) {
                carregarDetalhesEvento(pEVE_ID);
                configurarVisibilidadeBotaoCandidatar(pEVE_ID, USU_ID_SESSION);
            } else {
                Toast.makeText(this, "ID do evento não fornecido.", Toast.LENGTH_SHORT).show();
                finish();
            }

        } catch (Exception e) {
            Log.e(TAG, "Erro no onCreate", e);
            Toast.makeText(this, "Erro ao abrir detalhes do evento: " + e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void carregarDetalhesEvento(long eventoId) {
        try {
            Evento evento = db.carregaDadosEvento(eventoId);
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
                if (evento.getCatSeguranca() == 1) categorias.append("Segurança, ");
                if (evento.getCatLimpeza() == 1) categorias.append("Limpeza, ");
                if (evento.getCatInfraestrutura() == 1) categorias.append("Infraestrutura, ");
                if (evento.getCatOutros() == 1) categorias.append("Outros, ");

                if (categorias.length() > 0) {
                    categorias.setLength(categorias.length() - 2); // remove ", "
                } else {
                    categorias.append("Nenhuma");
                }
                binding.detailCategoriasConteudo.setText(categorias.toString());

            } else {
                Toast.makeText(this, "Evento não encontrado no banco de dados.", Toast.LENGTH_LONG).show();
                Log.e(TAG, "Evento com ID " + eventoId + " não encontrado.");
                finish();
            }
        } catch (Exception e) {
            Log.e(TAG, "Erro ao carregar detalhes do evento", e);
            Toast.makeText(this, "Erro ao carregar dados do evento: " + e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void configurarVisibilidadeBotaoCandidatar(long pEVE_ID, long pUSU_ID_SESSION) {
        try {
            if (pUSU_ID_SESSION != -1) {
                String tipoUsuario = db.getTipoUsuario(pUSU_ID_SESSION);
                if ("prestador".equals(tipoUsuario)) {
                    binding.btnCandidatar.setVisibility(View.VISIBLE);
                    if (db.candidatoJaExiste(pUSU_ID_SESSION, pEVE_ID)) {
                        binding.btnCandidatar.setText("Já Candidatado");
                        binding.btnCandidatar.setEnabled(false);
                    } else {
                        binding.btnCandidatar.setText("Candidatar-se");
                        binding.btnCandidatar.setEnabled(true);
                    }
                } else {
                    binding.btnCandidatar.setVisibility(View.GONE);
                }
            } else {
                binding.btnCandidatar.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            Log.e(TAG, "Erro ao configurar botão candidatar", e);
        }
    }

    private void candidatarSe(long pEVE_ID, long pUSU_ID_SESSION) {
        try {
            if (pUSU_ID_SESSION != -1 && pEVE_ID != -1) {
                if (db.inserirCandidato(pEVE_ID, pUSU_ID_SESSION)) {
                    Toast.makeText(this, "Candidatura realizada com sucesso!", Toast.LENGTH_SHORT).show();
                    binding.btnCandidatar.setText("Já Candidatado");
                    binding.btnCandidatar.setEnabled(false);
                } else {
                    Toast.makeText(this, "Erro ao se candidatar. Você já pode ter se candidatado a este evento.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Dados de usuário ou evento inválidos.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e(TAG, "Erro ao candidatar-se", e);
            Toast.makeText(this, "Erro ao se candidatar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
