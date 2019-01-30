package com.furnitureCompany.drawservice.controllers;

import com.furnitureCompany.drawservice.model.Promotion;
import com.furnitureCompany.drawservice.services.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "v1/promotions")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Promotion> getPromotions() {
        return promotionService.getAllPromotions();
    }

    @RequestMapping(value = "/{promotion_id}", method = RequestMethod.GET)
    public Promotion getPrizeById(@PathVariable("promotion_id") Long promotionId) {
        return promotionService.getPromotionById(promotionId);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void addPromotion(@RequestBody Promotion promotion) {
        promotionService.addPromotion(promotion);
    }

}
