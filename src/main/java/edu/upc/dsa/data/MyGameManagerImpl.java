package edu.upc.dsa.data;

import edu.upc.dsa.exceptions.NoIniciarJuegoException;
import edu.upc.dsa.exceptions.NoPartidaActivaException;
import edu.upc.dsa.exceptions.NoJugadorException;
import edu.upc.dsa.models.Actividad;
import edu.upc.dsa.models.Juego;
import edu.upc.dsa.models.Jugador;
import edu.upc.dsa.models.Partida;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;

public class MyGameManagerImpl implements MyGameManager {
    private static MyGameManager instance;
    final static Logger logger = Logger.getLogger(MyGameManagerImpl.class);
    HashMap<String, Jugador> jugadores;
    HashMap<String, Juego> juegos;

    public MyGameManagerImpl() {
        jugadores = new HashMap<>();
        juegos = new HashMap<>();
    }

    @Override
    public void crearJuego(String idJuego, String description, int niveles) {
        Juego juego = new Juego(idJuego, description, niveles);
        juegos.put(juego.getIdJuego(), juego);
    }

    @Override
    public void iniciarPartida(String idJuego, String idJugador) throws NoIniciarJuegoException {
        if (!juegos.containsKey(idJuego) || !jugadores.containsKey(idJugador)) {
            throw new NoIniciarJuegoException();
        }
        Juego juego = juegos.get(idJuego);
        Partida partida = new Partida(juego.getIdJuego(), juego.getDescripcion(), juego.getNiveles());
        Jugador jugador = jugadores.get(idJugador);
        if (jugador.getPartidaActual().getIdPartida() == null) {
            throw new NoIniciarJuegoException();
        }
        jugador.setPartidaActual(partida);
    }

    @Override
    public int nivelActual(String idJugador) throws NoJugadorException, NoPartidaActivaException {
        buscarJugador(idJugador);
        Jugador jugador = jugadores.get(idJugador);
        jugador.partidaActiva();
        return jugador.getPartidaActual().getNivelActual();
    }

    @Override
    public int puntosActuales(String idJugador) throws NoJugadorException, NoPartidaActivaException {
        buscarJugador(idJugador);
        Jugador jugador = jugadores.get(idJugador);
        jugador.partidaActiva();
        return jugador.getPartidaActual().getPuntosActuales();
    }

    public void siguienteNivel(int puntos, String idJugador, String fecha) throws NoJugadorException, NoPartidaActivaException {
        buscarJugador(idJugador);
        Jugador jugador = jugadores.get(idJugador);
        jugador.partidaActiva();
        Partida partida = jugador.getPartidaActual();
        if (partida.getNiveles() == partida.getNivelActual()) {
            int puntosActuales = jugador.getPartidaActual().getPuntosActuales();
            jugador.getPartidaActual().setPuntosActuales(puntosActuales+100);
            jugador.getPartidaActual().setFecha(fecha);
            jugador.finPartida();
        }
        else {
            int nivelActual = jugador.getPartidaActual().getNivelActual();
            jugador.getPartidaActual().setNivelActual(nivelActual+1);
            jugador.getPartidaActual().setFecha(fecha);
        }
    }

    public void finalJuego(String idJugador) throws NoJugadorException, NoPartidaActivaException {
        buscarJugador(idJugador);
        Jugador jugador = jugadores.get(idJugador);
        jugador.partidaActiva();
        jugador.finPartida();
    }

    @Override
    public List<Partida> partidasUsuario(String idJugador) throws NoJugadorException {
        buscarJugador(idJugador);
        Jugador jugador = jugadores.get(idJugador);
        List<Partida> partidas = jugador.getPartidas();
        return partidas;
    }

    @Override
    public List<Actividad> actividadJugadorEnJuego(String idJuego, String idJugador) throws NoJugadorException{
        buscarJugador(idJugador);
        Jugador jugador = jugadores.get(idJugador);
        List<Actividad> actividades = jugador.actividad(idJuego);
        return actividades;
    }

    public void buscarJugador(String idJugador) throws NoJugadorException {
        if (!jugadores.containsKey(idJugador)) {
            throw new NoJugadorException();
        }
    }

    public void registro(String idJugador) {
        Jugador nuevoJugador = new Jugador(idJugador);
        jugadores.put(idJugador, nuevoJugador);
    }

    public static MyGameManager getInstance() {
        if (instance==null) {
            instance = new MyGameManagerImpl();
        }
        return instance;
    }
}
