package com.cristian.retodeezer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.deezer.sdk.model.Playlist;
import com.deezer.sdk.model.Track;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.request.DeezerRequest;
import com.deezer.sdk.network.request.DeezerRequestFactory;
import com.deezer.sdk.network.request.event.JsonRequestListener;
import com.deezer.sdk.network.request.event.RequestListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListaCanciones extends AppCompatActivity {


    private TextView lblNombre;
    private TextView lblDescripcion;
    private TextView lblNumCanciones;
    private ImageView imageView;
    private ListView listView;
    private List<Track> canciones;
    private Playlist playlist;
    private AdaptadorCanciones adaptadorCanciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_canciones);

        lblNombre = findViewById(R.id.lblNombrePlaylist);
        lblDescripcion = findViewById(R.id.lblDescripcion);
        lblNumCanciones = findViewById(R.id.lblNumCanciones);
        imageView = findViewById(R.id.image);
        listView = findViewById(R.id.lista);
        canciones = new ArrayList<Track>();
        adaptadorCanciones = new AdaptadorCanciones(this);
        listView.setAdapter(adaptadorCanciones);

        String applicationID = "301664";
        final DeezerConnect deezerConnect = new DeezerConnect(this, applicationID);

        long id = 0;

        Bundle parametros = this.getIntent().getExtras();

        if (parametros != null) {

            id = parametros.getLong("identificador");
        }


        RequestListener listener = new JsonRequestListener() {

            public void onResult(Object result, Object requestId) {
                playlist = (Playlist) result;

                canciones = playlist.getTracks();

                lblDescripcion.setText(playlist.getDescription());
                lblNombre.setText(playlist.getTitle());
                lblNumCanciones.setText(""+ canciones.size());

                Picasso.get().load(playlist.getMediumImageUrl()).into(imageView);


                for(int i = 0 ; i< canciones.size();i++){

                    adaptadorCanciones.agregarLista(canciones.get(i));
                }





            }

            public void onUnparsedResult(String requestResponse, Object requestId) {
            }

            public void onException(Exception e, Object requestId) {
            }
        };


        DeezerRequest request = DeezerRequestFactory.requestPlaylist(id);


        request.setId("Hola");

        // launch the request asynchronously
        deezerConnect.requestAsync(request, listener);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                long identificador = canciones.get(position).getId();

             //   String name = canciones.get(position).getShortTitle();
               // String artista = canciones.get(position).getArtist().getName();
                //String album = canciones.get(position).getAlbum().getTitle();
                //String url = canciones.get(position).getAlbum().getSmallImageUrl();
                //String duracion = ""+canciones.get(position).getDuration();

                Intent i = new Intent(ListaCanciones.this, Cancion.class);
               i.putExtra("identificador", identificador);
                //i.putExtra("name",name);
                //i.putExtra("artista",artista);
                //i.putExtra("album ",album);
                //i.putExtra("duracion",duracion);
                //i.putExtra("url",url);
                startActivity(i);


            }
        });


    }
}
