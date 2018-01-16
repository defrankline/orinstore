package com.frank.api.service;

import com.frank.api.model.Brand;
import com.frank.api.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    public Brand createBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    public Brand getBrandById(Long id){
        return brandRepository.findOne(id);
    }

    public Brand updateBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public Page<Brand> getPaginatedBrands(Integer page, Integer perPage){
        return brandRepository.findAll(new PageRequest(page,perPage));
    }

    public void deleteBrand(Brand brand){
        brandRepository.delete(brand);
    }
}
