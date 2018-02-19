package com.frank.api.controller.setup;

import com.frank.api.config.Config;
import com.frank.api.controller.RestBaseController;
import com.frank.api.model.setup.Brand;
import com.frank.api.service.setup.BrandService;
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
    public List<Brand> getAllBrands() {
        return brandService.getAllBrands();
    }

    //Get all Brands - paginated
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/brands/paginated")
    public Page<Brand> getPaginatedBrands(@RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage) {
        return brandService.getPaginatedBrands(page,perPage);
    }

    // Create a new Brand
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/brands")
    public Page<Brand> createBrand(@Valid @RequestBody Brand brand, @RequestParam("perPage") Integer perPage) {
        brandService.createBrand(brand);
        return brandService.getPaginatedBrands(0,perPage);
    }

    // Get a Single Brand
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/brands/{id}")
    public ResponseEntity<Brand> getBrandById(@PathVariable(value = "id") Long id) {
        Brand brand = brandService.getBrandById(id);
        if (brand == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(brand);
    }

    // Update a Brand
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/brands/{id}")
    public Page<Brand> updateBrand(@PathVariable(value = "id") Long id, @Valid @RequestBody Brand brandDetails,@RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage) {
        Brand brand = brandService.getBrandById(id);
        brand.setName(brandDetails.getName());
        brandService.updateBrand(brand);
        return brandService.getPaginatedBrands(page,perPage);
    }

    // Delete a Brand
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/brands/{id}")
    public Page<Brand> deleteBrand(@PathVariable(value = "id") Long id,@RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage) {
        Brand brand = brandService.getBrandById(id);
        brandService.deleteBrand(brand);
        return brandService.getPaginatedBrands(page,perPage);
    }

}