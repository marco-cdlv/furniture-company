package com.furnitureCompany.drawservice.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.furnitureCompany.drawservice.clients.PurchaseOrderDetailRestTemplateClient;
import com.furnitureCompany.drawservice.clients.PurchaseOrderRestTemplateClient;
import com.furnitureCompany.drawservice.model.*;
import com.furnitureCompany.drawservice.repository.DrawRepository;
import net.bytebuddy.matcher.FilterableList;
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
    private PrizeService prizeService;

    @Autowired
    private WinnerService winnerService;

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

    public List<Winner> drawPrize(Date startDate, Date endDate, Draw draw) {

        // Adds a new draw
        addDraw(draw);

        // Adds the participants to the current draw
        Map<Long, List<Long>> ordersByParticipantId = getOrderIdsByParticipantId(startDate, endDate);
        addParticipants(draw.getDrawId(), ordersByParticipantId);

        // Generates the tickets and adds it to DB.
        Map<Long, Integer> chancesByCustomerId = getChancesByCustomerId(ordersByParticipantId);
        List<Ticket> tickets = generateTickets(chancesByCustomerId);

        // Draws the active prizes
        return drawTheActivePrizes(draw.getDrawId(), tickets);
    }

    private List<Winner> drawTheActivePrizes(Long drawId, List<Ticket> tickets) {
        List<Winner> winners = new ArrayList<>();
        List<Prize> activePrizes = prizeService.getActivePrizes();
        List<Ticket> winnerTickets = getWinnerTickets(tickets, activePrizes.size());

        for (int index = 0; index < activePrizes.size(); index++) {
            Winner winner = new Winner();
            winner.setParticipantId(winnerTickets.get(index).getParticipantId());
            winner.setPrizeId(activePrizes.get(index).getPrizeId());
            winner.setDrawId(drawId);

            winners.add(winner);
        }
        //Saves winners
        winnerService.saveWinners(winners);
        return winners;
    }

    private void addParticipants(Long drawId, Map<Long, List<Long>> ordersByParticipantId) {
        List<Participant> participants = new ArrayList<>();

        ordersByParticipantId.forEach((customerId, orders)-> {
            Participant participant = new Participant();
            participant.setCustomerId(customerId);
            participant.setDrawId(drawId);

            participants.add(participant);
        });

        participantService.addParticipants(participants);
    }

    private List<Ticket> generateTickets(Map<Long, Integer> chancesByCustomerId) {
        List<Ticket> tickets = new ArrayList<>();

        chancesByCustomerId.forEach((customerId, chances) -> {
            for (int index = 0; index < chances; index++) {
                Ticket ticket = new Ticket();
                ticket.setParticipantId(customerId);
                ticket.setTicketNumber((int) (index + customerId));
                tickets.add(ticket);
            }
        });

        ticketService.addTickets(tickets);
        return tickets;
    }

    private Map<Long, List<Long>> getOrderIdsByParticipantId(Date startDate, Date endDate) {
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

    private Map<Long, Integer> getChancesByCustomerId(Map<Long, List<Long>> ordersByParticipantId) {
        Map<Long, Integer> chancesByCustomerId = new HashMap<>();
        ordersByParticipantId.forEach((customerId, orderIds) -> {
            ObjectMapper mapper = new ObjectMapper();
            List<PurchaseOrderDetail> purchaseOrderDetails = mapper.convertValue(purchaseOrderDetailRestTemplateClient.getPurchaseOrderDetailsByOrderIds(orderIds),
                    new TypeReference<List<PurchaseOrderDetail>>(){});

            int chances = purchaseOrderDetails.stream().mapToInt(PurchaseOrderDetail::getChances).sum();
            chancesByCustomerId.put(customerId, chances);
        });
        return chancesByCustomerId;
    }

    private List<Ticket> getWinnerTickets(List<Ticket> tickets, int amountOfPrizes) {
        if (tickets == null || tickets.isEmpty()) {
            return null;
        }

        int amountOfTickets = tickets.size();
        Map<Long, Ticket> winnerTicketNumbersByParticipant = new HashMap<>();
        int count = 0;

        while(count < amountOfPrizes) {
            Random random = new Random();
            Integer winnerTicketNumber =  random.nextInt(amountOfTickets) + 1;

            Ticket winnerTicket =  tickets.get(winnerTicketNumber);

            if(!winnerTicketNumbersByParticipant.containsKey(winnerTicket.getParticipantId())) {
                winnerTicketNumbersByParticipant.put(winnerTicket.getParticipantId(), winnerTicket);
                count++;
            };
        }
        return winnerTicketNumbersByParticipant.values().stream().collect(Collectors.toList());
    }
}
