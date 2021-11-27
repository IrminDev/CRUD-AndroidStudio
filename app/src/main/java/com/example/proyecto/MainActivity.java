package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button menuAltas, menuBajas, menuCambios, menuConsultas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuAltas = findViewById(R.id.MenuAltas);
        menuBajas = findViewById(R.id.MenuBajas);
        menuCambios = findViewById(R.id.MenuCambios);
        menuConsultas = findViewById(R.id.MenuConsultas);

        menuAltas.setOnClickListener(this);
        menuBajas.setOnClickListener(this);
        menuCambios.setOnClickListener(this);
        menuConsultas.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String cadena = ((Button)view).getText().toString();
        Intent intento = new Intent(this, MainActivity.class);
        if(cadena.equals("Altas")){
            intento = new Intent(this, Altas.class);
        }
        else{
            if(cadena.equals("Bajas")){
                intento = new Intent(this, Bajas.class);
            }
            else{
                if(cadena.equals("Cambios")){
                    intento = new Intent(this, Cambios.class);
                }
                else{
                    if (cadena.equals("Consultas")){
                        intento = new Intent(this, Consultas.class);
                    }
                }
            }
        }
        startActivity(intento);
    }
}