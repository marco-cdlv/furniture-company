package com.furnitureCompany.drawservice.controllers;

import com.furnitureCompany.drawservice.model.Ticket;
import com.furnitureCompany.drawservice.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "v1/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @RequestMapping(value = "/{ticket_id}", method = RequestMethod.GET)
    public Ticket getTicketById(@PathVariable("ticket_id") Long ticketId) {
        return ticketService.getTicketById(ticketId);
    }

    @RequestMapping(value = "/participants/{participant_id}", method = RequestMethod.GET)
    public List<Ticket> getTicketsByParticipantId(@PathVariable("participant_id") Long participantId) {
        return ticketService.getTicketsByParticipantId(participantId);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Ticket addTicket(@RequestBody Ticket ticket) {
        return ticketService.addTicket(ticket);
    }
}
