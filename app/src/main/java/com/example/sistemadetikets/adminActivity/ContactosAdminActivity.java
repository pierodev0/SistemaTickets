package com.example.sistemadetikets.adminActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.example.sistemadetikets.RegistrodeUsuarios;
import com.example.sistemadetikets.UsuarioActivity.DetalleTicketActivity;
import com.example.sistemadetikets.UsuarioActivity.Usuario;
import com.example.sistemadetikets.adaptador.ContactoAdaptador;
import com.example.sistemadetikets.entidades.Contacto;
import com.example.sistemadetikets.entidades.ContactoLayout;

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
        setContentView(R.layout.activity_contactos_admin);

        //Establecer un Action bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setBackgroundColor(Color.TRANSPARENT);

       getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        userName = getIntent().getStringExtra("user_name");
        userLastName = getIntent().getStringExtra("user_last_name");
        userId = getIntent().getStringExtra("user_id");
        rolUser = getIntent().getStringExtra("user_rol");

        lvItems = (ListView) findViewById(R.id.lvItems);
        conn = new Basededatos(getApplicationContext());
        consultarListaUsuarios();
        arrayEntidad = getContactos();
        adaptador = new ContactoAdaptador(this,arrayEntidad);
        lvItems.setAdapter(adaptador);

        //Mostrar un mensaje de bienvenida con el nombre del usuario
        TextView txtBienvenida = findViewById(R.id.txt_nomb);
        txtBienvenida.setText("" + userName + " " + userLastName + "!");

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent intent = new Intent(ContactosAdminActivity.this, Admin.class);
                //intent.putExtra("data",arrayEntidad.get(position));
                intent.putExtra("data", arrayListContacto.get(position));
                intent.putExtra("user_name", userName);
                intent.putExtra("user_last_name", userLastName);
                intent.putExtra("user_id", arrayListContacto.get(position).getId().toString());
                startActivity(intent);

            }
        });
    }

    private void consultarListaUsuarios() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Contacto contacto = null;
        arrayListContacto = new ArrayList<Contacto>();


        //Cursor cursor = db.rawQuery("SELECT * FROM usuarios WHERE rol='usuario'",null);
        Cursor cursor = db.rawQuery("SELECT u.id AS id_usuario,u.nombres AS nombres,u.apellidos as apellidos,COUNT(t.id_ticket) AS numeros_ticket, u.razon_social AS razon_social " +
                "FROM usuarios u " +
                "LEFT JOIN ticket t ON u.id = t.id_usuario " +
                "WHERE u.rol = 'usuario' " +
                "GROUP BY u.id;", null);

        while (cursor.moveToNext()){
            contacto = new Contacto();
            contacto.setId(cursor.getInt(0));
            contacto.setNombre(cursor.getString(1) +" "+ cursor.getString(2));
            contacto.setNumeros_ticket(cursor.getInt(3));
            contacto.setRazon_social(cursor.getString(4));

            arrayListContacto.add(contacto);
        }
    }

    private ArrayList<ContactoLayout> getContactos(){
        ArrayList<ContactoLayout> listeItems = new ArrayList<>();

        for (int i = 0; i < arrayListContacto.size(); i++){
            Contacto item = arrayListContacto.get(i);

            Integer id = item.getId();
            String id_usuario = item.getNombre();
            Integer numTickets = item.getNumeros_ticket();
            String razonSocial = item.getRazon_social();


            listeItems.add(new ContactoLayout(id, id_usuario,numTickets,razonSocial));
        }
        return listeItems;
    }


}