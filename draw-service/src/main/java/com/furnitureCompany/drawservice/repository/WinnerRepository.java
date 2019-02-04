package com.furnitureCompany.drawservice.repository;

import com.furnitureCompany.drawservice.model.Winner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WinnerRepository extends CrudRepository<Winner, String> {
    Winner getWinnerByWinnerId(Long winnerId);
    List<Winner> getWinnersByPromotionId(Long promotionId);
}
