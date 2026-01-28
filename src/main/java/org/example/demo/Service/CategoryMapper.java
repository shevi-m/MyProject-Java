package org.example.demo.Service;

import org.example.demo.Model.Category;
import org.example.demo.dto.CategoryDTO;
import org.mapstruct.Mapper;

import java.io.IOException;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {


    List<CategoryDTO> categoriesToDTO(List<Category> categories);

    default CategoryDTO categoryToDTO (Category category) throws IOException {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setCategoryName(category.getCategoryName());
        categoryDTO.setThoughtsList(category.getThoughtsList());        if(category.getIconPath()!=null)
        categoryDTO.setIcon(ImageUtils.getImage(category.getIconPath()));

        return categoryDTO;

    }
}
