package com.furnitureCompany.drawservice.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.furnitureCompany.drawservice.clients.ProductRestTemplateClient;
import com.furnitureCompany.drawservice.clients.PurchaseOrderDetailRestTemplateClient;
import com.furnitureCompany.drawservice.clients.PurchaseOrderRestTemplateClient;
import com.furnitureCompany.drawservice.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DrawService {

    @Autowired
    private PurchaseOrderRestTemplateClient purchaseOrderRestTemplateClient;

    @Autowired
    private PurchaseOrderDetailRestTemplateClient purchaseOrderDetailRestTemplateClient;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    PrizeService prizeService;

    @Autowired
    PromotionService promotionService;

    @Autowired
    ProductRestTemplateClient productRestTemplateClient;

    private static final Logger logger = LoggerFactory.getLogger(DrawService.class);

    public List<Participant> drawPrize(Long promotionId) throws Exception {
        Promotion promotion = promotionService.getPromotionById(promotionId);

        if (promotion == null) {
            throw new Exception("The sent promotion id does not exists!!");
        }
        // Adds the participants to the current draw
        Map<Long, List<Long>> ordersByParticipantId = getOrderIdsByParticipantId(promotion.getStartDate(), promotion.getEndDate());
        List<Participant> participants = participantService.defineParticipants(promotion.getPromotionId(), ordersByParticipantId);

        if (participants == null || participants.isEmpty()) {
            throw new Exception(String.valueOf(new StringBuilder("Not found participants for the promotion dates: ")
                    .append(promotion.getStartDate())
                    .append(" <--> ")
                    .append(promotion.getEndDate())));
        }

        participantService.addParticipants(participants);

        // Generates the tickets and adds it to DB.
        Map<Long, Integer> chancesByCustomerId = getChancesByCustomerId(ordersByParticipantId);
        List<Ticket> tickets = ticketService.generateTickets(chancesByCustomerId);
        ticketService.addTickets(tickets);

        // Draws the active prizes
        List<Participant> winners = drawTheActivePrizes(promotion.getPromotionId(), participants, tickets);

        if (winners != null && !winners.isEmpty()) {
            promotion.setActive(false);
            promotionService.updatePromotion(promotion,promotionId);
        }

        participantService.addWinners(winners);
        return winners;
    }

    List<Participant> drawTheActivePrizes(Long promotionId, List<Participant> participants, List<Ticket> tickets) throws Exception {
        List<Participant> winners = new ArrayList<>();
        List<Prize> activePrizes = prizeService.getActivePrizesByPromotionId(promotionId, true);

        if (activePrizes == null || activePrizes.isEmpty()) {
            throw new Exception("Not found prizes, please ensure that prizes were registered!!");
        }

        List<Ticket> winnerTickets = ticketService.getWinnerTickets(tickets, activePrizes.size());

        for (int index = 0; index < activePrizes.size(); index++) {
            int finalIndex = index;
            Participant winner = participants.stream()
                    .filter(participant -> participant.getCustomerId() == winnerTickets.get(finalIndex).getParticipantId())
                    .findAny()
                    .orElse(null);

            if (winner != null) {
                winner.setWinner(true);
                winners.add(winner);
                activePrizes.get(index).setPromotionId(promotionId);
                activePrizes.get(index).setActive(false);
            }
        }
        logger.debug("DRAW-SERVICE -> Winners: " + winners);
        return winners;
    }

    Map<Long, List<Long>> getOrderIdsByParticipantId(Date startDate, Date endDate) {
        Map<Long, List<Long>> ordersByCustomerId = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        List<PurchaseOrder> purchaseOrders = mapper.convertValue(purchaseOrderRestTemplateClient.getPurchaseOrdersBetweenDates(startDate, endDate),
                new TypeReference<List<PurchaseOrder>>(){});

        purchaseOrders.forEach(purchaseOrder -> {
            Long customerId = purchaseOrder.getCustomerId();
            Long orderId = purchaseOrder.getPurchaseOrderId();

            groupChildIdsByParentId(ordersByCustomerId, customerId, orderId);
        });
        return ordersByCustomerId;
    }

    Map<Long, Integer> getChancesByCustomerId(Map<Long, List<Long>> ordersByParticipantId) {
        Map<Long, Integer> chancesByCustomerId = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        List<Long> purchaseOrderIds = new ArrayList<>();
        ordersByParticipantId.forEach((customerId, orders) -> {
            purchaseOrderIds.addAll(orders);
        });
        List<PurchaseOrderDetail> purchaseOrderDetails = mapper.convertValue(purchaseOrderDetailRestTemplateClient.getPurchaseOrderDetailsByOrderIds(purchaseOrderIds),
                new TypeReference<List<PurchaseOrderDetail>>(){});

        Map<Long, Integer> chancesByPurchaseOrder = getChancesByPurchaseOrderId(purchaseOrderDetails);

        ordersByParticipantId.forEach((customerId, orderIds) -> {
            chancesByCustomerId.put(customerId, orderIds.stream().mapToInt(orderId -> chancesByPurchaseOrder.get(orderId)).sum());
        });
        return chancesByCustomerId;
    }

    private Map<Long, Integer> getChancesByPurchaseOrderId(List<PurchaseOrderDetail> purchaseOrderDetails) {
        Map<Long, Integer> numberOfChancesByPurchaseOrder = new HashMap<>();

        ObjectMapper mapper = new ObjectMapper();
        getProductIdsByPurchaseOrder(purchaseOrderDetails).forEach((purchaseOrderId, productIds)->{
            List<Product> products = mapper.convertValue(productRestTemplateClient.getProductsByIds(productIds),
                    new TypeReference<List<Product>>(){});

            int chances = products.stream().mapToInt(Product::getNumberChances).sum();
            numberOfChancesByPurchaseOrder.put(purchaseOrderId, chances);
        });

        return numberOfChancesByPurchaseOrder;
    }

    private Map<Long, List<Long>> getProductIdsByPurchaseOrder(List<PurchaseOrderDetail> purchaseOrderDetails) {
        Map<Long, List<Long>> productIdsByPurchaseOrder = new HashMap<>();

        purchaseOrderDetails.forEach(purchaseOrderDetail -> {
            Long purchaseOrderId = purchaseOrderDetail.getPurchaseOrderId();
            Long productId = purchaseOrderDetail.getProductId();

            groupChildIdsByParentId(productIdsByPurchaseOrder, purchaseOrderId, productId);
        });

        return productIdsByPurchaseOrder;
    }

    private void groupChildIdsByParentId(Map<Long, List<Long>> childIdsByParentId, Long parentId, Long childId) {
        if (!childIdsByParentId.containsKey(parentId)) {
            List<Long> orderIds = new ArrayList<>();
            orderIds.add(childId);
            childIdsByParentId.put(parentId, orderIds);
        } else {
            childIdsByParentId.get(parentId).add(childId);
        }
    }
}
