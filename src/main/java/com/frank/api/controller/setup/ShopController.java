package com.frank.api.controller.setup;

import com.frank.api.config.Config;
import com.frank.api.controller.RestBaseController;
import com.frank.api.model.setup.Shop;
import com.frank.api.service.ShopService;
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

    // Get All Shops
    @GetMapping("/shops")
    public List<Shop> getAllShops() {
        return shopService.getAllShops();
    }

    //Get all Shops - paginated
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("/shops/paginated")
    public Page<Shop> getPaginatedShops(@RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage) {
        return shopService.getPaginatedShops(page,perPage);
    }

    // Create a new Shop
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/shops")
    public Shop createShop(@Valid @RequestBody Shop shop) {
        return shopService.createShop(shop);
    }

    // Get a Single Shop
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("/shops/{id}")
    public ResponseEntity<Shop> getShopById(@PathVariable(value = "id") Long shopId) {
        Shop shop = shopService.getShopById(shopId);
        if (shop == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(shop);
    }

    // Update a Shop
    @PutMapping("/shops/{id}")
    public ResponseEntity<Shop> updateShop(@PathVariable(value = "id") Long shopId, @Valid @RequestBody Shop shopDetails) {
        Shop shop = shopService.getShopById(shopId);
        if (shop == null) {
            return ResponseEntity.notFound().build();
        }

        shop.setName(shopDetails.getName());
        shop.setCode(shopDetails.getCode());
        shop.setEmail(shopDetails.getEmail());
        shop.setLandline(shopDetails.getLandline());
        shop.setMobile(shopDetails.getMobile());
        Shop updatedShop = shopService.updateShop(shop);
        return ResponseEntity.ok(updatedShop);
    }

    // Delete a Shop
    @DeleteMapping("/shops/{id}")
    public ResponseEntity<Shop> deleteShop(@PathVariable(value = "id") Long shopId) {
        Shop shop = shopService.getShopById(shopId);
        if (shop == null) {
            return ResponseEntity.notFound().build();
        }

        shopService.deleteShop(shop);
        return ResponseEntity.ok().build();
    }

}