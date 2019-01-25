package com.furnitureCompany.drawservice.controllers;

import com.furnitureCompany.drawservice.model.Winner;
import com.furnitureCompany.drawservice.services.WinnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "v1/winners")
public class WinnerController {

    @Autowired
    private WinnerService winnerService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Winner> getAllWinners() {
        return winnerService.getAllWinners();
    }

    @RequestMapping(value = "/{customer_id}", method = RequestMethod.GET)
    public Winner getWinnerByCustomerId(@PathVariable("customer_id") Long customerId) {
        return winnerService.getWinnerByCustomerID(customerId);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void addWinner(@RequestBody Winner winner) {
        winnerService.saveWinner(winner);
    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public void deleteAllWinners() {
        winnerService.resetWinner();
    }
}
