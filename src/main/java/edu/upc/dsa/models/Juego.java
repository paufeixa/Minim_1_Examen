package edu.upc.dsa.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Juego {
    String idJuego;
    String descripcion;
    int niveles;
    List<PuntuacionPorJugador> puntuacionesPorJugador;
    public Juego() {}

    public Juego(String idJuego, String descripcion, int niveles) {
        this.idJuego = idJuego;
        this.descripcion = descripcion;
        this.niveles = niveles;
        this.puntuacionesPorJugador = new ArrayList<>();
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

    public List<PuntuacionPorJugador> getPuntuacionesPorJugador() { return this.puntuacionesPorJugador; }

    public void setPuntuacionesPorJugador(List<PuntuacionPorJugador> puntuacionesPorJugador) { this.puntuacionesPorJugador = puntuacionesPorJugador; }

    public void puntuacionPorJugador(PuntuacionPorJugador puntuacionPorJugador) {
        this.puntuacionesPorJugador.add(puntuacionPorJugador);
    }

    public void puntuacionPorJugadorActualizada(PuntuacionPorJugador puntuacionPorJugador) {
        int i = this.puntuacionesPorJugador.size() - 1;
        boolean encontrado = false;
        while (i >= 0 && !encontrado) {
            if (Objects.equals(this.puntuacionesPorJugador.get(i).getIdJugador(), puntuacionPorJugador.getIdJugador())) {
                this.puntuacionesPorJugador.set(i, puntuacionPorJugador);
                encontrado = true;
            }
            i = i - 1;
        }
    }
}
