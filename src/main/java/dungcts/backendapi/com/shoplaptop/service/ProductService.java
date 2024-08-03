package dungcts.backendapi.com.shoplaptop.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private FileStorageService fileStorageService;

    public ProductDTO addProduct(ProductDTO productDTO) throws IOException {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStockQuantity(productDTO.getStockQuantity());
        product.setCategory(categoryRepository.findById(productDTO.getCategoryId()).orElse(null));
        product.setStatus(productDTO.getStatus());
        product.setCreatedBy(userRepository.findById(productDTO.getCreatedBy()).orElse(null));
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        // Xử lý tải lên hình ảnh
        if (productDTO.getImageUrl() != null && !productDTO.getImageUrl().isEmpty()) {
            String imageUrl = fileStorageService.storeFile(productDTO.getImageUrl());
            product.setImageUrl(imageUrl);
        }

        product = productRepository.save(product);
        return toDTO(product);
    }

    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) throws IOException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStockQuantity(productDTO.getStockQuantity());
        product.setCategory(categoryRepository.findById(productDTO.getCategoryId()).orElse(null));
        product.setStatus(productDTO.getStatus());
        product.setUpdatedAt(LocalDateTime.now());

        // Xử lý cập nhật ảnh
        if (productDTO.getImageUrl() != null && !productDTO.getImageUrl().isEmpty()) {
            String imageUrlPath = fileStorageService.storeFile(productDTO.getImageUrl());
            product.setImageUrl(imageUrlPath);
        }

        product = productRepository.save(product);
        return toDTO(product);
    }

    public void deleteProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("Product not found");
        }
        productRepository.deleteById(productId);
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOs = products.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return productDTOs;
    }

    private ProductDTO toDTO(Product product) {
        String categoryName = (product.getCategory() != null) ? product.getCategory().getName() : "";
        Long categoryId = (product.getCategory() != null) ? product.getCategory().getCategoryId() : null;
        return new ProductDTO(
                product.getProductId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getImageUrl(),
                categoryName,
                categoryId,
                product.getStatus(),
                (product.getCreatedBy() != null) ? product.getCreatedBy().getUserId() : null,
                product.getCreatedAt(),
                product.getUpdatedAt());
    }
}
