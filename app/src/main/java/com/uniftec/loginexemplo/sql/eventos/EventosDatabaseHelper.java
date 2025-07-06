package com.uniftec.loginexemplo.sql.eventos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.uniftec.loginexemplo.Constants;

import java.util.ArrayList;
import java.util.List;


public class EventosDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "apkProjeto.db";
    private static final int DATABASE_VERSION = Constants.DATABASE_VERSION;
    public static final String TABLE_EVENTS = "EVENTOS";
    public static final String EVE_ID = "id";
    public static final String EVE_NOME = "nome";
    public static final String EVE_DESCRICAO = "descricao";
    public static final String EVE_DATA_INICIO = "data_inicio";
    public static final String EVE_DATA_FIM = "data_fim";
    public static final String EVE_HORA_INICIO = "hora_inicio";
    public static final String EVE_HORA_FIM = "hora_fim";
    public static final String EVE_RUA = "rua";
    public static final String EVE_NUMERO = "numero";
    public static final String EVE_BAIRRO = "bairro";
    public static final String EVE_CIDADE = "cidade";
    public static final String EVE_UF = "uf";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_EVENTS + " (" +
                    EVE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    EVE_NOME + " TEXT NOT NULL, " +
                    EVE_DESCRICAO + " TEXT, " +
                    EVE_DATA_INICIO + " TEXT NOT NULL, " +
                    EVE_DATA_FIM + " TEXT NOT NULL, " +
                    EVE_HORA_INICIO + " TEXT NOT NULL, " +
                    EVE_HORA_FIM + " TEXT NOT NULL, " +
                    EVE_RUA + " TEXT, " +
                    EVE_NUMERO + " TEXT, " +
                    EVE_BAIRRO + " TEXT, " +
                    EVE_CIDADE + " TEXT, " +
                    EVE_UF + " TEXT);";

    public EventosDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        Log.d("EventosDB", "Tabela EVENTOS criada.");
        inserirDadosDeTeste(db); // Chama o método para inserir dados de teste
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("EventosDB", "Atualizando banco de dados de versão " + oldVersion + " para " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        onCreate(db);
    }

    public long criarEvento(Evento evento) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EVE_NOME, evento.getNome());
        values.put(EVE_DESCRICAO, evento.getDescricao());
        values.put(EVE_DATA_INICIO, evento.getDataInicio());
        values.put(EVE_DATA_FIM, evento.getDataFim());
        values.put(EVE_HORA_INICIO, evento.getHoraInicio());
        values.put(EVE_HORA_FIM, evento.getHoraFim());
        values.put(EVE_RUA, evento.getRua());
        values.put(EVE_NUMERO, evento.getNumero());
        values.put(EVE_BAIRRO, evento.getBairro());
        values.put(EVE_CIDADE, evento.getCidade());
        values.put(EVE_UF, evento.getUf());

        long result = -1;
        try {
            result = db.insert(TABLE_EVENTS, null, values);
            Log.d("EventosDB", "Evento criado com ID: " + result);
        } catch (Exception e) {
            Log.e("EventosDB", "Erro ao criar evento: " + e.getMessage(), e);
        } finally {
            db.close();
        }
        return result;
    }

    public Evento carregaDadosEvento(long pEVE_ID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        Evento evento = null;

        String[] columns = {
                EVE_ID, EVE_NOME, EVE_DESCRICAO,
                EVE_DATA_INICIO, EVE_DATA_FIM, EVE_HORA_INICIO, EVE_HORA_FIM,
                EVE_RUA, EVE_NUMERO, EVE_BAIRRO, EVE_CIDADE, EVE_UF
        };
        String selection = EVE_ID + " = ?";
        String[] selectionArgs = { String.valueOf(pEVE_ID) };

        try {
            cursor = db.query(TABLE_EVENTS, columns, selection, selectionArgs, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(EVE_ID));
                String nome = cursor.getString(cursor.getColumnIndexOrThrow(EVE_NOME));
                String descricao = cursor.getString(cursor.getColumnIndexOrThrow(EVE_DESCRICAO));
                String dataInicio = cursor.getString(cursor.getColumnIndexOrThrow(EVE_DATA_INICIO));
                String dataFim = cursor.getString(cursor.getColumnIndexOrThrow(EVE_DATA_FIM));
                String horaInicio = cursor.getString(cursor.getColumnIndexOrThrow(EVE_HORA_INICIO));
                String horaFim = cursor.getString(cursor.getColumnIndexOrThrow(EVE_HORA_FIM));
                String rua = cursor.getString(cursor.getColumnIndexOrThrow(EVE_RUA));
                String numero = cursor.getString(cursor.getColumnIndexOrThrow(EVE_NUMERO));
                String bairro = cursor.getString(cursor.getColumnIndexOrThrow(EVE_BAIRRO));
                String cidade = cursor.getString(cursor.getColumnIndexOrThrow(EVE_CIDADE));
                String uf = cursor.getString(cursor.getColumnIndexOrThrow(EVE_UF));

                evento = new Evento(id, nome, descricao, dataInicio,
                        dataFim, horaInicio, horaFim, rua, numero, bairro, cidade, uf);
            }
        } catch (Exception e) {
            Log.e("EventosDB", "Erro ao carregar evento: " + e.getMessage(), e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return evento;
    }

    public boolean atualizaEvento(Evento evento) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(EVE_NOME, evento.getNome());
        values.put(EVE_DESCRICAO, evento.getDescricao());
        values.put(EVE_DATA_INICIO, evento.getDataInicio());
        values.put(EVE_DATA_FIM, evento.getDataFim());
        values.put(EVE_HORA_INICIO, evento.getHoraInicio());
        values.put(EVE_HORA_FIM, evento.getHoraFim());
        values.put(EVE_RUA, evento.getRua());
        values.put(EVE_NUMERO, evento.getNumero());
        values.put(EVE_BAIRRO, evento.getBairro());
        values.put(EVE_CIDADE, evento.getCidade());
        values.put(EVE_UF, evento.getUf());

        String selection = EVE_ID + " = ?";
        String[] selectionArgs = { String.valueOf(evento.getId()) };

        int rowsAffected = 0;
        try {
            rowsAffected = db.update(TABLE_EVENTS, values, selection, selectionArgs);
            Log.d("EventosDB", "Evento ID " + evento.getId() + " atualizado. Linhas afetadas: " + rowsAffected);
        } catch (Exception e) {
            Log.e("EventosDB", "Erro ao atualizar evento: " + e.getMessage(), e);
        } finally {
            db.close();
        }
        return rowsAffected > 0;
    }

    public boolean excluirEvento(long pEVE_ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = EVE_ID + " = ?";
        String[] selectionArgs = { String.valueOf(pEVE_ID) };

        int rowsAffected = 0;
        try {
            rowsAffected = db.delete(TABLE_EVENTS, selection, selectionArgs);
            Log.d("EventosDB", "Evento ID " + pEVE_ID + " excluído. Linhas afetadas: " + rowsAffected);
        } catch (Exception e) {
            Log.e("EventosDB", "Erro ao excluir evento: " + e.getMessage(), e);
        } finally {
            db.close();
        }
        return rowsAffected > 0;
    }

    public List<Evento> retornaTodosEventos() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Evento> eventosList = new ArrayList<>();
        Cursor cursor = null;

        String[] columns = {
                EVE_ID, EVE_NOME, EVE_DESCRICAO,
                EVE_DATA_INICIO, EVE_DATA_FIM, EVE_HORA_INICIO, EVE_HORA_FIM,
                EVE_RUA, EVE_NUMERO, EVE_BAIRRO, EVE_CIDADE, EVE_UF
        };

        try {
            cursor = db.query(TABLE_EVENTS, columns, null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    long id = cursor.getLong(cursor.getColumnIndex(EVE_ID));
                    String nome = cursor.getString(cursor.getColumnIndex(EVE_NOME));
                    String descricao = cursor.getString(cursor.getColumnIndex(EVE_DESCRICAO));
                    String dataInicio = cursor.getString(cursor.getColumnIndex(EVE_DATA_INICIO));
                    String dataFim = cursor.getString(cursor.getColumnIndex(EVE_DATA_FIM));
                    String horaInicio = cursor.getString(cursor.getColumnIndex(EVE_HORA_INICIO));
                    String horaFim = cursor.getString(cursor.getColumnIndex(EVE_HORA_FIM));
                    String rua = cursor.getString(cursor.getColumnIndex(EVE_RUA));
                    String numero = cursor.getString(cursor.getColumnIndex(EVE_NUMERO));
                    String bairro = cursor.getString(cursor.getColumnIndex(EVE_BAIRRO));
                    String cidade = cursor.getString(cursor.getColumnIndex(EVE_CIDADE));
                    String uf = cursor.getString(cursor.getColumnIndex(EVE_UF));

                    Evento evento = new Evento(id, nome, descricao, dataInicio,
                            dataFim, horaInicio, horaFim, rua, numero, bairro, cidade, uf);
                    eventosList.add(evento);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("EventosDB", "Erro ao retornar todos os eventos: " + e.getMessage(), e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return eventosList;
    }

    // Método para inserir dados de teste na tabela EVENTOS
    private void inserirDadosDeTeste(SQLiteDatabase db) {
        Log.d("EventosDB", "Inserindo dados de teste...");

        // Evento 1
        ContentValues values1 = new ContentValues();
        values1.put(EVE_NOME, "Feira de Tecnologia 2025");
        values1.put(EVE_DESCRICAO, "A maior feira de tecnologia do sul do país.");
        values1.put(EVE_DATA_INICIO, "2025-08-20");
        values1.put(EVE_DATA_FIM, "2025-08-22");
        values1.put(EVE_HORA_INICIO, "09:00");
        values1.put(EVE_HORA_FIM, "18:00");
        values1.put(EVE_RUA, "Rua da Inovação");
        values1.put(EVE_NUMERO, "100");
        values1.put(EVE_BAIRRO, "Tecnoparque");
        values1.put(EVE_CIDADE, "Caxias do Sul");
        values1.put(EVE_UF, "RS");
        db.insert(TABLE_EVENTS, null, values1);

        // Evento 2
        ContentValues values2 = new ContentValues();
        values2.put(EVE_NOME, "Workshop de Fotografia Noturna");
        values2.put(EVE_DESCRICAO, "Aprenda técnicas de fotografia em ambientes com pouca luz.");
        values2.put(EVE_DATA_INICIO, "2025-09-05");
        values2.put(EVE_DATA_FIM, "2025-09-05");
        values2.put(EVE_HORA_INICIO, "19:00");
        values2.put(EVE_HORA_FIM, "22:00");
        values2.put(EVE_RUA, "Avenida das Artes");
        values2.put(EVE_NUMERO, "50");
        values2.put(EVE_BAIRRO, "Centro Histórico");
        values2.put(EVE_CIDADE, "Porto Alegre");
        values2.put(EVE_UF, "RS");
        db.insert(TABLE_EVENTS, null, values2);

        // Evento 3
        ContentValues values3 = new ContentValues();
        values3.put(EVE_NOME, "Festival de Cerveja Artesanal");
        values3.put(EVE_DESCRICAO, "Degustação de cervejas locais e nacionais.");
        values3.put(EVE_DATA_INICIO, "2025-10-12");
        values3.put(EVE_DATA_FIM, "2025-10-13");
        values3.put(EVE_HORA_INICIO, "14:00");
        values3.put(EVE_HORA_FIM, "23:00");
        values3.put(EVE_RUA, "Parque da Cidade");
        values3.put(EVE_NUMERO, "S/N");
        values3.put(EVE_BAIRRO, "Floresta");
        values3.put(EVE_CIDADE, "Gramado");
        values3.put(EVE_UF, "RS");
        db.insert(TABLE_EVENTS, null, values3);

        Log.d("EventosDB", "Dados de teste inseridos com sucesso.");
    }
}
