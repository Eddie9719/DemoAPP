package com.example.demoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.demoapp.Models.Film;
import com.example.demoapp.Models.SWAPI_Film_Response;
import com.example.demoapp.SWAPI.SWAPI_Film;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Vista_Lista extends AppCompatActivity {
    private static final String TAG = "FILM";
    private String user, url, created_date, films;
    private String[] peliculas;
    private Retrofit retrofit;
    private ArrayAdapter<String> listadapter;
    private List<String> lista_p ;
    private ListView listview;
    List<Film> lista_peliculasjson = new ArrayList<>();
    List<Film> lista = new ArrayList<>();
    Adaptador adapter;
    TextView Usuario, Created, Films;
    Button Logout;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Usuario = (TextView) findViewById(R.id.txtUsuario);
        Created = (TextView) findViewById(R.id.txtCreated);
        Films =  (TextView) findViewById(R.id.txtLista);
        listview = (ListView) findViewById(R.id.listaPeliculas);
        Logout = (Button) findViewById(R.id.btnCerrar);
        user = getIntent().getStringExtra("usuario");
        url = getIntent().getStringExtra("url");
        films = getIntent().getStringExtra("films");
        peliculas = films.split(",");
        created_date = getIntent().getStringExtra("created");
        Usuario.setText(user);
        Usuario.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
        Created.setText(created_date);
        Created.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
        lista_p =Split_films(peliculas);
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://swapi.dev/api/")
                .client(okHttpClient)
                .build();
        obtenerDatos();
        listadapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,lista_p);
        listview.setAdapter(listadapter);
        //adapter = new Adaptador(this,lista);
        //listview.setAdapter(adapter);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Vista_Lista.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public List<String> Split_films(String[] films){
        List<String> list_resultado = new ArrayList<>();
        for (int i= 0; i< films.length;i++)
        {
            list_resultado.add(films[i].replace("[","").replace("]","").trim());
        }
        return list_resultado;
    }

    public void obtenerDatos(){
        SWAPI_Film service = retrofit.create(SWAPI_Film.class);
        Call<SWAPI_Film_Response> filmCall = service.obtenerListaPeliculasSWAPI();
        filmCall.enqueue(new Callback<SWAPI_Film_Response>() {
            @Override
            public void onResponse(Call<SWAPI_Film_Response> call, Response<SWAPI_Film_Response> response) {
                if(response.isSuccessful())
                {
                    SWAPI_Film_Response swapi_response = response.body();
                    ArrayList<Film> listafilms = swapi_response.getResults();
                    lista_peliculasjson = swapi_response.getResults();
                    for (int j= 0; j< lista_p.size();j++)
                    {
                        for (int i= 0; i< lista_peliculasjson.size();i++)
                        {
                            Film film = lista_peliculasjson.get(i);
                            if(lista_p.get(j).equals(film.getUrl()))
                            {
                                lista.add(film);
                                Log.i(TAG, "Film " + film.getDirector() );
                            }
                        }
                    }
                }
                else{
                    Log.e(TAG, " onResponse:" + response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<SWAPI_Film_Response> call, Throwable t) {
                Log.e(TAG, "OnFailure:" + t.getMessage());
            }
        });
    }




}