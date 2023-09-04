package com.example.sistemadetikets.UsuarioActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sistemadetikets.Basededatos;
import com.example.sistemadetikets.MainActivity;
import com.example.sistemadetikets.R;
import com.example.sistemadetikets.adaptador.TicketAdaptador;
import com.example.sistemadetikets.entidades.Ticket;
import com.example.sistemadetikets.entidades.TicketLayout;

import java.util.ArrayList;

public class Usuario extends AppCompatActivity {
    private ListView lvItems;
    private TicketAdaptador adaptador;
    ArrayList<Ticket> arrayListTicket;

    ArrayList<TicketLayout> arrayListTicketLayout;
    Basededatos conn;

    TextView txt_bienvenida;
    String userId;
    String userName;
    String userLastName;

    //Crear menu para el action bar
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.item1){
            Toast.makeText(this, "Sesion finalizada", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        //Establecer un Action bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setBackgroundColor(Color.TRANSPARENT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userName = getIntent().getStringExtra("user_name");
        userLastName = getIntent().getStringExtra("user_last_name");
        userId = getIntent().getStringExtra("user_id");



        lvItems = (ListView) findViewById(R.id.lvItems);
        conn = new Basededatos(getApplicationContext());
        consultarListaTickets();
        arrayListTicketLayout = getTickets();
        adaptador = new TicketAdaptador(this, arrayListTicketLayout);
        lvItems.setAdapter(adaptador);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent intent = new Intent(Usuario.this, DetalleTicketActivity.class);
                //intent.putExtra("data",arrayEntidad.get(position));
                intent.putExtra("data", arrayListTicket.get(position));
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
        TextView txtBienvenida = findViewById(R.id.txt_nomb);
        txtBienvenida.setText("" + userName + " " + userLastName + "!");
    }




    private void consultarListaTickets() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Ticket ticket = null;
        arrayListTicket = new ArrayList<Ticket>();

        //SELECT * FROM tickets
        Cursor cursor = db.rawQuery("SELECT * FROM TICKET WHERE id_usuario="+userId,null);
        while (cursor.moveToNext()){
            ticket = new Ticket();
            ticket.setId(cursor.getInt(0));
            ticket.setId_usuario(cursor.getInt(1));
            ticket.setFechaCreacion(cursor.getString(2));
            ticket.setId_estado(cursor.getInt(3));
            ticket.setId_solicitud(cursor.getInt(4));
            ticket.setDescripcion(cursor.getString(5));
            ticket.setSolucion(cursor.getString(6));

            arrayListTicket.add(ticket);
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

        for (int i = 0; i < arrayListTicket.size(); i++){
            Ticket item = arrayListTicket.get(i);

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