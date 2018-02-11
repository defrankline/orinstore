package com.frank.api.service;

import com.frank.api.model.Sale;
import com.frank.api.model.SaleItem;
import com.frank.api.repository.SaleItemRepository;
import com.frank.api.repository.SaleItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SaleItemService {


    private SaleItemRepository saleItemRepository;

    @Autowired
    public SaleItemService(SaleItemRepository saleItemRepository) {
        this.saleItemRepository = saleItemRepository;
    }

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
