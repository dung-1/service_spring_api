package dungcts.backendapi.com.shoplaptop.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dungcts.backendapi.com.shoplaptop.common.ResourceNotFoundException;
import dungcts.backendapi.com.shoplaptop.dto.CategoryDTO;
import dungcts.backendapi.com.shoplaptop.entity.Category;
import dungcts.backendapi.com.shoplaptop.responsitory.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        if (categoryDTO.getParentId() != null) {
            category.setParent(categoryRepository.findById(categoryDTO.getParentId()).orElse(null));
        }
        category = categoryRepository.save(category);
        return categoryRepository.findCategoryDTOById(category.getCategoryId());
    }

    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        if (categoryDTO.getParentId() != null) {
            category.setParent(categoryRepository.findById(categoryDTO.getParentId()).orElse(null));
        } else {
            category.setParent(null);
        }
        category = categoryRepository.save(category);
        return categoryRepository.findCategoryDTOById(category.getCategoryId());
    }

    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        categoryRepository.delete(category);
    }

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(category -> new CategoryDTO(
                        category.getCategoryId(),
                        category.getName(),
                        category.getDescription(),
                        category.getParent() != null ? category.getParent().getCategoryId() : null))
                .collect(Collectors.toList());
    }
}
