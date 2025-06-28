package com.uniftec.loginexemplo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class FragmentEventos extends Fragment {

    public FragmentEventos() {
        // Construtor público obrigatório
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Toast.makeText(getContext(), "FragmentEventos carregado", Toast.LENGTH_SHORT).show();
        return inflater.inflate(R.layout.fragment_eventos, container, false);
    }
}
