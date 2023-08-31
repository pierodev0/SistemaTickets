package com.example.sistemadetikets.UsuarioActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.sistemadetikets.utilidades.Utilidades;

import java.util.ArrayList;

public class Usuario extends AppCompatActivity {
    private ListView lvItems;
    private TicketAdaptador adaptador;
    ArrayList<String> listaInformacion;
    ArrayList<Ticket> listaTicket;
    ArrayList<TicketLayout> listaTicketLayout;

    ArrayList<TicketLayout> arrayEntidad;
    Basededatos conn;

    TextView txt_bienvenida;
    String userId;
    String userName;
    String userLastName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        userName = getIntent().getStringExtra("user_name");
        userLastName = getIntent().getStringExtra("user_last_name");
        userId = getIntent().getStringExtra("user_id");



        lvItems = (ListView) findViewById(R.id.lvItems);
        conn = new Basededatos(getApplicationContext());
        consultarListaTickets();
        arrayEntidad = getTickets();
        adaptador = new TicketAdaptador(this,arrayEntidad);
        lvItems.setAdapter(adaptador);


        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent intent = new Intent(Usuario.this, DetalleTicketActivity.class);
                //intent.putExtra("data",arrayEntidad.get(position));
                intent.putExtra("data",listaTicket.get(position));
                intent.putExtra("user_name", userName);
                intent.putExtra("user_last_name", userLastName);
                intent.putExtra("user_id", userId);
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
        SharedPreferences preferences = getSharedPreferences("sesion", Context.MODE_PRIVATE);
        TextView txtBienvenida = findViewById(R.id.txt_nomb);
        txtBienvenida.setText("" + userName + " " + userLastName + "!");
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


    private ArrayList<TicketLayout> getTickets(){
        ArrayList<TicketLayout> listeItems = new ArrayList<>();

        for (int i = 0; i <listaTicket.size();i++){
            Ticket item = listaTicket.get(i);

            Integer id = item.getId();
            String tipoTicket = consultarTipoSolicitud(item.getId_solicitud());
            String descripcion = item.getDescripcion();
            String estado = item.getId_estado().toString();

            listeItems.add(new TicketLayout(id, tipoTicket,descripcion,estado));
        }
        return listeItems;
    }


    public void onClick(View view) {
        Intent intent = null;
        if(view.getId() == R.id.btnOpcionCrearTicket){
            intent = new Intent(Usuario.this, CrearTicketActivity.class);
            intent.putExtra("user_name", userName);
            intent.putExtra("user_last_name", userLastName);
            intent.putExtra("user_id", userId);

        }
        if(intent != null){
            startActivity(intent);
        }
    }


}