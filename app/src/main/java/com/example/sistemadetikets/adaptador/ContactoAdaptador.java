package com.example.sistemadetikets.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sistemadetikets.R;
import com.example.sistemadetikets.entidades.Contacto;
import com.example.sistemadetikets.entidades.ContactoLayout;
import com.example.sistemadetikets.entidades.TicketLayout;

import java.util.ArrayList;

public class ContactoAdaptador  extends BaseAdapter {

    private Context context;
    private ArrayList<ContactoLayout> listItems;

    public ContactoAdaptador(Context context, ArrayList<ContactoLayout> listItems) {
        this.context = context;
        this.listItems = listItems;
    }
    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ContactoLayout Item = (ContactoLayout) getItem(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.contacto_item,null);

        TextView txtRazonSocial = (TextView) convertView.findViewById(R.id.txtRazonSocial);
        TextView txtNumerosDeTickets = (TextView) convertView.findViewById(R.id.txtNumerosDeTickets);
        TextView txtNombrePersona = (TextView) convertView.findViewById(R.id.txtNombrePersona);



        txtRazonSocial.setText(Item.getRazon_social());
        txtNumerosDeTickets.setText(Item.getNumeros_ticket()+"");
        txtNombrePersona.setText("piero ");


        return convertView;
    }
}
