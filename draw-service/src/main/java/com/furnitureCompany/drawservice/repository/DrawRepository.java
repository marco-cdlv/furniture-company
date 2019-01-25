package com.furnitureCompany.drawservice.repository;

import com.furnitureCompany.drawservice.model.Draw;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrawRepository extends CrudRepository<Draw, String> {
    Draw getDrawByDrawId(Long drawId);
}
