package com.frank.api.controller;

import com.frank.api.Config;
import com.frank.api.model.Product;
import com.frank.api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = Config.ORIGINS, maxAge = Config.MAX_AGE)
@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * @param page
     * @param perPage
     * @return
     */
    public Page<Product> paginator(Integer page, Integer perPage) {
        return productService.getPaginatedProduct(page, perPage);
    }

    // Get All Products
    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    //Get all products - paginated
    @GetMapping("/products/paginated")
    public Page<Product> getPaginatedProducts(@RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage) {
        return this.paginator(page, perPage);
    }

    // Create a new Product
    @PostMapping("/products")
    public Product createProduct(@Valid @RequestBody Product product) {
        return productService.createProduct(product);
    }

    // Get a Single Product
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable(value = "id") Long productId) {
        Product product = productService.getProductById(productId);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(product);
    }

    // Update a Product
    @PutMapping("/products/{id}")
    public Page<Product> updateProduct(@PathVariable(value = "id") Long productId, @RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage, @Valid @RequestBody Product productDetails) {
        Product product = productService.getProductById(productId);
        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());
        product.setProductCategory(productDetails.getProductCategory());
        product.setBrand(productDetails.getBrand());
        productService.updateProduct(product);
        return this.paginator(page,perPage);
    }

    // Delete a Product
    @DeleteMapping("/products/{id}")
    public Page<Product> deleteProduct(@PathVariable(value = "id") Long productId,@RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage) {
        Product product = productService.getProductById(productId);
        productService.deleteProduct(product);
        return this.paginator(page,perPage);
    }

    @GetMapping("/products/search")
    public List<Product> search(@RequestParam("searchText") String searchText) {
        //search products
        return productService.searchProducts(searchText);
    }

}