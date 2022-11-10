package edu.upc.dsa.models;

import edu.upc.dsa.exceptions.NoPartidaActivaException;

import java.util.*;

public class Jugador {
    String idJugador;
    Partida partidaActual;

    String fecha;

    List<Partida> partidas;

    public Jugador() {
    }

    public Jugador(String idJugador) {
        this.idJugador = idJugador;
        this.partidaActual = new Partida();
        this.partidas = new LinkedList<>();
    }

    public String getIdJugador() {
        return this.idJugador;
    }

    public void setIdJugador(String idJugador) {
        this.idJugador = idJugador;
    }

    public Partida getPartidaActual() {
        return this.partidaActual;
    }

    public void setPartidaActual(Partida partidaActual) {
        this.partidaActual = partidaActual;
    }

    public List<Partida> getPartidas() {
        return partidas;
    }

    public void setPartidas(List<Partida> partidas) {
        this.partidas = partidas;
    }

    public void finPartida() {
        partidas.add(this.partidaActual);
        Partida partidaVacia = new Partida();
        this.partidaActual = partidaVacia;
    }

    public void partidaActiva() throws NoPartidaActivaException {
        if (partidaActual.getIdPartida() == null) {
            throw new NoPartidaActivaException();
        }
    }

    public List<Actividad> actividad(String idJuego) {
        List<Actividad> actividades = new ArrayList<>();
        int i = 1;
        while (i < partidas.size()) {
            if (partidas.get(i).getIdPartida() == idJuego) {
                int nivel = partidas.get(i).getNivelActual();
                int puntos = partidas.get(i).getPuntosActuales();
                String fecha = partidas.get(i).getFecha();
                Actividad actividad = new Actividad(nivel, puntos, fecha);
                actividades.add(actividad);
            }
            i = i + 1;
        }
        return actividades;
    }
}
