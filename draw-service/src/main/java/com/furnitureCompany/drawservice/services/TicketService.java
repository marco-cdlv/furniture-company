package com.furnitureCompany.drawservice.services;

import com.furnitureCompany.drawservice.model.Ticket;
import com.furnitureCompany.drawservice.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public void addTicket(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    public void addTickets(List<Ticket> tickets) {
       tickets.forEach(this::addTicket);
    }

    public List<Ticket> getAllTickets() {
        return (List<Ticket>) ticketRepository.findAll();
    }

    public List<Ticket> getTicketsByParticipantId(Long participantId) {
        return ticketRepository.getTicketsByParticipantId(participantId);
    }

    public Ticket getTicketById(Long ticketId) {
        return ticketRepository.getTicketByTicketId(ticketId);
    }
}
