package com.furnitureCompany.drawservice.controllers;

import com.furnitureCompany.drawservice.model.Prize;
import com.furnitureCompany.drawservice.services.PrizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "v1/prizes")
public class PrizeController {

    @Autowired
    private PrizeService prizeService;

    @RequestMapping(value = "/active/", method = RequestMethod.GET)
    public List<Prize> getActivePrizes() {
        return prizeService.getActivePrizes();
    }

    @RequestMapping(value = "/{prize_id}", method = RequestMethod.GET)
    public Prize getPrizeById(@PathVariable("prize_id") Long prizeId) {
        return prizeService.getPrizeById(prizeId);
    }

    @RequestMapping(value = "/draw/{draw_id}", method = RequestMethod.GET)
    public List<Prize> getPrizesByDrawId(@PathVariable("draw_id") Long drawId) {
        return prizeService.getPrizesByDrawId(drawId);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void addPrize(@RequestBody Prize prize) {
        prizeService.addPrize(prize);
    }

}
