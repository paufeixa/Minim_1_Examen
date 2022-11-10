package edu.upc.dsa.models;

public class JuegoCrear {
    String idJuego;
    String descripcion;
    int niveles;

    public JuegoCrear() {
    }

    public JuegoCrear(String idJuego, String descripcion, int niveles) {
        this.idJuego = idJuego;
        this.descripcion = descripcion;
        this.niveles = niveles;
    }

    public String getIdJuego() {
        return this.idJuego;
    }

    public void setIdJuego(String idJuego) {
        this.idJuego = idJuego;
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
}
