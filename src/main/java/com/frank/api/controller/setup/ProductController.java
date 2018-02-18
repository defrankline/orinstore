package com.frank.api.controller.setup;

import com.frank.api.config.Config;
import com.frank.api.controller.RestBaseController;
import com.frank.api.model.setup.Product;
import com.frank.api.service.setup.ProductService;
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
public class ProductController extends RestBaseController {

    @Autowired
    private ProductService productService;

    // Get All Products
    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    //Get all Products - paginated
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SALES_PERSON')")
    @GetMapping("/products/paginated")
    public Page<Product> getPaginatedProducts(@RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage) {
        return productService.getPaginatedProducts(page,perPage);
    }

    // Create a new Product
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/products")
    public Page<Product> createProduct(@Valid @RequestBody Product product, @RequestParam("perPage") Integer perPage) {
        productService.createProduct(product);
        return productService.getPaginatedProducts(0,perPage);
    }

    // Get a Single Product
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable(value = "id") Long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(product);
    }

    // Update a Product
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/products/{id}")
    public Page<Product> updateProduct(@PathVariable(value = "id") Long id, @Valid @RequestBody Product productDetails,@RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage) {
        Product product = productService.getProductById(id);
        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());
        product.setProductCategory(productDetails.getProductCategory());
        product.setBrand(productDetails.getBrand());
        productService.updateProduct(product);
        return productService.getPaginatedProducts(page,perPage);
    }

    // Delete a Product
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/products/{id}")
    public Page<Product> deleteProduct(@PathVariable(value = "id") Long id,@RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage) {
        Product product = productService.getProductById(id);
        productService.deleteProduct(product);
        return productService.getPaginatedProducts(page,perPage);
    }

}