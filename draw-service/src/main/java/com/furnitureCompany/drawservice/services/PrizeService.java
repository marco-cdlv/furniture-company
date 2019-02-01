package com.furnitureCompany.drawservice.services;

import com.furnitureCompany.drawservice.model.*;
import com.furnitureCompany.drawservice.repository.PrizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PrizeService {

    @Autowired
    private PrizeRepository prizeRepository;

    public List<Prize> getActivePrizes(boolean active) {
        return  prizeRepository.getPrizesByActive(active);
    }

    public List<Prize> getActivePrizesByPromotionId(Long promotionId, boolean active) {
        return  prizeRepository.getPrizesByPromotionIdAndActive(promotionId, active);
    }

    public Prize getPrizeById(Long prizeId) {
        return prizeRepository.getPrizeByPrizeId(prizeId);
    }

    public Prize addPrize(Prize prize) {
        return prizeRepository.save(prize);
    }

    public List<Prize> getPrizesByPromotionId(Long promotionId) {
        return prizeRepository.getPrizesByPromotionId(promotionId);
    }

    public List<Prize> getAllPrizes() {
        return (List<Prize>) prizeRepository.findAll();
    }

    public void addPrizes(List<Prize> prizes) {
        prizeRepository.saveAll(prizes);
    }

    public void deletePrizes() {
        prizeRepository.deleteAll();
    }

    public Prize updatePrize(Long prizeId, Prize prize) {
        Prize prizeToUpdate =  getPrizeById(prizeId);
        prizeToUpdate.setPromotionId(prize.getPromotionId() != null? prize.getPromotionId() : prizeToUpdate.getPromotionId());
        prizeToUpdate.setName(prize.getName() != null? prize.getName() : prizeToUpdate.getName());
        prizeToUpdate.setDescription(prize.getDescription() != null? prize.getDescription() : prizeToUpdate.getDescription());
        prizeToUpdate.setActive(prize.getActive()? prize.getActive() : prizeToUpdate.getActive());
        return prizeRepository.save(prizeToUpdate);
    }
}
