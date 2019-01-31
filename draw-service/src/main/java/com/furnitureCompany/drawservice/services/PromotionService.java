package com.furnitureCompany.drawservice.services;

import com.furnitureCompany.drawservice.model.Promotion;
import com.furnitureCompany.drawservice.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    public List<Promotion> getAllPromotions() {
        return (List<Promotion>) promotionRepository.findAll();
    }

    public Promotion getPromotionById(Long promotionId) {
        return promotionRepository.getPromotionByPromotionId(promotionId);
    }

    public void addPromotion(Promotion promotion) {
        promotionRepository.save(promotion);
    }

    public void addPromotions(List<Promotion> promotions) {
        promotionRepository.saveAll(promotions);
    }
}
