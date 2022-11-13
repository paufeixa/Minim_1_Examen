package edu.upc.dsa;

import edu.upc.dsa.data.MyGameManager;
import edu.upc.dsa.data.MyGameManagerImpl;
import edu.upc.dsa.exceptions.*;
import edu.upc.dsa.models.Actividad;
import edu.upc.dsa.models.ListaActividad;
import edu.upc.dsa.models.Partida;
import edu.upc.dsa.models.PuntuacionPorJugador;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class JuegoManagerImplTest {
    MyGameManager gm;

    @Before
    public void setUp() throws NoIniciarJuegoException, JugadorExistenteException {
        gm = new MyGameManagerImpl();
        gm.registro("Pau");
        gm.registro("Joan");
        gm.registro("Marc");


        gm.crearJuego("Juego1","Primer juego",3);
        gm.crearJuego("Juego2","Segundo juego",4);
        gm.crearJuego("Juego3","Tercer juego",5);
        gm.crearJuego("Juego4","Cuarto juego",6);

        gm.iniciarPartida("Juego1","Pau");
    }

    @After
    public void tearDown() { this.gm = null; }

    @Test
    public void testNuevoJugador() throws JugadorExistenteException {
        Assert.assertEquals(3,this.gm.numJugadores());

        this.gm.registro("Enric");
        Assert.assertEquals(4,this.gm.numJugadores());
    }

    @Test
    public void testNuevoJuego() {
        Assert.assertEquals(4,this.gm.numJuegos());

        this.gm.crearJuego("Juego5", "Quinto juego", 5);
        Assert.assertEquals(5,this.gm.numJuegos());
    }

    @Test
    public void testNuevaPartida() throws NoIniciarJuegoException, NoJugadorException {
        this.gm.iniciarPartida("Juego4", "Marc");
        Assert.assertEquals("Juego4",this.gm.getJugador("Marc").getPartidaActual().getIdPartida());
        Assert.assertEquals(50,this.gm.getJugador("Marc").getPartidaActual().getPuntosActuales());

        this.gm.iniciarPartida("Juego2", "Joan");
        Assert.assertEquals("Juego2",this.gm.getJugador("Joan").getPartidaActual().getIdPartida());
        Assert.assertEquals(4,this.gm.getJugador("Joan").getPartidaActual().getNiveles());
        Assert.assertEquals(1,this.gm.getJugador("Joan").getPartidaActual().getNivelActual());
    }

    @Test
    public void testNivelesYProgreso() throws NoJugadorException, NoPartidaActivaException {
        Assert.assertEquals(1, this.gm.nivelActual("Pau"));
        Assert.assertEquals(50, this.gm.puntosActuales("Pau"));

        this.gm.siguienteNivel(100, "Pau","10-11-2022");
        Assert.assertEquals(2, this.gm.nivelActual("Pau"));
        Assert.assertEquals(150, this.gm.puntosActuales("Pau"));

        this.gm.siguienteNivel(200, "Pau","11-11-2022");
        Assert.assertEquals(3, this.gm.nivelActual("Pau"));
        Assert.assertEquals(350, this.gm.puntosActuales("Pau"));

        this.gm.siguienteNivel(500, "Pau","12-12-2022");
        Assert.assertEquals(null, this.gm.getJugador("Pau").getPartidaActual());
        Assert.assertEquals(950, this.gm.getJugador("Pau").getPartidas().get(0).getPuntosActuales());

    }

    @Test
    public void testFinalJuego() throws NoJugadorException, NoPartidaActivaException, NoIniciarJuegoException {
        this.gm.finalJuego("Pau");
        Assert.assertNull(this.gm.getJugador("Pau").getPartidaActual());

        this.gm.iniciarPartida("Juego4", "Marc");
        Assert.assertEquals("Juego4", this.gm.getJugador("Marc").getPartidaActual().getIdPartida());
        this.gm.finalJuego("Marc");
        Assert.assertNull(this.gm.getJugador("Marc").getPartidaActual());
    }

    @Test
    public void testComparadorPuntos() throws NoPartidaActivaException, NoJugadorException, NoJuegoException, NoIniciarJuegoException {
        this.gm.siguienteNivel(100, "Pau","10-11-2022");
        this.gm.siguienteNivel(200, "Pau","11-11-2022");
        this.gm.siguienteNivel(500, "Pau","12-12-2022");

        this.gm.iniciarPartida("Juego1", "Marc");
        this.gm.siguienteNivel(100, "Marc","13-11-2022");
        this.gm.siguienteNivel(200, "Marc","14-11-2022");

        this.gm.iniciarPartida("Juego1", "Joan");
        this.gm.siguienteNivel(100, "Joan","10-11-2022");

        List<PuntuacionPorJugador> puntuacionPorJugador = this.gm.puntacionPorJugadorEnJuego("Juego1");
        Assert.assertEquals("Pau", puntuacionPorJugador.get(0).getIdJugador());
        Assert.assertEquals(950, puntuacionPorJugador.get(0).getPuntos());
        Assert.assertEquals("Marc", puntuacionPorJugador.get(1).getIdJugador());
        Assert.assertEquals(350, puntuacionPorJugador.get(1).getPuntos());
        Assert.assertEquals("Joan", puntuacionPorJugador.get(2).getIdJugador());
        Assert.assertEquals(150, puntuacionPorJugador.get(2).getPuntos());
    }

    @Test
    public void testPartidasUsuario() throws NoJugadorException, NoPartidaActivaException, NoIniciarJuegoException {
        List<Partida> partidas1 = this.gm.partidasJugador("Pau");
        Assert.assertEquals(0,partidas1.size());

        this.gm.finalJuego("Pau");
        List<Partida> partidas2 = this.gm.partidasJugador("Pau");
        Assert.assertEquals(1,partidas2.size());
        Assert.assertEquals("Juego1", this.gm.getJugador("Pau").getPartidas().get(0).getIdPartida());

        this.gm.iniciarPartida("Juego2","Pau");
        this.gm.finalJuego("Pau");
        List<Partida> partidas3 = this.gm.partidasJugador("Pau");
        Assert.assertEquals(2,partidas3.size());
        Assert.assertEquals("Juego1", this.gm.getJugador("Pau").getPartidas().get(0).getIdPartida());
        Assert.assertEquals("Juego2", this.gm.getJugador("Pau").getPartidas().get(1).getIdPartida());
    }

    @Test
    public void testActividad() throws NoPartidaActivaException, NoJugadorException, NoIniciarJuegoException {
        this.gm.siguienteNivel(200,"Pau","10-11-2022");
        this.gm.siguienteNivel(300,"Pau","11-11-2022");
        this.gm.siguienteNivel(200,"Pau","12-11-2022");
        List<ListaActividad> actividades1 = this.gm.actividadJugadorEnJuego("Juego1","Pau");
        Assert.assertEquals(1,actividades1.size());
        Assert.assertEquals(4,actividades1.get(0).getActividades().size());

        this.gm.iniciarPartida("Juego1","Pau");
        this.gm.siguienteNivel(200,"Pau","13-11-2022");
        this.gm.finalJuego("Pau");
        List<ListaActividad> actividades2 = this.gm.actividadJugadorEnJuego("Juego1","Pau");
        Assert.assertEquals(2,actividades2.size());
        Assert.assertEquals(4,actividades2.get(0).getActividades().size());
        Assert.assertEquals(3,actividades2.get(1).getActividades().size());

        this.gm.iniciarPartida("Juego2","Pau");
        this.gm.finalJuego("Pau");
        List<ListaActividad> actividades3 = this.gm.actividadJugadorEnJuego("Juego1","Pau");
        Assert.assertEquals(2,actividades3.size());
        List<ListaActividad> actividades4 = this.gm.actividadJugadorEnJuego("Juego2","Pau");
        Assert.assertEquals(1,actividades4.size());
        Assert.assertEquals(2,actividades4.get(0).getActividades().size());
    }
}
