package com.furnitureCompany.drawservice.controllers;

import com.furnitureCompany.drawservice.model.Customer;
import com.furnitureCompany.drawservice.model.Participant;
import com.furnitureCompany.drawservice.model.Winner;
import com.furnitureCompany.drawservice.services.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "v1/participants")
public class ParticipantController {


    @Autowired
    private ParticipantService participantService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Participant> getAllParticipants() {
        return participantService.getAllParticipants();
    }

    @RequestMapping(value = "/{participant_id}", method = RequestMethod.GET)
    public Participant getParticipantById(@PathVariable("participant_id") Long participantId) {
        return participantService.getParticipantById(participantId);
    }

    @RequestMapping(value = "/promotions/{promotion_id}", method = RequestMethod.GET)
    public List<Participant> geParticipantsByPromotionId(@PathVariable("promotion_id") Long promotionId) {
        return participantService.getParticipantsByPromotionId(promotionId);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Participant addParticipant(@RequestBody Participant participant) {
        return participantService.addParticipant(participant);
    }

    @RequestMapping(value = "/participants", method = RequestMethod.POST)
    public void addParticipants(@RequestBody List<Participant> participants) {
        participantService.addWinners(participants);
    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public void deleteParticipants() {
        participantService.deleteParticipants();
    }

    @RequestMapping(value = "/{participant_id}", method = RequestMethod.PUT)
    public Participant updateParticipant(@PathVariable("participant_id") Long participantId,
                                   @RequestBody Participant participant) {
        return participantService.updateParticipant(participantId, participant);
    }
}
