package com.cristian.retodeezer;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.deezer.sdk.model.Playlist;
import com.deezer.sdk.model.Track;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.request.DeezerRequest;
import com.deezer.sdk.network.request.DeezerRequestFactory;
import com.deezer.sdk.network.request.event.DeezerError;
import com.deezer.sdk.network.request.event.JsonRequestListener;
import com.deezer.sdk.network.request.event.RequestListener;
import com.deezer.sdk.player.TrackPlayer;
import com.deezer.sdk.player.exception.TooManyPlayersExceptions;
import com.deezer.sdk.player.networkcheck.WifiAndMobileNetworkStateChecker;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Cancion extends AppCompatActivity {

    private TextView lblNombre;
    private TextView lblArtista;
    private TextView lblAlbum;
    private TextView lblDuracion;
    private Track track;
    private List<Track> tracks;
    private ImageView image;
    private Button btnReproducir;
    private TrackPlayer mTrackPlayer;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancion);
        final String applicationID = "301664";
        final DeezerConnect deezerConnect = new DeezerConnect(this, applicationID);


        lblNombre = findViewById(R.id.nombreCancion);
        lblArtista = findViewById(R.id.nombreArtista);
        lblAlbum = findViewById(R.id.album);
        lblDuracion = findViewById(R.id.duracion);
        image = findViewById(R.id.fotocancion);
        btnReproducir = findViewById(R.id.reproducir);


        Bundle parametros = this.getIntent().getExtras();
        long id = 0;

        if (parametros != null) {

            id = parametros.getLong("identificador");


        }

        final long ultimo = id;


        RequestListener listener = new JsonRequestListener() {

            public void onResult(Object result, Object requestId) {
                track = (Track) result;

                int total = track.getDuration();
                int minutos = total / 60;
                int segundos = total % 60;

                String segun = "" + segundos;

                if (segun.length() == 1) {

                    segun = "0" + segun;
                }

                lblNombre.setText("" + track.getShortTitle());
                lblArtista.setText(track.getArtist().getName().toString());
                lblAlbum.setText("" + "Album: " + track.getAlbum().getTitle());
                lblDuracion.setText("" + minutos + ":" + segun);
                Picasso.get().load(track.getAlbum().getBigImageUrl()).into(image);

            }



            public void onUnparsedResult(String requestResponse, Object requestId) {
            }

            public void onException(Exception e, Object requestId) {
            }
        };


        DeezerRequest request = DeezerRequestFactory.requestTrack(id);


        request.setId("Hola");

        // launch the request asynchronously
        deezerConnect.requestAsync(request, listener);

        btnReproducir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Uri webpage = Uri.parse(track.getPreviewUrl());
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntent);


                try {

                    mTrackPlayer = new TrackPlayer(getApplication(), deezerConnect,
                            new WifiAndMobileNetworkStateChecker());
                  //  mTrackPlayer.playTrack(ultimo);

                }

                catch (TooManyPlayersExceptions e) {

                }
                catch (DeezerError e) {

                }
            }
        });


    }
}
