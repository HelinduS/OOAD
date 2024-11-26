package com.example.demo.Controller;

import com.example.demo.DTO.ProductDTO;
import com.example.demo.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO createdProduct = productService.saveProduct(productDTO);
        return ResponseEntity.ok(createdProduct);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateProduct(@RequestParam Long productId, @RequestParam double price,@RequestParam boolean stockAvailability) {
        System.out.println("Received request to update productId: " + productId + " with price: " + price);
        try {
            productService.updateProductDetails(productId, price, stockAvailability);
            return ResponseEntity.ok("Product updated successfully.");
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    @GetMapping("/count")
    public ResponseEntity<Long> getProductCount() {
        long count = productService.getTotalProductCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/get")
    public ResponseEntity<ProductDTO> getProductById(@RequestParam("productId") int productId) {
        try {
            ProductDTO product = productService.getProductById(productId);
            return ResponseEntity.ok(product);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteProduct(@RequestParam("productId") Long productId) {
        try {
            productService.deleteProductById(productId);
            return ResponseEntity.ok("Product deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
