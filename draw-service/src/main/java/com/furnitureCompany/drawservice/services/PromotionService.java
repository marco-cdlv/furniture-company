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

    public void updatePromotion(Promotion promotion, Long promotionId) {
        Promotion promotionToUpdate = promotionRepository.getPromotionByPromotionId(promotionId);

        promotionToUpdate.setName(promotion.getName());
        promotionToUpdate.setStartDate(promotion.getStartDate());
        promotionToUpdate.setEndDate(promotion.getEndDate());
        promotionToUpdate.setDescription(promotion.getDescription());

        promotionRepository.save(promotionToUpdate);
    }
}
