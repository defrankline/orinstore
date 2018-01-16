package com.frank.api.service;

import com.frank.api.model.Product;
import com.frank.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService {


    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product getProductById(Long id){
        return productRepository.findOne(id);
    }

    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Page<Product> getPaginatedProduct(Integer page, Integer perPage){
        return productRepository.findAll(new PageRequest(page,perPage));
    }

    public void deleteProduct(Product product){
        productRepository.delete(product);
    }
}
