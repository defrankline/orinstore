package com.frank.api.service;

import com.frank.api.model.Shop;
import com.frank.api.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ShopService {

    @Autowired
    private ShopRepository shopRepository;

    public Shop createShop(Shop shop) {
        return shopRepository.save(shop);
    }

    public Shop getShopById(Long id){
        return shopRepository.findOne(id);
    }

    public Shop updateShop(Shop shop) {
        return shopRepository.save(shop);
    }

    public List<Shop> getAllShops() {
        return shopRepository.findAll();
    }

    public Page<Shop> getPaginatedShops(Integer page, Integer perPage){
        return shopRepository.findAll(new PageRequest(page,perPage));
    }

    public void deleteShop(Shop shop){
        shopRepository.delete(shop);
    }

    public List<Shop> findAllShops() {
        return shopRepository.findAll();
    }
}
