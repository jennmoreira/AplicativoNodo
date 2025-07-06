package com.uniftec.loginexemplo.home.eventos;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.uniftec.loginexemplo.DetailEvento;
import com.uniftec.loginexemplo.evento.ActivityEvento;
import com.uniftec.loginexemplo.R;
import com.uniftec.loginexemplo.sql.eventos.Evento;
import com.uniftec.loginexemplo.sql.eventos.EventosDatabaseHelper;

import java.util.List;

public class EventosAdapter extends ArrayAdapter<Evento> {

    private Context mContext;
    private List<Evento> mEventosList;
    private String mTipoUsuarioLogado;

    public EventosAdapter(Context context, List<Evento> eventosList, String tipoUsuarioLogado) {
        super(context, 0, eventosList);
        this.mContext = context;
        this.mEventosList = eventosList;
        this.mTipoUsuarioLogado = tipoUsuarioLogado;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        Evento currentEvento = mEventosList.get(position);

        int layoutResId;
        layoutResId = R.layout.list_evento;

        if (listItem == null || !listItem.getTag().equals(layoutResId)) {
            listItem = LayoutInflater.from(mContext).inflate(layoutResId, parent, false);
            listItem.setTag(layoutResId);
        }

        TextView nomeEventoTextView = listItem.findViewById(R.id.listName);
        if (nomeEventoTextView != null) {
            nomeEventoTextView.setText(currentEvento.getNome());
        }

        ImageView btnEditar = listItem.findViewById(R.id.btnEditar);
        ImageView btnDelete = listItem.findViewById(R.id.btnDelete);
        ImageView btnView = listItem.findViewById(R.id.btnView);

        if ("criador".equals(mTipoUsuarioLogado)) {
            if (btnEditar != null) {
                btnEditar.setVisibility(View.VISIBLE);
                btnEditar.setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, ActivityEvento.class);
                    intent.putExtra("pEVE_ID", currentEvento.getId());
                    mContext.startActivity(intent);
                });
            }
            if (btnDelete != null) {
                btnDelete.setVisibility(View.VISIBLE);
                btnDelete.setOnClickListener(v -> {
                    EventosDatabaseHelper db = new EventosDatabaseHelper(mContext);
                    boolean sucesso = db.excluirEvento(currentEvento.getId());
                    if (sucesso) {
                        mEventosList.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(mContext, "Evento excluído com sucesso!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, "Erro ao excluir o evento.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            if (btnView != null) {
                btnView.setVisibility(View.GONE);
            }
        } else {
            if (btnEditar != null) {
                btnEditar.setVisibility(View.GONE);
            }
            if (btnDelete != null) {
                btnDelete.setVisibility(View.GONE);
            }
            if (btnView != null) {
                btnView.setVisibility(View.VISIBLE);
                btnView.setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, DetailEvento.class);
                    intent.putExtra("pEVE_ID", currentEvento.getId());
                    mContext.startActivity(intent);
                });
            }
        }

        return listItem;
    }

    public void setEventos(List<Evento> newEventos) {
        mEventosList.clear();
        mEventosList.addAll(newEventos);
        notifyDataSetChanged();
    }
}