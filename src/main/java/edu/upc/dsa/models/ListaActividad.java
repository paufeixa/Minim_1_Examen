package edu.upc.dsa.models;

import java.util.List;

public class ListaActividad {
    List<Actividad> actividades;

    public ListaActividad() {
    }

    public ListaActividad(List<Actividad> actividades) {
        this.actividades = actividades;
    }

    public List<Actividad> getActividades() { return this.actividades; }

    public void setActividades(List<Actividad> actividades) { this.actividades = actividades; }
}
