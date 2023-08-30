package com.example.sistemadetikets.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sistemadetikets.R;
import com.example.sistemadetikets.entidades.TicketLayout;

import java.util.ArrayList;

public class TicketAdaptador extends BaseAdapter {
    private Context context;
    private ArrayList<TicketLayout> listItems;

    public TicketAdaptador(Context context, ArrayList<TicketLayout> listItems) {
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
        TicketLayout Item = (TicketLayout) getItem(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.ticket_item,null);

        TextView txtIdTicket = (TextView) convertView.findViewById(R.id.txtIdTicket);
        TextView txtTipoTicket = (TextView) convertView.findViewById(R.id.txtTipoTicket);
        TextView txtDescripcionTicket = (TextView) convertView.findViewById(R.id.txtDescripcionTicket);
        TextView txtEstadoTicket = (TextView) convertView.findViewById(R.id.txtEstadoTicket);


        //imgFoto.setImageREsource(Item.getImgFoto);
        txtIdTicket.setText(Item.getId()+""); //Tiene que ser string
        txtTipoTicket.setText(Item.getNombreSolicitud());
        txtDescripcionTicket.setText(Item.getDescripcion());
        txtEstadoTicket.setText(mostrarEstado(Integer.parseInt(Item.getEstado())));


        return convertView;
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
}
