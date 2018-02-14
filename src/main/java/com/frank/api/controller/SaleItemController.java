package com.frank.api.controller;

import com.frank.api.config.Config;
import com.frank.api.model.SaleItem;
import com.frank.api.service.SaleItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = Config.ORIGINS, maxAge = Config.MAX_AGE)
@RestController
@RequestMapping("/api")
public class SaleItemController extends RestBaseController{

    @Autowired
    private SaleItemService saleItemService;

    // Get All SaleItems
    @GetMapping("/saleItems")
    public List<SaleItem> getAllSaleItems() {
        return saleItemService.getAllSaleItems();
    }

    //Get all saleItems - paginated
    @GetMapping("/saleItems/paginated")
    public Page<SaleItem> getPaginatedSaleItems(@RequestParam("page") Integer page,@RequestParam("perPage") Integer perPage) {
        return saleItemService.getPaginatedSaleItem(page,perPage);
    }

    // Create a new SaleItem
    @PostMapping("/saleItems")
    public SaleItem createSaleItem(@Valid @RequestBody SaleItem saleItem) {
        return saleItemService.createSaleItem(saleItem);
    }

    // Get a Single SaleItem
    @GetMapping("/saleItems/{id}")
    public ResponseEntity<SaleItem> getSaleItemById(@PathVariable(value = "id") Long saleItemId) {
        SaleItem saleItem = saleItemService.getSaleItemById(saleItemId);
        if (saleItem == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(saleItem);
    }

    // Update a SaleItem
    @PutMapping("/saleItems/{id}")
    public ResponseEntity<SaleItem> updateSaleItem(@PathVariable(value = "id") Long saleItemId, @Valid @RequestBody SaleItem saleItemDetails) {
        SaleItem saleItem = saleItemService.getSaleItemById(saleItemId);
        if (saleItem == null) {
            return ResponseEntity.notFound().build();
        }
        saleItem.setPrice(saleItemDetails.getPrice());
        saleItem.setQty(saleItemDetails.getQty());
        saleItem.setProduct(saleItemDetails.getProduct());
        saleItem.setSale(saleItemDetails.getSale());
        SaleItem updatedSaleItem = saleItemService.updateSaleItem(saleItem);
        return ResponseEntity.ok(updatedSaleItem);
    }

    // Delete a SaleItem
    @DeleteMapping("/saleItems/{id}")
    public ResponseEntity<SaleItem> deleteSaleItem(@PathVariable(value = "id") Long saleItemId) {
        SaleItem saleItem = saleItemService.getSaleItemById(saleItemId);
        if (saleItem == null) {
            return ResponseEntity.notFound().build();
        }

        saleItemService.deleteSaleItem(saleItem);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/sales/items")
    public HashMap<String, Object> items(@RequestParam("saleId") Long saleId) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("status", "0");
        response.put("items", saleItemService.getSaleItems(saleId));
        return response;
    }
}