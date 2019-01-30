package com.furnitureCompany.drawservice.controllers;

import com.furnitureCompany.drawservice.model.Participant;
import com.furnitureCompany.drawservice.services.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "/participants/{draw_id}", method = RequestMethod.GET)
    public List<Participant> geParticipantsByDraw(@PathVariable("draw_id") Long drawId) {
        return participantService.getParticipantsByDrawId(drawId);
    }

    @RequestMapping(value = "/winners/{draw_id}", method = RequestMethod.GET)
    public List<Participant> getWinnersByDraw(@PathVariable("draw_id") Long drawId) {
        return participantService.getWinnersByDrawId(drawId);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void addParticipant(@RequestBody Participant participant) {
        participantService.addParticipant(participant);
    }

    @RequestMapping(value = "/winners/", method = RequestMethod.POST)
    public void addParticipants(@RequestBody List<Participant> participants) {
        participantService.addWinners(participants);
    }
}
