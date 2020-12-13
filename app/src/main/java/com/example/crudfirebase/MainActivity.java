package com.example.crudfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    EditText etNombre, etArtista, etGenero, etFecha, etPais;
    FloatingActionButton fabGuardar, fabListar;

    ProgressDialog progressDialog;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String updateId, updateNombre, updateArtista, updateGenero, updateFecha, updatePais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNombre = findViewById(R.id.etNombre);
        etArtista = findViewById(R.id.etArtista);
        etGenero = findViewById(R.id.etGenero);
        etFecha = findViewById(R.id.etFecha);
        etPais = findViewById(R.id.etPais);

        fabGuardar = findViewById(R.id.fabGuardar);
        fabListar = findViewById(R.id.fabListar);

        progressDialog = new ProgressDialog(this);

        db = FirebaseFirestore.getInstance();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Agregar registro");


        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            actionBar.setTitle("Actualizar Datos");

            updateId = bundle.getString("updateId");
            updateNombre = bundle.getString("updateNombre");
            updateArtista = bundle.getString("updateArtista");
            updateGenero = bundle.getString("updateGenero");
            updateFecha = bundle.getString("updateFecha");
            updatePais = bundle.getString("updatePais");

            etNombre.setText(updateNombre);
            etArtista.setText(updateArtista);
            etGenero.setText(updateGenero);
            etFecha.setText(updateFecha);
            etPais.setText(updatePais);

        } else {
            actionBar.setTitle("Agregar");
        }


        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 != null) {
                    String id = updateId;
                    String nombre = etNombre.getText().toString().trim();
                    String artista = etArtista.getText().toString().trim();
                    String genero = etGenero.getText().toString().trim();
                    String fecha = etFecha.getText().toString().trim();
                    String pais = etPais.getText().toString().trim();

                    actualizarDatos(id, nombre, artista, genero, fecha, pais);

                } else {
                    String nombre = etNombre.getText().toString().trim();
                    String artista = etArtista.getText().toString().trim();
                    String genero = etGenero.getText().toString().trim();
                    String fecha = etFecha.getText().toString().trim();
                    String pais = etPais.getText().toString().trim();

                    cargarDatos(nombre, artista, genero, fecha, pais);
                }
            }
        });


        fabListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ListActivityPerson.class));
                finish();
            }
        });

    }


    private void cargarDatos(String nombre, String artista, String genero, String fecha, String pais) {
        progressDialog.setTitle("Agregar datos");
        progressDialog.show();
        String id = UUID.randomUUID().toString();

        Map<String, Object> doc = new HashMap<>();
        doc.put("id", id);
        doc.put("nombre", nombre);
        doc.put("artista", artista);
        doc.put("genero", genero);
        doc.put("fecha", fecha);
        doc.put("pais", pais);


        db.collection("Documents").document(id).set(doc).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Datos almacenados con éxito...", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Ha ocurrido un error..." + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void actualizarDatos(String id, String nombre, String artista, String genero, String fecha, String pais) {
        progressDialog.setTitle("Actualizando datos a Firebase");
        progressDialog.show();

        db.collection("Documents")
                .document(id).update(
                "nombre", nombre,
                "artista", artista,
                "genero", genero,
                "fecha", fecha,
                "pais", pais
        )
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Actualización exitosa...", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Ha ocurrido un error..." + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}