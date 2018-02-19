package com.frank.api.controller.setup;

import com.frank.api.config.Config;
import com.frank.api.controller.RestBaseController;
import com.frank.api.model.setup.ProductCategory;
import com.frank.api.service.setup.ProductCategoryService;
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
public class ProductCategoryController extends RestBaseController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/product-categories")
    public List<ProductCategory> getAllProductCategories() {
        return productCategoryService.getAllProductCategories();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/product-categories/paginated")
    public Page<ProductCategory> getPaginatedProductCategories(@RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage) {
        return productCategoryService.getPaginatedProductCategories(page, perPage);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/product-categories")
    public Page<ProductCategory> createProductCategory(@Valid @RequestBody ProductCategory productCategory, @RequestParam("perPage") Integer perPage) {
        productCategoryService.createProductCategory(productCategory);
        return productCategoryService.getPaginatedProductCategories(0, perPage);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/product-categories/{id}")
    public ResponseEntity<ProductCategory> getProductCategoryById(@PathVariable(value = "id") Long id) {
        ProductCategory productCategory = productCategoryService.getProductCategoryById(id);
        if (productCategory == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(productCategory);
    }
    
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/product-categories/{id}")
    public Page<ProductCategory> updateProductCategory(@PathVariable(value = "id") Long id, @Valid @RequestBody ProductCategory productCategoryDetails, @RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage) {
        ProductCategory productCategory = productCategoryService.getProductCategoryById(id);
        productCategory.setName(productCategoryDetails.getName());
        productCategoryService.updateProductCategory(productCategory);
        return productCategoryService.getPaginatedProductCategories(page, perPage);
    }

    // Delete a ProductCategory
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/product-categories/{id}")
    public Page<ProductCategory> deleteProductCategory(@PathVariable(value = "id") Long id, @RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage) {
        ProductCategory productCategory = productCategoryService.getProductCategoryById(id);
        productCategoryService.deleteProductCategory(productCategory);
        return productCategoryService.getPaginatedProductCategories(page, perPage);
    }

}