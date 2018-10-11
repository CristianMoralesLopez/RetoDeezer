package com.cristian.retodeezer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityRecord;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.deezer.sdk.model.Playlist;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorListas extends BaseAdapter {


    private Activity activity;
    private ArrayList<Playlist> playlists;


    public AdaptadorListas(Activity activity) {
        this.activity = activity;
        playlists = new ArrayList<Playlist>();
    }

    @Override
    public int getCount() {
        return playlists.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View renglon = inflater.inflate(R.layout.renglon, null, false);
        TextView txtNombre = renglon.findViewById(R.id.lista);
        TextView txtUsuario = renglon.findViewById(R.id.usuario);
        TextView txtNumero = renglon.findViewById(R.id.numCanciones);

        ImageView image = renglon.findViewById(R.id.iv_foto);

        txtNombre.setText("Nombre de la lista: "+ playlists.get(position).getTitle());
        txtUsuario.setText("Nombre del creador: "+playlists.get(position).getCreator().getName());
       txtNumero.setText("Numero de canciones: " + playlists.get(position).getDuration());

        Picasso.get().load(playlists.get(position).getMediumImageUrl()).into(image);



        return renglon;
    }


    public void agregarLista (Playlist play){
        playlists.add(play);
        notifyDataSetChanged();
    }

    public void limpiar(){
        playlists.clear();
    }
}
