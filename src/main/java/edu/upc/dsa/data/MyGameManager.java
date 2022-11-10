package edu.upc.dsa.data;

import edu.upc.dsa.exceptions.NoIniciarJuegoException;
import edu.upc.dsa.exceptions.NoPartidaActivaException;
import edu.upc.dsa.exceptions.NoJugadorException;
import edu.upc.dsa.models.Actividad;
import edu.upc.dsa.models.Juego;
import edu.upc.dsa.models.Partida;

import java.util.List;

public interface MyGameManager {
    public void crearJuego(String idJuego, String description, int niveles);

    public void iniciarPartida(String idJuego, String idJugador) throws NoIniciarJuegoException;

    public int nivelActual(String idJugador) throws NoJugadorException, NoPartidaActivaException;

    public int puntosActuales(String idJugador) throws NoJugadorException, NoPartidaActivaException;

    public void siguienteNivel(int puntos, String idJugador, String fecha) throws NoJugadorException, NoPartidaActivaException;

    public void finalJuego(String idJugador) throws NoJugadorException, NoPartidaActivaException;

    public void buscarJugador(String idJugador) throws NoJugadorException;

    public List<Partida> partidasUsuario(String idJugador) throws NoJugadorException;

    public List<Actividad> actividadJugadorEnJuego(String idJuego, String idJugador) throws NoJugadorException;

    public void registro(String idJugador);
}
