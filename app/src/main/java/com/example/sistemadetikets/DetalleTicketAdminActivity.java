package com.example.sistemadetikets;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.sistemadetikets.entidades.Ticket;
import com.example.sistemadetikets.entidades.TicketLayout;

public class DetalleTicketAdminActivity extends AppCompatActivity {

    Basededatos conn;

    TextView txtIdTicket,txtNombreRazonSocial,txtCorreo,txtTipoTicket,txtDescripcionTicket,txtEstadoTicket,txtSolucionTicket;

    private TicketLayout item;
    String userId;
    Ticket ticket = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_ticket_admin);
    }
}