package com.furnitureCompany.drawservice.services;

import com.furnitureCompany.drawservice.clients.PurchaseOrderDetailRestTemplateClient;
import com.furnitureCompany.drawservice.clients.PurchaseOrderRestTemplateClient;
import com.furnitureCompany.drawservice.model.*;
import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(JMockit.class)
public class DrawServiceTest {

    private DrawService drawService = new DrawService();

    @Mocked
    PurchaseOrderDetailRestTemplateClient purchaseOrderDetailRestTemplateClient;

    @Mocked
    PurchaseOrderRestTemplateClient purchaseOrderRestTemplateClient;


    @Test
    public void test_generateTickets_ItShouldGenerate24TicketsByAllChances() {
        // setup
        Map<Long, Integer> chancesByCustomerId = new HashMap<>();
        chancesByCustomerId.put(1L, 12);
        chancesByCustomerId.put(2L, 7);
        chancesByCustomerId.put(3L, 5);

        // executes
        List<Ticket> results = drawService.generateTickets(chancesByCustomerId);

        // results
        assert results.size() == 24;
    }

    @Test
    public void test_generateTickets_ItShouldReturnNullIfChancesByCustomerIsNullOrEmpty() {
        assert drawService.generateTickets(Collections.EMPTY_MAP) == null;
    }

    @Test
    public void test_defineParticipants_ItShouldReturnTheListOfParticipants() {
        // setup
        Long drawId = 1L;
        Map<Long, List<Long>> ordersByParticipantId = new HashMap<>();
        ordersByParticipantId.put(1L, new ArrayList<>(Arrays.asList(1L, 2L)));
        ordersByParticipantId.put(2L, new ArrayList<>(Arrays.asList(3L)));
        ordersByParticipantId.put(3L, new ArrayList<>(Arrays.asList(4L)));

        // executes
        List<Participant> results = drawService.defineParticipants(drawId, ordersByParticipantId);

        // results
        assert !results.isEmpty();
    }

    @Test
    public void test_defineParticipants_ItShouldReturnNullIfOrdersByParticipantsAreNullOrEmpty() {
        assert drawService.defineParticipants(1L, Collections.EMPTY_MAP) == null;
    }

    @Test
    public void test_getWinnerTickets_itShouldReturnNullWhenTicketsAreEmpty() {
        // setup
        int amountOfPrices = 5;

        // executes
        List<Ticket> results = drawService.getWinnerTickets(Collections.emptyList(), amountOfPrices);

        // results
        assert results == null;
    }

    @Test
    public void test_getWinnerTickets_itShouldReturnFiveWinnerTickets() {
        // setup
        int amountOfPrizes = 5;
        List<Ticket> tickets = getTickets(20);

        // executes
        List<Ticket> results = drawService.getWinnerTickets(tickets, amountOfPrizes);

        // results
        assert results.size() == 5;
    }

    private List<Ticket> getTickets(int numberOfTickets) {
        List<Ticket> tickets = new ArrayList<>();
        for(int index = 0; index < numberOfTickets; index++) {
            Ticket ticket = new Ticket();
            ticket.setTicketNumber(10 + numberOfTickets);
            ticket.setParticipantId((long) index);
            ticket.setTicketId((long) index);
            tickets.add(ticket);
        }
        return tickets;
    }
}