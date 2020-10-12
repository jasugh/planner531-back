package fi.jukka.planner531.controller;

import fi.jukka.planner531.dto.CategoryDto;
import fi.jukka.planner531.dto.ExerciseDto;
import fi.jukka.planner531.exception.BadRequestException;
import fi.jukka.planner531.exception.NotFoundException;
import fi.jukka.planner531.model.Category;
import fi.jukka.planner531.model.Exercise;
import fi.jukka.planner531.repository.CategoryRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryRepository categoryRepository;
    private static final ModelMapper modelMapper = new ModelMapper();

    private Throwable cause(String field) {
        return new Exception(field);
    }

    @Autowired
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;

    }

    @GetMapping("")
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public CategoryDto getCategoryById(@PathVariable Long id) {
        return convertToDTO(categoryRepository.getOne(id));

    }

    @PostMapping("")
    public CategoryDto saveCategory(@RequestBody CategoryDto categoryDto) {

        if (categoryDto.getName().isBlank()) {
            throw new BadRequestException("Category name can not be blank", cause("name"));
        }

        if (categoryRepository.findFirstByName(categoryDto.getName()) != null) {
            throw new BadRequestException("Category with that name already exist", cause("name"));
        }

        Category newCategory = new Category();
        newCategory.setName(categoryDto.getName());
        newCategory.setNotes(categoryDto.getNotes());
        categoryRepository.save(newCategory);

        return convertToDTO(newCategory);
    }

    @PutMapping("/{id}")
    public CategoryDto replaceCategory(@RequestBody Category category, @PathVariable Long id) {
        if (category.getName().isBlank()) {
            throw new BadRequestException("Category name can not be blank", cause("name"));
        }

        Category cat = categoryRepository.findFirstByName(category.getName());
        if (cat != null && !cat.getId().equals(id)) {
            throw new BadRequestException("Category with that name already exist", cause("name"));
        }

        return categoryRepository.findById(id)
                .map(c -> {
                    c.setName(category.getName());
                    c.setNotes(category.getNotes());

                    return convertToDTO(categoryRepository.save(c));
                })
                .orElseThrow(() -> new NotFoundException("Category not found with id " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found with id " + id));

        if (category.getExercises().size() > 0) {
            throw new BadRequestException("There are still exercises in this category, they must be removed first",
                    cause("exercises"));
        }
        categoryRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    private CategoryDto convertToDTO(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }
}
