package com.example.sistemadetikets.UsuarioActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sistemadetikets.Basededatos;
import com.example.sistemadetikets.R;
import com.example.sistemadetikets.entidades.Ticket;
import com.example.sistemadetikets.entidades.TicketLayout;
import com.example.sistemadetikets.utilidades.Utilidades;

public class DetalleTicketActivity extends AppCompatActivity {
    Basededatos conn;

    TextView txtIdTicket,txtNombrePersona,txtTipoTicket,txtDescripcionTicket,txtEstadoTicket,txtSolucionTicket;

    TextView txtCorreo;

    private TicketLayout item;
    String userId;
    Ticket ticket = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_ticket);

        conn = new Basededatos(getApplicationContext());

        txtIdTicket = (TextView) findViewById(R.id.txtIdTicket);
        txtNombrePersona = (TextView) findViewById(R.id.txtNombrePersona);
        txtTipoTicket = (TextView) findViewById(R.id.txtTipoTicket);
        txtDescripcionTicket = (TextView) findViewById(R.id.txtDescripcionTicket);
        txtEstadoTicket = (TextView) findViewById(R.id.txtEstadoTicket);
        txtSolucionTicket = (TextView) findViewById(R.id.txtSolucionTicket);
        txtCorreo = (TextView) findViewById(R.id.txtCorreo);

        userId = getIntent().getStringExtra("user_id");

        //item = (Ticket) getIntent().getSerializableExtra("data");

        Bundle objetoEnviado = getIntent().getExtras();


        if(objetoEnviado != null){
            ticket = (Ticket) objetoEnviado.getSerializable("data");

            txtIdTicket.setText(ticket.getId()+"");
            txtTipoTicket.setText(consultarTipoSolicitud(ticket.getId_solicitud()));
            txtDescripcionTicket.setText(ticket.getDescripcion()+"");
            txtEstadoTicket.setText(mostrarEstado(ticket.getId_estado()));
            txtSolucionTicket.setText(ticket.getSolucion());
            consultarUsuario(ticket.getId_usuario());
        }

    }

    private String consultarTipoSolicitud(int id_solicitud){
        SQLiteDatabase db = conn.getReadableDatabase();
        String resultadoTipoSolicitud=null;
        // SELECT * from tipo_solicitud WHERE id_solicitud = ?
        Cursor cursor = db.rawQuery("SELECT * FROM tipo_solicitud WHERE id_solicitud=?", new String[]{String.valueOf(id_solicitud)});
        while (cursor.moveToNext()){
            resultadoTipoSolicitud = cursor.getString(1);
        }
        return resultadoTipoSolicitud;
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

    public void onClick(View view) {
        Intent miIntent = null;
        if(view.getId() == R.id.btnOpcionActualizarTicket){
            miIntent = new Intent(DetalleTicketActivity.this, ActualizarTicketActivity.class);
            miIntent.putExtra("data",ticket);
            miIntent.putExtra("user_id", userId);

        }
        if(view.getId() == R.id.btnEliminarTicket){
            eliminarTicket();
        }
        if(miIntent != null){
            startActivity(miIntent);
        }
    }

    private void eliminarTicket() {
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros = {txtIdTicket.getText().toString()};

        db.delete(Utilidades.TABLE_TICKET,Utilidades.COLUMN_ID_TICKET+"=?",parametros);
        db.close();
        Intent intent = new Intent(DetalleTicketActivity.this, Usuario.class);
        startActivity(intent);
        Toast.makeText(this, "Se elimino el ticket", Toast.LENGTH_SHORT).show();
    }
}