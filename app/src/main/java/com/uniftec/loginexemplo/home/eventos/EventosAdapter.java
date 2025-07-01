package com.uniftec.loginexemplo.home.eventos; // O pacote mudou para .home.eventos

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.uniftec.loginexemplo.ActivityEvento;
import com.uniftec.loginexemplo.R;
import com.uniftec.loginexemplo.sql.eventos.Evento;
import com.uniftec.loginexemplo.sql.eventos.EventosDatabaseHelper;

import java.util.List;

public class EventosAdapter extends ArrayAdapter<Evento> {

    private Context mContext;
    private List<Evento> mEventosList;

    public EventosAdapter(Context context, List<Evento> eventosList) {
        super(context, 0, eventosList);
        this.mContext = context;
        this.mEventosList = eventosList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        }

        Evento currentEvento = mEventosList.get(position);

        TextView nomeEventoTextView = listItem.findViewById(R.id.listName);
        if (nomeEventoTextView != null) {
            nomeEventoTextView.setText(currentEvento.getNome());
        }

        ImageView btnEditar = listItem.findViewById(R.id.btnEditar);
        ImageView btnDelete = listItem.findViewById(R.id.btnDelete);

        if (btnEditar != null) {
            btnEditar.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, ActivityEvento.class);
                intent.putExtra("eventoIdParaEditar", currentEvento.getId());
                mContext.startActivity(intent);
            });
        }

        if (btnDelete != null) {
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

        return listItem;
    }

    public void setEventos(List<Evento> newEventos) {

        mEventosList.clear();
        mEventosList.addAll(newEventos);
        notifyDataSetChanged();
    }
}
