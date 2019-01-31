package com.furnitureCompany.drawservice.services;

import com.furnitureCompany.drawservice.clients.ProductRestTemplateClient;
import com.furnitureCompany.drawservice.clients.PurchaseOrderDetailRestTemplateClient;
import com.furnitureCompany.drawservice.clients.PurchaseOrderRestTemplateClient;
import com.furnitureCompany.drawservice.model.*;
import com.furnitureCompany.drawservice.repository.DrawRepository;
import com.furnitureCompany.drawservice.utils.Utils;
import mockit.*;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

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

    @Injectable
    ProductRestTemplateClient productRestTemplateClient;

    private Long drawId;

    @Before
    public void setup() {
        drawId = 14L;
    }

    @Test
    public void test_drawTheActivePrizes_ItShouldReturnTheListOfWinners( ) throws Exception {
        // setup
        List<Ticket> tickets = Utils.getTickets(10);
        List<Prize> activePrizes = Utils.getPrizes(3, drawId);
        List<Participant> participants = Utils.getParticipants(3, drawId);

        new Expectations() {{
            prizeService.getActivePrizes();
            result = activePrizes;

            ticketService.getWinnerTickets((List<Ticket>)any, anyInt);
            result = Utils.getTickets(3);
        }};

        // execute
        List<Participant> results = drawService.drawTheActivePrizes(drawId, participants, tickets);

        // verify
        assert results != null && !results.isEmpty();
    }

    @Test
    public void test_getChancesByCustomerId_ItShouldReturnAMapWithChancesByCustomer() {
        // setup

        Map<Long, List<Long>> ordersByParticipantId = new HashMap<>();
        ordersByParticipantId.put(1L, new ArrayList<>(Arrays.asList(1L)));

        new Expectations(){{
            purchaseOrderDetailRestTemplateClient.getPurchaseOrderDetailsByOrderIds((List<Long>) any);
            result = Utils.getPurchaseOrderDetail(1);;

            productRestTemplateClient.getProductsByIds((List<Long>) any);
            result = Utils.getProducts(1);
        }};

        // execute
        Map<Long, Integer> results = drawService.getChancesByCustomerId(ordersByParticipantId);

        // verify
        assert results != null && !results.isEmpty();
    }

    @Test
    public void test_getOrderIdsByParticipantId_ItShouldReturnAMapWithOrderByParticipant() {
        // setup
        List<PurchaseOrder> purchaseOrders = Utils.getPurchaseOrders(1);
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
        List<PurchaseOrder> purchaseOrders = Utils.getPurchaseOrders(2);
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
}