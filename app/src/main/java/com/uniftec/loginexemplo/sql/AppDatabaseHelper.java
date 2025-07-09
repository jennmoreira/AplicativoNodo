package com.uniftec.loginexemplo.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.uniftec.loginexemplo.sql.candidatos.Candidato;
import com.uniftec.loginexemplo.sql.eventos.Evento;
import com.uniftec.loginexemplo.sql.usuarios.Usuario;

import java.util.ArrayList;
import java.util.List;

public class AppDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "apkProjeto.db";
    private static final int DATABASE_VERSION = 160;

    private Context context;

    // --- Constantes da Tabela USUARIOS ---
    public static final String TABLE_USERS = "USUARIOS";
    public static final String USU_ID = "id";
    public static final String USU_NOME = "name";
    public static final String USU_EMAIL = "email";
    public static final String USU_SENHA = "password";
    public static final String USU_ENDERECO = "endereco";
    public static final String USU_ESTADO = "estado";
    public static final String USU_CIDADE = "cidade";
    public static final String USU_TELEFONE = "telefone";
    public static final String USU_TIPO = "tipo_usuario";

    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    USU_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USU_NOME + " TEXT NOT NULL, " +
                    USU_EMAIL + " TEXT NOT NULL UNIQUE, " +
                    USU_TELEFONE + " TEXT , " +
                    USU_ENDERECO + " TEXT , " +
                    USU_ESTADO + " TEXT , " +
                    USU_CIDADE + " TEXT , " +
                    USU_TIPO + " TEXT NOT NULL DEFAULT 'prestador' , " +
                    USU_SENHA + " TEXT NOT NULL);";

    // --- Constantes da Tabela EVENTOS ---
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

    private static final String CREATE_TABLE_EVENTS =
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

    // --- Constantes da Tabela CANDIDATOS ---
    public static final String TABLE_CANDIDATOS = "CANDIDATOS";
    public static final String CAN_ID = "can_id";
    // EVE_ID e USU_ID já estão definidos acima
    public static final String CAN_EVE_ID = "eve_id"; // Renomeado para evitar conflito
    public static final String CAN_USU_ID = "usu_id"; // Renomeado para evitar conflito

    private static final String CREATE_TABLE_CANDIDATOS =
            "CREATE TABLE IF NOT EXISTS " + TABLE_CANDIDATOS + " (" +
                    CAN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CAN_EVE_ID + " INTEGER NOT NULL, " +
                    CAN_USU_ID + " INTEGER NOT NULL, " +
                    "UNIQUE(" + CAN_EVE_ID + ", " + CAN_USU_ID + ")" +
                    ");";


    public AppDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_USERS);
            inserirUsuarioPadrao(db); // Insere usuários padrão após a criação da tabela
            db.execSQL(CREATE_TABLE_EVENTS);
            db.execSQL(CREATE_TABLE_CANDIDATOS);
        } catch (Exception e) {
            Log.e("AppDatabaseHelper", "Erro ao criar tabelas: " + e.getMessage());
            throw new RuntimeException("Falha ao criar tabelas", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("AppDatabaseHelper", "Atualizando banco de dados de v. " + oldVersion + " para " + newVersion);

        // Lógica de backup e restauração para USUARIOS
        List<Usuario> usuariosBackup = new ArrayList<>();
        if (tabelaExiste(db, TABLE_USERS)) {
            usuariosBackup = obterUsuariosParaBackup(db);
        }

        // Lógica de backup e restauração para EVENTOS
        List<Evento> eventosBackup = new ArrayList<>();
        if (tabelaExiste(db, TABLE_EVENTS)) {
            eventosBackup = obterEventosParaBackup(db);
        }

        // Lógica de backup e restauração para CANDIDATOS
        List<Candidato> candidatosBackup = new ArrayList<>();
        if (tabelaExiste(db, TABLE_CANDIDATOS)) {
            candidatosBackup = obterCandidatosParaBackup(db);
        }

        // Dropa todas as tabelas existentes
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CANDIDATOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        // Cria as novas tabelas
        onCreate(db);

        // Restaura os dados
        if (!usuariosBackup.isEmpty()) {
            restaurarUsuariosDoBackup(db, usuariosBackup);
        }
        if (!eventosBackup.isEmpty()) {
            restaurarEventosDoBackup(db, eventosBackup);
        }
        if (!candidatosBackup.isEmpty()) {
            restaurarCandidatosDoBackup(db, candidatosBackup);
        }
    }

    private boolean tabelaExiste(SQLiteDatabase db, String nomeTabela) {
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?",
                    new String[]{nomeTabela});
            return cursor != null && cursor.moveToFirst();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    // --- Métodos de backup/restauração para USUARIOS ---
    private List<Usuario> obterUsuariosParaBackup(SQLiteDatabase db) {
        List<Usuario> usuarios = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_USERS, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    long id = cursor.getLong(cursor.getColumnIndexOrThrow(USU_ID));
                    String nome = cursor.getString(cursor.getColumnIndexOrThrow(USU_NOME));
                    String email = cursor.getString(cursor.getColumnIndexOrThrow(USU_EMAIL));
                    String senha = cursor.getString(cursor.getColumnIndexOrThrow(USU_SENHA));
                    String telefone = cursor.getString(cursor.getColumnIndexOrThrow(USU_TELEFONE));
                    String endereco = cursor.getString(cursor.getColumnIndexOrThrow(USU_ENDERECO));
                    String estado = cursor.getString(cursor.getColumnIndexOrThrow(USU_ESTADO));
                    String cidade = cursor.getString(cursor.getColumnIndexOrThrow(USU_CIDADE));
                    String tipoUsuario = cursor.getString(cursor.getColumnIndexOrThrow(USU_TIPO));

                    usuarios.add(new Usuario(id, nome, email, telefone, endereco, estado, cidade, tipoUsuario, senha));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return usuarios;
    }

    private void restaurarUsuariosDoBackup(SQLiteDatabase db, List<Usuario> usuarios) {
        for (Usuario usuario : usuarios) {
            ContentValues values = new ContentValues();
            values.put(USU_NOME, usuario.getNome());
            values.put(USU_EMAIL, usuario.getEmail());
            values.put(USU_TELEFONE, usuario.getTelefone());
            values.put(USU_ENDERECO, usuario.getEndereco());
            values.put(USU_ESTADO, usuario.getEstado());
            values.put(USU_CIDADE, usuario.getCidade());
            values.put(USU_TIPO, usuario.getTipoUsuario());
            values.put(USU_SENHA, usuario.getSenha()); // Certifique-se de restaurar a senha
            db.insert(TABLE_USERS, null, values);
        }
    }

    // --- Métodos de backup/restauração para EVENTOS ---
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
                    int catSeguranca = cursor.getInt(cursor.getColumnIndexOrThrow(EVE_CAT_SEG));
                    int catLimpeza = cursor.getInt(cursor.getColumnIndexOrThrow(EVE_CAT_LIM));
                    int catInfraestrutura = cursor.getInt(cursor.getColumnIndexOrThrow(EVE_CAT_INF));
                    int catOutros = cursor.getInt(cursor.getColumnIndexOrThrow(EVE_CAT_OUT));

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

    // --- Métodos de backup/restauração para CANDIDATOS ---
    private List<Candidato> obterCandidatosParaBackup(SQLiteDatabase db) {
        List<Candidato> candidatos = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_CANDIDATOS, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    long canId = cursor.getLong(cursor.getColumnIndexOrThrow(CAN_ID));
                    long eveId = cursor.getLong(cursor.getColumnIndexOrThrow(CAN_EVE_ID));
                    long usuId = cursor.getLong(cursor.getColumnIndexOrThrow(CAN_USU_ID));
                    candidatos.add(new Candidato(canId, eveId, usuId));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return candidatos;
    }

    private void restaurarCandidatosDoBackup(SQLiteDatabase db, List<Candidato> candidatos) {
        for (Candidato candidato : candidatos) {
            ContentValues values = new ContentValues();
            values.put(CAN_EVE_ID, candidato.getEveId());
            values.put(CAN_USU_ID, candidato.getUsuId());
            db.insert(TABLE_CANDIDATOS, null, values);
        }
    }


    // --- Métodos de USUARIOS ---
    private void inserirUsuarioPadrao(SQLiteDatabase db) {
        String nomePadrao = "Usuário Criador";
        String emailPadrao = "c@gmail.com";
        String senhaPadrao = "111111";
        String tipoPadrao = "criador";

        ContentValues values = new ContentValues();
        values.put(USU_NOME, nomePadrao);
        values.put(USU_EMAIL, emailPadrao);
        values.put(USU_SENHA, senhaPadrao);
        values.put(USU_TIPO, tipoPadrao);
        values.put(USU_TELEFONE, "");
        values.put(USU_ENDERECO, "");
        values.put(USU_ESTADO, "");
        values.put(USU_CIDADE, "");
        db.insert(TABLE_USERS, null, values);

        nomePadrao = "Usuário Prestador";
        emailPadrao = "p@gmail.com";
        senhaPadrao = "222222";
        tipoPadrao = "prestador";

        ContentValues values2 = new ContentValues();
        values2.put(USU_NOME, nomePadrao);
        values2.put(USU_EMAIL, emailPadrao);
        values2.put(USU_SENHA, senhaPadrao);
        values2.put(USU_TIPO, tipoPadrao);
        values2.put(USU_TELEFONE, "");
        values2.put(USU_ENDERECO, "");
        values2.put(USU_ESTADO, "");
        values2.put(USU_CIDADE, "");
        db.insert(TABLE_USERS, null, values2);
    }

    public boolean criarUsuario(String pUSU_NOME, String pUSU_EMAIL, String pUSU_SENHA, String pUSU_TIPO) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USU_NOME, pUSU_NOME);
        values.put(USU_EMAIL, pUSU_EMAIL);
        values.put(USU_SENHA, pUSU_SENHA);
        values.put(USU_TIPO, pUSU_TIPO);
        values.put(USU_TELEFONE, "");
        values.put(USU_ENDERECO, "");
        values.put(USU_ESTADO, "");
        values.put(USU_CIDADE, "");

        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1;
    }

    public boolean atualizaUsuario(long pUSU_ID, String pUSU_NOME, String pUSU_EMAIL, String pUSU_TELEFONE, String pUSU_ENDERECO, String pUSU_ESTADO, String pUSU_CIDADE) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USU_NOME, pUSU_NOME);
        values.put(USU_EMAIL, pUSU_EMAIL);
        values.put(USU_TELEFONE, pUSU_TELEFONE);
        values.put(USU_ENDERECO, pUSU_ENDERECO);
        values.put(USU_ESTADO, pUSU_ESTADO);
        values.put(USU_CIDADE, pUSU_CIDADE);

        String selection = USU_ID + " = ?";
        String[] selectionArgs = { String.valueOf(pUSU_ID) };

        int rowsAffected = db.update(TABLE_USERS, values, selection, selectionArgs);
        db.close();

        return rowsAffected > 0;
    }

    public boolean validaEmailUtilizado(String pUSU_EMAIL) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = { USU_EMAIL };
        String selection = USU_EMAIL + " = ?";
        String[] selectionArgs = { pUSU_EMAIL };

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);

        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0;
    }

    public long realizaLogin(String pUSU_EMAIL, String pUSU_SENHA) {
        SQLiteDatabase db = this.getReadableDatabase();
        long userId = -1;

        String[] columns = { USU_ID, USU_EMAIL, USU_SENHA };
        String selection = USU_EMAIL + " = ?" + " AND " + USU_SENHA + " = ?";
        String[] selectionArgs = { pUSU_EMAIL, pUSU_SENHA };

        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null );

            if (cursor != null && cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(USU_ID);
                if (idIndex != -1) {
                    userId = cursor.getLong(idIndex);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return userId;
    }

    public String retornaNomeUsuario(int pUSU_ID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String userName = "";

        String[] columns = { USU_NOME };
        String selection = USU_ID + " = ?";
        String[] selectionArgs = { String.valueOf(pUSU_ID) };

        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                int nameColumnIndex = cursor.getColumnIndex(USU_NOME);
                if (nameColumnIndex != -1) {
                    userName = cursor.getString(nameColumnIndex);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return userName;
    }

    public Usuario carregaDadosUsuario(long pUSU_ID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        Usuario usuario = null;

        String[] columns = { USU_ID, USU_NOME, USU_EMAIL, USU_TELEFONE, USU_ENDERECO, USU_ESTADO, USU_CIDADE, USU_SENHA, USU_TIPO };
        String selection = USU_ID + " = ?";
        String[] selectionArgs = { String.valueOf(pUSU_ID) };

        try {
            cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(USU_ID));
                String nome = cursor.getString(cursor.getColumnIndexOrThrow(USU_NOME));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(USU_EMAIL));
                String telefone = cursor.getString(cursor.getColumnIndexOrThrow(USU_TELEFONE));
                String endereco = cursor.getString(cursor.getColumnIndexOrThrow(USU_ENDERECO));
                String estado = cursor.getString(cursor.getColumnIndexOrThrow(USU_ESTADO));
                String cidade = cursor.getString(cursor.getColumnIndexOrThrow(USU_CIDADE));
                String senha = cursor.getString(cursor.getColumnIndexOrThrow(USU_SENHA));
                String tipoUsuario = cursor.getString(cursor.getColumnIndexOrThrow(USU_TIPO));

                usuario = new Usuario(id, nome, email, telefone, endereco, estado, cidade, tipoUsuario, senha);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return usuario;
    }

    public boolean validaSenhaAtual(long pUSU_ID, String pUSU_SENHA) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = { USU_ID };
        String selection = USU_ID + " = ?" + " AND " + USU_SENHA + " = ?";
        String[] selectionArgs = { String.valueOf(pUSU_ID) , pUSU_SENHA};

        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(USU_ID);
                if (idIndex != -1) {
                    return true;
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return false;
    }

    public void atualizarSenhaUsuario(long pUSU_ID, String pUSU_SENHA) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USU_SENHA, pUSU_SENHA);

        String selection = USU_ID + " = ?";
        String[] selectionArgs = { String.valueOf(pUSU_ID) };

        db.update(TABLE_USERS, values, selection, selectionArgs);
        db.close();
    }

    public String getTipoUsuario(long pUSU_ID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String tipo = null;
        Cursor cursor = null;
        try {
            String[] columns = { USU_TIPO };
            String selection = USU_ID + " = ?";
            String[] selectionArgs = { String.valueOf(pUSU_ID) };

            cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                tipo = cursor.getString(cursor.getColumnIndexOrThrow(USU_TIPO));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return tipo;
    }

    public List<Usuario> getTop10RecentUsers() {
        List<Usuario> usuarios = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        String query = "SELECT " + USU_ID + ", " + USU_NOME + ", " + USU_EMAIL + ", " + USU_TELEFONE + ", " + USU_ENDERECO + ", " + USU_ESTADO + ", " + USU_CIDADE + ", " + USU_TIPO + ", " + USU_SENHA +
                " FROM " + TABLE_USERS +
                " ORDER BY " + USU_ID + " DESC LIMIT 10";

        try {
            cursor = db.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    long id = cursor.getLong(cursor.getColumnIndexOrThrow(USU_ID));
                    String nome = cursor.getString(cursor.getColumnIndexOrThrow(USU_NOME));
                    String email = cursor.getString(cursor.getColumnIndexOrThrow(USU_EMAIL));
                    String telefone = cursor.getString(cursor.getColumnIndexOrThrow(USU_TELEFONE));
                    String endereco = cursor.getString(cursor.getColumnIndexOrThrow(USU_ENDERECO));
                    String estado = cursor.getString(cursor.getColumnIndexOrThrow(USU_ESTADO));
                    String cidade = cursor.getString(cursor.getColumnIndexOrThrow(USU_CIDADE));
                    String tipoUsuario = cursor.getString(cursor.getColumnIndexOrThrow(USU_TIPO));
                    String senha = cursor.getString(cursor.getColumnIndexOrThrow(USU_SENHA));

                    usuarios.add(new Usuario(id, nome, email, telefone, endereco, estado, cidade, tipoUsuario, senha));
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
        return usuarios;
    }

    // --- Métodos de EVENTOS ---
    public long criarEvento(Evento evento) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
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
            Log.e("AppDatabaseHelper", "Erro ao criar evento: " + e.getMessage());
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
            Log.e("AppDatabaseHelper", "Erro ao atualizar evento: " + e.getMessage());
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
            String selection = EVE_ID + " = ?";
            String[] selectionArgs = { String.valueOf(pEVE_ID) };

            int rowsAffected = db.delete(TABLE_EVENTS, selection, selectionArgs);
            return rowsAffected > 0;
        } catch (Exception e) {
            Log.e("AppDatabaseHelper", "Erro ao excluir evento: " + e.getMessage());
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

    // --- Métodos de CANDIDATOS ---
    public boolean inserirCandidato(long eveId, long usuId) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(CAN_EVE_ID, eveId);
            values.put(CAN_USU_ID, usuId);

            long result = db.insert(TABLE_CANDIDATOS, null, values);
            return result != -1;
        } catch (Exception e) {
            Log.e("AppDatabaseHelper", "Erro ao inserir candidato: " + e.getMessage());
            return false;
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public boolean candidatoJaExiste(long eveId, long usuId) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();
            String query = "SELECT " + CAN_ID + " FROM " + TABLE_CANDIDATOS +
                    " WHERE " + CAN_USU_ID + " = ? AND " + CAN_EVE_ID + " = ?";
            cursor = db.rawQuery(query, new String[]{
                    String.valueOf(usuId),
                    String.valueOf(eveId)
            });

            return cursor != null && cursor.moveToFirst();
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }
    }

    public List<Usuario> getTop10RecentCandidates() {
        List<Usuario> candidates = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT T2." + USU_ID + ", T2." + USU_NOME + ", T2." + USU_EMAIL + ", T2." + USU_TELEFONE + ", T2." + USU_ENDERECO + ", T2." + USU_ESTADO + ", T2." + USU_CIDADE + ", T2." + USU_TIPO +
                ", T2." + USU_SENHA +
                " FROM " + TABLE_CANDIDATOS + " AS T1" +
                " INNER JOIN " + TABLE_USERS + " AS T2 ON T1." + CAN_USU_ID + " = T2." + USU_ID +
                " GROUP BY T2." + USU_ID +
                " ORDER BY T1." + CAN_ID + " DESC LIMIT 10";

        Cursor cursor = null;
        try {
            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    long id = cursor.getLong(cursor.getColumnIndexOrThrow(USU_ID));
                    String nome = cursor.getString(cursor.getColumnIndexOrThrow(USU_NOME));
                    String email = cursor.getString(cursor.getColumnIndexOrThrow(USU_EMAIL));
                    String telefone = cursor.getString(cursor.getColumnIndexOrThrow(USU_TELEFONE));
                    String endereco = cursor.getString(cursor.getColumnIndexOrThrow(USU_ENDERECO));
                    String estado = cursor.getString(cursor.getColumnIndexOrThrow(USU_ESTADO));
                    String cidade = cursor.getString(cursor.getColumnIndexOrThrow(USU_CIDADE));
                    String tipoUsuario = cursor.getString(cursor.getColumnIndexOrThrow(USU_TIPO));
                    String senha = cursor.getString(cursor.getColumnIndexOrThrow(USU_SENHA));

                    candidates.add(new Usuario(id, nome, email, telefone, endereco, estado, cidade, tipoUsuario, senha));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }
        return candidates;
    }

    public List<Evento> getTop10RecentAppliedEventsByPrestador(long prestadorUsuId) {
        List<Evento> events = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT T2." + EVE_ID + ", T2." + EVE_NOME + ", T2." + EVE_DESCRICAO + ", T2." + EVE_DATA_INICIO + ", T2." + EVE_DATA_FIM + ", T2." + EVE_HORA_INICIO + ", T2." + EVE_HORA_FIM + ", T2." + EVE_RUA + ", T2." + EVE_NUMERO + ", T2." + EVE_BAIRRO + ", T2." + EVE_CIDADE + ", T2." + EVE_UF + ", T2." + EVE_CAT_SEG + ", T2." + EVE_CAT_LIM + ", T2." + EVE_CAT_INF + ", T2." + EVE_CAT_OUT +
                " FROM " + TABLE_CANDIDATOS + " AS T1" +
                " INNER JOIN " + TABLE_EVENTS + " AS T2 ON T1." + CAN_EVE_ID + " = T2." + EVE_ID +
                " WHERE T1." + CAN_USU_ID + " = ?" +
                " ORDER BY T1." + CAN_ID + " DESC LIMIT 10";

        Cursor cursor = null;
        try {
            cursor = db.rawQuery(query, new String[]{String.valueOf(prestadorUsuId)});

            if (cursor.moveToFirst()) {
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

                    events.add(new Evento(id, nome, descricao, dataInicio,
                            dataFim, horaInicio, horaFim, rua, numero, bairro, cidade, uf,
                            catSeguranca, catLimpeza, catInfraestrutura, catOutros));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }
        return events;
    }
}