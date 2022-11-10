package edu.upc.dsa.models;

import java.util.LinkedList;
import java.util.List;

public class Juego {
    String idJuego;
    String descripcion;
    int niveles;
    List<Jugador> jugadores;
    public Juego() {}

    public Juego(String idJuego, String descripcion, int niveles) {
        this.idJuego = idJuego;
        this.descripcion = descripcion;
        this.niveles = niveles;
        this.jugadores = new LinkedList<>();
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

    public List<Jugador> getJugadores() { return this.jugadores; }

    public void setJugadores(List<Jugador> jugadores) { this.jugadores = jugadores; }
}
