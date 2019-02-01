package com.furnitureCompany.drawservice.repository;

import com.furnitureCompany.drawservice.model.Prize;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrizeRepository extends CrudRepository<Prize, String> {
    Prize getPrizeByPrizeId(Long prizeId);
    List<Prize> getPrizesByPromotionId(Long promotionId);
    List<Prize> getPrizesByActive(boolean active);
    List<Prize> getPrizesByPromotionIdAndActive(Long promotionId, boolean active);
}
