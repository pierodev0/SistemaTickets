package com.example.sistemadetikets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sistemadetikets.entidades.TipoSolicitud;

import java.util.ArrayList;

public class CrearTicketActivity extends AppCompatActivity {

    Spinner comboSpinner;
    EditText txtDescripcion;
    Basededatos conn;

    ArrayList<String> listaTipos;
    ArrayList<TipoSolicitud> tipoSolicituds;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_ticket);

        conn = new Basededatos(getApplicationContext());

        comboSpinner = findViewById(R.id.spinner);
        txtDescripcion = findViewById(R.id.txtDescripcion);

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

        values.put(Basededatos.COLUMN_DESCRIPCION,txtDescripcion.getText().toString());
        values.put(Basededatos.COLUMN_NAME_STATE,"Abierto");
        values.put(Basededatos.COLUMN_ID_USUARIO,userId);

        int idCombo = (int) comboSpinner.getSelectedItemId();

        if(idCombo!= 0){
            int idTipo = tipoSolicituds.get(idCombo - 1).getId();

            String tipoTicket = listaTipos.get(idTipo);
            values.put(Basededatos.COLUMN_TICKET_ID_SOLICITUD,tipoTicket);

            Long idResultante = db.insert(Basededatos.TABLE_TICKET,Basededatos.COLUMN_ID_TICKET,values);
            db.close();
            Intent intent = new Intent(CrearTicketActivity.this,Usuario.class);
            startActivity(intent);
            Toast.makeText(this, "Ticket creado", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No ha seleccionado el tipo de problema", Toast.LENGTH_SHORT).show();
        }



    }
}