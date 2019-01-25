package com.furnitureCompany.drawservice.repository;

import com.furnitureCompany.drawservice.model.Prize;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrizeRepository extends CrudRepository<Prize, String> {
}
