package com.example.sistemadetikets.adminActivity;

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

import com.example.sistemadetikets.Basededatos;
import com.example.sistemadetikets.R;
import com.example.sistemadetikets.UsuarioActivity.ActualizarTicketActivity;
import com.example.sistemadetikets.UsuarioActivity.Usuario;
import com.example.sistemadetikets.entidades.Estado;
import com.example.sistemadetikets.entidades.Ticket;
import com.example.sistemadetikets.entidades.TipoSolicitud;
import com.example.sistemadetikets.utilidades.Utilidades;

import java.util.ArrayList;

public class ActualizarDescripcionTicketActivity extends AppCompatActivity {

    EditText txtSolucion;

    Spinner comboSpinner;
    ArrayList<String> listaTipos;
    ArrayList<Estado> tipoEstado;
    Basededatos conn;

    String userId;
    String userName;
    String userLastName;

    Ticket ticket = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_descripcion_ticket);

        conn = new Basededatos(getApplicationContext());

        comboSpinner = findViewById(R.id.spinner);
        txtSolucion = (EditText) findViewById(R.id.txtSolucion);

        consultarListaTipos();
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,listaTipos);
        comboSpinner.setAdapter(adapter);


        Bundle objetoEnviado = getIntent().getExtras();
        if(objetoEnviado != null){
            ticket = (Ticket) objetoEnviado.getSerializable("data");

        }


    }
    private void consultarListaTipos() {
        SQLiteDatabase db = conn.getReadableDatabase();

        Estado estado = null;
        tipoEstado = new ArrayList<Estado>();

        //SELECT * FROM tipo_solicitud
        Cursor cursor = db.rawQuery("SELECT * FROM estado",null);
        while (cursor.moveToNext()){
            estado = new Estado();
            estado.setId_estado(cursor.getInt(0));
            estado.setNombre_estado(cursor.getString(1));

            tipoEstado.add(estado);

        }

        obtenerLista();
    }

    private void obtenerLista() {
        listaTipos = new ArrayList<String>();
        for (int i = 0; i <tipoEstado.size();i++){
            listaTipos.add(tipoEstado.get(i).getNombre_estado());
        }
    }

    private void actualizarTicket() {
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros = {ticket.getId()+""};


        int idCombo = (int) comboSpinner.getSelectedItemId();



            int idEstado = tipoEstado.get(idCombo).getId_estado();

            ContentValues values = new ContentValues();

            values.put(Utilidades.COLUMN_DESCRIPCION,ticket.getDescripcion());
            values.put(Utilidades.COLUMN_TICKET_ID_STATE,idEstado);
            values.put(Utilidades.COLUMN_ID_USUARIO,ticket.getId_usuario().toString());
            values.put(Utilidades.COLUMN_TICKET_ID_SOLICITUD,ticket.getId_solicitud());
            values.put(Utilidades.COLUMN_SOLUTION,txtSolucion.getText().toString());

            db.update(Utilidades.TABLE_TICKET,values,Utilidades.COLUMN_ID_TICKET+"=?",parametros);

            Intent intent = new Intent(getApplicationContext(), ContactosAdminActivity.class);
            intent.putExtra("user_name", userName);
            intent.putExtra("user_last_name", userLastName);
            intent.putExtra("user_id", userId);
            startActivity(intent);
            Toast.makeText(this, "Se actualizo el ticket", Toast.LENGTH_SHORT).show();
            db.close();
        }







    public void onClick(View view) {
            if(view.getId() == R.id.btnActualizarTicket){
                actualizarTicket();
            }
    }
}