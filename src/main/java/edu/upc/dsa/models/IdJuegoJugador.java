package edu.upc.dsa.models;

public class IdJuegoJugador {
    String idJuego;
    String idJugador;

    public IdJuegoJugador() {

    }

    public IdJuegoJugador(String idJuego, String idJugador) {
        this.idJuego = idJuego;
        this.idJugador = idJugador;
    }

    public String getIdJuego() { return this.idJuego; }

    public void setIdJuego(String idJuego) { this.idJuego = idJuego; }

    public String getIdJugador() { return this.idJugador; }

    public void setIdJugador(String idJugador) { this.idJugador = idJugador; }
}
