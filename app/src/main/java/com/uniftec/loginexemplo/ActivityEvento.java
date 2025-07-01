package com.uniftec.loginexemplo;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.uniftec.loginexemplo.sql.eventos.Evento;
import com.uniftec.loginexemplo.sql.eventos.EventosDatabaseHelper;

public class ActivityEvento extends AppCompatActivity {

    private EditText editNome, editTextDescricao;
    private EditText editDataInicio, editHoraInicio, editDataFinal, editHoraFinal;
    private EditText editRua, editNumeroPredial, editBairro, editCidade, editUF;
    private Button buttonFinalizarCadastro;
    private AppCompatImageButton btnVoltarNovoEvento;
    private Uri selectedImageUri;
    private EventosDatabaseHelper eventosDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_evento);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        eventosDb = new EventosDatabaseHelper(this);

        editNome = findViewById(R.id.editNome);
        editTextDescricao = findViewById(R.id.editTextDescricao);

        editDataInicio = findViewById(R.id.editDataInicio);
        editHoraInicio = findViewById(R.id.editHoraInicio);
        editDataFinal = findViewById(R.id.editDataFinal);
        editHoraFinal = findViewById(R.id.editHoraFinal);

        editRua = findViewById(R.id.editRua);
        editNumeroPredial = findViewById(R.id.editNumeroPredial);
        editBairro = findViewById(R.id.editBairro);
        editCidade = findViewById(R.id.editCidade);
        editUF = findViewById(R.id.editUF);

        buttonFinalizarCadastro = findViewById(R.id.buttonFinalizarCadastro);
        btnVoltarNovoEvento = findViewById(R.id.btnVoltarNovoEvento);

        btnVoltarNovoEvento.setOnClickListener(v -> finish());

        buttonFinalizarCadastro.setOnClickListener(v -> finalizarCadastro());
    }

    private void finalizarCadastro() {
        String nome = editNome.getText().toString().trim();
        String descricao = editTextDescricao.getText().toString().trim();
        String imagemPath = (selectedImageUri != null) ? selectedImageUri.toString() : "";
        String dataInicio = editDataInicio.getText().toString().trim();
        String horaInicio = editHoraInicio.getText().toString().trim();
        String dataFim = editDataFinal.getText().toString().trim();
        String horaFim = editHoraFinal.getText().toString().trim();
        String rua = editRua.getText().toString().trim();
        String numero = editNumeroPredial.getText().toString().trim();
        String bairro = editBairro.getText().toString().trim();
        String cidade = editCidade.getText().toString().trim();
        String uf = editUF.getText().toString().trim();

        if (nome.isEmpty()) {
            editNome.setError("Nome é obrigatório.");
            editNome.requestFocus();
            return;
        }
        if (descricao.isEmpty()) {
            editTextDescricao.setError("Descrição é obrigatória.");
            editTextDescricao.requestFocus();
            return;
        }
        if (dataInicio.isEmpty()) {
            editDataInicio.setError("Data de início é obrigatória.");
            editDataInicio.requestFocus();
            return;
        }
        if (horaInicio.isEmpty()) {
            editHoraInicio.setError("Hora de início é obrigatória.");
            editHoraInicio.requestFocus();
            return;
        }
        if (dataFim.isEmpty()) {
            editDataFinal.setError("Data final é obrigatória.");
            editDataFinal.requestFocus();
            return;
        }
        if (horaFim.isEmpty()) {
            editHoraFinal.setError("Hora final é obrigatória.");
            editHoraFinal.requestFocus();
            return;
        }
        if (rua.isEmpty()) {
            editRua.setError("Rua é obrigatória.");
            editRua.requestFocus();
            return;
        }

        if (bairro.isEmpty()) {
            editBairro.setError("Bairro é obrigatório.");
            editBairro.requestFocus();
            return;
        }
        if (cidade.isEmpty()) {
            editCidade.setError("Cidade é obrigatória.");
            editCidade.requestFocus();
            return;
        }
        if (uf.isEmpty()) {
            editUF.setError("UF é obrigatória.");
            editUF.requestFocus();
            return;
        }
        if (uf.length() != 2) {
            editUF.setError("UF deve ter 2 caracteres.");
            editUF.requestFocus();
            return;
        }

        Evento novoEvento = new Evento(
                nome, descricao, imagemPath, dataInicio, dataFim,
                horaInicio, horaFim, rua, numero, bairro, cidade, uf
        );

        long id = eventosDb.criarEvento(novoEvento);

        if (id != -1) {
            Toast.makeText(this, "Evento cadastrado com sucesso!", Toast.LENGTH_SHORT).show(); // Informa ao FragmentEventos que um novo evento foi adicionado
            finish();
        } else {
            Toast.makeText(this, "Erro ao cadastrar evento.", Toast.LENGTH_SHORT).show();
        }
    }
}
