package com.frank.api.controller.sale;

import com.frank.api.config.Config;
import com.frank.api.controller.RestBaseController;
import com.frank.api.model.sale.Sale;
import com.frank.api.model.sale.SaleItem;
import com.frank.api.service.sale.SaleItemService;
import com.frank.api.service.sale.SaleService;
import com.frank.api.helper.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = Config.ORIGINS, maxAge = Config.MAX_AGE)
@RestController
@RequestMapping("/api")
public class SaleController extends RestBaseController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private SaleItemService saleItemService;

    // Get All Sales
    @GetMapping("/sales")
    public List<Sale> getAllSales() {
        return saleService.getAllSales();
    }

    //Get all Sales - paginated
    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('SALES_PERSON')")
    @GetMapping("/sales/paginated")
    public Page<Sale> getPaginatedSales(@RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage) {
        return saleService.getPaginatedSales(page,perPage);
    }

    // Create a new Sale
    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('SALES_PERSON') ")
    @PostMapping("/sales")
    public Page<Sale> createSale(@Valid @RequestBody Sale sale, @RequestParam("perPage") Integer perPage) {
        RandomString randomString = new RandomString();
        String receipt = randomString.randomString(16);
        sale.setReceipt(receipt);
        sale.setNetAmount(sale.getNetAmount());
        sale.setTax(sale.getTax());
        sale.setSaleDate(sale.getSaleDate());
        sale.setPaid(sale.getPaid());
        sale.setCustomer(sale.getCustomer());
        sale.setCreatedAt(sale.getCreatedAt());
        saleService.createSale(sale);
        return saleService.getPaginatedSales(0,perPage);
    }

    // Get a Single Sale
    @PreAuthorize("hasAuthority('MANAGER')or hasAuthority('SALES_PERSON')")
    @GetMapping("/sales/{id}")
    public ResponseEntity<Sale> getSaleById(@PathVariable(value = "id") Long id) {
        Sale sale = saleService.getSaleById(id);
        if (sale == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(sale);
    }

    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('SALES_PERSON')")
    @PutMapping("/sales/{id}")
    public Page<Sale> updateSale(@PathVariable(value = "id") Long id, @Valid @RequestBody Sale saleDetails,@RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage) {
        Sale sale = saleService.getSaleById(id);
        RandomString randomString = new RandomString();
        String receipt = randomString.randomString(16);
        sale.setReceipt(receipt);
        sale.setNetAmount(saleDetails.getNetAmount());
        sale.setTax(saleDetails.getTax());
        sale.setSaleDate(saleDetails.getSaleDate());
        sale.setPaid(saleDetails.getPaid());
        sale.setCustomer(saleDetails.getCustomer());
        saleService.updateSale(sale);
        return saleService.getPaginatedSales(page,perPage);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @DeleteMapping("/sales/{id}")
    public Page<Sale> deleteSale(@PathVariable(value = "id") Long id,@RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage) {
        Sale sale = saleService.getSaleById(id);
        saleService.deleteSale(sale);
        return saleService.getPaginatedSales(page,perPage);
    }
}