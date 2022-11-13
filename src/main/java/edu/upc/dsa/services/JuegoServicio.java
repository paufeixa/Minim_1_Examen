package edu.upc.dsa.services;

import edu.upc.dsa.data.MyGameManager;
import edu.upc.dsa.data.MyGameManagerImpl;
import edu.upc.dsa.exceptions.NoJuegoException;
import edu.upc.dsa.exceptions.NoJugadorException;
import edu.upc.dsa.models.Juego;
import edu.upc.dsa.models.JuegoCrear;
import edu.upc.dsa.models.JugadorRegistro;
import edu.upc.dsa.models.PuntuacionPorJugador;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/juego", description = "Endpoint to Object Service")
@Path("/juego")
public class JuegoServicio {
    private MyGameManager gm;

    public JuegoServicio() {
        this.gm = MyGameManagerImpl.getInstance();
    }

    @POST
    @ApiOperation(value = "crear un nuevo Juego", notes = "Crea un nuevo juego")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Éxito", response= JuegoCrear.class),
            @ApiResponse(code = 500, message = "Falta información")
    })
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response nuevoJuego(JuegoCrear juegoCrear) {
        if (juegoCrear.getIdJuego() == null || juegoCrear.getDescripcion() == null || juegoCrear.getNiveles() == 0) {
            return Response.status(500).entity(juegoCrear).build();
        }
        this.gm.crearJuego(juegoCrear.getIdJuego(), juegoCrear.getDescripcion(), juegoCrear.getNiveles());
        return Response.status(200).entity(juegoCrear).build();
    }

    @GET
    @ApiOperation(value = "obtiene todos los Jugadores", notes = "Obtiene todos los jugadores ordenados por puntos descendientemente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Éxito", response = PuntuacionPorJugador.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Juego no encontrado")
    })
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersByOrder(@PathParam("id") String idJuego) throws NoJuegoException {
        try {
            List<PuntuacionPorJugador> users = this.gm.puntacionPorJugadorEnJuego(idJuego);
            GenericEntity<List<PuntuacionPorJugador>> entity = new GenericEntity<List<PuntuacionPorJugador>>(users) {
            };
            return Response.status(200).entity(entity).build();
        }
        catch (NoJuegoException e) {
            return Response.status(404).build();
        }
    }
}
