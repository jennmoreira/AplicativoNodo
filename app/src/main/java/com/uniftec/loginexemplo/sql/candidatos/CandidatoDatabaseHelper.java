package com.uniftec.loginexemplo.sql.candidatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CandidatoDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "apkProjeto.db";
    private static final int DATABASE_VERSION = 110;

    public static final String TABLE_CANDIDATOS = "CANDIDATOS";
    public static final String CAN_ID = "can_id";
    public static final String EVE_ID = "eve_id";
    public static final String USU_ID = "usu_id";

    private static final String TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_CANDIDATOS + " (" +
                    CAN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    EVE_ID + " INTEGER NOT NULL, " +
                    USU_ID + " INTEGER NOT NULL, " +
                    "UNIQUE(" + EVE_ID + ", " + USU_ID + ")" +
                    ");";

    public CandidatoDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(TABLE_CREATE);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao criar tabela CANDIDATOS", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            if (tabelaExiste(db, TABLE_CANDIDATOS)) {
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_CANDIDATOS);
            }
            onCreate(db);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar banco CANDIDATOS", e);
        }
    }

    private boolean tabelaExiste(SQLiteDatabase db, String nomeTabela) {
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(
                    "SELECT name FROM sqlite_master WHERE type='table' AND name=?",
                    new String[]{nomeTabela});
            return cursor != null && cursor.moveToFirst();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public boolean inserirCandidato(long eveId, long usuId) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            if (!tabelaExiste(db, TABLE_CANDIDATOS)) {
                onCreate(db);
            }

            ContentValues values = new ContentValues();
            values.put(EVE_ID, eveId);
            values.put(USU_ID, usuId);

            long result = db.insert(TABLE_CANDIDATOS, null, values);
            return result != -1;
        } catch (Exception e) {
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
            if (!tabelaExiste(db, TABLE_CANDIDATOS)) {
                return false;
            }

            String query = "SELECT " + CAN_ID + " FROM " + TABLE_CANDIDATOS +
                    " WHERE " + USU_ID + " = ? AND " + EVE_ID + " = ?";
            cursor = db.rawQuery(query, new String[]{
                    String.valueOf(usuId),
                    String.valueOf(eveId)
            });

            return cursor != null && cursor.moveToFirst();
        } catch (Exception e) {
            return false;
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }
    }
}
