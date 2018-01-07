package com.frank.api.controller;

import com.frank.api.Config;
import com.frank.api.model.Sale;
import com.frank.api.model.SaleItem;
import com.frank.api.service.SaleItemService;
import com.frank.api.service.SaleService;
import com.frank.api.utils.RandomString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = Config.ORIGINS, maxAge = Config.MAX_AGE)
@RestController
@RequestMapping("/api")
public class SaleController {

    @Autowired
    private SaleService saleService;

    private final Logger logger = LoggerFactory.getLogger(SaleController.class);

    // Get All Sales
    @GetMapping("/sales")
    public List<Sale> getAllSales() {
        return saleService.getAllSales();
    }

    //Get all sales - paginated
    @GetMapping("/sales/paginated")
    public Page<Sale> getPaginatedSales(@RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage) {
        return saleService.getPaginatedSale(page,perPage);
    }

    // Create a new Sale
    @PostMapping("/sales")
    public ResponseEntity createSale(@Valid @RequestBody Sale sale) {
        RandomString randomString = new RandomString();
        Double vat = Config.VAT;
        String receipt = randomString.randomString(16);
        sale.setReceipt(receipt);
        sale.setNetAmount(sale.getNetAmount());
        sale.setTax(sale.getTax());
        sale.setSaleDate(sale.getSaleDate());
        sale.setPaid(sale.getPaid());
        sale.setCustomer(sale.getCustomer());
        sale.setCreatedAt(sale.getCreatedAt());
        if(saleService.createSale(sale) == null){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok().body(saleService.createSale(sale));
    }

    // Get a Single Sale
    @GetMapping("/sales/{id}")
    public ResponseEntity<Sale> getSaleById(@PathVariable(value = "id") Long saleId) {
        Sale sale = saleService.getSaleById(saleId);
        if (sale == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(sale);
    }

    // Update a Sale
    @PutMapping("/sales/{id}")
    public ResponseEntity<Sale> updateSale(@PathVariable(value = "id") Long saleId, @Valid @RequestBody Sale saleDetails) {
        Sale sale = saleService.getSaleById(saleId);
        if (sale == null) {
            return ResponseEntity.notFound().build();
        }

        RandomString randomString = new RandomString();

        String receipt = randomString.randomString(16);
        sale.setReceipt(receipt);
        sale.setNetAmount(saleDetails.getNetAmount());
        sale.setTax(saleDetails.getTax());
        sale.setSaleDate(saleDetails.getSaleDate());
        sale.setPaid(saleDetails.getPaid());
        sale.setCustomer(saleDetails.getCustomer());

        Sale updatedSale = saleService.updateSale(sale);
        return ResponseEntity.ok(updatedSale);
    }

    // Delete a Sale
    @DeleteMapping("/sales/{id}")
    public ResponseEntity<Sale> deleteSale(@PathVariable(value = "id") Long saleId) {
        Sale sale = saleService.getSaleById(saleId);
        if (sale == null) {
            return ResponseEntity.notFound().build();
        }

        saleService.deleteSale(sale);
        return ResponseEntity.ok().build();
    }

    // Get SaleItems
    @GetMapping("/sales/items")
    public List<SaleItem> getSaleItems(@RequestParam("sale_id") Long saleId) {
        return saleService.getSaleItems(saleId);
    }
}