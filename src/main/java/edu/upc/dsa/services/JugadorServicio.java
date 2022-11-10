package edu.upc.dsa.services;

import edu.upc.dsa.data.MyGameManager;
import edu.upc.dsa.data.MyGameManagerImpl;
import edu.upc.dsa.exceptions.NoJugadorException;
import edu.upc.dsa.exceptions.NoPartidaActivaException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api(value = "/jugador", description = "Endpoint to User Service")
@Path("/jugador")
public class JugadorServicio {
    private MyGameManager gm;

    public JugadorServicio() {
        this.gm = MyGameManagerImpl.getInstance();
    }

    @GET
    @ApiOperation(value = "obtiene el Nivel Actual", notes = "obtiene el Nivel Actual del jugador")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = int.class),
    })
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNivelActual(@PathParam("id") String idJugador) throws NoPartidaActivaException, NoJugadorException {
        int nivel = this.gm.nivelActual(idJugador);
        return Response.status(200).entity(nivel).build();
    }

    @GET
    @ApiOperation(value = "obtiene la Puntuacion Actual", notes = "obtiene la Puntuacion Actual del jugador")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = int.class),
    })
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPuntuacionActual(@PathParam("id") String idJugador) throws NoPartidaActivaException, NoJugadorException {
        int puntosActuales = this.gm.puntosActuales(idJugador);
        return Response.status(200).entity(puntosActuales).build();
    }
}
