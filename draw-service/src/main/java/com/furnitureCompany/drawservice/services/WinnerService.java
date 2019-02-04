package com.furnitureCompany.drawservice.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.furnitureCompany.drawservice.clients.CustomerRestTemplateClient;
import com.furnitureCompany.drawservice.model.*;
import com.furnitureCompany.drawservice.repository.WinnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class    WinnerService {

    @Autowired
    private WinnerRepository winnerRepository;

    @Autowired
    private PrizeService prizeService;

    @Autowired
    private CustomerRestTemplateClient customerRestTemplateClient;

    @Autowired
    private  PromotionService promotionService;

    @Autowired
    private  ParticipantService participantService;


    public Winner addWinner(Winner winner) {
        return winnerRepository.save(winner);
    }

    public void addWinners(List<Winner> winners) {
        winnerRepository.saveAll(winners);
    }

    public List<Winner> getAllWinners() {
        return (List<Winner>) winnerRepository.findAll();
    }

    public List<Winner> getWinnersByPromotionId(Long promotionId) {
        return winnerRepository.getWinnersByPromotionId(promotionId);
    }

    public Winner getWinnerById(Long winnerId) {
        return winnerRepository.getWinnerByWinnerId(winnerId);
    }

    public List<WinnerData> getWinnersDataByPromotionId(Long promotionId) {
        List<Winner> winners = winnerRepository.getWinnersByPromotionId(promotionId);
        Map<Long, String> prizeNameByPrizeId = getPrizeNameByPrizeId(promotionId);
        Map<Long, Customer> customerByParticipantId = getCustomerByParticipantId(winners);
        String promotionName = promotionService.getPromotionById(promotionId).getName();

        List<WinnerData> winnersData = new ArrayList<>();

        winners.forEach(winner -> {
            Customer customer = customerByParticipantId.get(winner.getParticipantId());

            WinnerData winnerData = new WinnerData();
            winnerData.setPrizeName(prizeNameByPrizeId.get(winner.getPrizeId()));
            winnerData.setPromotionName(promotionName);
            winnerData.setWinnerFullName(customer.getFullName());
            winnerData.setWinnerPersonalNumber(customer.getPersonalIdNumber());
            winnerData.setWinnerPhoneNumber(customer.getPhoneNumber());

            winnersData.add(winnerData);
        });

        return winnersData;
    }

    private Map<Long, String> getPrizeNameByPrizeId(Long promotionId) {
        List<Prize> prizes = prizeService.getPrizesByPromotionId(promotionId);
        return prizes.stream().collect(Collectors.toMap(Prize::getPrizeId, Prize::getName));
    }

    private Map<Long, Customer> getCustomerByParticipantId(List<Winner> winners) {
        List<Participant> participants = participantService.getParticipantsByIds(winners.stream().map(Winner::getParticipantId).collect(Collectors.toList()));
        ObjectMapper mapper = new ObjectMapper();
        List<Customer> customers = mapper.convertValue(customerRestTemplateClient.getCustomersByIds(participants
                        .stream().map(Participant::getCustomerId).collect(Collectors.toList())),
                new TypeReference<List<Customer>>(){});
        Map<Long, Customer> customerById = customers.stream().collect(Collectors.toMap(Customer::getCustomerId, Function.identity()));

        Map<Long, Customer> customerByParticipantId = new HashMap<>();

        participants.forEach(participant -> {
            customerByParticipantId.put(participant.getParticipantId(), customerById.get(participant.getCustomerId()));
        });
        return customerByParticipantId;
    }
}
