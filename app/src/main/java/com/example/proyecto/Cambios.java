package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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

import java.lang.reflect.Array;
import java.util.Arrays;

public class Cambios extends AppCompatActivity implements View.OnClickListener {

    Spinner spinnerGenero, spinnerSubgenero;
    EditText artista, album;
    CheckBox stock, importado;
    RadioButton preventa, disponible;
    Button cambiar, buscar, regresar;
    String genero, subgenero;
    String[] generos = {"Rock", "Pop", "Metal", "Urbano", "Electrónica"};
    String[] subgenerosRock = {"Alternativo", "Ska", "Punk", "Progresivo", "Grunge", "Hard", "Piscodélico"};
    String[] subgenerosPop = {"Mainstream", "Latino", "Independiente", "Punk"};
    String[] subgenerosMetal = {"Heavy", "Thrash", "Death", "Black", "Hardcore", "Electrocore", "Deathcore"};
    String[] subgenerosUrbano = {"Reggaeton", "Trap", "Rap", "Hip-hop"};
    String[] subgenerosElectronica = {"House", "EDM", "Dance", "Techno", "Drum and bass", "Hardcore"};
    int id = 0;


    private void listarGeneros(){
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, generos);
        spinnerGenero.setAdapter(adaptador);
    }

    private void listarRock(){
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, subgenerosRock);
        spinnerSubgenero.setAdapter(adaptador);
    }

    private void listarPop(){
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, subgenerosPop);
        spinnerSubgenero.setAdapter(adaptador);
    }

    private void listarMetal(){
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, subgenerosMetal);
        spinnerSubgenero.setAdapter(adaptador);
    }

    private void listarUrbano(){
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, subgenerosUrbano);
        spinnerSubgenero.setAdapter(adaptador);
    }

    private void listarElectronica(){
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, subgenerosElectronica);
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
        buscar.setEnabled(true);
        cambiar.setEnabled(false);
        stock.setEnabled(false);
        preventa.setEnabled(false);
        importado.setEnabled(false);
        disponible.setEnabled(false);
        spinnerGenero.setEnabled(false);
        spinnerSubgenero.setEnabled(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambios);

        spinnerGenero = findViewById(R.id.cambioGenero);
        spinnerSubgenero = findViewById(R.id.cambioSubgenero);
        spinnerGenero.setEnabled(false);
        spinnerSubgenero.setEnabled(false);

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

        artista = findViewById(R.id.cambioArtista);
        album = findViewById(R.id.cambioAlbum);

        stock = findViewById(R.id.cambioStock);
        importado = findViewById(R.id.cambioImportado);

        preventa = findViewById(R.id.opcCambio1);
        disponible = findViewById(R.id.opcCambio2);

        cambiar = findViewById(R.id.cambiosCambiar);
        buscar = findViewById(R.id.cambiosBuscar);
        regresar = findViewById(R.id.cambiosRegresar);

        cambiar.setOnClickListener(this);
        buscar.setOnClickListener(this);
        regresar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String cadena = ((Button)view).getText().toString();
        if(cadena.equals("Buscar")){
            String nombreArtista = artista.getText().toString();
            String nombreAlbum = album.getText().toString();
            Administrar admin = new Administrar(this, "Musica", null, 1);
            SQLiteDatabase baseDatos = admin.getReadableDatabase();
            if(!nombreArtista.equals("") && !nombreAlbum.equals("")){
                Cursor fila = baseDatos.rawQuery("SELECT estado, importacion, stock, genero, subgenero, id FROM albumes WHERE artista = '" + nombreArtista + "' AND album = '" + nombreAlbum + "'", null);
                if(fila.moveToFirst()){
                    String estado = fila.getString(0);
                    String importacion = fila.getString(1);
                    String stockDisponible = fila.getString(2);
                    String generoCad = fila.getString(3);
                    String subgeneroCad = fila.getString(4);
                    id = fila.getInt(5);
                    if(estado.equals("Preventa")){
                        preventa.setChecked(true);
                    }
                    else{
                        if(estado.equals("Disponible")){
                            disponible.setChecked(true);
                        }
                    }

                    if(importacion.equals("Importado")){
                        importado.setChecked(true);
                    }
                    if(stockDisponible.equals("Con stock")){
                        stock.setChecked(true);
                    }

                    for (int i = 0; i < generos.length; i++) {
                        if(generoCad.equals(generos[i])){
                            spinnerGenero.setSelection(i);
                            break;
                        }
                    }

                    String[] subgeneros = {""};

                    if(Arrays.asList(subgenerosRock).contains(subgeneroCad)){
                        subgeneros = subgenerosRock;
                    }
                    else{
                        if(Arrays.asList(subgenerosPop).contains(subgeneroCad)){
                            subgeneros = subgenerosPop;
                        }
                        else{
                            if(Arrays.asList(subgenerosMetal).contains(subgeneroCad)){
                                subgeneros = subgenerosMetal;
                            }
                            else{
                                if(Arrays.asList(subgenerosUrbano).contains(subgeneroCad)){
                                    subgeneros = subgenerosUrbano;
                                }
                                else{
                                    if(Arrays.asList(subgenerosElectronica).contains(subgeneroCad)){
                                        subgeneros = subgenerosElectronica;
                                    }
                                }
                            }
                        }
                    }

                    for (int i = 0; i < subgeneros.length; i++) {
                        if(subgeneroCad.equals(subgeneros[i])){
                            spinnerSubgenero.setSelection(i);
                            break;
                        }
                    }

                    buscar.setEnabled(false);
                    cambiar.setEnabled(true);
                    stock.setEnabled(true);
                    preventa.setEnabled(true);
                    importado.setEnabled(true);
                    disponible.setEnabled(true);
                    spinnerGenero.setEnabled(true);
                    spinnerSubgenero.setEnabled(true);
                }
                else{
                    Toast.makeText(this, "No existe el album "+ nombreAlbum + " de " + nombreArtista, Toast.LENGTH_SHORT).show();
                    artista.setText("");
                    album.setText("");
                }
            }
            else{
                Toast.makeText(this, "No dejes espacios en blanco", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            if(cadena.equals("Cambiar")) {
                String nombreArtista = artista.getText().toString();
                String nombreAlbum = album.getText().toString();
                if (preventa.isChecked() || disponible.isChecked()) {
                    String estado = "";
                    if (preventa.isChecked()) {
                        estado = "Preventa";
                    } else {
                        if (disponible.isChecked()) {
                            estado = "Disponible";
                        }
                    }

                    String cadenaStock = "Sin stock";
                    String cadenaImportado = "No es importado";
                    if (stock.isChecked()) {
                        cadenaStock = "Con stock";
                    }
                    if (importado.isChecked()) {
                        cadenaImportado = "Importado";
                    }
                    Administrar admin = new Administrar(this, "Musica", null, 1);
                    SQLiteDatabase baseDatos = admin.getWritableDatabase();
                    if(!nombreArtista.equals("") && !nombreAlbum.equals("")){
                        ContentValues registro = new ContentValues();
                        registro.put("id", id);
                        registro.put("artista", nombreArtista);
                        registro.put("album", nombreAlbum);
                        registro.put("estado", estado);
                        registro.put("stock", cadenaStock);
                        registro.put("importacion", cadenaImportado);
                        registro.put("genero", genero);
                        registro.put("subgenero", subgenero);
                        int cantidad = baseDatos.update("albumes", registro, "id = " + id, null);
                        baseDatos.close();
                        if(cantidad == 1){
                            Toast.makeText(this, "Cambio realizado", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(this, "Hubo un problema en el cambio", Toast.LENGTH_SHORT).show();
                        }
                        desactivarElementos();
                    }
                } else {
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
}