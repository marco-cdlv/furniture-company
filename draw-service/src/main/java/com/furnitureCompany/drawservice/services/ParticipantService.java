package com.furnitureCompany.drawservice.services;

import com.furnitureCompany.drawservice.model.Draw;
import com.furnitureCompany.drawservice.model.Participant;
import com.furnitureCompany.drawservice.repository.DrawRepository;
import com.furnitureCompany.drawservice.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;

    public void addParticipant(Participant participant) {
        participantRepository.save(participant);
    }

    public void addParticipants(List<Participant> participants) {
        if (participants != null && !participants.isEmpty()) {
            participants.forEach(this::addParticipant);
        }
    }

    public List<Participant> getAllParticipants() {
        return (List<Participant>) participantRepository.findAll();
    }

    public Participant getParticipantById(Long participantId) {
        return participantRepository.getParticipantByParticipantId(participantId);
    }

    public List<Participant> getParticipantsByDrawId(Long drawId) {
        return participantRepository.getParticipantsByDrawId(drawId);
    }
}
