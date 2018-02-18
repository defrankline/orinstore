package com.frank.api.controller.setup;

import com.frank.api.config.Config;
import com.frank.api.controller.RestBaseController;
import com.frank.api.model.setup.Shop;
import com.frank.api.service.setup.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = Config.ORIGINS, maxAge = Config.MAX_AGE)
@RestController
@RequestMapping("/api")
public class ShopController extends RestBaseController {

    @Autowired
    private ShopService shopService;
    
    @GetMapping("/shops")
    public List<Shop> getAllShops() {
        return shopService.getAllShops();
    }
    
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/shops/paginated")
    public Page<Shop> getPaginatedShops(@RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage) {
        return shopService.getPaginatedShops(page,perPage);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/shops")
    public Page<Shop> createShop(@Valid @RequestBody Shop shop, @RequestParam("perPage") Integer perPage) {
        shopService.createShop(shop);
        return shopService.getPaginatedShops(0,perPage);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/shops/{id}")
    public ResponseEntity<Shop> getShopById(@PathVariable(value = "id") Long id) {
        Shop shop = shopService.getShopById(id);
        if (shop == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(shop);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/shops/{id}")
    public Page<Shop> updateShop(@PathVariable(value = "id") Long id, @Valid @RequestBody Shop shopDetails,@RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage) {
        Shop shop = shopService.getShopById(id);
        shop.setName(shopDetails.getName());
        shop.setCode(shopDetails.getCode());
        shop.setEmail(shopDetails.getEmail());
        shop.setMobile(shopDetails.getMobile());
        shop.setLandline(shopDetails.getLandline());
        shopService.updateShop(shop);
        return shopService.getPaginatedShops(page,perPage);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/shops/{id}")
    public Page<Shop> deleteShop(@PathVariable(value = "id") Long id,@RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage) {
        Shop shop = shopService.getShopById(id);
        shopService.deleteShop(shop);
        return shopService.getPaginatedShops(page,perPage);
    }

}