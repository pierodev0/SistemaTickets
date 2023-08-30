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

    // ----------------------tabla usuario
    private static final String TABLE_USERS = "usuarios";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOMBRES = "nombres";
    private static final String COLUMN_APELLIDOS = "apellidos";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_ROLE = "rol";


    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NOMBRES + " TEXT,"
            + COLUMN_APELLIDOS + " TEXT,"
            + COLUMN_EMAIL + " TEXT,"
            + COLUMN_PASSWORD + " TEXT,"
            + COLUMN_ROLE + " TEXT DEFAULT 'usuario'"
            + ")";

    // ----------------------tabla estado
    public static final String TABLE_STATE = "estado";
    public static final String COLUMN_ID_STATE = "id_estado";
    public static final String COLUMN_NAME_STATE = "nombre_estado";

    private static final String CREATE_TABLE_STATE = "CREATE TABLE " +
            TABLE_STATE + "(" +
            COLUMN_ID_STATE + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NAME_STATE + " TEXT" +
            ")";

    //Campos para tipo solicitud ticket
    public static final String TABLE_TIPO_SOLICITUD = "tipo_solicitud";
    public static final String COLUMN_ID_SOLICITUD = "id_solicitud";
    public static final String COLUMN_NOMBRE = "nombre";

    private static final String CREATE_TABLE_TIPO_SOLICITUD = "CREATE TABLE " +
            TABLE_TIPO_SOLICITUD + "(" +
            COLUMN_ID_SOLICITUD + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NOMBRE + " TEXT" +
            ")";


    // ----------------------tabla ticket
    public static final String TABLE_TICKET = "ticket";

    public static final String COLUMN_ID_TICKET = "id_ticket";
    public static final String COLUMN_ID_USUARIO = "id_usuario";
    public static final String COLUMN_FECHA_CREACION = "fecha_creacion";
    public static final String COLUMN_TICKET_ID_STATE = "id_estado";

    public static final String COLUMN_TICKET_ID_SOLICITUD = "id_solicitud"; // id_solicitud
    public static final String COLUMN_DESCRIPCION = "descripcion";
    public static final String COLUMN_SOLUTION = "solucion";

    public static final String CREATE_TABLE_TICKET = "CREATE TABLE " +
            TABLE_TICKET + "(" +
            COLUMN_ID_TICKET + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_ID_USUARIO + " INTEGER," +
            COLUMN_FECHA_CREACION + " DATETIME DEFAULT CURRENT_TIMESTAMP," +
            COLUMN_TICKET_ID_STATE + " INTEGER DEFAULT 1," +
            COLUMN_TICKET_ID_SOLICITUD + " INTEGER," +
            COLUMN_DESCRIPCION + " TEXT," +
            COLUMN_SOLUTION + " TEXT DEFAULT 'En espera de solución'" +
            ")";


    public Basededatos(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_STATE);
        db.execSQL(CREATE_TABLE_TIPO_SOLICITUD);
        db.execSQL(CREATE_TABLE_TICKET);
        insertarAdminPorDefecto(db);
        insertarEstadosPorDefecto(db);
        insertarDatosEjemplo(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIPO_SOLICITUD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TICKET);
        onCreate(db);
    }

    // Insertando usuario con rol administrador
    private void insertarAdminPorDefecto(SQLiteDatabase db){
        ContentValues values = new ContentValues();

        values.put(COLUMN_NOMBRES, "Piero");
        values.put(COLUMN_APELLIDOS, "Bayona");
        values.put(COLUMN_EMAIL, "admin@gmail.com");
        values.put(COLUMN_PASSWORD, "admin");
        values.put(COLUMN_ROLE, "admin");
        db.insert(TABLE_USERS, null, values);
    }

    // Insertando estados para los ticket
    private void insertarEstadosPorDefecto(SQLiteDatabase db){
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME_STATE, "Abierto");
        db.insert(TABLE_STATE, null, values);

        values.put(COLUMN_NAME_STATE, "En proceso");
        db.insert(TABLE_STATE, null, values);

        values.put(COLUMN_NAME_STATE, "Cerrado");
        db.insert(TABLE_STATE, null, values);
    }

    private void insertarDatosEjemplo(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + TABLE_TIPO_SOLICITUD + " (" + COLUMN_NOMBRE + ") VALUES ('Problemas con el email')");
        db.execSQL("INSERT INTO " + TABLE_TIPO_SOLICITUD + " (" + COLUMN_NOMBRE + ") VALUES ('Problemas con la app')");
        db.execSQL("INSERT INTO " + TABLE_TIPO_SOLICITUD + " (" + COLUMN_NOMBRE + ") VALUES ('Virus en computadora')");

        ContentValues values = new ContentValues();

        // Ticket de ejemplo 1
        values.put(COLUMN_ID_USUARIO, 2); // Reemplaza 1 con el ID del usuario real
        values.put(COLUMN_DESCRIPCION, "Problemas con el correo electrónico desde el domingo. NO puedo entrar y me voy quedar sin trabajo");
        values.put(COLUMN_TICKET_ID_SOLICITUD,1);
        values.put(COLUMN_TICKET_ID_STATE,1);
        db.insert(TABLE_TICKET, null, values);

        // Ticket de ejemplo 2
        values.clear();
        values.put(COLUMN_ID_USUARIO, 2); // Reemplaza 2 con el ID del usuario real
        values.put(COLUMN_DESCRIPCION, "El codigo no me compila le he preguntado a Chat GPT que pasa pero no me ayuda. AAAAAAAAAAAAA");
        values.put(COLUMN_TICKET_ID_SOLICITUD,2);
        values.put(COLUMN_TICKET_ID_STATE,1);
        db.insert(TABLE_TICKET, null, values);

        // Ticket de ejemplo 3
        values.clear();
        values.put(COLUMN_ID_USUARIO, 2); // Reemplaza 3 con el ID del usuario real
        values.put(COLUMN_DESCRIPCION, "Desde ayer no puedo entrar a mi pc debido a que me dice que le de dinero por bitcoin no se que hacer");
        values.put(COLUMN_TICKET_ID_SOLICITUD,3);
        values.put(COLUMN_TICKET_ID_STATE,1);
        db.insert(TABLE_TICKET, null, values);

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

    public String obtenerIdUsuario(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_ID};
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
            userName = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
        }
        cursor.close();
        db.close();

        return userName;
    }

    public String obtenerRolUsuarioDesdeBD(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_ROLE};
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

        String userRole = "";
        if (cursor.moveToFirst()) {
            userRole = cursor.getString(cursor.getColumnIndex(COLUMN_ROLE));
        }
        cursor.close();
        db.close();

        return userRole;
    }
}
