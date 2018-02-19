package com.frank.api.controller.pos;

import com.frank.api.config.Config;
import com.frank.api.controller.RestBaseController;
import com.frank.api.model.pos.SaleItem;
import com.frank.api.service.sale.SaleItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

@CrossOrigin(origins = Config.ORIGINS, maxAge = Config.MAX_AGE)
@RestController
@RequestMapping("/api")
public class SaleItemController extends RestBaseController {

    @Autowired
    private SaleItemService saleItemService;
    
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER') or hasAuthority('SALES_PERSON')")
    @GetMapping("/sales/items")
    public HashMap<String, Object> items(@RequestParam("id") Long id) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("status", "0");
        response.put("items", saleItemService.getSaleItems(id));
        return response;
    }
    
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER') or hasAuthority('SALES_PERSON')")
    @PostMapping("/sale-items")
    public SaleItem createSaleItem(@Valid @RequestBody SaleItem saleItem) {
        return saleItemService.createSaleItem(saleItem);
    }

}