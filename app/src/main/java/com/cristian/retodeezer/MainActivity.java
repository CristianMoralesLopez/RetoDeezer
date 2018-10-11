package com.cristian.retodeezer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.deezer.sdk.model.Playlist;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.request.DeezerRequest;
import com.deezer.sdk.network.request.DeezerRequestFactory;
import com.deezer.sdk.network.request.event.JsonRequestListener;
import com.deezer.sdk.network.request.event.RequestListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ImageButton btnBusqueda;
    private EditText txtBusqueda;
    private ListView lista;
    private AdaptadorListas adaptadorListas;
    private ArrayList<Playlist> listPlaylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String applicationID = "301664";
        final DeezerConnect deezerConnect = new DeezerConnect(this, applicationID);


        btnBusqueda = findViewById(R.id.btnBusqueda);
        txtBusqueda = findViewById(R.id.txtBusqueda);
        lista = findViewById(R.id.lista);
        adaptadorListas = new AdaptadorListas(this);
        lista.setAdapter(adaptadorListas);
        listPlaylist = new ArrayList<Playlist>();


        btnBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listPlaylist.clear();
                adaptadorListas.limpiar();

                Toast.makeText(getApplicationContext(), "click", Toast.LENGTH_SHORT).show();
                // the request listener
                RequestListener listener = new JsonRequestListener() {

                    public void onResult(Object result, Object requestId) {
                        listPlaylist = (ArrayList<Playlist>) result;

                        for (int i = 0; i < listPlaylist.size(); i++) {
                            adaptadorListas.agregarLista(listPlaylist.get(i));
                        }


                    }

                    public void onUnparsedResult(String requestResponse, Object requestId) {
                    }

                    public void onException(Exception e, Object requestId) {
                    }
                };




// create the request


                //DeezerRequest request2 = DeezerRequestFactory.requestArtistPlaylists(artistId);
                DeezerRequest request3 = DeezerRequestFactory.requestSearchPlaylists(txtBusqueda.getText().toString());
                //DeezerRequest request = DeezerRequestFactory.requestArtistAlbums(artistId);
                request3.setId("Hola");

                // launch the request asynchronously
                deezerConnect.requestAsync(request3, listener);

// set a requestId, that will be passed on the listener's callback methods
                //request.setId("myRequest");
            }
        });


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                long identificador = listPlaylist.get(position).getId();

                Intent i = new Intent(MainActivity.this, ListaCanciones.class);
                i.putExtra("identificador", identificador);
                startActivity(i);


            }
        });

    }
}
