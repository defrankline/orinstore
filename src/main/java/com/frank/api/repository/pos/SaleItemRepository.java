package com.frank.api.repository.pos;
import com.frank.api.model.pos.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleItemRepository extends JpaRepository<SaleItem, Long> {
    public List<SaleItem> findAllBySaleId(Long sale_id);
}