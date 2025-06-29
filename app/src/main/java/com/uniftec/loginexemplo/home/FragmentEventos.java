package com.uniftec.loginexemplo.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.uniftec.loginexemplo.DetailEvento;
import com.uniftec.loginexemplo.ActivityNovoEvento;
import com.uniftec.loginexemplo.ActivityNovoEvento2;
import com.uniftec.loginexemplo.ActivityNovoEvento3;
import com.uniftec.loginexemplo.ActivityNovoEvento4;
import com.uniftec.loginexemplo.R;

import java.util.ArrayList;
import java.util.Arrays;

public class FragmentEventos extends Fragment {

    private static final int REQUEST_NOVO_EVENTO = 1;

    private ArrayList<String> eventos;
    private EventosAdapter adapter;

    public FragmentEventos() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_eventos, container, false);
        Toast.makeText(getContext(), "FragmentEventos carregado", Toast.LENGTH_SHORT).show();

        eventos = new ArrayList<>(Arrays.asList(
                "Feira de Tecnologia",
                "Workshop de Fotografia",
                "Festival de Comida",
                "Palestra de Inovação",
                "Curso de Desenvolvimento Android",
                "Show de Música ao Vivo",
                "Feira de Artesanato",
                "Encontro de Gamers",
                "Exposição de Pintura",
                "Caminhada Ecológica"
        ));

        ListView listaEventos = view.findViewById(R.id.lista_eventos);

        adapter = new EventosAdapter(getContext(), eventos);
        listaEventos.setAdapter(adapter);

        listaEventos.setOnItemClickListener((AdapterView<?> parent, View v, int position, long id) -> {
            Intent intent = new Intent(getActivity(), DetailEvento.class);
            intent.putExtra("nomeEvento", eventos.get(position));
            startActivity(intent);
        });

        FloatingActionButton btnAdicionar = view.findViewById(R.id.btnAdicionarEvento);
        btnAdicionar.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ActivityNovoEvento.class);
            startActivityForResult(intent, REQUEST_NOVO_EVENTO);
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_NOVO_EVENTO && resultCode == getActivity().RESULT_OK && data != null) {
            String novoEvento = data.getStringExtra("novoEvento");
            if (novoEvento != null && !novoEvento.isEmpty()) {
                eventos.add(novoEvento);
                adapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "Evento adicionado: " + novoEvento, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
