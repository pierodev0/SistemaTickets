package com.example.sistemadetikets.UsuarioActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sistemadetikets.Basededatos;
import com.example.sistemadetikets.R;
import com.example.sistemadetikets.entidades.TipoSolicitud;
import com.example.sistemadetikets.utilidades.Utilidades;

import java.util.ArrayList;

public class CrearTicketActivity extends AppCompatActivity {

    Spinner comboSpinner;
    EditText txtDescripcion;
    Basededatos conn;

    ArrayList<String> listaTipos;
    ArrayList<TipoSolicitud> tipoSolicituds;
    String userId;
    String userName;
    String userLastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_ticket);

        conn = new Basededatos(getApplicationContext());

        comboSpinner = findViewById(R.id.spinner);
        txtDescripcion = findViewById(R.id.txtDescripcion);

        userName = getIntent().getStringExtra("user_name");
        userLastName = getIntent().getStringExtra("user_last_name");
        userId = getIntent().getStringExtra("user_id");


        consultarListaTipos();
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,listaTipos);
        comboSpinner.setAdapter(adapter);


    }

    private void consultarListaTipos() {
        SQLiteDatabase db = conn.getReadableDatabase();

        TipoSolicitud solicitud = null;
        tipoSolicituds = new ArrayList<TipoSolicitud>();

        //SELECT * FROM tipo_solicitud
        Cursor cursor = db.rawQuery("SELECT * FROM tipo_solicitud",null);
        while (cursor.moveToNext()){
            solicitud = new TipoSolicitud();
            solicitud.setId(cursor.getInt(0));
            solicitud.setNombre(cursor.getString(1));

            tipoSolicituds.add(solicitud);

        }

        obtenerLista();
    }

    private void obtenerLista() {
        listaTipos = new ArrayList<String>();
        listaTipos.add("Seleccione");
        for (int i = 0; i <tipoSolicituds.size();i++){
            listaTipos.add(tipoSolicituds.get(i).getNombre());
        }
    }

    public void onClick(View view) {
        crearTicket();
    }

    private void crearTicket() {

        if(TextUtils.isEmpty(txtDescripcion.getText().toString())){
            txtDescripcion.setError("La Descripcion es obligatoria");
            return;
        }


        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Utilidades.COLUMN_ID_USUARIO,userId);
        values.put(Utilidades.COLUMN_DESCRIPCION,txtDescripcion.getText().toString());
        values.put(Utilidades.COLUMN_TICKET_ID_STATE,1);
        // values.put(Utilidades.COLUMN_NAME_STATE,"Abierto");


        int idCombo = (int) comboSpinner.getSelectedItemId();

        if(idCombo!= 0){
            int idTipo = tipoSolicituds.get(idCombo - 1).getId();

            values.put(Utilidades.COLUMN_TICKET_ID_SOLICITUD,idTipo);

            Long idResultante = db.insert(Utilidades.TABLE_TICKET,Utilidades.COLUMN_ID_TICKET,values);
            db.close();

            Intent intent = new Intent(CrearTicketActivity.this, Usuario.class);
            intent.putExtra("user_name", userName);
            intent.putExtra("user_last_name", userLastName);
            intent.putExtra("user_id", userId);
            startActivity(intent);
            Toast.makeText(this, "Ticket creado", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "No ha seleccionado el tipo de problema", Toast.LENGTH_SHORT).show();
        }



    }
}