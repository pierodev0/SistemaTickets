package com.example.sistemadetikets;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.sistemadetikets.utilidades.Utilidades;
public class Basededatos extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SystemTikets.db";
    private static final int DATABASE_VERSION = 1;
    public Basededatos(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Utilidades.CREATE_TABLE_USERS);
        db.execSQL(Utilidades.CREATE_TABLE_STATE);
        db.execSQL(Utilidades.CREATE_TABLE_TIPO_SOLICITUD);
        db.execSQL(Utilidades.CREATE_TABLE_TICKET);
        Utilidades.insertarAdminPorDefecto(db);
        Utilidades.insertarEstadosPorDefecto(db);
        Utilidades.insertarDatosEjemplo(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.TABLE_TIPO_SOLICITUD);
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.TABLE_TICKET);
        onCreate(db);
    }

    public void insertUser(String name, String lastName, String email, String password,String razon) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Utilidades.COLUMN_NOMBRES, name);
        values.put(Utilidades.COLUMN_APELLIDOS, lastName);
        values.put(Utilidades.COLUMN_EMAIL, email);
        values.put(Utilidades.COLUMN_PASSWORD, password);
        values.put(Utilidades.COLUMN_RAZON_SOCIAL, razon);

        // Insert the new row
        long newRowId = db.insert(Utilidades.TABLE_USERS, null, values);
        db.close();
    }

    public boolean checkUserCredentials(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {Utilidades.COLUMN_ID}; // Solo necesitamos el ID para verificar la existencia del usuario
        String selection = Utilidades.COLUMN_EMAIL + " = ? AND " + Utilidades.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(
                Utilidades.TABLE_USERS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean userExists = cursor.moveToFirst();
        cursor.close();
        db.close();
        return userExists;
    }



    public String obtenerNombreUsuarioDesdeBD(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {Utilidades.COLUMN_NOMBRES};
        String selection = Utilidades.COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(
                Utilidades.TABLE_USERS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        String userName = "";
        if (cursor.moveToFirst()) {
            userName = cursor.getString(cursor.getColumnIndex(Utilidades.COLUMN_NOMBRES));
        }
        cursor.close();
        db.close();

        return userName;
    }

    public String obtenerApellidoUsuarioDesdeBD(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {Utilidades.COLUMN_APELLIDOS};
        String selection = Utilidades.COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(
                Utilidades.TABLE_USERS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        String userLastName = "";
        if (cursor.moveToFirst()) {
            userLastName = cursor.getString(cursor.getColumnIndex(Utilidades.COLUMN_APELLIDOS));
        }
        cursor.close();
        db.close();

        return userLastName;
    }

    public String obtenerIdUsuario(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {Utilidades.COLUMN_ID};
        String selection = Utilidades.COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(
                Utilidades.TABLE_USERS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        String userName = "";
        if (cursor.moveToFirst()) {
            userName = cursor.getString(cursor.getColumnIndex(Utilidades.COLUMN_ID));
        }
        cursor.close();
        db.close();

        return userName;
    }

    public String obtenerRolUsuarioDesdeBD(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {Utilidades.COLUMN_ROLE};
        String selection = Utilidades.COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(
                Utilidades.TABLE_USERS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        String userRole = "";
        if (cursor.moveToFirst()) {
            userRole = cursor.getString(cursor.getColumnIndex(Utilidades.COLUMN_ROLE));
        }
        cursor.close();
        db.close();

        return userRole;
    }
}
