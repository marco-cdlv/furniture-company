package com.furnitureCompany.drawservice.controllers;

import com.furnitureCompany.drawservice.model.Draw;
import com.furnitureCompany.drawservice.model.Participant;
import com.furnitureCompany.drawservice.services.DrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "v1/draws")
public class DrawController {

    @Autowired
    private DrawService drawService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Draw> getAllDraws() {
        return drawService.getAllDraws();
    }

    @RequestMapping(value = "/{draw_id}", method = RequestMethod.GET)
    public Draw getDrawById(@PathVariable("draw_id") Long drawId) {
        return drawService.getDrawById(drawId);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void addDraw(@RequestBody Draw draw) {
        drawService.addDraw(draw);
    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public void deleteAllDraws() {
        drawService.removeAllDraws();
    }

    @RequestMapping(value = "/start/", method = RequestMethod.POST)
    public List<Participant> drawPrizes(@RequestBody Draw draw) throws Exception {
        return drawService.drawPrize(draw);
    }
}
