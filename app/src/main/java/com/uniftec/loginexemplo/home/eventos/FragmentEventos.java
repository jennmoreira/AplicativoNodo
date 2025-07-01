package com.uniftec.loginexemplo.home;

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
import com.uniftec.loginexemplo.DetailEvento;
import com.uniftec.loginexemplo.ActivityEvento;
import com.uniftec.loginexemplo.R;
import com.uniftec.loginexemplo.home.eventos.EventosAdapter;
import com.uniftec.loginexemplo.sql.eventos.Evento;
import com.uniftec.loginexemplo.sql.eventos.EventosDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class FragmentEventos extends Fragment {

    private static final int REQUEST_NOVO_EVENTO = 1;
    private List<Evento> eventos;
    private EventosAdapter adapter;
    private EventosDatabaseHelper eventosDb;

    public FragmentEventos() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_eventos, container, false);

        eventosDb = new EventosDatabaseHelper(getContext());
        eventos = new ArrayList<>();

        ListView listaEventos = view.findViewById(R.id.lista_eventos);
        adapter = new EventosAdapter(getContext(), eventos);
        listaEventos.setAdapter(adapter);

        carregarEventosDoBanco();

        listaEventos.setOnItemClickListener((AdapterView<?> parent, View v, int position, long id) -> {
            Evento eventoClicado = eventos.get(position);
            Intent intent = new Intent(getActivity(), DetailEvento.class);
            intent.putExtra("eventoId", eventoClicado.getId());
            startActivity(intent);
        });

        FloatingActionButton btnAdicionar = view.findViewById(R.id.btnAdicionarEvento);
        btnAdicionar.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ActivityEvento.class);
            startActivityForResult(intent, REQUEST_NOVO_EVENTO);
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
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
            carregarEventosDoBanco();
            Toast.makeText(getContext(), "Lista de eventos atualizada!", Toast.LENGTH_SHORT).show();
        }
    }
}
