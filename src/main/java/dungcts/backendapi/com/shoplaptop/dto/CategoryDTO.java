package dungcts.backendapi.com.shoplaptop.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class CategoryDTO {
    private Long categoryId;
    private String name;
    private String description;
    private Long parentId;

    public CategoryDTO() {
    }

    public CategoryDTO(Long categoryId, String name, String description, Long parentId) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.parentId = parentId;
    }

}
