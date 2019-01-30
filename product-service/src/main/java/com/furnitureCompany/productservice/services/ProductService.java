package com.furnitureCompany.productservice.services;


import com.furnitureCompany.productservice.model.Product;
import com.furnitureCompany.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product getProductById(Long productId) {
        return productRepository.findProductById(productId);
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public List<Product> getProducts() {
        List<Product> products = (List<Product>) productRepository.findAll();
        return products;
    }

    public List<Product> getProductByIds(List<Long> productIds) {
        return productRepository.findProductsByIdIn(productIds);
    }

    public void updateProduct(Product product, Long productId) {
        Product productToUpdate = productRepository.findProductById(productId);

        productToUpdate.setName(product.getName());
        productToUpdate.setModel(product.getModel());
        productToUpdate.setAmount(product.getAmount());
        productToUpdate.setColor(product.getColor());

        productRepository.save(productToUpdate);
    }

    public void deleteProduct(String productId) {
        productRepository.deleteById(productId);
    }
}
