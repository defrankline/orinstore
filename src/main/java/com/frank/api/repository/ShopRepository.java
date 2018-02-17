package com.frank.api.repository;

import com.frank.api.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    public Shop findOneByCode(String code);
}