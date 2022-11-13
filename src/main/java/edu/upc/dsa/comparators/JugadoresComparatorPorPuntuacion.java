package edu.upc.dsa.comparators;

import edu.upc.dsa.models.PuntuacionPorJugador;

import java.util.Comparator;

public class JugadoresComparatorPorPuntuacion implements Comparator<PuntuacionPorJugador> {
    public int compare(PuntuacionPorJugador puntuacionPorJugador1, PuntuacionPorJugador puntuacionPorJugador2) {
        return (puntuacionPorJugador2.getPuntos() - puntuacionPorJugador1.getPuntos());
    }
}
