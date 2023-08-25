package com.example.sistemadetikets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sistemadetikets.entidades.Ticket;
import com.example.sistemadetikets.entidades.TipoSolicitud;

import java.util.ArrayList;

public class ActualizarTicketActivity extends AppCompatActivity {

    TextView txtIdTicket,txtNombrePersona,txtTipoTicket,txtDescripcionTicket,txtEstadoTicket,txtSolucionTicket,txtCorreo;

    Spinner comboSpinner;
    ArrayList<String> listaTipos;
    ArrayList<TipoSolicitud> tipoSolicituds;
    Basededatos conn;

    String userId;
    Ticket ticket = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_ticket);

        conn = new Basededatos(getApplicationContext());

        txtIdTicket = (TextView) findViewById(R.id.txtIdTicket);
        txtNombrePersona = (TextView) findViewById(R.id.txtNombrePersona);
        txtDescripcionTicket = (TextView) findViewById(R.id.txtDescripcionTicket);
        txtEstadoTicket = (TextView) findViewById(R.id.txtEstadoTicket);
        txtSolucionTicket = (TextView) findViewById(R.id.txtSolucionTicket);
        txtCorreo = (TextView) findViewById(R.id.txtCorreo);

        userId = getIntent().getStringExtra("user_id");
        comboSpinner = findViewById(R.id.spinner);

        consultarListaTipos();
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,listaTipos);
        comboSpinner.setAdapter(adapter);

        Bundle objetoEnviado = getIntent().getExtras();
        if(objetoEnviado != null){
            ticket = (Ticket) objetoEnviado.getSerializable("data");

            txtIdTicket.setText(ticket.getId()+"");

        }

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
        if(view.getId() == R.id.btnActualizarTicket){
            actualizarTicket();
        }

    }



    private void actualizarTicket() {
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros = {txtIdTicket.getText().toString()};
        ContentValues values = new ContentValues();

        values.put(Basededatos.COLUMN_DESCRIPCION,txtDescripcionTicket.getText().toString());
        values.put(Basededatos.COLUMN_ESTADO_TICKET,"Abierto");
        values.put(Basededatos.COLUMN_ID_USUARIO,ticket.getId_usuario().toString());

        int idCombo = (int) comboSpinner.getSelectedItemId();

        if(idCombo!= 0){
            int idTipo = tipoSolicituds.get(idCombo - 1).getId();

            String tipoTicket = listaTipos.get(idTipo);
            values.put(Basededatos.COLUMN_TIPO_TICKET,tipoTicket);

            db.update(Basededatos.TABLE_TICKET,values,Basededatos.COLUMN_ID_TICKET+"=?",parametros);

            Intent intent = new Intent(ActualizarTicketActivity.this,Usuario.class);
            startActivity(intent);
            Toast.makeText(this, "Se actualizo el ticket", Toast.LENGTH_SHORT).show();
            db.close();
        }else {
            Toast.makeText(this, "No ha seleccionado el tipo de problema", Toast.LENGTH_SHORT).show();
        }


    }
}