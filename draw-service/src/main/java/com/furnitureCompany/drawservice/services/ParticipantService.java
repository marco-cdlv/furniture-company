package com.furnitureCompany.drawservice.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.furnitureCompany.drawservice.clients.CustomerRestTemplateClient;
import com.furnitureCompany.drawservice.model.*;
import com.furnitureCompany.drawservice.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private CustomerRestTemplateClient customerRestTemplateClient;

    @Autowired
    private PrizeService prizeService;

    @Autowired
    private  PromotionService promotionService;

    public Participant addParticipant(Participant participant) {
        return participantRepository.save(participant);
    }

    public void addParticipants(List<Participant> participants) {
        participantRepository .saveAll(participants);
    }

    public List<Participant> getAllParticipants() {
        return (List<Participant>) participantRepository.findAll();
    }

    public Participant getParticipantById(Long participantId) {
        return participantRepository.getParticipantByParticipantId(participantId);
    }

    public List<Participant> getParticipantsByPromotionId(Long promotionId) {
        return participantRepository.getParticipantsByPromotionId(promotionId);
    }

    public List<Winner> getWinnersByPromotionId(Boolean winner, Long promotionId) {
        List<Participant> winners = participantRepository.getParticipantsByWinnerAndPromotionId( winner, promotionId);
        List<Prize> prizes = prizeService.getPrizesByPromotionId(promotionId);
        String promotionName = promotionService.getPromotionById(promotionId).getName();

        ObjectMapper mapper = new ObjectMapper();
        List<Customer> customers = mapper.convertValue(customerRestTemplateClient.getCustomersByIds(winners.stream().map(Participant::getCustomerId).collect(Collectors.toList())),
                new TypeReference<List<Customer>>(){});

        List<Winner> winnersToDisplay = new ArrayList<>();
        for (int index = 0; index < winners.size(); index++) {
            Customer customer =  customers.get(index);

            Winner winnerToDisplay = new Winner();
            winnerToDisplay.setFullName(customer.getFullName());
            winnerToDisplay.setAddress(customer.getAddress());
            winnerToDisplay.setPhoneNumber(customer.getPhoneNumber());
            winnerToDisplay.setPrizeName(prizes.get(index).getName());
            winnerToDisplay.setPromotionName(promotionName);

            winnersToDisplay.add(winnerToDisplay);
        }

        return winnersToDisplay;
    }

    public void addWinners(List<Participant> participants) {
        participantRepository.saveAll(participants);
    }

    public List<Participant> defineParticipants(Long promotionId, Map<Long, List<Long>> ordersByParticipantId) {
        if(ordersByParticipantId == null || ordersByParticipantId.isEmpty()) {
            return null;
        }

        List<Participant> participants = new ArrayList<>();
        ordersByParticipantId.forEach((customerId, orders)-> {
            Participant participant = new Participant();
            participant.setCustomerId(customerId);
            participant.setPromotionId(promotionId);
            participants.add(participant);
        });
        return participants;
    }

    public void deleteParticipants() {
        participantRepository.deleteAll();
    }

    public Participant updateParticipant(Long participantId, Participant participant) {
        Participant participantToUpdate = participantRepository.getParticipantByParticipantId(participantId);

        participantToUpdate.setPromotionId(participant.getPromotionId() != null?
                participant.getPromotionId(): participantToUpdate.getPromotionId());
        participantToUpdate.setWinner(participant.getWinner() != null? participant.getWinner() : participantToUpdate.getWinner());
        participantToUpdate.setCustomerId(participant.getCustomerId() != null? participant.getCustomerId() : participantToUpdate.getCustomerId());
        return participantRepository.save(participantToUpdate);
    }
}
