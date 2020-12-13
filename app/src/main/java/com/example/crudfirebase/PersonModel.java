package com.example.crudfirebase;

public class PersonModel {

    String id, nombre, artista, genero, fecha, pais;

    public PersonModel(String id, String nombre, String artista, String genero, String fecha, String pais) {
        this.id = id;
        this.nombre = nombre;
        this.artista = artista;
        this.genero = genero;
        this.fecha = fecha;
        this.pais = pais;
    }

    public PersonModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) { this.artista = artista; }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getPais() { return pais; }

    public void setPais(String pais) {
        this.pais = pais;
    }

}
