package com.example.demo.Service;

import com.example.demo.DTO.ProductDTO;
import com.example.demo.Entity.Products;
import com.example.demo.Repository.ProductsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductsRepository productsRepository;


    @Autowired
    public ProductService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public ProductDTO saveProduct(ProductDTO productDTO) {
        Products product = new Products(
                productDTO.getProductName(),
                productDTO.getPrice(),
                productDTO.isStockAvailability(),
                productDTO.getProductDescription(),
                productDTO.getImageUrl()
        );
        Products savedProduct = productsRepository.save(product);
        return mapToDTO(savedProduct);
    }

    public List<ProductDTO> getAllProducts() {
        return productsRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public void updateProductPrice(int productId, double newPrice) {
        Optional<Products> optionalProduct = productsRepository.findById((long) productId); // Cast to long for JPA compatibility
        if (optionalProduct.isPresent()) {
            System.out.println("Product found: " + optionalProduct.get());
            Products product = optionalProduct.get();
            product.setPrice(newPrice);
            productsRepository.save(product); // Save the updated product
        } else {
            throw new IllegalArgumentException("Product with ID " + productId + " not found.");
        }
    }

    public void updateProductStockAvailability(Long productId, boolean stockAvailability) {
        Optional<Products> optionalProduct = productsRepository.findById(productId);

        if (optionalProduct.isPresent()) {
            Products product = optionalProduct.get();
            product.setStockAvailability(stockAvailability);
            productsRepository.save(product);
        } else {
            throw new IllegalArgumentException("Product not found with ID: " + productId);
        }
    }

    public void updateProductDetails(Long productId, double newPrice, boolean stockAvailability) {
        Optional<Products> optionalProduct = productsRepository.findById(productId);

        if (optionalProduct.isPresent()) {
            Products product = optionalProduct.get();
            product.setPrice(newPrice);
            product.setStockAvailability(stockAvailability);
            productsRepository.save(product);
        } else {
            throw new IllegalArgumentException("Product with ID " + productId + " not found.");
        }
    }


    public long getTotalProductCount() {
        return productsRepository.count();
    }

    @Transactional
    public void deleteProductById(Long productId) {
        Optional<Products> optionalProduct = productsRepository.findById((long) productId);
        if (optionalProduct.isPresent()) {
            productsRepository.delete(optionalProduct.get());
        } else {
            throw new IllegalArgumentException("Product with ID " + productId + " not found.");
        }
    }

    public ProductDTO getProductById(int productId) {
        Optional<Products> optionalProduct = productsRepository.findById((long) productId); // Cast to long for JPA compatibility
        if (optionalProduct.isPresent()) {
            return mapToDTO(optionalProduct.get());
        } else {
            throw new IllegalArgumentException("Product with ID " + productId + " not found.");
        }
    }

    // Helper method: Map entity to DTO
    private ProductDTO mapToDTO(Products product) {
        return new ProductDTO(
                (int) product.getProductId(),
                product.getProductName(),
                product.getDescription(),
                product.getPrice(),
                product.isStockAvailability(),
                product.getImageUrl()
        );
    }
}
