package net.kaaass.kmall.controller;

import net.kaaass.kmall.controller.request.ProductAddRequest;
import net.kaaass.kmall.dto.ProductDto;
import net.kaaass.kmall.exception.NotFoundException;
import net.kaaass.kmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@PreAuthorize("permitAll()")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ProductDto addProduct(@RequestBody ProductAddRequest productDto) {
        // TODO 输入校验
        return productService.addProduct(productDto).orElseThrow();
    }

    @GetMapping("/{id}/")
    public ProductDto getProductById(@PathVariable String id) throws NotFoundException {
        return productService.getById(id)
                .orElseThrow(() -> new NotFoundException("未找到此商品！"));
    }

    @GetMapping("/")
    public List<ProductDto> getAllProducts(Pageable pageable) {
        return productService.getAll(pageable);
    }
}
