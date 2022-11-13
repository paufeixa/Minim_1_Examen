package edu.upc.dsa.models;

public class PuntuacionPorJugador {
    String idJugador;
    int puntos;

    public PuntuacionPorJugador() {}

    public PuntuacionPorJugador(String idJugador, int puntos) {
        this.idJugador = idJugador;
        this.puntos = puntos;
    }

    public String getIdJugador() { return this.idJugador; }

    public void setIdJugador(String idJugador) { this.idJugador = idJugador; }

    public int getPuntos() { return this.puntos; }

    public void setPuntos(int puntos) { this.puntos = puntos; }
}
