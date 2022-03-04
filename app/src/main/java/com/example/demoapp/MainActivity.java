package com.example.demoapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demoapp.Models.Film;
import com.example.demoapp.Models.People;
import com.example.demoapp.Models.SWAPI_Film_Response;
import com.example.demoapp.Models.SWAPI_Response;
import com.example.demoapp.SWAPI.SWAPI_Film;
import com.example.demoapp.SWAPI.SWAPI_Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "PEOPLE";
    private static final String AES  = "AES";
    private Retrofit retrofit;
    private String encrypted_password, encrypted_input_password ;
    private String encrypted_msg;
    EditText username, password;
    TextView error;
    Button login;
    private List<People> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.txtUser);
        password = (EditText) findViewById(R.id.txtPassword);
        error = (TextView) findViewById(R.id.txtError);
        login = (Button) findViewById(R.id.btnLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(username.getText().toString())||TextUtils.isEmpty(password.getText().toString()))
                {

                    Toast.makeText(MainActivity.this,"Ambos campos son obligatorios!",Toast.LENGTH_SHORT).show();
                }
                else{
                    login();
                }
            }
        });
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://swapi.dev/api/")
                .client(okHttpClient)
                .build();
        obtenerDatos();
    }
    private String encriptar(String mensaje, String mensaje2) throws Exception{
        String key = "Star*Wars*SWAPI*-Test/2022-03-01";
        String[] result = new String[2];
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
        keyGenerator.init( 128);
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] bytesSecretKey = secretKey.getEncoded();
        SecretKeySpec secretkeySpec = new SecretKeySpec(key.getBytes(), AES);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE, secretkeySpec);
        byte[] mensajeEncriptado = cipher.doFinal((mensaje).getBytes());
        byte[] mensajeEncriptado2 = cipher.doFinal(mensaje2.getBytes());
        Log.i(TAG, "Encriptado" + mensajeEncriptado);
        Log.i(TAG, "Encriptado" + mensajeEncriptado2);

        //Desencriptar el texto
        /*cipher.init(Cipher.DECRYPT_MODE, secretkeySpec);
        byte[] mensajeDesEncriptado = cipher.doFinal(mensajeEncriptado);
        byte[] mensajeDesEncriptado2 = cipher.doFinal(mensajeEncriptado2);
        Log.i(TAG, new String (mensajeDesEncriptado));
        Log.i(TAG, new String (mensajeDesEncriptado2));*/

        return mensajeEncriptado.toString();
    }

    private void login() {
        for (int i= 0; i< lista.size();i++)
        {
            try {
                //encrypted_input_password = ;
                //encrypted_password = (lista.get(i).getHair_color());
                //encrypted_msg = encriptar(encrypted_input_password,encrypted_password);
                if (username.getText().toString().equals(lista.get(i).getName()) && password.getText().toString().equals(lista.get(i).getHair_color()))
                {
                    Intent intent = new Intent(MainActivity.this, Vista_Lista.class);
                    intent.putExtra("usuario", lista.get(i).getName());
                    intent.putExtra("url",lista.get(i).getUrl());
                    intent.putExtra("created",lista.get(i).getCreated());
                    intent.putExtra("films",Arrays.toString(lista.get(i).getFilms()));
                    startActivity(intent);
                    finish();
                    Toast.makeText(MainActivity.this,"´Bienvenido: "+lista.get(i).getName(),Toast.LENGTH_SHORT).show();
                    error.setText("");
                    break;
                }
                else{
                    error.setText("Usuario y/o contraseña incorrectos");
                    error.setTextColor(Color.rgb(203,71,65));
                    error.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            error.setText("");
                        }
                    }, 3000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void obtenerDatos(){
        SWAPI_Service service = retrofit.create(SWAPI_Service.class);
        Call<SWAPI_Response> peopleCall = service.obtenerListaPersonasSWAPI();
        peopleCall.enqueue(new Callback<SWAPI_Response>() {
            @Override
            public void onResponse(Call<SWAPI_Response> call, Response<SWAPI_Response> response) {
                if(response.isSuccessful())
                {
                    SWAPI_Response swapi_response = response.body();
                    ArrayList<People> listapeople = swapi_response.getResults();
                    lista = swapi_response.getResults();
                    for (int i= 0; i< listapeople.size();i++)
                    {
                        People p = listapeople.get(i);
                        Log.i(TAG, "´People: " + p.getName() + " " + p.getHair_color());
                    }
                }
                else{
                    Log.e(TAG, " onResponse:" + response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<SWAPI_Response> call, Throwable t) {
                Log.e(TAG, "OnFailure:" + t.getMessage());
            }
        });
    }
}