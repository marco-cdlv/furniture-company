package com.furnitureCompany.drawservice.controllers;

import com.furnitureCompany.drawservice.model.Participant;
import com.furnitureCompany.drawservice.model.Promotion;
import com.furnitureCompany.drawservice.services.DrawService;
import com.furnitureCompany.drawservice.services.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequestMapping(value = "v1/promotions")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private DrawService drawService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Promotion> getPromotions() {
        return promotionService.getAllPromotions();
    }

    @RequestMapping(value = "/{promotion_id}", method = RequestMethod.GET)
    public Promotion getPromotionById(@PathVariable("promotion_id") Long promotionId) {
        return promotionService.getPromotionById(promotionId);
    }

    @RequestMapping(value = "/active/{active}", method = RequestMethod.GET)
    public List<Promotion> getAllActivePromotions(@PathVariable("active") boolean active) {
        return promotionService.getAllActivePromotions(active);
    }

    @RequestMapping(value = "/{promotion_id}", method = RequestMethod.PUT)
    public Promotion updatePromotion(@RequestBody Promotion promotion, @PathVariable("promotion_id") Long promotionId) {
        return promotionService.updatePromotion(promotion, promotionId);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Promotion addPromotion(@RequestBody Promotion promotion) {
        return promotionService.addPromotion(promotion);
    }

    @RequestMapping(value = "/promotions", method = RequestMethod.POST)
    public void addPromotions(@RequestBody List<Promotion> promotions) {
        promotionService.addPromotions(promotions);
    }

    @RequestMapping(value = "/{promotion_id}/draws", method = RequestMethod.GET)
    public List<Participant> drawPrizes(@PathVariable("promotion_id") Long promotionId) throws Exception {
        return drawService.drawPrize(promotionId);
    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public void deleteAllPromotions() {
       promotionService.deleteAllPromotions();
    }

}
