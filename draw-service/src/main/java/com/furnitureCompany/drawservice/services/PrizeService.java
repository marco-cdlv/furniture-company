package com.furnitureCompany.drawservice.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.furnitureCompany.drawservice.clients.CustomerRestTemplateClient;
import com.furnitureCompany.drawservice.clients.ProductRestTemplateClient;
import com.furnitureCompany.drawservice.clients.PurchaseOrderRestTemplateClient;
import com.furnitureCompany.drawservice.model.*;
import com.furnitureCompany.drawservice.repository.PrizeRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PrizeService {

    @Autowired
    private PrizeRepository prizeRepository;

    @Autowired
    CustomerRestTemplateClient customerRestTemplateClient;

    @Autowired
    PurchaseOrderRestTemplateClient purchaseOrderRestTemplateClient;

    @Autowired
    ProductRestTemplateClient productRestTemplateClient;

    @Autowired
    private WinnerService winnerService;

    public List<Prize> getActivePrizes() {
        return  StreamSupport.stream(prizeRepository.findAll().spliterator(), false)
                .filter(prize -> prize.isActive())
                .collect(Collectors.toList());
    }

    public void addPrize(Prize prize) {
        prizeRepository.save(prize);
    }

    //@HystrixCommand
    public void drawPrize(Date startDate, Date endDate) {
        Map<Integer, Long> ticketsAndCustomers = getTickets(startDate, endDate);
        List<Prize> prizes = getActivePrizes();
        int amountOfTickets = ticketsAndCustomers.size();
        Map<Long, Long> winners = new HashMap<>();

        int count = 0;
        while (count < prizes.size()) {
            int winnerTicketNumber = getWinnerTicketNumber(amountOfTickets);

            Long customerId = ticketsAndCustomers.get(winnerTicketNumber);

            if (!winners.containsKey(customerId)) {
                winners.put(customerId, prizes.get(count).getPrizeId());
                // Saves the winner to Data base.
                Winner winner = new Winner();
                winner.setCustomerId(customerId);
                winner.setPrizeId(winners.get(customerId));

                if (winnerService.getWinnerByCustomerID(customerId) == null
                && winnerService.getWinnerByPrizeID(prizes.get(count).getPrizeId()) == null) {
                    winnerService.saveWinner(winner);
                }
            }
            count++;
        }
    }

    private int getWinnerTicketNumber(int ticketsNumber) {
        Random random = new Random();
        return random.nextInt(ticketsNumber) + 1;
    }

    private Map<Integer, Long> getTickets(Date startDate, Date endDate) {
        Map<Integer, Long> tickets = new HashMap<>();
        Map<Long, Integer> chancesByCustomer = calculateNumberOfChancesByCustomer(startDate, endDate);

        final int[] ticketNumber = {1};
        chancesByCustomer.forEach((customerId, numberOfChances) -> {
            for (int index = 0; index < numberOfChances; index++) {
                tickets.put(ticketNumber[0]++, customerId);
            }
        });
        return tickets;
    }

    private Map<Long, Integer> calculateNumberOfChancesByCustomer(Date startDate, Date endDate) {
        ObjectMapper mapper = new ObjectMapper();

        List<Customer> participants = mapper.convertValue(getParticipants(startDate, endDate),
                new TypeReference<List<Customer>>(){});

        Map<Long, Integer> numberOfChancesByCustomer = new HashMap<>();

        participants.stream().forEach(participant ->  {
            List<String> models = getFurnitureModels(participant.getCustomerId());
            numberOfChancesByCustomer.put(participant.getCustomerId(), getChances(models));
        });

        return numberOfChancesByCustomer;
    }

    private List<Customer> getParticipants(Date startDate, Date endDate) {
        ObjectMapper mapper = new ObjectMapper();
        List<PurchaseOrder> purchaseOrders = mapper.convertValue(purchaseOrderRestTemplateClient.getPurchaseOrdersBetweenDates(startDate, endDate),
                new TypeReference<List<PurchaseOrder>>(){});

        List<Long> customerIds = purchaseOrders.stream()
                .distinct()
                .map(PurchaseOrder::getCustomerId).collect(Collectors.toList());

        return customerRestTemplateClient.getCustomersByIds(customerIds);
    }

    private int getChances(List<String> models){
        int numberOfChances = 0;
        for(String model : models) {
            if ("XXL".equals(model)) {
                numberOfChances = numberOfChances + 4;
            }
            if ("XL".equals(model)) {
                numberOfChances = numberOfChances + 3;
            }
            if ("L".equals(model)) {
                numberOfChances = numberOfChances + 2;
            }
        }
        return numberOfChances;
    }

    private List<String> getFurnitureModels(Long customerId) {
        List<String> models = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        List<PurchaseOrder> purchaseOrders = mapper.convertValue(purchaseOrderRestTemplateClient.getPurchaseOrdersByCustomerId(customerId),
                new TypeReference<List<PurchaseOrder>>(){});

        purchaseOrders.stream()
            .forEach(purchaseOrder -> {
                Product product = productRestTemplateClient.getProductsById(purchaseOrder.getFurnitureId());

                if (product != null) {
                    int count = 0;
                    while(count < purchaseOrder.getAmount()) {
                        models.add(product.getModel());
                        count++;
                    }
                }
            });

        return models;
    }
}
