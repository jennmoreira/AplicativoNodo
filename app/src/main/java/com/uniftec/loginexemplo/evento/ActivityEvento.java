package com.uniftec.loginexemplo.evento;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.uniftec.loginexemplo.R;
import com.uniftec.loginexemplo.home.HomeActivity;
import com.uniftec.loginexemplo.sql.eventos.Evento;
import com.uniftec.loginexemplo.sql.eventos.EventosDatabaseHelper;

public class ActivityEvento extends AppCompatActivity {

    private EditText editNome, editTextDescricao;
    private EditText editDataInicio, editHoraInicio, editDataFinal, editHoraFinal;
    private EditText editRua, editNumeroPredial, editBairro, editCidade, editUF;
    private Button buttonFinalizarCadastro;
    private AppCompatImageButton btnVoltarNovoEvento;
    private EventosDatabaseHelper eventosDb;
    private long pEVE_ID = -1;
    private long USU_ID_SESSION = -1;
    private CheckBox checkSeguranca, checkLimpeza, checkInfraestrutura, checkOutros;

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

        Intent intent = getIntent();
        if (intent != null) {
            USU_ID_SESSION = intent.getLongExtra("USU_ID_SESSION", -1L);
            pEVE_ID = intent.getLongExtra("pEVE_ID", -1L); // Pega o ID do evento se for para editar
        }

        inicializarComponentes();
        configurarEventos(USU_ID_SESSION);

        eventosDb = new EventosDatabaseHelper(this);

        if (pEVE_ID != -1) {
            carregarDadosEvento(pEVE_ID);
        }
    }

    private void inicializarComponentes() {
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
        checkSeguranca = findViewById(R.id.checkSeguranca);
        checkLimpeza = findViewById(R.id.checkLimpeza);
        checkInfraestrutura = findViewById(R.id.checkInfraestrutura);
        checkOutros = findViewById(R.id.checkOutros);
        buttonFinalizarCadastro = findViewById(R.id.buttonFinalizarCadastro);
        btnVoltarNovoEvento = findViewById(R.id.btnVoltarNovoEvento);
    }

    private void configurarEventos(long USU_ID_SESSION){
        btnVoltarNovoEvento.setOnClickListener(v -> voltar(USU_ID_SESSION));
        buttonFinalizarCadastro.setOnClickListener(v -> finalizarCadastro());
    }

    private void carregarDadosEvento(long pEVE_ID) {
        Evento evento = eventosDb.carregaDadosEvento(pEVE_ID);
        if (evento != null) {
            editNome.setText(evento.getNome());
            editTextDescricao.setText(evento.getDescricao());
            editDataInicio.setText(evento.getDataInicio());
            editHoraInicio.setText(evento.getHoraInicio());
            editDataFinal.setText(evento.getDataFim());
            editHoraFinal.setText(evento.getHoraFim());
            editRua.setText(evento.getRua());
            editNumeroPredial.setText(evento.getNumero());
            editBairro.setText(evento.getBairro());
            editCidade.setText(evento.getCidade());
            editUF.setText(evento.getUf());
            checkSeguranca.setChecked(evento.getCatSeguranca() == 1);
            checkLimpeza.setChecked(evento.getCatLimpeza() == 1);
            checkInfraestrutura.setChecked(evento.getCatInfraestrutura() == 1);
            checkOutros.setChecked(evento.getCatOutros() == 1);
            buttonFinalizarCadastro.setText("Atualizar Evento");
        } else {
            Toast.makeText(this, "Evento não encontrado.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void finalizarCadastro() {
        String nome = editNome.getText().toString().trim();
        String descricao = editTextDescricao.getText().toString().trim();
        String dataInicio = editDataInicio.getText().toString().trim();
        String horaInicio = editHoraInicio.getText().toString().trim();
        String dataFim = editDataFinal.getText().toString().trim();
        String horaFim = editHoraFinal.getText().toString().trim();
        String rua = editRua.getText().toString().trim();
        String numero = editNumeroPredial.getText().toString().trim();
        String bairro = editBairro.getText().toString().trim();
        String cidade = editCidade.getText().toString().trim();
        String uf = editUF.getText().toString().trim();
        int catSeguranca = checkSeguranca.isChecked() ? 1 : 0;
        int catLimpeza = checkLimpeza.isChecked() ? 1 : 0;
        int catInfraestrutura = checkInfraestrutura.isChecked() ? 1 : 0;
        int catOutros = checkOutros.isChecked() ? 1 : 0;

        if (nome.isEmpty() || descricao.isEmpty() || dataInicio.isEmpty() ||
                horaInicio.isEmpty() || dataFim.isEmpty() || horaFim.isEmpty() ||
                rua.isEmpty() || bairro.isEmpty() || cidade.isEmpty() || uf.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos obrigatórios.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (uf.length() != 2) {
            editUF.setError("UF deve ter 2 caracteres.");
            editUF.requestFocus();
            return;
        }

        Evento evento = new Evento(
                nome, descricao, dataInicio, dataFim,
                horaInicio, horaFim, rua, numero, bairro, cidade, uf,
                catSeguranca, catLimpeza, catInfraestrutura, catOutros
        );

        boolean sucesso;
        if (pEVE_ID != -1) {
            evento.setId(pEVE_ID);
            sucesso = eventosDb.atualizaEvento(evento);
            if (sucesso) {
                Toast.makeText(this, "Evento atualizado com sucesso!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Erro ao atualizar evento.", Toast.LENGTH_SHORT).show();
            }
        } else { // Modo de criação
            long id = eventosDb.criarEvento(evento);
            sucesso = (id != -1);
            if (sucesso) {
                Toast.makeText(this, "Evento cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Erro ao cadastrar evento.", Toast.LENGTH_SHORT).show();
            }
        }
        if (sucesso) {
            setResult(RESULT_OK);
            finish();
        }
    }

    private void voltar(long USU_ID_SESSION) {
        Intent intent = new Intent(ActivityEvento.this, HomeActivity.class);
        intent.putExtra("USU_ID_SESSION", USU_ID_SESSION);
        startActivity(intent);
        finish();
    }
}