package edu.upc.dsa.services;

import edu.upc.dsa.data.MyGameManager;
import edu.upc.dsa.data.MyGameManagerImpl;
import edu.upc.dsa.exceptions.NoIniciarJuegoException;
import edu.upc.dsa.models.Juego;
import edu.upc.dsa.models.JuegoCrear;
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
            @ApiResponse(code = 200, message = "Successful", response= Juego.class),
            @ApiResponse(code = 500, message = "Missing Information")
    })
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newObject(JuegoCrear juego) {
        if (juego.getIdJuego() == null || juego.getDescripcion() == null || juego.getNiveles() == 0) {
            return Response.status(500).entity(juego).build();
        }
        this.gm.crearJuego(juego.getIdJuego(), juego.getDescripcion(), juego.getNiveles());
        return Response.status(200).entity(juego).build();
    }
}
