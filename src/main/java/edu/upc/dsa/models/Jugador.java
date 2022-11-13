package edu.upc.dsa.models;

import edu.upc.dsa.exceptions.NoPartidaActivaException;

import java.util.*;

public class Jugador {
    String idJugador;
    Partida partidaActual;

    List<Partida> partidas;

    public Jugador() {}

    public Jugador(String idJugador) {
        this.idJugador = idJugador;
        this.partidaActual = null;
        this.partidas = new ArrayList<>();
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
        this.partidaActual = null;
    }

    public List<ListaActividad> actividad(String idJuego) {
        List<ListaActividad> actividades = new ArrayList<>();
        int i = 0;
        while (i < partidas.size()) {
            if (Objects.equals(partidas.get(i).getIdPartida(), idJuego)) {
                ListaActividad listaActividad = new ListaActividad(partidas.get(i).getActividad());
                actividades.add(listaActividad);
            }
            i = i + 1;
        }
        return actividades;
    }
}
