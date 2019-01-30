package com.furnitureCompany.productservice.repository;

import com.furnitureCompany.productservice.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, String> {
    Product findProductById(Long productId);
    List<Product> findProductsByIdIn(List<Long> productIds);
}
