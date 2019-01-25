package com.furnitureCompany.drawservice.repository;

import com.furnitureCompany.drawservice.model.Participant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends CrudRepository<Participant, String> {
    Participant getParticipantByParticipantId(Long participantId);
    List<Participant> getParticipantsByDrawId(Long drawId);
}
