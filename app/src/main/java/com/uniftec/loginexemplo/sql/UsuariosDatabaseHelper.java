package com.uniftec.loginexemplo.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UsuariosDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "apkProjeto.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_USERS = "USUARIOS";
    public static final String USU_ID = "id";
    public static final String USU_NOME = "name";
    public static final String USU_EMAIL = "email";
    public static final String USU_SENHA = "password";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    USU_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USU_NOME + " TEXT NOT NULL, " +
                    USU_EMAIL + " TEXT NOT NULL, " +
                    USU_SENHA + " TEXT NOT NULL);";

    public UsuariosDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public boolean criarUsuario(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USU_NOME, name);
        values.put(USU_EMAIL, email);
        values.put(USU_SENHA, password);

        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1;
    }

    public boolean validaEmailUtilizado(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = { USU_EMAIL };
        String selection = USU_EMAIL + " = ?";
        String[] selectionArgs = { email };

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);

        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0;
    }

    public long realizaLogin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        long userId = -1;

        String[] columns = { USU_ID, USU_EMAIL, USU_SENHA };
        String selection = USU_EMAIL + " = ?" + " AND " + USU_SENHA + " = ?";
        String[] selectionArgs = { email, password };

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
}
