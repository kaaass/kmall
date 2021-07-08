package net.kaaass.kmall.controller;

import net.kaaass.kmall.constraints.Uuid;
import net.kaaass.kmall.controller.request.ProductTemplateRequest;
import net.kaaass.kmall.dto.ProductTemplateDto;
import net.kaaass.kmall.exception.NotFoundException;
import net.kaaass.kmall.service.ProductTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/template")
@PreAuthorize("hasRole('ADMIN')")
public class ProductTemplateController {

    @Autowired
    private ProductTemplateService service;

    @GetMapping("/")
    List<ProductTemplateDto> getAll(Pageable pageable) {
        return service.getAll(pageable);
    }

    @GetMapping("/{id}/")
    ProductTemplateDto get(@PathVariable String id) throws NotFoundException {
        return service.get(id);
    }

    @PutMapping("/")
    ProductTemplateDto addProductTemplate(@Validated @RequestBody ProductTemplateRequest request) {
        return service.add(request);
    }

    @PutMapping("/{id}/")
    ProductTemplateDto editProductTemplate(@PathVariable String id,
                                           @Validated @RequestBody ProductTemplateRequest request) throws NotFoundException {
        return service.edit(id, request);
    }

    @DeleteMapping("/{id}/")
    void removeProductTemplate(@PathVariable String id) {
        service.removeById(id);
    }
}
