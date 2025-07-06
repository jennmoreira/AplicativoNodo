package com.uniftec.loginexemplo.sql.usuarios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UsuariosDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "apkProjeto.db";
    private static final int DATABASE_VERSION = 91;
    public static final String TABLE_USERS = "USUARIOS";
    public static final String USU_ID = "id";
    public static final String USU_NOME = "name";
    public static final String USU_EMAIL = "email";
    public static final String USU_SENHA = "password";
    public static final String USU_ENDERECO = "endereco";
    public static final String USU_ESTADO = "estado";
    public static final String USU_CIDADE = "cidade";
    public static final String USU_TELEFONE = "telefone";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    USU_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USU_NOME + " TEXT NOT NULL, " +
                    USU_EMAIL + " TEXT NOT NULL UNIQUE, " +
                    USU_TELEFONE + " TEXT , " +
                    USU_ENDERECO + " TEXT , " +
                    USU_ESTADO + " TEXT , " +
                    USU_CIDADE + " TEXT , " +
                    USU_SENHA + " TEXT NOT NULL);";

    public UsuariosDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        inserirUsuarioPadrao(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    private void inserirUsuarioPadrao(SQLiteDatabase db) {
        String nomePadrao = "Usuário Teste";
        String emailPadrao = "a@gmail.com";
        String senhaPadrao = "123456";

        ContentValues values = new ContentValues();
        values.put(USU_NOME, nomePadrao);
        values.put(USU_EMAIL, emailPadrao);
        values.put(USU_SENHA, senhaPadrao);

        db.insert(TABLE_USERS, null, values);
    }

    public boolean criarUsuario(String pUSU_NOME, String pUSU_EMAIL, String pUSU_SENHA) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USU_NOME, pUSU_NOME);
        values.put(USU_EMAIL, pUSU_EMAIL);
        values.put(USU_SENHA, pUSU_SENHA);

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

        String[] columns = { USU_ID, USU_NOME, USU_EMAIL, USU_TELEFONE, USU_ENDERECO, USU_ESTADO, USU_CIDADE };
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

                usuario = new Usuario(id, nome, email, telefone, endereco, estado, cidade);
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
                int nameColumnIndex = cursor.getColumnIndex(USU_ID);
                if (nameColumnIndex != -1) {
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
}
