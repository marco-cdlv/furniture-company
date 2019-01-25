package com.furnitureCompany.drawservice.controllers;

import com.furnitureCompany.drawservice.model.Prize;
import com.furnitureCompany.drawservice.model.Winner;
import com.furnitureCompany.drawservice.services.PrizeService;
import com.furnitureCompany.drawservice.services.WinnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "v1/prizes")
public class PrizeController {

    @Autowired
    private PrizeService prizeService;

    @Autowired
    private WinnerService winnerService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Prize> getActivePrizes() {
        return prizeService.getActivePrizes();
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void addPrize(@RequestBody Prize prize) {
        prizeService.addPrize(prize);
    }

    @RequestMapping(value = "/draw/{start_date}/{end_date}", method = RequestMethod.GET)
    public List<Winner> drawPrizes(@PathVariable("start_date")@DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                   @PathVariable("end_date")@DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        prizeService.drawPrize(startDate, endDate);
        return winnerService.getAllWinners();
    }
}
