package com.furnitureCompany.drawservice.repository;

import com.furnitureCompany.drawservice.model.Promotion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends CrudRepository<Promotion, String> {
    Promotion getPromotionByPromotionId(Long promotionId);
}
