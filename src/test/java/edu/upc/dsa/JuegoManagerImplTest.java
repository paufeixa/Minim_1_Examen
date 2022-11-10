package edu.upc.dsa;

import edu.upc.dsa.data.MyGameManager;
import edu.upc.dsa.data.MyGameManagerImpl;
import edu.upc.dsa.exceptions.NoIniciarJuegoException;
import edu.upc.dsa.exceptions.NoJugadorException;
import edu.upc.dsa.exceptions.NoPartidaActivaException;
import edu.upc.dsa.models.Actividad;
import edu.upc.dsa.models.Juego;
import edu.upc.dsa.models.Jugador;
import edu.upc.dsa.models.Partida;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class JuegoManagerImplTest {
    MyGameManager gm;

    @Before
    public void setUp() throws NoJugadorException, NoIniciarJuegoException {
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
    public void testNivelesYProgreso() throws NoJugadorException, NoPartidaActivaException {
        Assert.assertEquals(1,this.gm.nivelActual("Pau"));
        Assert.assertEquals(50,this.gm.puntosActuales("Pau"));
        this.gm.siguienteNivel(100,"Pau","10-11-2022");
        Assert.assertEquals(2,this.gm.nivelActual("Pau"));
        this.gm.siguienteNivel(200,"Pau","11-11-2022");
        Assert.assertEquals(3,this.gm.nivelActual("Pau"));
        this.gm.siguienteNivel(500,"Pau","12-12-2022");
        Assert.assertEquals(0,this.gm.nivelActual("Pau"));
    }

    @Test
    public void testFinalJuego() throws NoJugadorException, NoPartidaActivaException {
        this.gm.finalJuego("Pau");
        Assert.assertEquals(0,this.gm.nivelActual("Pau"));
    }

    @Test
    public void testPartidasUsuario() throws NoJugadorException, NoPartidaActivaException, NoIniciarJuegoException {
        List<Partida> partidas1 = this.gm.partidasUsuario("Pau");
        Assert.assertEquals(0,partidas1.size());

        this.gm.finalJuego("Pau");
        List<Partida> partidas2 = this.gm.partidasUsuario("Pau");
        Assert.assertEquals(1,partidas2.size());

        this.gm.iniciarPartida("Juego2","Pau");
        this.gm.finalJuego("Pau");
        List<Partida> partidas3 = this.gm.partidasUsuario("Pau");
        Assert.assertEquals(2,partidas3.size());
    }

    @Test
    public void testActividad() throws NoPartidaActivaException, NoJugadorException, NoIniciarJuegoException {
        this.gm.siguienteNivel(200,"Pau","10-11-2022");
        this.gm.siguienteNivel(300,"Pau","11-11-2022");
        this.gm.siguienteNivel(200,"Pau","12-11-2022");

        this.gm.iniciarPartida("Juego1","Pau");
        this.gm.siguienteNivel(200,"Pau","13-11-2022");
        this.gm.siguienteNivel(300,"Pau","14-11-2022");
        this.gm.finalJuego("Pau");

        List<Actividad> actividades1 = this.gm.actividadJugadorEnJuego("Juego1","Pau");
        Assert.assertEquals(2,actividades1.size());

        this.gm.iniciarPartida("Juego2","Pau");
        this.gm.finalJuego("Pau");
        List<Actividad> actividades2 = this.gm.actividadJugadorEnJuego("Juego1","Pau");
        Assert.assertEquals(2,actividades2.size());
    }
}
