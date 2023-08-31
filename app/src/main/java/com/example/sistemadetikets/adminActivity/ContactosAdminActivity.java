package com.example.sistemadetikets.adminActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sistemadetikets.Basededatos;
import com.example.sistemadetikets.R;
import com.example.sistemadetikets.adaptador.ContactoAdaptador;
import com.example.sistemadetikets.adaptador.TicketAdaptador;
import com.example.sistemadetikets.entidades.Contacto;
import com.example.sistemadetikets.entidades.ContactoLayout;
import com.example.sistemadetikets.entidades.Ticket;
import com.example.sistemadetikets.entidades.TicketLayout;

import java.util.ArrayList;

public class ContactosAdminActivity extends AppCompatActivity {
    private ListView lvItems;
    private ContactoAdaptador adaptador;
    ArrayList<Contacto> arrayListContacto;

    ArrayList<ContactoLayout> arrayEntidad;
    Basededatos conn;

    TextView txt_bienvenida;
    String userId;
    String userName;
    String userLastName;
    String rolUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos_admin);

        userName = getIntent().getStringExtra("user_name");
        userLastName = getIntent().getStringExtra("user_last_name");
        userId = getIntent().getStringExtra("user_id");
        rolUser = getIntent().getStringExtra("user_rol");

        lvItems = (ListView) findViewById(R.id.lvItems);
        conn = new Basededatos(getApplicationContext());
        consultarListaTickets();
        arrayEntidad = getTickets();
        adaptador = new ContactoAdaptador(this,arrayEntidad);
        lvItems.setAdapter(adaptador);

        //Mostrar un mensaje de bienvenida con el nombre del usuario
        TextView txtBienvenida = findViewById(R.id.txt_nomb);
        txtBienvenida.setText("" + userName + " " + userLastName + "!");
    }

    private void consultarListaTickets() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Contacto contacto = null;
        arrayListContacto = new ArrayList<Contacto>();

        //SELECT * FROM tickets
        Cursor cursor = db.rawQuery("SELECT * FROM usuarios WHERE rol='usuario'",null);
        while (cursor.moveToNext()){
            contacto = new Contacto();
            contacto.setId(cursor.getInt(0));
            contacto.setId_usuario(cursor.getInt(1));
            contacto.setNumeros_ticket(10);
            contacto.setRazon_social("Empresa SAC");

            arrayListContacto.add(contacto);
        }
    }

    private ArrayList<ContactoLayout> getTickets(){
        ArrayList<ContactoLayout> listeItems = new ArrayList<>();

        for (int i = 0; i < arrayListContacto.size(); i++){
            Contacto item = arrayListContacto.get(i);

            Integer id = item.getId();
            Integer id_usuario = item.getId_usuario();
            Integer numTickets = item.getNumeros_ticket();
            String razonSocial = item.getRazon_social();


            listeItems.add(new ContactoLayout(id, id_usuario,numTickets,razonSocial));
        }
        return listeItems;
    }


}