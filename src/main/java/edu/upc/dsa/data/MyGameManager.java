package edu.upc.dsa.data;

import edu.upc.dsa.exceptions.*;
import edu.upc.dsa.models.*;

import java.util.List;

public interface MyGameManager {
    public void crearJuego(String idJuego, String description, int niveles);

    public void iniciarPartida(String idJuego, String idJugador) throws NoIniciarJuegoException;

    public int nivelActual(String idJugador) throws NoJugadorException, NoPartidaActivaException;

    public int puntosActuales(String idJugador) throws NoJugadorException, NoPartidaActivaException;

    public void siguienteNivel(int puntos, String idJugador, String fecha) throws NoJugadorException, NoPartidaActivaException;

    public void finalJuego(String idJugador) throws NoJugadorException, NoPartidaActivaException;

    public List<PuntuacionPorJugador> puntacionPorJugadorEnJuego(String idJuego) throws NoJuegoException;

    public List<Partida> partidasJugador(String idJugador) throws NoJugadorException;

    public List<ListaActividad> actividadJugadorEnJuego(String idJuego, String idJugador) throws NoJugadorException;

    public Jugador buscarJugador(String idJugador) throws NoJugadorException;

    public Partida partidaActiva(String idJugador) throws NoPartidaActivaException, NoJugadorException;

    public void registro(String idJugador) throws JugadorExistenteException;

    public int numJuegos();

    public int numJugadores();

    public Jugador getJugador(String idJugador) throws NoJugadorException;
}
