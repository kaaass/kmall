package net.kaaass.kmall.service;

import net.kaaass.kmall.controller.request.ProductTemplateRequest;
import net.kaaass.kmall.dto.ProductTemplateDto;
import net.kaaass.kmall.exception.NotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductTemplateService {
    List<ProductTemplateDto> getAll(Pageable pageable);

    ProductTemplateDto add(ProductTemplateRequest request);

    ProductTemplateDto edit(String id, ProductTemplateRequest request) throws NotFoundException;

    void removeById(String id);

    ProductTemplateDto get(String id) throws NotFoundException;

    void setForCategory(String id, String cid) throws NotFoundException;
}
