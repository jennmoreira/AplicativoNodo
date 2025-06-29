package com.uniftec.loginexemplo.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;
import com.uniftec.loginexemplo.R;

import java.util.List;

public class EventosAdapter extends BaseAdapter {

    private Context context;
    private List<String> eventos;  // só nomes dos eventos mesmo

    public EventosAdapter(Context context, List<String> eventos) {
        this.context = context;
        this.eventos = eventos;
    }

    @Override
    public int getCount() {
        return eventos.size();
    }

    @Override
    public Object getItem(int position) {
        return eventos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        ShapeableImageView imagemEvento, botaoEditar, botaoDeletar;
        TextView nomeEvento;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.imagemEvento = convertView.findViewById(R.id.listImage);
            holder.nomeEvento = convertView.findViewById(R.id.listName);
            holder.botaoEditar = convertView.findViewById(R.id.listEdit);
            holder.botaoDeletar = convertView.findViewById(R.id.listDelete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String eventoNome = eventos.get(position);
        holder.nomeEvento.setText(eventoNome);

        // Sete as imagens padrão ou diferentes se quiser
        holder.imagemEvento.setImageResource(R.drawable.ic_evento);

        // Aqui você pode adicionar os listeners nos botões se quiser:
        holder.botaoEditar.setOnClickListener(v -> {
            // Exemplo simples: Toast
            android.widget.Toast.makeText(context, "Editar: " + eventoNome, android.widget.Toast.LENGTH_SHORT).show();
            // Ou abrir tela para editar evento...
        });

        holder.botaoDeletar.setOnClickListener(v -> {
            android.widget.Toast.makeText(context, "Deletar: " + eventoNome, android.widget.Toast.LENGTH_SHORT).show();
            // Aqui você pode remover o evento da lista e dar notifyDataSetChanged()
        });

        return convertView;
    }
}
