package com.furnitureCompany.drawservice.services;

import com.furnitureCompany.drawservice.model.Winner;
import com.furnitureCompany.drawservice.repository.WinnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WinnerService {

    @Autowired
    private WinnerRepository winnerRepository;

    public void saveWinner(Winner winner) {
        winnerRepository.save(winner);
    }

    public List<Winner> getAllWinners() {
        return (List<Winner>) winnerRepository.findAll();
    }

    public void resetWinner() {
        winnerRepository.deleteAll();
    }

    public Winner getWinnerByCustomerID(Long customerId) {
        return winnerRepository.getWinnerByCustomerId(customerId);
    }

    public Winner getWinnerByPrizeID(Long prizeId) {
        return winnerRepository.getWinnerByPrizeId(prizeId);
    }

}
