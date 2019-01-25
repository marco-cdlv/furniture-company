package com.furnitureCompany.productservice.controllers;

import com.furnitureCompany.productservice.model.Product;
import com.furnitureCompany.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "v1/products")
public class ProductServiceController {

    @Autowired
    private ProductService productService;

    @Value("${spring.jpa.database:default}")
    private String test;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Product getProduct(@PathVariable("id") Long productId) {
        return productService.getProductById(productId);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void saveProduct(@RequestBody Product product) {
        productService.saveProduct(product);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void updateProduct(@PathVariable("id") Long productId, @RequestBody Product product) {
        productService.updateProduct(product, productId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable("id") String productId) {
        productService.deleteProduct(productId);
    }
}
