package com.cristian.retodeezer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.deezer.sdk.model.Playlist;
import com.deezer.sdk.model.Track;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorCanciones extends BaseAdapter {


    private Activity activity;
    private ArrayList<Track> tracks;



    public AdaptadorCanciones(Activity activity) {
        this.activity = activity;
        tracks = new ArrayList<Track>();
    }

    @Override
    public int getCount() {
        return tracks.size();
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
        View renglon = inflater.inflate(R.layout.renglon2, null, false);
        TextView txtNombre = renglon.findViewById(R.id.nombre);
        TextView txtArtista = renglon.findViewById(R.id.artista);
        TextView txtAnoLanzamiento = renglon.findViewById(R.id.anoLanzamiento);
        ImageView imageView = renglon.findViewById(R.id.imageFotoCancion);



        txtNombre.setText("Nombre de la cancion"+ tracks.get(position).getTitle());
        txtArtista.setText("Nombre del artista: "+tracks.get(position).getArtist().getName());
        txtAnoLanzamiento.setText("Año de Lanzamiento: " + tracks.get(position).getDuration());
        Picasso.get().load(tracks.get(position).getAlbum().getMediumImageUrl()).into(imageView);





        return renglon;
    }


    public void agregarLista (Track track){
        tracks.add(track);
        notifyDataSetChanged();
    }

    public void limpiar(){
        tracks.clear();
    }







}
