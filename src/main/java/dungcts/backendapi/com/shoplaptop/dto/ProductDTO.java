package dungcts.backendapi.com.shoplaptop.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class ProductDTO {
    private Long productId;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;

    @Transient
    private MultipartFile imageUrl; // Sử dụng Transient để JPA không ánh xạ thuộc tính này

    private String imageUrlPath; // Đường dẫn của file ảnh trong database
    private Long categoryId;
    private String status;
    private Long createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ProductDTO() {
    }

    public ProductDTO(Long productId, String name, String description, BigDecimal price, int stockQuantity,
            String imageUrlPath, Long categoryId, String status, Long createdBy, LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.imageUrlPath = imageUrlPath;
        this.categoryId = categoryId;
        this.status = status;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
