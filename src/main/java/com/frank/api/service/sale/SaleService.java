package com.frank.api.service.sale;

import com.frank.api.model.pos.Sale;
import com.frank.api.repository.pos.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

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

    public Page<Sale> getPaginatedSales(Integer page, Integer perPage){
        return saleRepository.findAll(new PageRequest(page,perPage));
    }

    public void deleteSale(Sale sale){
        saleRepository.delete(sale);
    }
}
