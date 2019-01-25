package com.furnitureCompany.drawservice.repository;

import com.furnitureCompany.drawservice.model.Prize;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrizeRepository extends CrudRepository<Prize, String> {
    Prize getPrizeByPrizeId(Long prizeId);
    List<Prize> getPrizesByDrawId(Long drawId);
    //List<Prize> getPrizesByActive();
}
