package com.uniftec.loginexemplo.home.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.uniftec.loginexemplo.R;
import com.uniftec.loginexemplo.sql.AppDatabaseHelper;
import com.uniftec.loginexemplo.sql.eventos.Evento;
import com.uniftec.loginexemplo.sql.usuarios.Usuario;

import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment {

    private long USU_ID_SESSION = -1;
    private AppDatabaseHelper db;
    private ListView homeListView;
    private TextView homeTitle;
    private ArrayAdapter<String> adapter;
    private List<String> dataList;

    public FragmentHome() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            USU_ID_SESSION = getArguments().getLong("USU_ID_SESSION", -1L);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        db = new AppDatabaseHelper(getContext());

        homeListView = view.findViewById(R.id.home_list_view);
        homeTitle = view.findViewById(R.id.home_title);
        dataList = new ArrayList<>();
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, dataList);
        homeListView.setAdapter(adapter);

        loadHomeContent();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadHomeContent();
    }

    private void loadHomeContent() {
        if (USU_ID_SESSION != -1) {
            String tipoUsuario = null;
            tipoUsuario = db.getTipoUsuario(USU_ID_SESSION);

            dataList.clear();

            if ("criador".equals(tipoUsuario)) {
                homeTitle.setText("Últimos 10 Usuários Cadastrados");
                List<Usuario> recentUsers = db.getTop10RecentUsers();
                if (recentUsers != null && !recentUsers.isEmpty()) {
                    for (Usuario user : recentUsers) {
                        dataList.add("Nome: " + user.getNome() + " | Email: " + user.getEmail());
                    }
                } else {
                    dataList.add("Nenhum usuário recente encontrado.");
                }
            } else if ("prestador".equals(tipoUsuario)) {
                homeTitle.setText("Últimos 10 Eventos que Você se Candidatou");
                List<Evento> recentAppliedEvents = db.getTop10RecentAppliedEventsByPrestador(USU_ID_SESSION);
                if (recentAppliedEvents != null && !recentAppliedEvents.isEmpty()) {
                    for (Evento event : recentAppliedEvents) {
                        dataList.add("Evento: " + event.getNome() + " | Data: " + event.getDataInicio());
                    }
                } else {
                    dataList.add("Nenhum evento recente encontrado.");
                }
            } else {
                homeTitle.setText("Bem-vindo!");
                dataList.add("Faça login para ver o conteúdo personalizado.");
            }
            adapter.notifyDataSetChanged();
        } else {
            homeTitle.setText("Bem-vindo!");
            dataList.clear();
            dataList.add("Faça login para ver o conteúdo personalizado.");
            adapter.notifyDataSetChanged();
        }
    }
}