package com.frank.api.repository.setup;

import com.frank.api.model.setup.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    public Shop findOneByCode(String code);
}