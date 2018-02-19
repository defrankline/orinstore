package com.frank.api.service.sale;

import com.frank.api.model.pos.SaleItem;
import com.frank.api.repository.pos.SaleItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SaleItemService {

    @Autowired
    private SaleItemRepository saleItemRepository;

    public SaleItem createSaleItem(SaleItem saleItem) {
        return saleItemRepository.save(saleItem);
    }

    public SaleItem getSaleItemById(Long id){
        return saleItemRepository.findOne(id);
    }

    public SaleItem updateSaleItem(SaleItem saleItem) {
        return saleItemRepository.save(saleItem);
    }

    public List<SaleItem> getAllSaleItems() {
        return saleItemRepository.findAll();
    }

    public Page<SaleItem> getPaginatedSaleItem(Integer page, Integer perPage){
        return saleItemRepository.findAll(new PageRequest(page,perPage));
    }

    public void deleteSaleItem(SaleItem saleItem){
        saleItemRepository.delete(saleItem);
    }

    public List<SaleItem> getSaleItems(Long sale_id) {
        return saleItemRepository.findAllBySaleId(sale_id);
    }
}
