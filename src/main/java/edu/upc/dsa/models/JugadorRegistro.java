package edu.upc.dsa.models;

public class JugadorRegistro {
    String idJugador;

    public JugadorRegistro() {
    }

    public JugadorRegistro(String idJugador) {
        this.idJugador = idJugador;
    }

    public String getIdJugador() {
        return this.idJugador;
    }

    public void setIdJugador(String idJugador) {
        this.idJugador = idJugador;
    }
}