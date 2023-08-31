package com.example.sistemadetikets.utilidades;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class Utilidades {

    public static String USER_ID;

    // ----------------------tabla usuario
    public static final String TABLE_USERS = "usuarios";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOMBRES = "nombres";
    public static final String COLUMN_APELLIDOS = "apellidos";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_ROLE = "rol";
    public static final String COLUMN_RAZON_SOCIAL = "razon_social";


    public static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NOMBRES + " TEXT,"
            + COLUMN_APELLIDOS + " TEXT,"
            + COLUMN_EMAIL + " TEXT,"
            + COLUMN_PASSWORD + " TEXT,"
            + COLUMN_ROLE + " TEXT DEFAULT 'usuario',"
            + COLUMN_RAZON_SOCIAL + " TEXT NULL"
            + ")";

    // ----------------------tabla estado
    public static final String TABLE_STATE = "estado";
    public static final String COLUMN_ID_STATE = "id_estado";
    public static final String COLUMN_NAME_STATE = "nombre_estado";

    public static final String CREATE_TABLE_STATE = "CREATE TABLE " +
            TABLE_STATE + "(" +
            COLUMN_ID_STATE + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NAME_STATE + " TEXT" +
            ")";

    //Campos para tipo solicitud ticket
    public static final String TABLE_TIPO_SOLICITUD = "tipo_solicitud";
    public static final String COLUMN_ID_SOLICITUD = "id_solicitud";
    public static final String COLUMN_NOMBRE = "nombre";

    public static final String CREATE_TABLE_TIPO_SOLICITUD = "CREATE TABLE " +
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


    // Insertando usuario con rol administrador
    public static void insertarAdminPorDefecto(SQLiteDatabase db){
        ContentValues values = new ContentValues();

        values.put(COLUMN_NOMBRES, "Admin");
        values.put(COLUMN_APELLIDOS, "");
        values.put(COLUMN_EMAIL, "admin");
        values.put(COLUMN_PASSWORD, "admin");
        values.put(COLUMN_ROLE, "admin");
        db.insert(TABLE_USERS, null, values);

        values.clear();
        values.put(COLUMN_NOMBRES, "jose");
        values.put(COLUMN_APELLIDOS, "vega");
        values.put(COLUMN_EMAIL, "user1");
        values.put(COLUMN_PASSWORD, "123");
        values.put(COLUMN_ROLE, "usuario");
        values.put(COLUMN_RAZON_SOCIAL, "PANADERIA JOSE S.A.C");
        db.insert(TABLE_USERS, null, values);

        values.clear();
        values.put(COLUMN_NOMBRES, "maria");
        values.put(COLUMN_APELLIDOS, "josefina");
        values.put(COLUMN_EMAIL, "user2");
        values.put(COLUMN_PASSWORD, "123");
        values.put(COLUMN_ROLE, "usuario");
        values.put(COLUMN_RAZON_SOCIAL, "PASTELERIA JOSEFINA S.A.C");
        db.insert(TABLE_USERS, null, values);
    }

    public static void insertarEstadosPorDefecto(SQLiteDatabase db){
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME_STATE, "Abierto");
        db.insert(TABLE_STATE, null, values);

        values.put(COLUMN_NAME_STATE, "En proceso");
        db.insert(TABLE_STATE, null, values);

        values.put(COLUMN_NAME_STATE, "Cerrado");
        db.insert(TABLE_STATE, null, values);
    }

    public static void insertarDatosEjemplo(SQLiteDatabase db) {
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

        // Ticket de ejemplo 1
        values.put(COLUMN_ID_USUARIO, 2); // Reemplaza 1 con el ID del usuario real
        values.put(COLUMN_DESCRIPCION, "Problemas con SENATI. NO puedo entrar y me voy quedar sin trabajo");
        values.put(COLUMN_TICKET_ID_SOLICITUD,1);
        values.put(COLUMN_TICKET_ID_STATE,1);
        db.insert(TABLE_TICKET, null, values);

        // Ticket de ejemplo 2
        values.clear();
        values.put(COLUMN_ID_USUARIO, 3); // Reemplaza 2 con el ID del usuario real
        values.put(COLUMN_DESCRIPCION, "El codigo no me compila le he preguntado a Chat GPT que pasa pero no me ayuda. AAAAAAAAAAAAA");
        values.put(COLUMN_TICKET_ID_SOLICITUD,2);
        values.put(COLUMN_TICKET_ID_STATE,1);
        db.insert(TABLE_TICKET, null, values);

        // Ticket de ejemplo 3
        values.clear();
        values.put(COLUMN_ID_USUARIO, 3); // Reemplaza 3 con el ID del usuario real
        values.put(COLUMN_DESCRIPCION, "Desde ayer no puedo entrar a mi pc debido a que me dice que le de dinero por bitcoin no se que hacer");
        values.put(COLUMN_TICKET_ID_SOLICITUD,3);
        values.put(COLUMN_TICKET_ID_STATE,1);
        db.insert(TABLE_TICKET, null, values);

    }
}
