package com.frank.api.controller;

import com.frank.api.config.Config;
import com.frank.api.model.Brand;
import com.frank.api.service.BrandService;
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
public class BrandController extends RestBaseController {

    @Autowired
    private BrandService brandService;

    // Get All Brands
    @GetMapping("/brands")
    @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
    public List<Brand> getAllBrands() {
        return brandService.getAllBrands();
    }

    //Get all Brands - paginated
    @GetMapping("/brands/paginated")
    @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
    public Page<Brand> getPaginatedBrands(@RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage) {
        return brandService.getPaginatedBrands(page,perPage);
    }

    // Create a new Brand
    @PostMapping("/brands")
    public Brand createBrand(@Valid @RequestBody Brand brand) {
        return brandService.createBrand(brand);
    }

    // Get a Single Brand
    @GetMapping("/brands/{id}")
    public ResponseEntity<Brand> getBrandById(@PathVariable(value = "id") Long brandId) {
        Brand brand = brandService.getBrandById(brandId);
        if (brand == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(brand);
    }

    // Update a Brand
    @PutMapping("/brands/{id}")
    public ResponseEntity<Brand> updateBrand(@PathVariable(value = "id") Long brandId, @Valid @RequestBody Brand brandDetails) {
        Brand brand = brandService.getBrandById(brandId);
        if (brand == null) {
            return ResponseEntity.notFound().build();
        }

        brand.setName(brandDetails.getName());
        Brand updatedBrand = brandService.updateBrand(brand);
        return ResponseEntity.ok(updatedBrand);
    }

    // Delete a Brand
    @DeleteMapping("/brands/{id}")
    public ResponseEntity<Brand> deleteBrand(@PathVariable(value = "id") Long brandId) {
        Brand brand = brandService.getBrandById(brandId);
        if (brand == null) {
            return ResponseEntity.notFound().build();
        }

        brandService.deleteBrand(brand);
        return ResponseEntity.ok().build();
    }

}