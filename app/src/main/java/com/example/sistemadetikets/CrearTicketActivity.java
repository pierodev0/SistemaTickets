package com.example.sistemadetikets;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sistemadetikets.entidades.TipoSolicitud;

import java.util.ArrayList;

public class CrearTicketActivity extends AppCompatActivity {

    Spinner comboSpinner;
    Basededatos conn;

    ArrayList<String> listaTipos;
    ArrayList<TipoSolicitud> tipoSolicituds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_ticket);

        conn = new Basededatos(getApplicationContext());

        comboSpinner = findViewById(R.id.spinner);


        consultarListaPersonas();
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,listaTipos);
        comboSpinner.setAdapter(adapter);


    }

    private void consultarListaPersonas() {
        SQLiteDatabase db = conn.getReadableDatabase();

        TipoSolicitud solicitud = null;
        tipoSolicituds = new ArrayList<TipoSolicitud>();

        //SELECT * FROM USUARIOS
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
        int idCombo = (int) comboSpinner.getSelectedItemId();
        if(idCombo!= 0){
            int idTipo = tipoSolicituds.get(idCombo - 1).getId();
            String nombreTicket = listaTipos.get(idTipo);
            Toast.makeText(this, nombreTicket, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No Seleccionado", Toast.LENGTH_SHORT).show();
        }

    }
}