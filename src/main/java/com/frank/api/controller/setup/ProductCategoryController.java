package com.frank.api.controller.setup;

import com.frank.api.config.Config;
import com.frank.api.controller.RestBaseController;
import com.frank.api.model.setup.ProductCategory;
import com.frank.api.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = Config.ORIGINS, maxAge = Config.MAX_AGE)
@RestController
@RequestMapping("/api")
public class ProductCategoryController extends RestBaseController {

    @Autowired
    private ProductCategoryService productCategoryService;

    // Get All Product Categories
    @GetMapping("/product-categories")
    public List<ProductCategory> getAllProductCategories() {
        return productCategoryService.getAllProductCategories();
    }

    //Get all product-categories - paginated
    @GetMapping("/product-categories/paginated")
    public Page<ProductCategory> getPaginatedProductCategories(@RequestParam("page") Integer page,@RequestParam("perPage") Integer perPage) {
        return productCategoryService.getPaginatedProductCategories(page,perPage);
    }

    // Create a new ProductCategory
    @PostMapping("/product-categories")
    public ProductCategory createProductCategory(@Valid @RequestBody ProductCategory productCategory) {
        return productCategoryService.createProductCategory(productCategory);
    }

    // Get a Single ProductCategory
    @GetMapping("/product-categories/{id}")
    public ResponseEntity<ProductCategory> getProductCategoryById(@PathVariable(value = "id") Long productCategoryId) {
        ProductCategory productCategory = productCategoryService.getProductCategoryById(productCategoryId);
        if (productCategory == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(productCategory);
    }

    // Update a ProductCategory
    @PutMapping("/product-categories/{id}")
    public ResponseEntity<ProductCategory> updateProductCategory(@PathVariable(value = "id") Long productCategoryId, @Valid @RequestBody ProductCategory productCategoryDetails) {
        ProductCategory productCategory = productCategoryService.getProductCategoryById(productCategoryId);
        if (productCategory == null) {
            return ResponseEntity.notFound().build();
        }

        productCategory.setName(productCategoryDetails.getName());
        ProductCategory updatedProductCategory = productCategoryService.updateProductCategory(productCategory);
        return ResponseEntity.ok(updatedProductCategory);
    }

    // Delete a ProductCategory
    @DeleteMapping("/product-categories/{id}")
    public ResponseEntity<ProductCategory> deleteProductCategory(@PathVariable(value = "id") Long productCategoryId) {
        ProductCategory productCategory = productCategoryService.getProductCategoryById(productCategoryId);
        if (productCategory == null) {
            return ResponseEntity.notFound().build();
        }

        productCategoryService.deleteProductCategory(productCategory);
        return ResponseEntity.ok().build();
    }

}