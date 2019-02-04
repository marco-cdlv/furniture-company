package com.furnitureCompany.drawservice.controllers;

import com.furnitureCompany.drawservice.model.Winner;
import com.furnitureCompany.drawservice.model.WinnerData;
import com.furnitureCompany.drawservice.services.WinnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "v1/winners")
public class WinnerController {

    @Autowired
    private WinnerService winnerService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Winner> getAllWinners() {
        return winnerService.getAllWinners();
    }

    @RequestMapping(value = "/{winner_id}", method = RequestMethod.GET)
    public Winner getWinnerById(@PathVariable("winner_id") Long winnerId) {
        return winnerService.getWinnerById(winnerId);
    }

    @RequestMapping(value = "/promotions/{promotion_id}", method = RequestMethod.GET)
    public List<WinnerData> getWinnersByPromotionId(@PathVariable("promotion_id") Long promotionId) {
        return winnerService.getWinnersDataByPromotionId(promotionId);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Winner addWinner(@RequestBody Winner winner) {
        return winnerService.addWinner(winner);
    }

    @RequestMapping(value = "/winners", method = RequestMethod.POST)
    public void addWinners(@RequestBody List<Winner> winners) {
        winnerService.addWinners(winners);
    }
}
