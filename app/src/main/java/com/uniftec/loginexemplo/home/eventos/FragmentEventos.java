package com.uniftec.loginexemplo.home.eventos;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.uniftec.loginexemplo.evento.DetailEvento;
import com.uniftec.loginexemplo.evento.ActivityEvento;
import com.uniftec.loginexemplo.R;
import com.uniftec.loginexemplo.sql.eventos.Evento;
import com.uniftec.loginexemplo.sql.eventos.EventosDatabaseHelper;
import com.uniftec.loginexemplo.sql.usuarios.UsuariosDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class FragmentEventos extends Fragment {

    private static final int REQUEST_NOVO_EVENTO = 1;
    private List<Evento> eventos;
    private EventosAdapter adapter;
    private EventosDatabaseHelper eventosDb;
    private UsuariosDatabaseHelper usuariosDb;
    private long USU_ID_SESSION = -1;
    private String tipoUsuarioLogado = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            USU_ID_SESSION = getArguments().getLong("USU_ID_SESSION", -1L);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_eventos, container, false);

        eventosDb = new EventosDatabaseHelper(getContext());
        usuariosDb = new UsuariosDatabaseHelper(getContext());
        eventos = new ArrayList<>();

        if (USU_ID_SESSION != -1) {
            tipoUsuarioLogado = usuariosDb.getTipoUsuario(USU_ID_SESSION);
        }

        ListView listaEventos = view.findViewById(R.id.lista_eventos);
        adapter = new EventosAdapter(getContext(), eventos, tipoUsuarioLogado, USU_ID_SESSION);
        listaEventos.setAdapter(adapter);

        carregarEventosDoBanco();

        listaEventos.setOnItemClickListener((AdapterView<?> parent, View v, int position, long id) -> {
            Evento eventoClicado = eventos.get(position);
            Intent intent = new Intent(getActivity(), DetailEvento.class);
            intent.putExtra("pEVE_ID", eventoClicado.getId());
            startActivity(intent);
        });

        FloatingActionButton btnAdicionar = view.findViewById(R.id.btnAdicionarEvento);
        if ("criador".equals(tipoUsuarioLogado)) {
            btnAdicionar.setVisibility(View.VISIBLE);
            btnAdicionar.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), ActivityEvento.class);
                intent.putExtra("USU_ID_SESSION", USU_ID_SESSION);
                startActivityForResult(intent, REQUEST_NOVO_EVENTO);
            });
        } else {
            btnAdicionar.setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (USU_ID_SESSION != -1) {
            tipoUsuarioLogado = usuariosDb.getTipoUsuario(USU_ID_SESSION);
            FloatingActionButton btnAdicionar = getView().findViewById(R.id.btnAdicionarEvento);
            if ("criador".equals(tipoUsuarioLogado)) {
                btnAdicionar.setVisibility(View.VISIBLE);
            } else {
                btnAdicionar.setVisibility(View.GONE);
            }
            adapter.setTipoUsuarioLogado(tipoUsuarioLogado);
            adapter.notifyDataSetChanged();
        }
        carregarEventosDoBanco();
    }

    private void carregarEventosDoBanco() {
        List<Evento> eventosDoBanco = eventosDb.retornaTodosEventos();

        if (eventosDoBanco != null) {
            adapter.setEventos(eventosDoBanco);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_NOVO_EVENTO && resultCode == getActivity().RESULT_OK) {
            if (data != null && data.hasExtra("USU_ID_SESSION")) {
                USU_ID_SESSION = data.getLongExtra("USU_ID_SESSION", -1L);
            }
            carregarEventosDoBanco();
            Toast.makeText(getContext(), "Lista de eventos atualizada!", Toast.LENGTH_SHORT).show();
        }
    }
}