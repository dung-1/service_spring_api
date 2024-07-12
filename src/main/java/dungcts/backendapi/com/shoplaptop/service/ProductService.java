package dungcts.backendapi.com.shoplaptop.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dungcts.backendapi.com.shoplaptop.common.ResourceNotFoundException;
import dungcts.backendapi.com.shoplaptop.dto.ProductDTO;
import dungcts.backendapi.com.shoplaptop.entity.Product;
import dungcts.backendapi.com.shoplaptop.responsitory.CategoryRepository;
import dungcts.backendapi.com.shoplaptop.responsitory.ProductRepository;
import dungcts.backendapi.com.shoplaptop.responsitory.UserRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    public ProductDTO addProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStockQuantity(productDTO.getStockQuantity());
        product.setImageUrl(productDTO.getImageUrl());
        product.setCategory(categoryRepository.findById(productDTO.getCategoryId()).orElse(null));
        product.setStatus(productDTO.getStatus());
        // product.setCreatedBy(userRepository.findById(productDTO.getCreatedBy()).orElse(null));
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        product = productRepository.save(product);
        return productRepository.findProductDTOById(product.getProductId());
    }

    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStockQuantity(productDTO.getStockQuantity());
        product.setImageUrl(productDTO.getImageUrl());
        product.setCategory(categoryRepository.findById(productDTO.getCategoryId()).orElse(null));
        product.setStatus(productDTO.getStatus());
        product.setUpdatedAt(LocalDateTime.now());
        product = productRepository.save(product);
        return productRepository.findProductDTOById(product.getProductId());
    }

    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        productRepository.delete(product);
    }
}
