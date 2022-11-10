package edu.upc.dsa.models;

public class Actividad {
    int nivel;
    int puntos;
    String fecha;

    public Actividad() {}

    public Actividad(int nivel, int puntos, String fecha) {
        this.nivel = nivel;
        this.puntos = puntos;
        this.fecha = fecha;
    }

    public int getNivel() { return this.nivel; }

    public void setNivel(int nivel) { this.nivel = nivel; }

    public int getPuntos() { return this.puntos; }

    public void setPuntos(int puntos) { this.puntos = puntos; }

    public String getFecha() { return this.fecha; }

    public void setFecha(String fecha) { this.fecha = fecha; }
}
