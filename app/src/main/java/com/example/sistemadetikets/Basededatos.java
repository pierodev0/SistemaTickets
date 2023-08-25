package com.example.sistemadetikets;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Basededatos extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SystemTikets.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USERS = "usuarios";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOMBRES = "nombres";
    private static final String COLUMN_APELLIDOS = "apellidos";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NOMBRES + " TEXT,"
            + COLUMN_APELLIDOS + " TEXT,"
            + COLUMN_EMAIL + " TEXT,"
            + COLUMN_PASSWORD + " TEXT"
            + ")";


    //Campos para tipo solicitud
    public static final String TABLE_TIPO_SOLICITUD = "tipo_solicitud";
    public static final String COLUMN_ID_SOLICITUD = "id_solicitud";
    public static final String COLUMN_NOMBRE = "nombre";

    private static final String CREATE_TABLE_TIPO_SOLICITUD = "CREATE TABLE " +
            TABLE_TIPO_SOLICITUD + "(" +
            COLUMN_ID_SOLICITUD + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NOMBRE + " TEXT" +
            ")";
    public Basededatos(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_TIPO_SOLICITUD);
        insertarDatosEjemplo(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIPO_SOLICITUD);
        onCreate(db);
    }

    private void insertarDatosEjemplo(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + TABLE_TIPO_SOLICITUD + " (" + COLUMN_NOMBRE + ") VALUES ('Problemas con el email')");
        db.execSQL("INSERT INTO " + TABLE_TIPO_SOLICITUD + " (" + COLUMN_NOMBRE + ") VALUES ('Problemas con la app')");
        db.execSQL("INSERT INTO " + TABLE_TIPO_SOLICITUD + " (" + COLUMN_NOMBRE + ") VALUES ('Virus en computadora')");
    }

    public void insertUser(String name, String lastName, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRES, name);
        values.put(COLUMN_APELLIDOS, lastName);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);

        // Insert the new row
        long newRowId = db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public boolean checkUserCredentials(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_ID}; // Solo necesitamos el ID para verificar la existencia del usuario
        String selection = COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(
                TABLE_USERS,
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
        String[] projection = {COLUMN_NOMBRES};
        String selection = COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(
                TABLE_USERS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        String userName = "";
        if (cursor.moveToFirst()) {
            userName = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRES));
        }
        cursor.close();
        db.close();

        return userName;
    }

    public String obtenerApellidoUsuarioDesdeBD(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_APELLIDOS};
        String selection = COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(
                TABLE_USERS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        String userLastName = "";
        if (cursor.moveToFirst()) {
            userLastName = cursor.getString(cursor.getColumnIndex(COLUMN_APELLIDOS));
        }
        cursor.close();
        db.close();

        return userLastName;
    }
}
