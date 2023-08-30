package com.example.sistemadetikets.UsuarioActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sistemadetikets.Basededatos;
import com.example.sistemadetikets.R;
import com.example.sistemadetikets.entidades.Ticket;
import com.example.sistemadetikets.entidades.TipoSolicitud;
import com.example.sistemadetikets.utilidades.Utilidades;

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
            txtEstadoTicket.setText(mostrarEstado(ticket.getId_estado()));
            txtSolucionTicket.setText(ticket.getSolucion());
            txtIdTicket.setText(ticket.getId()+"");
            consultarUsuario(ticket.getId_usuario());

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

    private String mostrarEstado(int estado){
        String respuesta = "";
        if(estado == 1){
            respuesta = "Abierto";
        } else if (estado==2) {
            respuesta="En proceso";
        } else if (estado==3) {
            respuesta="Cerrado";
        }
        return respuesta;
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

        values.put(Utilidades.COLUMN_DESCRIPCION,txtDescripcionTicket.getText().toString());
        values.put(Utilidades.COLUMN_TICKET_ID_STATE,1);
        values.put(Utilidades.COLUMN_ID_USUARIO,ticket.getId_usuario().toString());

        int idCombo = (int) comboSpinner.getSelectedItemId();

        if(idCombo!= 0){
            int idTipo = tipoSolicituds.get(idCombo - 1).getId();
            values.put(Utilidades.COLUMN_TICKET_ID_SOLICITUD,idTipo);

            db.update(Utilidades.TABLE_TICKET,values,Utilidades.COLUMN_ID_TICKET+"=?",parametros);

            Intent intent = new Intent(ActualizarTicketActivity.this, Usuario.class);
            startActivity(intent);
            Toast.makeText(this, "Se actualizo el ticket", Toast.LENGTH_SHORT).show();
            db.close();
        }else {
            Toast.makeText(this, "No ha seleccionado el tipo de problema", Toast.LENGTH_SHORT).show();
        }


    }

    private void consultarUsuario(Integer idUsuario) {
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros = {idUsuario.toString()};
        String[] campos ={"nombres","apellidos","email"};

        try {
            Cursor cursor = db.query("usuarios",campos,"id"+"=?",parametros,null,null,null);
            cursor.moveToFirst();
            txtNombrePersona.setText(cursor.getString(0)+" "+cursor.getString(1));
            txtCorreo.setText(cursor.getString(2));
            cursor.close();
        } catch (Exception e){
            Toast.makeText(this, "El registro no existe", Toast.LENGTH_SHORT).show();
            //campoNombrePersona.setText("");
            //campoTelefonoPersona.setText("");
        }
    }
}