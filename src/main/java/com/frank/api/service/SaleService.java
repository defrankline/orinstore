package com.frank.api.service;

import com.frank.api.model.Sale;
import com.frank.api.model.SaleItem;
import com.frank.api.repository.SaleItemRepository;
import com.frank.api.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SaleService {


    private SaleRepository saleRepository;
    private SaleItemRepository saleItemRepository;

    @Autowired
    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public Sale createSale(Sale sale) {
        return saleRepository.save(sale);
    }

    public Sale getSaleById(Long id){
        return saleRepository.findOne(id);
    }

    public Sale updateSale(Sale sale) {
        return saleRepository.save(sale);
    }

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public List<SaleItem> getSaleItems(Long saleId) {
        return saleItemRepository.findAll();
    }

    public Page<Sale> getPaginatedSale(Integer page, Integer perPage){
        return saleRepository.findAll(new PageRequest(page,perPage));
    }

    public void deleteSale(Sale sale){
        saleRepository.delete(sale);
    }
}
