package com.frank.api.service.setup;

import com.frank.api.model.setup.Product;
import com.frank.api.repository.setup.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService {


    @Autowired
    private ProductRepository productRepository;

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

    public Page<Product> getPaginatedProducts(Integer page, Integer perPage){
        return productRepository.findAll(new PageRequest(page,perPage));
    }

    public void deleteProduct(Product product){
        productRepository.delete(product);
    }

    public List<Product> searchProducts(String searchText) {
        return productRepository.findAllByNameLike(searchText);
    }
}
