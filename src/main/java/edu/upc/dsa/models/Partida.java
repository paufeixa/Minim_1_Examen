package edu.upc.dsa.models;

public class Partida {
    String idPartida;
    String descripcion;
    int niveles;
    int nivelActual;
    int puntosActuales;

    String fecha;

    public Partida() {
        this.idPartida = "";
        this.descripcion = "";
        this.niveles = 0;
        this.nivelActual = 0;
        this.puntosActuales = 0;
        this.fecha = "";
    }

    public Partida(String idPartida, String descripcion, int niveles) {
        this.idPartida = idPartida;
        this.descripcion = descripcion;
        this.niveles = niveles;
        this.nivelActual = 1;
        this.puntosActuales = 50;
        this.fecha = "";
    }

    public String getIdPartida() {
        return this.idPartida;
    }

    public void setIdPartida(String idPartida) {
        this.idPartida = idPartida;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getNiveles() {
        return this.niveles;
    }

    public void setNiveles(int niveles) {
        this.niveles = niveles;
    }

    public int getNivelActual() {
        return this.nivelActual;
    }

    public void setNivelActual(int nivelActual) {
        this.nivelActual = nivelActual;
    }

    public int getPuntosActuales() {
        return this.puntosActuales;
    }

    public void setPuntosActuales(int puntosActuales) {
        this.puntosActuales = puntosActuales;
    }

    public String getFecha() {
        return this.fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}