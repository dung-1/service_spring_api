package dungcts.backendapi.com.shoplaptop.responsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dungcts.backendapi.com.shoplaptop.dto.ProductDTO;
import dungcts.backendapi.com.shoplaptop.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT new dungcts.backendapi.com.shoplaptop.dto.ProductDTO(p.productId, p.name, p.description, p.price, p.stockQuantity, p.imageUrl, p.category.categoryId, p.status, p.createdBy.userId, p.createdAt, p.updatedAt) FROM Product p WHERE p.productId = :productId")
    ProductDTO findProductDTOById(@Param("productId") Long productId);

    List<Product> findAll();

}
