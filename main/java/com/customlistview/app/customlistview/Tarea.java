package com.customlistview.app.customlistview;

/**
 * Created by Gerard on 15/12/2015.
 */
public class Tarea {
        private String nombre;
        private String hora;
        private int categoria;

    public Tarea(String nombre, String hora, int categoria) {
        this.nombre = nombre;
        this.hora = hora;
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }
}
