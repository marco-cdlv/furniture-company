package com.furnitureCompany.drawservice.services;

import com.furnitureCompany.drawservice.clients.ProductRestTemplateClient;
import com.furnitureCompany.drawservice.clients.PurchaseOrderDetailRestTemplateClient;
import com.furnitureCompany.drawservice.clients.PurchaseOrderRestTemplateClient;
import com.furnitureCompany.drawservice.model.Draw;
import com.furnitureCompany.drawservice.model.Participant;
import com.furnitureCompany.drawservice.model.PurchaseOrderDetail;
import com.furnitureCompany.drawservice.model.Ticket;
import com.furnitureCompany.drawservice.repository.DrawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

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

    public void drawPrize(Date startDate, Date endDate) {

        Draw draw = new Draw();
        draw.setDrawDate(new Date());
        draw.setName("First draw");
        draw.setDescription("First draw description");
        addDraw(draw);

        Map<Long, List<Long>> ordersByParticipantId = getOrderIdsByParticipantId(startDate, endDate);
        AddParticipants(draw.getDrawId(), ordersByParticipantId);

        Map<Long, Integer> chancesByCustomerId = getChancesByCustomerId(ordersByParticipantId);
        generateTickets(chancesByCustomerId);
    }

    private void AddParticipants(Long drawId, Map<Long, List<Long>> ordersByParticipantId) {
        ordersByParticipantId.forEach((customerId, orders)-> {
            Participant participant = new Participant();
            participant.setCustomerId(customerId);
            participant.setDrawId(drawId);

            participantService.addParticipant(participant);
        });
    }

    private void generateTickets(Map<Long, Integer> chancesByCustomerId) {

        chancesByCustomerId.forEach((customerId, chances) -> {
            Ticket ticket = new Ticket();
            ticket.setParticipantId(customerId);

            for (int index = 0; index < chances; chances++) {
                ticket.setTicketNumber((int) (customerId + index));
            }

            ticketService.addTicket(ticket);
        });
    }

    private Map<Long, List<Long>> getOrderIdsByParticipantId(Date startDate, Date endDate) {
        Map<Long, List<Long>> ordersByCustomerId = new HashMap<>();

        purchaseOrderRestTemplateClient.getPurchaseOrdersBetweenDates(startDate, endDate)
                .forEach(purchaseOrder -> {
                    Long customerId = purchaseOrder.getCustomerId();
                    Long orderId = purchaseOrder.getPurchaseOrderId();
                    if (!ordersByCustomerId.containsKey(customerId)) {
                        ordersByCustomerId.put(customerId, Collections.singletonList(orderId));
                    } else {
                        ordersByCustomerId.get(customerId).add(orderId);
                    }
                });
        return ordersByCustomerId;
    }

    private Map<Long, Integer> getChancesByCustomerId(Map<Long, List<Long>> ordersByParticipantId) {
        Map<Long, Integer> chancesByCustomerId = new HashMap<>();
        ordersByParticipantId.forEach((customerId, orderIds) -> {
            int chances = purchaseOrderDetailRestTemplateClient.getPurchaseOrderDetailsByOrderIds(orderIds)
                    .stream().mapToInt(PurchaseOrderDetail::getChances).sum();
            chancesByCustomerId.put(customerId, chances);
        });
        return chancesByCustomerId;
    }
}
