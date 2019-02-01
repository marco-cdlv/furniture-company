package com.furnitureCompany.drawservice.repository;

import com.furnitureCompany.drawservice.model.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, String> {
    List<Ticket> getTicketsByParticipantId(Long participantId);
    Ticket getTicketByTicketId(Long ticketId);
    //List<Ticket> saveAll(List<Ticket> tickets);
}
