package com.example.proyecto;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Bajas extends AppCompatActivity implements View.OnClickListener {

    EditText album, artista;
    Button eliminar, regresar;

    private void desactivarElementos(){
        artista.setText("");
        album.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bajas);
        album = findViewById(R.id.bajasAlbum);
        artista = findViewById(R.id.bajasArtista);

        eliminar = findViewById(R.id.bajasEliminar);
        regresar = findViewById(R.id.bajasRegresar);
        eliminar.setOnClickListener(this);
        regresar.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        String cadena = ((Button)view).getText().toString();
        if(cadena.equals("Eliminar")){
            String nombreAlbum = album.getText().toString();
            String nombreArtista = artista.getText().toString();
            Administrar admin = new Administrar(this, "Musica", null, 1);
            SQLiteDatabase baseDatos = admin.getReadableDatabase();
            if(!nombreAlbum.equals("") && !nombreArtista.equals("")){
                Cursor fila = baseDatos.rawQuery("SELECT * FROM albumes WHERE artista = '" + nombreArtista +"' AND album = '" + nombreAlbum + "'", null);
                if(fila.moveToFirst()){
                    AlertDialog.Builder alerta = new AlertDialog.Builder(this);
                    alerta.setTitle("Importante");
                    alerta.setMessage("¿Está seguro de eliminar el álbum " + nombreAlbum + " del artista " + nombreArtista + "?");
                    alerta.setCancelable(false);
                    alerta.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            baseDatos.delete("albumes", "artista = '" + nombreArtista + "' AND album = '" + nombreAlbum + "'", null);
                            Toast.makeText(getApplicationContext(), "El álbum " + nombreAlbum + " del artista "+ nombreArtista + " ha sido eliminado", Toast.LENGTH_SHORT).show();
                            desactivarElementos();
                        }
                    });
                    alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getApplicationContext(), "No se eliminó el álbum " + nombreAlbum + " del artista " + nombreArtista, Toast.LENGTH_SHORT).show();
                            desactivarElementos();
                        }
                    });
                    alerta.show();
                }
                else{
                    Toast.makeText(this, "No existe el álbum " + nombreAlbum + " del artista " + nombreArtista, Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this, "No dejes espacios en blanco", Toast.LENGTH_SHORT).show();
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