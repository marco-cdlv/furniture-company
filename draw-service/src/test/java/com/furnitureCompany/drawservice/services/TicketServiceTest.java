package com.furnitureCompany.drawservice.services;

import com.furnitureCompany.drawservice.model.Ticket;
import com.furnitureCompany.drawservice.utils.Utils;
import mockit.Tested;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class TicketServiceTest {
    @Tested
    TicketService ticketService;

    @Test
    public void test_generateTickets_ItShouldGenerate24TicketsByAllChances() {
        // setup
        Map<Long, Integer> chancesByCustomerId = new HashMap<>();
        chancesByCustomerId.put(1L, 12);
        chancesByCustomerId.put(2L, 7);
        chancesByCustomerId.put(3L, 5);

        // execute
        List<Ticket> results = ticketService.generateTickets(chancesByCustomerId);

        // verify
        assert results.size() == 24;
    }

    @Test
    public void test_generateTickets_ItShouldReturnNullIfChancesByCustomerIsNullOrEmpty() {
        assert ticketService.generateTickets(Collections.EMPTY_MAP) == null;
    }

    @Test
    public void test_getWinnerTickets_itShouldReturnNullWhenTicketsAreEmpty() {
        // setup
        int amountOfPrizes = 5;

        // execute
        List<Ticket> results = ticketService.getWinnerTickets(Collections.emptyList(), amountOfPrizes);

        // verify
        assert results == null;
    }

    @Test
    public void test_getWinnerTickets_itShouldReturnFiveWinnerTickets() {
        // setup
        int amountOfPrizes = 5;
        List<Ticket> tickets = Utils.getTickets(20);

        // execute
        List<Ticket> results = ticketService.getWinnerTickets(tickets, amountOfPrizes);

        // verify
        assert results != null && results.size() == 5;
    }
}