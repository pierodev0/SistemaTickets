package com.example.sistemadetikets;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetalleTicketActivity extends AppCompatActivity {

    TextView txtIdTicket,txtNombrePersona,txtTipoTicket,txtDescripcionTicket,txtEstadoTicket,txtSolucionTicket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_ticket);

        txtIdTicket = (TextView) findViewById(R.id.txtIdTicket);
        txtNombrePersona = (TextView) findViewById(R.id.txtNombrePersona);
        txtTipoTicket = (TextView) findViewById(R.id.txtTipoTicket);
        txtDescripcionTicket = (TextView) findViewById(R.id.txtDescripcionTicket);
        txtEstadoTicket = (TextView) findViewById(R.id.txtEstadoTicket);
        txtSolucionTicket = (TextView) findViewById(R.id.txtSolucionTicket);


    }
}