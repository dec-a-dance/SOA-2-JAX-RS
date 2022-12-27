package com.soa.jax.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soa.jax.dto.SearchDTO;
import com.soa.jax.dto.TicketDTO;
import com.soa.jax.entity.Ticket;
import com.soa.jax.entity.inner.Coordinates;
import com.soa.jax.repo.TicketRepo;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import javax.ejb.EJB;
import java.util.List;

@Path("/ticket")
public class TicketResource {
    @EJB
    private TicketRepo ticketRepo;

    ObjectMapper mapper = new ObjectMapper();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTicket(@PathParam("id") Long id) throws JsonProcessingException {
        mapper.findAndRegisterModules();
        return mapper.writeValueAsString(ticketRepo.getTicket(id));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String saveTicket(TicketDTO dto) throws JsonProcessingException {
        mapper.findAndRegisterModules();
        return mapper.writeValueAsString(ticketRepo.addTicket(dto));
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateTicket(TicketDTO dto) throws JsonProcessingException {
        mapper.findAndRegisterModules();
        return mapper.writeValueAsString(ticketRepo.update(dto));
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteTicket(@PathParam("id") Long id) throws JsonProcessingException {
        mapper.findAndRegisterModules();
        ticketRepo.delete(id);
        return "removed ticket with id = " + id;
    }

    @POST
    @Path("/all")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getTickets(@QueryParam("page") int page, @QueryParam("size") int size, SearchDTO dto) throws JsonProcessingException {
        mapper.findAndRegisterModules();
        List<Ticket> tickets = ticketRepo.getTickets(page, size, null, dto);
        StringBuilder resp = new StringBuilder("[");
        boolean comma = false;
        for (Ticket t: tickets){
            if (comma) {
                resp.append(", ");
            }
            comma = true;
            resp.append(mapper.writeValueAsString(t));
        }
        resp.append("]");
        return resp.toString();
    }

    @GET
    @Path("/get-min-by-coords")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMinByCoords() throws JsonProcessingException {
        mapper.findAndRegisterModules();
        return mapper.writeValueAsString(ticketRepo.getByMinCoords());
    }

    @GET
    @Path("/count-comments-less/{val}")
    @Produces(MediaType.APPLICATION_JSON)
    public String countCommentsLess(@PathParam("val") String value) throws JsonProcessingException {
        mapper.findAndRegisterModules();
        return mapper.writeValueAsString(ticketRepo.countCommentLess(value));
    }

    @GET
    @Path("/get-all-discount-less/{val}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllDiscountLess(@PathParam("val") Integer value) throws JsonProcessingException {
        mapper.findAndRegisterModules();
        return mapper.writeValueAsString(ticketRepo.getAllDiscountLess(value));
    }

}