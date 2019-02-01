package com.furnitureCompany.drawservice.services;

import com.furnitureCompany.drawservice.model.Participant;
import com.furnitureCompany.drawservice.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;

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

    public List<Participant> getWinnersByPromotionId(Boolean winner, Long promotionId) {
        return participantRepository.getParticipantsByWinnerAndPromotionId( winner, promotionId);
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
