package dungcts.backendapi.com.shoplaptop.responsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dungcts.backendapi.com.shoplaptop.dto.CategoryDTO;
import dungcts.backendapi.com.shoplaptop.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT new dungcts.backendapi.com.shoplaptop.dto.CategoryDTO(c.categoryId, c.name, c.description, c.parent.categoryId) FROM Category c WHERE c.categoryId = :categoryId")
    CategoryDTO findCategoryDTOById(@Param("categoryId") Long categoryId);
}
