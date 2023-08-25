package com.example.sistemadetikets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sistemadetikets.entidades.Ticket;
import com.example.sistemadetikets.entidades.TicketLayout;

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
            txtTipoTicket.setText(ticket.getTipoTicket()+"");
            txtDescripcionTicket.setText(ticket.getDescripcion()+"");
            txtEstadoTicket.setText(ticket.getEstadoTicket()+"");
            consultarUsuario(ticket.getId_usuario());
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

    public void onClick(View view) {
        Intent miIntent = null;
        if(view.getId() == R.id.btnOpcionActualizarTicket){
            miIntent = new Intent(DetalleTicketActivity.this,ActualizarTicketActivity.class);
            miIntent.putExtra("data",ticket);
            miIntent.putExtra("user_id", userId);

        }
        if(miIntent != null){
            startActivity(miIntent);
        }
    }
}