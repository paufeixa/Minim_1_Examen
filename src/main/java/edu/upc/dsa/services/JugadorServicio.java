package edu.upc.dsa.services;

import edu.upc.dsa.data.MyGameManager;
import edu.upc.dsa.data.MyGameManagerImpl;
import edu.upc.dsa.exceptions.*;
import edu.upc.dsa.models.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/jugador", description = "Endpoint to Jugador Service")
@Path("/jugador")
public class JugadorServicio {
    private MyGameManager gm;

    public JugadorServicio() {
        this.gm = MyGameManagerImpl.getInstance();
    }

    @POST
    @ApiOperation(value = "inicia una nueva Partida", notes = "Inicia un partida")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Éxito", response = Partida.class),
            @ApiResponse(code = 500, message = "Falta información"),
            @ApiResponse(code = 501, message = "No es posible iniciar la partida")
    })
    @Path("/partidaActual")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response iniciarPartida(IdJuegoJugador idJuegoJugador) throws NoIniciarJuegoException {
        if (idJuegoJugador.getIdJuego() == null || idJuegoJugador.getIdJugador() == null) {
            return Response.status(500).entity(idJuegoJugador).build();
        }
        try {
            this.gm.iniciarPartida(idJuegoJugador.getIdJuego(), idJuegoJugador.getIdJugador());
        }
        catch (NoIniciarJuegoException e) {
            return Response.status(501).entity(idJuegoJugador).build();
        }
        return Response.status(200).entity(idJuegoJugador).build();
    }

    @GET
    @ApiOperation(value = "obtiene el Nivel Actual", notes = "Obtiene el Nivel Actual del jugador")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Éxito", response = EnteroREST.class),
    })
    @Path("/{id}/nivel")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNivelActual(@PathParam("id") String idJugador) throws NoPartidaActivaException, NoJugadorException {
        EnteroREST nivel = new EnteroREST(this.gm.nivelActual(idJugador));
        return Response.status(200).entity(nivel).build();
    }

    @GET
    @ApiOperation(value = "obtiene la Puntuacion Actual", notes = "Obtiene la Puntuacion Actual del jugador")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Éxito", response = EnteroREST.class),
    })
    @Path("/{id}/puntos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPuntuacionActual(@PathParam("id") String idJugador) throws NoPartidaActivaException, NoJugadorException {
        EnteroREST puntosActuales = new EnteroREST(this.gm.puntosActuales(idJugador));
        return Response.status(200).entity(puntosActuales).build();
    }

    @PUT
    @ApiOperation(value = "pasa de Nivel", notes = "Pasa al siguiente nivel")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Éxito"),
            @ApiResponse(code = 404, message = "Jugador no encontrado"),
            @ApiResponse(code = 405, message = "No hay partida activa")
    })
    @Path("/{id}/siguienteNivel")
    public Response siguienteNivel(@PathParam("id") String idJugador) throws NoJugadorException, NoPartidaActivaException {
        try {
            int puntos = 75;
            String fecha = "10-11-2022";
            this.gm.siguienteNivel(puntos, idJugador, fecha);
        }
        catch (NoJugadorException e) {
            return Response.status(404).build();
        }
        catch (NoPartidaActivaException e) {
            return Response.status(405).build();
        }
        return Response.status(200).build();
    }

    @PUT
    @ApiOperation(value = "finaliza una Partida", notes = "Finaliza la partida actual")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Éxito"),
            @ApiResponse(code = 404, message = "Jugador no encontrado"),
            @ApiResponse(code = 405, message = "No hay partida activa")
    })
    @Path("/{id}/end")
    public Response finalPartida(@PathParam("id") String idJugador) throws NoJugadorException, NoPartidaActivaException {
        try {
            this.gm.finalJuego(idJugador);
        }
        catch (NoJugadorException e) {
            return Response.status(404).build();
        }
        catch (NoPartidaActivaException e) {
            return Response.status(405).build();
        }
        return Response.status(200).build();
    }

    @GET
    @ApiOperation(value = "obtiene las Partidas del Jugador", notes = "Obtiene las partidas que ha jugado un jugador")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Éxito", response = Partida.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Jugador no encontrado")
    })
    @Path("/{id}/partidas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response partidasJugador(@PathParam("id") String idJugador) throws NoJugadorException {
        try {
            List<Partida> partidas = this.gm.partidasJugador(idJugador);
            GenericEntity<List<Partida>> entity = new GenericEntity<List<Partida>>(partidas) {
            };
            return Response.status(200).entity(entity).build();
        }
        catch (NoJugadorException e) {
            return Response.status(404).build();
        }
    }

    @POST
    @ApiOperation(value = "obtiene la Actividad del Jugador", notes = "Obtiene la actividad de un jugador en un juego")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Éxito", response = ListaActividad.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Jugador no encontrado")
    })
    @Path("/actividades")
    @Produces(MediaType.APPLICATION_JSON)
    public Response partidasJugador(IdJuegoJugador idJuegoJugador) throws NoJugadorException {
        try {
            List<ListaActividad> actividades = this.gm.actividadJugadorEnJuego(idJuegoJugador.getIdJuego(), idJuegoJugador.getIdJugador());
            GenericEntity<List<ListaActividad>> entity = new GenericEntity<List<ListaActividad>>(actividades) {
            };
            return Response.status(200).entity(entity).build();
        }
        catch (NoJugadorException e) {
            return Response.status(404).build();
        }
    }

    @POST
    @ApiOperation(value = "crea un nuevo Jugador", notes = "Registra un nuevo jugador")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Éxito", response = Jugador.class),
            @ApiResponse(code = 500, message = "Falta información"),
            @ApiResponse(code = 501, message = "Jugador ya registrado")
    })
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response nuevoJugador(JugadorRegistro jugadorRegistro) throws JugadorExistenteException, NoJugadorException {
        Jugador jugador = new Jugador();
        if (jugadorRegistro.getIdJugador() == null) {
            return Response.status(500).entity(jugador).build();
        }
        try {
            this.gm.registro(jugadorRegistro.getIdJugador());
            jugador = this.gm.getJugador(jugadorRegistro.getIdJugador());
        }
        catch (JugadorExistenteException e) {
            return Response.status(501).entity(jugador).build();
        }
        return Response.status(200).entity(jugador).build();
    }
}
