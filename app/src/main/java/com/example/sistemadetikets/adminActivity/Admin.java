package com.example.sistemadetikets.adminActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sistemadetikets.Basededatos;
import com.example.sistemadetikets.R;
import com.example.sistemadetikets.adaptador.TicketAdaptador;
import com.example.sistemadetikets.entidades.Ticket;
import com.example.sistemadetikets.entidades.TicketLayout;

import java.util.ArrayList;

public class Admin extends AppCompatActivity {
    private ListView lvItemsAdmin;
    private TicketAdaptador adaptador;
    ArrayList<String> listaInformacion;
    ArrayList<Ticket> listaTicket;

    ArrayList<TicketLayout> arrayEntidad;
    Basededatos conn;

    TextView txt_bienvenida;
    String userId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        String userName = getIntent().getStringExtra("user_name");
        String userLastName = getIntent().getStringExtra("user_last_name");
        userId = getIntent().getStringExtra("user_id");


        lvItemsAdmin = (ListView) findViewById(R.id.lvItemsAdmin);
        conn = new Basededatos(getApplicationContext());
        consultarListaTickets();
        arrayEntidad = getTickets();
        adaptador = new TicketAdaptador(this,arrayEntidad);
        lvItemsAdmin.setAdapter(adaptador);

        lvItemsAdmin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent intent = new Intent(Admin.this, DetalleTicketAdminActivity.class);
                //intent.putExtra("data",arrayEntidad.get(position));
                intent.putExtra("data",listaTicket.get(position));
                startActivity(intent);


                /*
                //Enviar el objeto a otra actividad
                Ticket ticket = listaTicket.get(position);
                Intent intent = new Intent(Usuario.this, DetalleTicketActivity.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("data",ticket);
                startActivity(intent);
                */

            }
        });




        //Mostrar un mensaje de bienvenida con el nombre del usuario
        txt_bienvenida = findViewById(R.id.txt_nomb_admin);
        txt_bienvenida.setText("" + userName + " " + userLastName + "!");
    }

    private void consultarListaTickets() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Ticket ticket = null;
        listaTicket = new ArrayList<Ticket>();

        //SELECT * FROM tickets
        Cursor cursor = db.rawQuery("SELECT * FROM TICKET",null);
        while (cursor.moveToNext()){
            ticket = new Ticket();
            ticket.setId(cursor.getInt(0));
            ticket.setId_usuario(cursor.getInt(1));
            ticket.setFechaCreacion(cursor.getString(2));
            ticket.setId_estado(cursor.getInt(3));
            ticket.setId_solicitud(cursor.getInt(4));
            ticket.setDescripcion(cursor.getString(5));
            ticket.setSolucion(cursor.getString(6));

            listaTicket.add(ticket);

        }

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


    private ArrayList<TicketLayout> getTickets(){
        ArrayList<TicketLayout> listeItems = new ArrayList<>();

        for (int i = 0; i <listaTicket.size();i++){
            Ticket item = listaTicket.get(i);

            Integer id = item.getId();
            String tipoTicket = item.getId_solicitud().toString();
            String descripcion = item.getDescripcion();
            String estado = item.getId_estado().toString();

            listeItems.add(new TicketLayout(id, tipoTicket,descripcion,estado));
        }
        return listeItems;
    }

    public void onClick(View view) {
        Intent miIntent = null;
//        if(view.getId() == R.id.btnOpcionCrearTicket){
//            miIntent = new Intent(Admin.this,CrearTicketAdminActivity.class);
//            miIntent.putExtra("user_id", userId);
//
//        }
        if(miIntent != null){
            startActivity(miIntent);
        }
    }
}