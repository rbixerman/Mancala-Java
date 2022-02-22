package mancala.api;

import jakarta.servlet.http.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import mancala.api.models.ErrorDTO;
import mancala.api.models.MancalaDTO;

import mancala.api.models.MoveResultDTO;
import mancala.domain.MancalaImpl;
import mancala.domain.exceptions.MancalaException;

@Path("/move")
public class MoveMancala {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response initialize(@Context HttpServletRequest request, @QueryParam("index") int index) {
        HttpSession session = request.getSession(true);
        MancalaImpl mancala = (MancalaImpl) session.getAttribute("mancala");

        try {
            mancala.playPit(index);

            session.setAttribute("mancala", mancala);
            String playerOneName = (String) session.getAttribute("player1");
            String playerTwoName = (String) session.getAttribute("player2");

            MancalaDTO mancalaDTO = MancalaDTO.createMancalaDTO(mancala, playerOneName, playerTwoName);
            MoveResultDTO output = new MoveResultDTO(true, mancalaDTO, null);

            return Response.status(200).entity(output).build();

        } catch (MancalaException mancalaException) {
            MoveResultDTO output = new MoveResultDTO(false, null, mancalaException.getMessage());

            return Response.status(200).entity(output).build();
        }
    }
}
