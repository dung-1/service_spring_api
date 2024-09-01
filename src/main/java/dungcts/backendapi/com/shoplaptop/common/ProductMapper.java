package dungcts.backendapi.com.shoplaptop.common;

import dungcts.backendapi.com.shoplaptop.dto.ProductDTO;
import dungcts.backendapi.com.shoplaptop.entity.Category;
import dungcts.backendapi.com.shoplaptop.entity.Product;
import dungcts.backendapi.com.shoplaptop.entity.UserAccount;

public class ProductMapper {
    public static ProductDTO toDto(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setProductId(product.getProductId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setCategoryId(product.getCategory().getCategoryId());
        dto.setStatus(product.getStatus());
        dto.setCreatedBy(product.getCreatedBy().getUserId());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdatedAt(product.getUpdatedAt());
        return dto;
    }

    public static Product toEntity(ProductDTO dto, Category category, UserAccount userAccount) {
        Product product = new Product();
        product.setProductId(dto.getProductId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());
        product.setCategory(category);
        product.setStatus(dto.getStatus());
        product.setCreatedBy(userAccount);
        product.setCreatedAt(dto.getCreatedAt());
        product.setUpdatedAt(dto.getUpdatedAt());
        return product;
    }
}
