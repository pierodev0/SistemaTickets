package com.example.sistemadetikets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sistemadetikets.adaptador.TicketAdaptador;
import com.example.sistemadetikets.entidades.TicketLayout;

import java.util.ArrayList;

public class Usuario extends AppCompatActivity {
    private ListView lvItems;
    private TicketAdaptador adaptador;

    TextView txt_bienvenida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        String userName = getIntent().getStringExtra("user_name");
        String userLastName = getIntent().getStringExtra("user_last_name");

        lvItems = (ListView) findViewById(R.id.lvItems);
        adaptador = new TicketAdaptador(this,getArrayItems());
        lvItems.setAdapter(adaptador);




        //Mostrar un mensaje de bienvenida con el nombre del usuario
        TextView txtBienvenida = findViewById(R.id.txt_nomb);
        txtBienvenida.setText("" + userName + " " + userLastName + "!");
    }

    private ArrayList<TicketLayout> getArrayItems(){
        ArrayList<TicketLayout> listeItems = new ArrayList<>();
        listeItems.add(new TicketLayout(1, "SS BLUES", "Goku y Vegeta"));
        listeItems.add(new TicketLayout(2, "SS BLUE Y SS ROSE", "Goku y Black"));
        listeItems.add(new TicketLayout(3, "DB HEROES 1", "Personajes nuevos"));
        listeItems.add(new TicketLayout(4, "DB HEROES 2", "Otros personajes, Majin Boo"));
        listeItems.add(new TicketLayout(5, "VEGETA", "Sacrificio de Vegeta"));

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