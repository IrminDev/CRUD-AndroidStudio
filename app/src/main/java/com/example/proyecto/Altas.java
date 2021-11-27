package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class Altas extends AppCompatActivity implements View.OnClickListener {

    Spinner spinnerGenero, spinnerSubgenero;
    Button subir, regresar;
    EditText artista, album;
    CheckBox stock, importado;
    RadioButton disponible, preventa;
    String genero, subgenero;

    private void listarGeneros(){
        String generos[] = {"Rock", "Pop", "Metal", "Urbano", "Electrónica"};
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, generos);
        spinnerGenero.setAdapter(adaptador);
    }

    private void listarRock(){
        String subgeneros[] = {"Alternativo", "Ska", "Punk", "Progresivo", "Grunge", "Hard", "Piscodélico"};
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, subgeneros);
        spinnerSubgenero.setAdapter(adaptador);
    }

    private void listarPop(){
        String subgeneros[] = {"Mainstream", "Latino", "Independiente", "Punk"};
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, subgeneros);
        spinnerSubgenero.setAdapter(adaptador);
    }

    private void listarMetal(){
        String subgeneros[] = {"Heavy", "Thrash", "Death", "Black", "Hardcore", "Electrocore", "Deathcore"};
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, subgeneros);
        spinnerSubgenero.setAdapter(adaptador);
    }

    private void listarUrbano(){
        String subgeneros[] = {"Reggaeton", "Trap", "Rap", "Hip-hop"};
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, subgeneros);
        spinnerSubgenero.setAdapter(adaptador);
    }

    private void listarElectronica(){
        String subgeneros[] = {"House", "EDM", "Dance", "Techno", "Drum and bass", "Hardcore"};
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, subgeneros);
        spinnerSubgenero.setAdapter(adaptador);
    }

    private void desactivarElementos(){
        artista.setText("");
        album.setText("");
        spinnerGenero.setSelection(0);
        spinnerSubgenero.setSelection(0);
        stock.setChecked(false);
        preventa.setChecked(false);
        importado.setChecked(false);
        disponible.setChecked(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altas);

        //Combos dinámicos
        spinnerGenero = findViewById(R.id.altaGenero);
        spinnerSubgenero = findViewById(R.id.altaSubgenero);

        listarGeneros();

        spinnerGenero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                genero = spinnerGenero.getSelectedItem().toString();
                if (i == 0){
                    listarRock();
                }
                else{
                    if(i == 1){
                        listarPop();
                    }
                    else{
                        if(i == 2){
                            listarMetal();
                        }
                        else{
                            if(i == 3){
                                listarUrbano();
                            }
                            else{
                                if(i == 4){
                                    listarElectronica();
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerSubgenero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                subgenero = spinnerSubgenero.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Demás componentes
        artista = findViewById(R.id.altaArtista);
        album = findViewById(R.id.altaAlbum);
        preventa = findViewById(R.id.opcAlta1);
        disponible = findViewById(R.id.opcAlta2);
        stock = findViewById(R.id.altaCheck);
        importado = findViewById(R.id.altaCheck2);

        //Botones
        subir = findViewById(R.id.altaSubir);
        regresar = findViewById(R.id.altaRegresar);

        subir.setOnClickListener(this);
        regresar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String cadena = ((Button)view).getText().toString();
        if(cadena.equals("Alta")){
            String nombreArtista = artista.getText().toString();
            String nombreAlbum = album.getText().toString();
            if(preventa.isChecked() || disponible.isChecked()){
                String estado = "";
                if(preventa.isChecked()){
                    estado = "Preventa";
                }
                else{
                    if(disponible.isChecked()){
                        estado = "Disponible";
                    }
                }

                String cadenaStock = "Sin stock";
                String cadenaImportado = "No es importado";
                if(stock.isChecked()){
                    cadenaStock = "Con stock";
                }
                if(importado.isChecked()){
                    cadenaImportado = "Importado";
                }
                Administrar admin = new Administrar(this, "Musica", null, 1);
                SQLiteDatabase baseDatos = admin.getWritableDatabase();
                if(!nombreArtista.equals("") && !nombreAlbum.equals("")){
                    ContentValues registro = new ContentValues();
                    registro.put("artista", nombreArtista);
                    registro.put("album", nombreAlbum);
                    registro.put("estado", estado);
                    registro.put("stock", cadenaStock);
                    registro.put("importacion", cadenaImportado);
                    registro.put("genero", genero);
                    registro.put("subgenero", subgenero);
                    baseDatos.insert("albumes", null, registro);
                    baseDatos.close();
                    Toast.makeText(this, nombreAlbum + " dado de alta", Toast.LENGTH_SHORT).show();
                    desactivarElementos();
                }
                else{
                    Toast.makeText(this, "No dejes espacios en blanco", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this, "Selecciona un estado del álbum", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            if(cadena.equals("Regresar")){
                desactivarElementos();
                Intent intento = new Intent(this, MainActivity.class);
                startActivity(intento);
            }
        }
    }
}