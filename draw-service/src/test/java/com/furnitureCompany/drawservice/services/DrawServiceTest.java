package com.furnitureCompany.drawservice.services;

import com.furnitureCompany.drawservice.clients.PurchaseOrderDetailRestTemplateClient;
import com.furnitureCompany.drawservice.clients.PurchaseOrderRestTemplateClient;
import com.furnitureCompany.drawservice.model.*;
import com.furnitureCompany.drawservice.repository.DrawRepository;
import mockit.*;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class DrawServiceTest {

    @Tested
    DrawService drawService;

    @Injectable
    DrawRepository drawRepository;

    @Injectable
    PurchaseOrderRestTemplateClient purchaseOrderRestTemplateClient;

    @Injectable
    PurchaseOrderDetailRestTemplateClient purchaseOrderDetailRestTemplateClient;

    @Injectable
    TicketService ticketService;

    @Injectable
    ParticipantService participantService;

    @Injectable
    PrizeService prizeService;

    private Long drawId;

    @Before
    public void setup() {
        drawId = 14L;
    }

    @Test
    public void test_generateTickets_ItShouldGenerate24TicketsByAllChances() {
        // setup
        Map<Long, Integer> chancesByCustomerId = new HashMap<>();
        chancesByCustomerId.put(1L, 12);
        chancesByCustomerId.put(2L, 7);
        chancesByCustomerId.put(3L, 5);

        // execute
        List<Ticket> results = drawService.generateTickets(chancesByCustomerId);

        // verify
        assert results.size() == 24;
    }

    @Test
    public void test_generateTickets_ItShouldReturnNullIfChancesByCustomerIsNullOrEmpty() {
        assert drawService.generateTickets(Collections.EMPTY_MAP) == null;
    }

    @Test
    public void test_defineParticipants_ItShouldReturnTheListOfParticipants() {
        // setup
        Map<Long, List<Long>> ordersByParticipantId = new HashMap<>();
        ordersByParticipantId.put(1L, new ArrayList<>(Arrays.asList(1L, 2L)));
        ordersByParticipantId.put(2L, new ArrayList<>(Arrays.asList(3L)));
        ordersByParticipantId.put(3L, new ArrayList<>(Arrays.asList(4L)));

        // execute
        List<Participant> results = drawService.defineParticipants(drawId, ordersByParticipantId);

        // verify
        assert results!= null && results.size() == ordersByParticipantId.size();
    }

    @Test
    public void test_defineParticipants_ItShouldReturnNullIfOrdersByParticipantsAreNullOrEmpty() {
        assert drawService.defineParticipants(1L, Collections.EMPTY_MAP) == null;
    }

    @Test
    public void test_getWinnerTickets_itShouldReturnNullWhenTicketsAreEmpty() {
        // setup
        int amountOfPrizes = 5;

        // execute
        List<Ticket> results = drawService.getWinnerTickets(Collections.emptyList(), amountOfPrizes);

        // verify
        assert results == null;
    }

    @Test
    public void test_getWinnerTickets_itShouldReturnFiveWinnerTickets() {
        // setup
        int amountOfPrizes = 5;
        List<Ticket> tickets = getTickets(20);

        // execute
        List<Ticket> results = drawService.getWinnerTickets(tickets, amountOfPrizes);

        // verify
        assert results != null && results.size() == 5;
    }

    @Test
    public void test_drawTheActivePrizes_ItShouldReturnTheListOfWinners( ) {
        // setup
        List<Ticket> tickets = getTickets(10);
        List<Prize> activePrizes = getPrizes(3, drawId);

        new Expectations() {{
            prizeService.getActivePrizes();
            result = activePrizes;
        }};

        // execute
        List<Participant> results = drawService.drawTheActivePrizes(drawId, tickets);

        // verify
        assert results != null && !results.isEmpty();
    }

    @Test
    public void test_getChancesByCustomerId_ItShouldReturnAMapWithChancesByCustomer() {
        // setup
        List<PurchaseOrderDetail> purchaseOrderDetails = getPurchaseOrderDetail(2);
        Map<Long, List<Long>> ordersByParticipantId = new HashMap<>();
        ordersByParticipantId.put(1L, new ArrayList<>(Arrays.asList(1L)));

        new Expectations(){{
            purchaseOrderDetailRestTemplateClient.getPurchaseOrderDetailsByOrderIds((List<Long>) any);
            result = purchaseOrderDetails;
        }};

        // execute
        Map<Long, Integer> results = drawService.getChancesByCustomerId(ordersByParticipantId);

        // verify
        assert results != null && !results.isEmpty();
    }

    @Test
    public void test_getOrderIdsByParticipantId_ItShouldReturnAMapWithOrderByParticipant() {
        // setup
        List<PurchaseOrder> purchaseOrders = getPurchaseOrders(1);
        Date startDate = new Date();
        Date endDate = new Date();

        new Expectations(){{
            purchaseOrderRestTemplateClient.getPurchaseOrdersBetweenDates((Date)any, (Date)any);
            result = purchaseOrders;
        }};

        // execute
        Map<Long, List<Long>> results = drawService.getOrderIdsByParticipantId(startDate, endDate);

        // verify
        assert results != null && !results.isEmpty();
    }

    @Test
    public void test_getOrderIdsByParticipantId_ItShouldReturnAMapWithOrdersByParticipant() {
        // setup
        List<PurchaseOrder> purchaseOrders = getPurchaseOrders(2);
        Date startDate = new Date();
        Date endDate = new Date();

        new Expectations(){{
            purchaseOrderRestTemplateClient.getPurchaseOrdersBetweenDates((Date)any, (Date)any);
            result = purchaseOrders;
        }};

        // execute
        Map<Long, List<Long>> results = drawService.getOrderIdsByParticipantId(startDate, endDate);

        // verify
        assert results != null && !results.isEmpty();
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

    private List<Prize> getPrizes(int numberOfPrizes, Long drawId) {
        List<Prize> prizes = new ArrayList<>();

        for(int index = 0; index < numberOfPrizes; index++) {
            Prize prize = new Prize();
            prize.setName("King " + index);
            prize.setDescription("Summer prizes " + index);
            prize.setActive(true);
            prize.setDrawId(drawId);
            prizes.add(prize);
        }
        return prizes;
    }

    private List<PurchaseOrderDetail> getPurchaseOrderDetail(int numberOfPurchaseOrderDetail) {
        List<PurchaseOrderDetail> purchaseOrderDetails = new ArrayList<>();
        for(int index = 0; index < numberOfPurchaseOrderDetail; index++) {
            PurchaseOrderDetail purchaseOrderDetail = new PurchaseOrderDetail();
            purchaseOrderDetail.setPurchaseOrderDetailId(1L + index);
            purchaseOrderDetail.setPurchaseOrderId(2L + index);
            purchaseOrderDetail.setProductId(1L + index);
            purchaseOrderDetail.setQuantity(2 + index);
            purchaseOrderDetail.setChances(2 + index);
            purchaseOrderDetails.add(purchaseOrderDetail);
        }
        return purchaseOrderDetails;
    }

    private List<PurchaseOrder> getPurchaseOrders(int amountOfPurchaseOrders) {
        List<PurchaseOrder> purchaseOrders = new ArrayList<>();
        for(int index = 0; index < amountOfPurchaseOrders; index++) {
            PurchaseOrder purchaseOrder = new PurchaseOrder();
            purchaseOrder.setPurchaseOrderId(1L + index);
            purchaseOrder.setCustomerId(1L);
            purchaseOrder.setPurchaseDate(new Date());
            purchaseOrders.add(purchaseOrder);
        }
        return purchaseOrders;
    }
}