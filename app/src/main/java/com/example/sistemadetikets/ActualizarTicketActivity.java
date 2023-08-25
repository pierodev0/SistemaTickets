package com.example.sistemadetikets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sistemadetikets.entidades.Ticket;

public class ActualizarTicketActivity extends AppCompatActivity {

    TextView txtIdTicket,txtNombrePersona,txtTipoTicket,txtDescripcionTicket,txtEstadoTicket,txtSolucionTicket,txtCorreo;


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
        txtTipoTicket = (TextView) findViewById(R.id.txtTipoTicket);
        txtDescripcionTicket = (TextView) findViewById(R.id.txtDescripcionTicket);
        txtEstadoTicket = (TextView) findViewById(R.id.txtEstadoTicket);
        txtSolucionTicket = (TextView) findViewById(R.id.txtSolucionTicket);
        txtCorreo = (TextView) findViewById(R.id.txtCorreo);

        userId = getIntent().getStringExtra("user_id");

        Bundle objetoEnviado = getIntent().getExtras();
        if(objetoEnviado != null){
            ticket = (Ticket) objetoEnviado.getSerializable("data");

            txtIdTicket.setText(ticket.getId()+"");

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
        values.put(Basededatos.COLUMN_TIPO_TICKET,txtTipoTicket.getText().toString());

        db.update(Basededatos.TABLE_TICKET,values,Basededatos.COLUMN_ID_TICKET+"=?",parametros);

        Intent intent = new Intent(ActualizarTicketActivity.this,Usuario.class);
        startActivity(intent);
        Toast.makeText(this, "Se actualizo el ticket", Toast.LENGTH_SHORT).show();
        db.close();
    }
}