package com.furnitureCompany.drawservice.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.furnitureCompany.drawservice.clients.ProductRestTemplateClient;
import com.furnitureCompany.drawservice.clients.PurchaseOrderDetailRestTemplateClient;
import com.furnitureCompany.drawservice.clients.PurchaseOrderRestTemplateClient;
import com.furnitureCompany.drawservice.model.*;
import com.furnitureCompany.drawservice.repository.DrawRepository;
import net.bytebuddy.implementation.bytecode.Throw;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DrawService {

    @Autowired
    private DrawRepository drawRepository;

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

    public void addDraw(Draw draw) {
        drawRepository.save(draw);
    }

    public List<Draw> getAllDraws() {
        return (List<Draw>) drawRepository.findAll();
    }

    public Draw getDrawById(Long drawId) {
        return drawRepository.getDrawByDrawId(drawId);
    }

    public void removeAllDraws() {
        drawRepository.deleteAll();
    }

    public List<Participant> drawPrize(Draw draw) throws Exception {
        // Adds a new draw
        addDraw(draw);
        Promotion promotion = promotionService.getPromotionById(draw.getPromotionId());

        if (promotion == null) {
            throw new Exception("The sent promotion id does not exists!!");
        }

        // Adds the participants to the current draw
        Map<Long, List<Long>> ordersByParticipantId = getOrderIdsByParticipantId(promotion.getStartDate(), promotion.getEndDate());
        List<Participant> participants = defineParticipants(draw.getDrawId(), ordersByParticipantId);

        if (participants == null || participants.isEmpty()) {
            throw new Exception(String.valueOf(new StringBuilder("Not found participants for the promotion dates: ")
                    .append(promotion.getStartDate())
                    .append(" <--> ")
                    .append(promotion.getEndDate())));
        }
        participantService.addParticipants(participants);

        // Generates the tickets and adds it to DB.
        Map<Long, Integer> chancesByCustomerId = getChancesByCustomerId(ordersByParticipantId);
        List<Ticket> tickets = generateTickets(chancesByCustomerId);
        ticketService.addTickets(tickets);

        // Draws the active prizes
        List<Participant> winners = drawTheActivePrizes(draw.getDrawId(), participants, tickets);
        participantService.addWinners(winners);
        return winners;
    }

    List<Participant> drawTheActivePrizes(Long drawId, List<Participant> participants, List<Ticket> tickets) throws Exception {
        List<Participant> winners = new ArrayList<>();
        List<Prize> activePrizes = prizeService.getActivePrizes();

        if (activePrizes == null || activePrizes.isEmpty()) {
            throw new Exception("Not found prizes!!");
        }

        List<Ticket> winnerTickets = getWinnerTickets(tickets, activePrizes.size());

        for (int index = 0; index < activePrizes.size(); index++) {
            int finalIndex = index;
            Participant winner = participants.stream()
                    .filter(participant -> participant.getCustomerId() == winnerTickets.get(finalIndex).getParticipantId())
                    .findAny()
                    .orElse(null);

            if (winner != null) {
                winner.setWinner(true);
                winners.add(winner);
                activePrizes.get(index).setDrawId(drawId);
                activePrizes.get(index).setActive(false);
            }
        }
        logger.debug("DRAW-SERVICE -> Winners: " + winners);
        return winners;
    }

    List<Participant> defineParticipants(Long drawId, Map<Long, List<Long>> ordersByParticipantId) {
        if(ordersByParticipantId == null || ordersByParticipantId.isEmpty()) {
            return null;
        }

        List<Participant> participants = new ArrayList<>();
        ordersByParticipantId.forEach((customerId, orders)-> {
            Participant participant = new Participant();
            participant.setCustomerId(customerId);
            participant.setDrawId(drawId);
            participants.add(participant);
        });
        logger.debug("DRAW-SERVICE -> Participants: " + participants);
        return participants;
    }

    List<Ticket> generateTickets(Map<Long, Integer> chancesByCustomerId) {
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
        logger.debug("DRAW-SERVICE -> tickets: " + tickets);
        return tickets;
    }

    Map<Long, List<Long>> getOrderIdsByParticipantId(Date startDate, Date endDate) {
        Map<Long, List<Long>> ordersByCustomerId = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        List<PurchaseOrder> purchaseOrders = mapper.convertValue(purchaseOrderRestTemplateClient.getPurchaseOrdersBetweenDates(startDate, endDate),
                new TypeReference<List<PurchaseOrder>>(){});

        purchaseOrders.forEach(purchaseOrder -> {
            Long customerId = purchaseOrder.getCustomerId();
            Long orderId = purchaseOrder.getPurchaseOrderId();

            if (!ordersByCustomerId.containsKey(customerId)) {
                List<Long> orderIds = new ArrayList<>();
                orderIds.add(orderId);
                ordersByCustomerId.put(customerId, orderIds);
            } else {
                ordersByCustomerId.get(customerId).add(orderId);
            }
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
        logger.debug("DRAW-SERVICE -> Winner tickets: " + winnerTickets);
        return winnerTickets;
    }

    private Map<Long, Integer> getChancesByPurchaseOrderId(List<PurchaseOrderDetail> purchaseOrderDetails) {
        Map<Long, List<Long>> productIdsByPurchaseOrder = new HashMap<>();
        Map<Long, Integer> numberOfChancesByPurchaseOrder = new HashMap<>();

        purchaseOrderDetails.forEach(purchaseOrderDetail -> {
            Long purchaseOrderId = purchaseOrderDetail.getPurchaseOrderId();
            Long productId = purchaseOrderDetail.getProductId();

            if (!productIdsByPurchaseOrder.containsKey(purchaseOrderId)) {
                List<Long> productIds = new ArrayList<>();
                productIds.add(productId);
                productIdsByPurchaseOrder.put(purchaseOrderId, productIds);
            } else {
                productIdsByPurchaseOrder.get(purchaseOrderId).add(productId);
            }
        });

        ObjectMapper mapper = new ObjectMapper();
        productIdsByPurchaseOrder.forEach((purchaseOrderId, productIds)->{
            List<Product> products = mapper.convertValue(productRestTemplateClient.getProductsByIds(productIds),
                    new TypeReference<List<Product>>(){});
            
            int chances = products.stream().mapToInt(Product::getNumberChances).sum();
            numberOfChancesByPurchaseOrder.put(purchaseOrderId, chances);
        });

        return numberOfChancesByPurchaseOrder;
    }
}
