package edu.upc.dsa.data;

import edu.upc.dsa.comparators.JugadoresComparatorPorPuntuacion;
import edu.upc.dsa.exceptions.*;
import edu.upc.dsa.models.*;
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
        logger.info("Juego creado con id '" + idJuego + "' y " + niveles + " niveles");
    }

    @Override
    public void iniciarPartida(String idJuego, String idJugador) throws NoIniciarJuegoException {
        if (!juegos.containsKey(idJuego) || !jugadores.containsKey(idJugador)) {
            logger.warn("No se puede crear la partida. Juego o jugador no existen");
            throw new NoIniciarJuegoException();
        }
        Juego juego = juegos.get(idJuego);
        Jugador jugador = jugadores.get(idJugador);
        if (jugador.getPartidaActual() != null) {
            logger.warn("El jugador ya está en una partida");
            throw new NoIniciarJuegoException();
        }
        Partida partida = new Partida(juego.getIdJuego(), juego.getDescripcion(), juego.getNiveles());
        jugador.setPartidaActual(partida);
        PuntuacionPorJugador puntuacionPorJugador = new PuntuacionPorJugador(idJugador, jugador.getPartidaActual().getPuntosActuales());
        juego.puntuacionPorJugador(puntuacionPorJugador);
        Actividad actividad = new Actividad(partida.getNivelActual(), partida.getPuntosActuales(), partida.getFecha());
        jugador.getPartidaActual().crearActividad(actividad);
        logger.info("Partida iniciada por el jugador '" + idJugador + "' en el juego '" + idJuego + "'");
    }

    @Override
    public int nivelActual(String idJugador) throws NoJugadorException, NoPartidaActivaException {
        Partida partidaActual = partidaActiva(idJugador);
        logger.info("El jugador '" + idJugador + "' está en el nivel " + partidaActual.getNivelActual() + " en el juego '" + partidaActual.getIdPartida() + "'");
        return partidaActual.getNivelActual();
    }

    @Override
    public int puntosActuales(String idJugador) throws NoJugadorException, NoPartidaActivaException {
        Partida partidaActual = partidaActiva(idJugador);
        logger.info("El jugador '" + idJugador + "' tiene " + partidaActual.getPuntosActuales() + " en el juego '" + partidaActual.getIdPartida() + "'");
        return partidaActual.getPuntosActuales();
    }

    @Override
    public void siguienteNivel(int puntos, String idJugador, String fecha) throws NoJugadorException, NoPartidaActivaException {
        Partida partidaActual = partidaActiva(idJugador);
        Jugador jugador = jugadores.get(idJugador);
        if (partidaActual.getNiveles() == partidaActual.getNivelActual()) {
            int puntosActuales = partidaActual.getPuntosActuales();
            partidaActual.setPuntosActuales(puntosActuales+puntos+100);
            partidaActual.setFecha(fecha);
            PuntuacionPorJugador puntuacionPorJugador = new PuntuacionPorJugador(idJugador, partidaActual.getPuntosActuales());
            juegos.get(partidaActual.getIdPartida()).puntuacionPorJugadorActualizada(puntuacionPorJugador);
            Actividad actividad = new Actividad(partidaActual.getNivelActual(), partidaActual.getPuntosActuales(), partidaActual.getFecha());
            partidaActual.crearActividad(actividad);
            jugador.finPartida();
            logger.info("El jugador '" + idJugador + "' ha finalizado la partida '"+ partidaActual.getIdPartida() + "' al haber completado todos los niveles");
        }
        else {
            int puntosActuales = partidaActual.getPuntosActuales();
            partidaActual.setPuntosActuales(puntosActuales+puntos);
            int nivelActual = partidaActual.getNivelActual();
            partidaActual.setNivelActual(nivelActual+1);
            partidaActual.setFecha(fecha);
            PuntuacionPorJugador puntuacionPorJugador = new PuntuacionPorJugador(idJugador, partidaActual.getPuntosActuales());
            juegos.get(partidaActual.getIdPartida()).puntuacionPorJugadorActualizada(puntuacionPorJugador);
            Actividad actividad = new Actividad(partidaActual.getNivelActual(), partidaActual.getPuntosActuales(), partidaActual.getFecha());
            partidaActual.crearActividad(actividad);
            logger.info("El jugador '" + idJugador + "' ha pasado al siguiente nivel (Nivel " + partidaActual.getNivelActual() + ") en la partida '"+ partidaActual.getIdPartida() + "'");
        }
    }

    @Override
    public void finalJuego(String idJugador) throws NoJugadorException, NoPartidaActivaException {
        Partida partidaActual = partidaActiva(idJugador);
        Jugador jugador = jugadores.get(idJugador);
        Actividad actividad = new Actividad(partidaActual.getNivelActual(), partidaActual.getPuntosActuales(), partidaActual.getFecha());
        partidaActual.crearActividad(actividad);
        jugador.finPartida();
        logger.info("El jugador '" + idJugador + "' ha decidido finalizar la partida '"+ partidaActual.getIdPartida() + "'");
    }

    @Override
    public List<PuntuacionPorJugador> puntacionPorJugadorEnJuego(String idJuego) throws NoJuegoException {
        if (!juegos.containsKey(idJuego)) {
            logger.warn("Juego inexistente");
            throw new NoJuegoException();
        }
        List<PuntuacionPorJugador> puntuacionPorJugador = juegos.get(idJuego).getPuntuacionesPorJugador();
        puntuacionPorJugador.sort(new JugadoresComparatorPorPuntuacion());
        logger.info("Lista ordenada por puntuaciones en el juego '" + idJuego + "'");
        return puntuacionPorJugador;
    }

    @Override
    public List<Partida> partidasJugador(String idJugador) throws NoJugadorException {
        buscarJugador(idJugador);
        Jugador jugador = jugadores.get(idJugador);
        List<Partida> partidas = jugador.getPartidas();
        logger.info("Lista de partidas del jugador '" + idJugador + "'");
        return partidas;
    }

    @Override
    public List<ListaActividad> actividadJugadorEnJuego(String idJuego, String idJugador) throws NoJugadorException{
        buscarJugador(idJugador);
        Jugador jugador = jugadores.get(idJugador);
        List<ListaActividad> actividades = jugador.actividad(idJuego);
        logger.info("Lista de actividad del jugador '" + idJugador + "' en el juego '" + idJuego + "'");
        return actividades;
    }

    @Override
    public Jugador buscarJugador(String idJugador) throws NoJugadorException {
        if (!jugadores.containsKey(idJugador)) {
            logger.warn("Jugador inexistente");
            throw new NoJugadorException();
        }
        return jugadores.get(idJugador);
    }

    public Partida partidaActiva(String idJugador) throws NoPartidaActivaException, NoJugadorException {
        Jugador jugador = buscarJugador(idJugador);
        if (jugador.getPartidaActual() == null) {
            logger.warn("El jugador '" + idJugador + "' no tiene ninguna partida activa");
            throw new NoPartidaActivaException();
        }
        return jugador.getPartidaActual();
    }

    @Override
    public void registro(String idJugador) throws JugadorExistenteException {
        if (jugadores.containsKey(idJugador)) {
            logger.warn("Jugador existente. Error al registrar");
            throw new JugadorExistenteException();
        }
        Jugador nuevoJugador = new Jugador(idJugador);
        jugadores.put(idJugador, nuevoJugador);
        logger.info("Jugador registrado con id '" + idJugador + "'");
    }

    @Override
    public int numJuegos() { return this.juegos.size(); }

    @Override
    public int numJugadores() { return this.jugadores.size(); }

    @Override
    public Jugador getJugador(String idJugador) throws NoJugadorException {
        buscarJugador(idJugador);
        return jugadores.get(idJugador);
    }

    public static MyGameManager getInstance() {
        if (instance==null) {
            instance = new MyGameManagerImpl();
        }
        return instance;
    }
}
