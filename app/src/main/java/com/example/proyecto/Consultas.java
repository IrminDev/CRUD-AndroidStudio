package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Consultas extends AppCompatActivity implements View.OnClickListener {
    EditText artista, album;
    TextView estado, importado, stock, genero, subgenero;
    Button cambiar, regresar;

    public void desactivarElementos(){
        artista.setText("");
        album.setText("");
        estado.setText("Estado:");
        importado.setText("Importado:");
        stock.setText("Stock:");
        genero.setText("Género:");
        subgenero.setText("Subgénero:");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas);

        artista = findViewById(R.id.consultaArtista);
        album = findViewById(R.id.consultaAlbum);

        estado = findViewById(R.id.consultaEstado);
        importado = findViewById(R.id.consultaImportado);
        stock = findViewById(R.id.consultaStock);
        genero = findViewById(R.id.consultaGenero);
        subgenero = findViewById(R.id.consultaSubgenero);

        cambiar = findViewById(R.id.consultaConsultar);
        regresar = findViewById(R.id.consultaRegresar);

        cambiar.setOnClickListener(this);
        regresar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String cadena = ((Button)view).getText().toString();
        if(cadena.equals("Consulta")){
            String nombreArtista = artista.getText().toString();
            String nombreAlbum = album.getText().toString();
            Administrar admin = new Administrar(this, "Musica", null, 1);
            SQLiteDatabase baseDatos = admin.getReadableDatabase();
            if(!nombreAlbum.equals("") && !nombreArtista.equals("")){
                Cursor fila = baseDatos.rawQuery("SELECT estado, importacion, stock, genero, subgenero FROM albumes WHERE artista = '" + nombreArtista + "' AND album = '" + nombreAlbum + "'", null);
                if(fila.moveToFirst()){
                    estado.setText("Estado: " + fila.getString(0));
                    importado.setText("Importación: " + fila.getString(1));
                    stock.setText("Stock: " + fila.getString(2));
                    genero.setText("Género: " + fila.getString(3));
                    subgenero.setText("Subgénero: " + fila.getString(4));
                }
                else{
                    Toast.makeText(this, "No existe el album "+ nombreAlbum + " de " + nombreArtista, Toast.LENGTH_SHORT).show();
                    desactivarElementos();
                }
                baseDatos.close();
            }
            else{
                Toast.makeText(this, "No dejes espacios en blanco", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            if(cadena.equals("Regresar")){
                Intent intento = new Intent(this, MainActivity.class);
                startActivity(intento);
                desactivarElementos();
            }
        }
    }
}