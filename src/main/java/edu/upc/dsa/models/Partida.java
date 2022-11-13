package edu.upc.dsa.models;

import java.util.ArrayList;
import java.util.List;

public class Partida {
    String idPartida;
    String descripcion;
    int niveles;
    int nivelActual;
    int puntosActuales;
    String fecha;
    List<Actividad> actividades;

    public Partida() {}

    public Partida(String idPartida, String descripcion, int niveles) {
        this.idPartida = idPartida;
        this.descripcion = descripcion;
        this.niveles = niveles;
        this.nivelActual = 1;
        this.puntosActuales = 50;
        this.fecha = "";
        this.actividades = new ArrayList<>();
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

    public List<Actividad> getActividad() {
        return actividades;
    }

    public void setActividad(List<Actividad> actividades) {
        this.actividades = actividades;
    }

    public void crearActividad(Actividad actividad) {
        this.actividades.add(actividad);
    }
}