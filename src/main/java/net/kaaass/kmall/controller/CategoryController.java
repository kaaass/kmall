package net.kaaass.kmall.controller;

import net.kaaass.kmall.constraints.Uuid;
import net.kaaass.kmall.controller.request.CategoryAddRequest;
import net.kaaass.kmall.dao.entity.CategoryEntity;
import net.kaaass.kmall.dao.repository.CategoryRepository;
import net.kaaass.kmall.dto.CategoryDto;
import net.kaaass.kmall.exception.NotFoundException;
import net.kaaass.kmall.mapper.ProductMapper;
import net.kaaass.kmall.service.CategoryService;
import net.kaaass.kmall.service.ProductTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/category")
@PreAuthorize("permitAll()")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductTemplateService productTemplateService;

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryDto addCategory(@RequestBody CategoryAddRequest categoryDto) {
        // TODO 数据校验
        return categoryService.add(categoryDto).orElseThrow();
    }

    @GetMapping("/{id}/")
    public CategoryDto getCategoryById(@PathVariable String id) throws NotFoundException {
        return categoryService.getById(id);
    }

    @GetMapping("/")
    public List<CategoryDto> getAllProducts(Pageable pageable) {
        return categoryService.getAll(pageable);
    }

    @PostMapping("/{id}/template/")
    void setForCategory(@PathVariable String id,
                        @RequestParam(required = false) String tid) throws NotFoundException {
        productTemplateService.setForCategory(tid, id);
    }
}
