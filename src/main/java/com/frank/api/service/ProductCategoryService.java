package com.frank.api.service;

import com.frank.api.model.ProductCategory;
import com.frank.api.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public ProductCategory createProductCategory(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }

    public ProductCategory getProductCategoryById(Long id){
        return productCategoryRepository.findOne(id);
    }

    public ProductCategory updateProductCategory(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }

    public List<ProductCategory> getAllProductCategories() {
        return productCategoryRepository.findAll();
    }

    public Page<ProductCategory> getPaginatedProductCategories(Integer page, Integer perPage){
        return productCategoryRepository.findAll(new PageRequest(page,perPage));
    }

    public void deleteProductCategory(ProductCategory productCategory){
        productCategoryRepository.delete(productCategory);
    }
}
