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

    public List<Prize> getActivePrizes() {
        return  StreamSupport.stream(prizeRepository.findAll().spliterator(), false)
                .filter(prize -> prize.isActive())
                .collect(Collectors.toList());
    }

    public Prize getPrizeById(Long prizeId) {
        return prizeRepository.getPrizeByPrizeId(prizeId);
    }

    public void addPrize(Prize prize) {
        prizeRepository.save(prize);
    }

    public List<Prize> getPrizesByDrawId(Long drawId) {
        return prizeRepository.getPrizesByDrawId(drawId);
    }

//    public List<Prize> getActivePrizes() {
//        return prizeRepository.getPrizesByActive();
//    }
}
