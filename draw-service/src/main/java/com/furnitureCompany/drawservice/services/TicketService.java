package com.furnitureCompany.drawservice.services;

import com.furnitureCompany.drawservice.model.Ticket;
import com.furnitureCompany.drawservice.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public void addTicket(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    public void addTickets(List<Ticket> tickets) {
        ticketRepository.saveAll(tickets);
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

    public List<Ticket> generateTickets(Map<Long, Integer> chancesByCustomerId) {
        if(chancesByCustomerId == null || chancesByCustomerId.isEmpty()) {
            return null;
        }

        List<Ticket> tickets = new ArrayList<>();
        chancesByCustomerId.forEach((customerId, chances) -> {
            for (int index = 0; index < chances; index++) {
                Ticket ticket = new Ticket();
                ticket.setParticipantId(customerId);
                ticket.setTicketNumber((int) (index + customerId));
                tickets.add(ticket);
            }
        });
        return tickets;
    }

    List<Ticket> getWinnerTickets(List<Ticket> tickets, int amountOfPrizes) {
        if (tickets == null || tickets.isEmpty()) {
            return null;
        }

        int amountOfTickets = tickets.size();
        Map<Long, Ticket> winnerTicketNumbersByParticipant = new HashMap<>();
        int count = 0;

        while(count < amountOfPrizes) {
            Random random = new Random();
            Integer winnerTicketNumber =  random.nextInt(amountOfTickets);

            Ticket winnerTicket =  tickets.get(winnerTicketNumber);

            if(!winnerTicketNumbersByParticipant.containsKey(winnerTicket.getParticipantId())) {
                winnerTicketNumbersByParticipant.put(winnerTicket.getParticipantId(), winnerTicket);
                count++;
            };
        }

        List<Ticket> winnerTickets = winnerTicketNumbersByParticipant.values().stream().collect(Collectors.toList());
        return winnerTickets;
    }
}
