package org.example.demo.Controller;

import org.example.demo.Model.Category;
import org.example.demo.Service.CategoryMapper;
import org.example.demo.Service.CategoryRepository;
import org.example.demo.Service.ImageUtils;
import org.example.demo.dto.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
//האם צריך את זה?
//@CrossOrigin
public class CategoryController {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;
    private static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "\\images\\";

    @Autowired
    public CategoryController(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }
//V
    @GetMapping("/getCategory/{id}")
    public ResponseEntity<CategoryDTO> get(@PathVariable long id) throws IOException {
        Category category = categoryRepository.findById(id).get();
        if (category != null)
            return new ResponseEntity<>(categoryMapper.categoryToDTO(category), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
//V
    @GetMapping("/getAllCategories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        try {
            List<Category> categories=categoryRepository.findAll();
            return new ResponseEntity<>(categoryMapper.categoriesToDTO(categories), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
//V
    @PostMapping("/uploadCategory")
    public ResponseEntity<Category> uploadThoughtWithPicture(@RequestPart("image") MultipartFile file, Category category) {
        try {
            ImageUtils.uploadImage(file);
            category.setIconPath(file.getOriginalFilename());
            Category c = categoryRepository.save(category);
            return new ResponseEntity<>(c, HttpStatus.CREATED);
        } catch (IOException e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
