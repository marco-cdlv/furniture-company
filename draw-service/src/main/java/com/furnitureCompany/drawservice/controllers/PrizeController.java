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

    @RequestMapping(value = "/active/{active}", method = RequestMethod.GET)
    public List<Prize> getActivePrizes(@PathVariable("active") boolean active) {
        return prizeService.getActivePrizes(active);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Prize> getAllPrizes() {
        return prizeService.getAllPrizes();
    }

    @RequestMapping(value = "/{prize_id}", method = RequestMethod.GET)
    public Prize getPrizeById(@PathVariable("prize_id") Long prizeId) {
        return prizeService.getPrizeById(prizeId);
    }

    @RequestMapping(value = "/promotion/{promotion_id}", method = RequestMethod.GET)
    public List<Prize> getPrizesByPromotionId(@PathVariable("promotion_id") Long promotionId) {
        return prizeService.getPrizesByPromotionId(promotionId);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Prize addPrize(@RequestBody Prize prize) {
        return prizeService.addPrize(prize);
    }

    @RequestMapping(value = "/prizes", method = RequestMethod.POST)
    public void addPrizes(@RequestBody List<Prize> prizes) {
        prizeService.addPrizes(prizes);
    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public void deletePrizes() {
        prizeService.deletePrizes();
    }

    @RequestMapping(value = "/{prize_id}", method = RequestMethod.PUT)
    public Prize updatePrize(@PathVariable("prize_id") Long prizeId,
                            @RequestBody Prize prize) {
        return prizeService.updatePrize(prizeId, prize);
    }
}
