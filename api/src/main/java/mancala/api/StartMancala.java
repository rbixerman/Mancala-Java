package mancala.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import mancala.api.models.MancalaDTO;
import mancala.api.models.PlayerInputDTO;
import mancala.domain.Mancala;
import mancala.domain.MancalaImpl;

@Path("/start")
public class StartMancala {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response initialize(@Context HttpServletRequest request,
                               PlayerInputDTO players) {
        Mancala mancala = new MancalaImpl();
        String namePlayer1 = players.getNameplayer1();
        String namePlayer2 = players.getNameplayer2();

        HttpSession session = request.getSession(true);
        session.setAttribute("mancala", mancala);
        session.setAttribute("player2", namePlayer2);
        session.setAttribute("player1", namePlayer1);

        MancalaDTO output = MancalaDTO.createMancalaDTO(mancala, namePlayer1, namePlayer2);
        return Response.status(200).entity(output).build();
    }
}
