package com.uniftec.loginexemplo.sql.eventos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class EventosDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "apkProjeto.db";
    private static final int DATABASE_VERSION = 110;
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
    public static final String EVE_CAT_SEG = "categoria_seguranca";
    public static final String EVE_CAT_LIM = "categoria_limpeza";
    public static final String EVE_CAT_INF = "categoria_infraestrutura";
    public static final String EVE_CAT_OUT = "categoria_outros";

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
                    EVE_UF + " TEXT, " +
                    EVE_CAT_SEG + " INTEGER DEFAULT 0, " +
                    EVE_CAT_LIM + " INTEGER DEFAULT 0, " +
                    EVE_CAT_INF + " INTEGER DEFAULT 0, " +
                    EVE_CAT_OUT + " INTEGER DEFAULT 0" +
                    ");";

    public EventosDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(TABLE_CREATE);
            if (tabelaExiste(db, TABLE_EVENTS)) {
                inserirDadosDeTeste(db);
            }
        } catch (Exception e) {
            throw new RuntimeException("Falha ao criar tabela EVENTOS", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            List<Evento> eventosBackup = new ArrayList<>();
            if (tabelaExiste(db, TABLE_EVENTS)) {
                eventosBackup = obterEventosParaBackup(db);
            }

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
            onCreate(db);
            if (!eventosBackup.isEmpty()) {
                restaurarEventosDoBackup(db, eventosBackup);
            }
        } catch (Exception e) {
            throw new RuntimeException("Falha no upgrade do banco", e);
        }
    }

    private boolean tabelaExiste(SQLiteDatabase db, String nomeTabela) {
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?",
                    new String[]{nomeTabela});
            return cursor != null && cursor.getCount() > 0;
        } catch (Exception e) {
            return false;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private List<Evento> obterEventosParaBackup(SQLiteDatabase db) {
        List<Evento> eventos = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_EVENTS, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
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

                    int catSeguranca = 0;
                    if (cursor.getColumnIndex(EVE_CAT_SEG) != -1) {
                        catSeguranca = cursor.getInt(cursor.getColumnIndexOrThrow(EVE_CAT_SEG));
                    }
                    int catLimpeza = 0;
                    if (cursor.getColumnIndex(EVE_CAT_LIM) != -1) {
                        catLimpeza = cursor.getInt(cursor.getColumnIndexOrThrow(EVE_CAT_LIM));
                    }
                    int catInfraestrutura = 0;
                    if (cursor.getColumnIndex(EVE_CAT_INF) != -1) {
                        catInfraestrutura = cursor.getInt(cursor.getColumnIndexOrThrow(EVE_CAT_INF));
                    }
                    int catOutros = 0;
                    if (cursor.getColumnIndex(EVE_CAT_OUT) != -1) {
                        catOutros = cursor.getInt(cursor.getColumnIndexOrThrow(EVE_CAT_OUT));
                    }

                    Evento evento = new Evento(id, nome, descricao, dataInicio,
                            dataFim, horaInicio, horaFim, rua, numero, bairro, cidade, uf,
                            catSeguranca, catLimpeza, catInfraestrutura, catOutros);
                    eventos.add(evento);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return eventos;
    }

    private void restaurarEventosDoBackup(SQLiteDatabase db, List<Evento> eventos) {
        for (Evento evento : eventos) {
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
            values.put(EVE_CAT_SEG, evento.getCatSeguranca());
            values.put(EVE_CAT_LIM, evento.getCatLimpeza());
            values.put(EVE_CAT_INF, evento.getCatInfraestrutura());
            values.put(EVE_CAT_OUT, evento.getCatOutros());

            db.insert(TABLE_EVENTS, null, values);
        }
    }

    public long criarEvento(Evento evento) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            if (!tabelaExiste(db, TABLE_EVENTS)) {
                onCreate(db);
            }
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
            values.put(EVE_CAT_SEG, evento.getCatSeguranca());
            values.put(EVE_CAT_LIM, evento.getCatLimpeza());
            values.put(EVE_CAT_INF, evento.getCatInfraestrutura());
            values.put(EVE_CAT_OUT, evento.getCatOutros());

            long result = db.insert(TABLE_EVENTS, null, values);
            return result;
        } catch (Exception e) {
            return -1;
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public Evento carregaDadosEvento(long pEVE_ID) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        Evento evento = null;

        try {
            db = this.getReadableDatabase();
            if (!tabelaExiste(db, TABLE_EVENTS)) {
                return null;
            }

            String[] columns = {
                    EVE_ID, EVE_NOME, EVE_DESCRICAO,
                    EVE_DATA_INICIO, EVE_DATA_FIM, EVE_HORA_INICIO, EVE_HORA_FIM,
                    EVE_RUA, EVE_NUMERO, EVE_BAIRRO, EVE_CIDADE, EVE_UF,
                    EVE_CAT_SEG, EVE_CAT_LIM, EVE_CAT_INF, EVE_CAT_OUT
            };
            String selection = EVE_ID + " = ?";
            String[] selectionArgs = { String.valueOf(pEVE_ID) };

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
                int catSeguranca = cursor.getInt(cursor.getColumnIndexOrThrow(EVE_CAT_SEG));
                int catLimpeza = cursor.getInt(cursor.getColumnIndexOrThrow(EVE_CAT_LIM));
                int catInfraestrutura = cursor.getInt(cursor.getColumnIndexOrThrow(EVE_CAT_INF));
                int catOutros = cursor.getInt(cursor.getColumnIndexOrThrow(EVE_CAT_OUT));

                evento = new Evento(id, nome, descricao, dataInicio,
                        dataFim, horaInicio, horaFim, rua, numero, bairro, cidade, uf,
                        catSeguranca, catLimpeza, catInfraestrutura, catOutros);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return evento;
    }

    public boolean atualizaEvento(Evento evento) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            if (!tabelaExiste(db, TABLE_EVENTS)) {
                return false;
            }
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
            values.put(EVE_CAT_SEG, evento.getCatSeguranca());
            values.put(EVE_CAT_LIM, evento.getCatLimpeza());
            values.put(EVE_CAT_INF, evento.getCatInfraestrutura());
            values.put(EVE_CAT_OUT, evento.getCatOutros());

            String selection = EVE_ID + " = ?";
            String[] selectionArgs = { String.valueOf(evento.getId()) };

            int rowsAffected = db.update(TABLE_EVENTS, values, selection, selectionArgs);
            return rowsAffected > 0;
        } catch (Exception e) {
            return false;
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public boolean excluirEvento(long pEVE_ID) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            if (!tabelaExiste(db, TABLE_EVENTS)) {
                return false;
            }
            String selection = EVE_ID + " = ?";
            String[] selectionArgs = { String.valueOf(pEVE_ID) };

            int rowsAffected = db.delete(TABLE_EVENTS, selection, selectionArgs);
            return rowsAffected > 0;
        } catch (Exception e) {
            return false;
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public List<Evento> retornaTodosEventos() {
        SQLiteDatabase db = null;
        List<Evento> eventosList = new ArrayList<>();
        Cursor cursor = null;

        try {
            db = this.getReadableDatabase();
            if (!tabelaExiste(db, TABLE_EVENTS)) {
                return eventosList;
            }

            String[] columns = {
                    EVE_ID, EVE_NOME, EVE_DESCRICAO,
                    EVE_DATA_INICIO, EVE_DATA_FIM, EVE_HORA_INICIO, EVE_HORA_FIM,
                    EVE_RUA, EVE_NUMERO, EVE_BAIRRO, EVE_CIDADE, EVE_UF,
                    EVE_CAT_SEG, EVE_CAT_LIM, EVE_CAT_INF, EVE_CAT_OUT
            };

            cursor = db.query(TABLE_EVENTS, columns, null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
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
                    int catSeguranca = cursor.getInt(cursor.getColumnIndexOrThrow(EVE_CAT_SEG));
                    int catLimpeza = cursor.getInt(cursor.getColumnIndexOrThrow(EVE_CAT_LIM));
                    int catInfraestrutura = cursor.getInt(cursor.getColumnIndexOrThrow(EVE_CAT_INF));
                    int catOutros = cursor.getInt(cursor.getColumnIndexOrThrow(EVE_CAT_OUT));

                    Evento evento = new Evento(id, nome, descricao, dataInicio,
                            dataFim, horaInicio, horaFim, rua, numero, bairro, cidade, uf,
                            catSeguranca, catLimpeza, catInfraestrutura, catOutros);
                    eventosList.add(evento);
                } while (cursor.moveToNext());
            }
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

    private void inserirDadosDeTeste(SQLiteDatabase db) {

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
        values1.put(EVE_CAT_SEG, 1);
        values1.put(EVE_CAT_LIM, 0);
        values1.put(EVE_CAT_INF, 1);
        values1.put(EVE_CAT_OUT, 0);
        long result1 = db.insert(TABLE_EVENTS, null, values1);

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
        values2.put(EVE_CAT_SEG, 0);
        values2.put(EVE_CAT_LIM, 0);
        values2.put(EVE_CAT_INF, 0);
        values2.put(EVE_CAT_OUT, 1);
        long result2 = db.insert(TABLE_EVENTS, null, values2);

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
        values3.put(EVE_CAT_SEG, 1);
        values3.put(EVE_CAT_LIM, 1);
        values3.put(EVE_CAT_INF, 0);
        values3.put(EVE_CAT_OUT, 0);
        long result3 = db.insert(TABLE_EVENTS, null, values3);
    }
}