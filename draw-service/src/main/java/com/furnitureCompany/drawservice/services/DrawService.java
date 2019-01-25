package com.furnitureCompany.drawservice.services;

import com.furnitureCompany.drawservice.model.Draw;
import com.furnitureCompany.drawservice.repository.DrawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrawService {

    @Autowired
    private DrawRepository drawRepository;

    public void addDraw(Draw draw) {
        drawRepository.save(draw);
    }

    public List<Draw> getAllDraws() {
        return (List<Draw>) drawRepository.findAll();
    }

    public Draw getDrawById(Long drawId) {
        return drawRepository.getDrawByDrawId(drawId);
    }

    public void removeAllDraws() {
        drawRepository.deleteAll();
    }
}
