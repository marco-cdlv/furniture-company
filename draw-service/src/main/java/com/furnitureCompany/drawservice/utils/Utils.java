package com.furnitureCompany.drawservice.utils;

import com.furnitureCompany.drawservice.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Utils {

    public static List<String> models = Arrays.asList(new String[]{"XXL", "XL", "L"});
    public static List<String> names = Arrays.asList(new String[]{"chair", "table", "desk"});
    public static List<String> colors = Arrays.asList(new String[]{"white", "black", "orange"});
    public static List<Integer> chances = Arrays.asList(new Integer[]{4, 3, 2});

    public static List<Ticket> getTickets(int numberOfTickets) {
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

    public static List<Prize> getPrizes(int numberOfPrizes, Long promotionId) {
        List<Prize> prizes = new ArrayList<>();

        for(int index = 0; index < numberOfPrizes; index++) {
            Prize prize = new Prize();
            prize.setName("King " + index);
            prize.setDescription("Summer prizes " + index);
            prize.setActive(true);
            prize.setPromotionId(promotionId);
            prizes.add(prize);
        }
        return prizes;
    }

    public static List<PurchaseOrderDetail> getPurchaseOrderDetail(int numberOfPurchaseOrderDetail) {
        List<PurchaseOrderDetail> purchaseOrderDetails = new ArrayList<>();
        for(int index = 0; index < numberOfPurchaseOrderDetail; index++) {
            PurchaseOrderDetail purchaseOrderDetail = new PurchaseOrderDetail();
            purchaseOrderDetail.setPurchaseOrderDetailId(1L + index);
            purchaseOrderDetail.setPurchaseOrderId(1L + index);
            purchaseOrderDetail.setProductId(1L + index);
            purchaseOrderDetail.setQuantity(2 + index);
            purchaseOrderDetails.add(purchaseOrderDetail);
        }
        return purchaseOrderDetails;
    }

    public static List<PurchaseOrder> getPurchaseOrders(int amountOfPurchaseOrders) {
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

    public static List<Product> getProducts(int amountOfProducts) {
        List<Product> products = new ArrayList<>();
        for(int index = 0; index < amountOfProducts; index++) {
            Product product = new Product();
            product.setId((long) index);
            product.setName(Utils.names.get(index));
            product.setModel(Utils.models.get(index));
            product.setColor(Utils.colors.get(index));
            product.setNumberChances(Utils.chances.get(index));
            product.setAmount(2);
            products.add(product);
        }
        return products;
    }

    public static List<Participant> getParticipants(int amountOfParticipants, Long promotionId) {
        List<Participant> participants = new ArrayList<>();
        for(int index = 0; index < amountOfParticipants; index++) {
            Participant participant = new Participant();
            participant.setParticipantId(1L + index);
            participant.setCustomerId(1L+ index);
            participant.setPromotionId(promotionId);
            participant.setWinner(false);
            participants.add(participant);
        }
        return participants;
    }
}
