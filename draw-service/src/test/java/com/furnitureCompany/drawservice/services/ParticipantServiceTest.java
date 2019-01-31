package com.furnitureCompany.drawservice.services;

import com.furnitureCompany.drawservice.model.Participant;
import mockit.Tested;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class ParticipantServiceTest {

    @Tested
    ParticipantService participantService;

    @Test
    public void test_defineParticipants_ItShouldReturnTheListOfParticipants() {
        // setup
        Map<Long, List<Long>> ordersByParticipantId = new HashMap<>();
        ordersByParticipantId.put(1L, new ArrayList<>(Arrays.asList(1L, 2L)));
        ordersByParticipantId.put(2L, new ArrayList<>(Arrays.asList(3L)));
        ordersByParticipantId.put(3L, new ArrayList<>(Arrays.asList(4L)));

        // execute
        List<Participant> results = participantService.defineParticipants(1L, ordersByParticipantId);

        // verify
        assert results!= null && results.size() == ordersByParticipantId.size();
    }

    @Test
    public void test_defineParticipants_ItShouldReturnNullIfOrdersByParticipantsAreNullOrEmpty() {
        assert participantService.defineParticipants(1L, Collections.EMPTY_MAP) == null;
    }
}