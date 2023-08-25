package com.example.sistemadetikets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sistemadetikets.adaptador.TicketAdaptador;
import com.example.sistemadetikets.entidades.Ticket;
import com.example.sistemadetikets.entidades.TicketLayout;

import java.util.ArrayList;

public class Usuario extends AppCompatActivity {
    private ListView lvItems;
    private TicketAdaptador adaptador;
    ArrayList<String> listaInformacion;
    ArrayList<Ticket> listaTicket;

    ArrayList<TicketLayout> arrayEntidad;
    Basededatos conn;

    TextView txt_bienvenida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        String userName = getIntent().getStringExtra("user_name");
        String userLastName = getIntent().getStringExtra("user_last_name");

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
                intent.putExtra("data",arrayEntidad.get(position));
                startActivity(intent);
            }
        });




        //Mostrar un mensaje de bienvenida con el nombre del usuario
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
            ticket.setTipoTicket(cursor.getString(3));
            ticket.setDescripcion(cursor.getString(4));
            ticket.setEstadoTicket(cursor.getString(5));

            listaTicket.add(ticket);

        }
        obtenerLista();
    }

    private void obtenerLista() {
        listaInformacion = new ArrayList<String>();
        for (int i = 0; i <listaTicket.size();i++){
            //listaInformacion.add(listaTicket.get(i).getId() + " - "+listaTicket.get(i).getNombre());
        }
    }

    private ArrayList<TicketLayout> getTickets(){
        ArrayList<TicketLayout> listeItems = new ArrayList<>();

        for (int i = 0; i <listaTicket.size();i++){
            Ticket item = listaTicket.get(i);

            Integer id = item.getId();
            String tipoTicket = item.getTipoTicket();
            String descripcion = item.getDescripcion();
            String estado = item.getEstadoTicket();

            listeItems.add(new TicketLayout(id, tipoTicket,descripcion,estado));
        }
        return listeItems;
    }




    public void onClick(View view) {
        Intent miIntent = null;
        if(view.getId() == R.id.btnOpcionCrearTicket){
            miIntent = new Intent(Usuario.this,CrearTicketActivity.class);
        }
        if(miIntent != null){
            startActivity(miIntent);
        }
    }
}